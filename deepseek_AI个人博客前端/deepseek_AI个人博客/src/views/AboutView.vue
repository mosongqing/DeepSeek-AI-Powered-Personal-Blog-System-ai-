<template>
  <div class="container">
    <h2>🧠 满血deepseekAI ：</h2>

    <!-- 未登录提示 -->
    <div v-if="!userId" class="login-overlay">
      <div class="login-modal">
        <p>⚠️ 请先登录后再使用聊天功能。</p>
      </div>
    </div>

    <!-- 聊天展示区域 -->
    <div class="chat-box" ref="chatBox" v-if="userId">
      <div v-for="(msg, index) in visibleMessages" :key="index" class="message">
        <div :class="['bubble', msg.role]">
          <strong>{{ msg.role === 'user' ? '你' : 'AI' }}：</strong>
          <div v-html="renderMarkdown(msg.content)"></div>
        </div>
      </div>

      <!-- 当前流式输出 -->
      <div v-if="output !== ''" class="message">
        <div class="bubble assistant">
          <strong>AI：</strong>
          <div v-html="renderMarkdown(output)"></div>
        </div>
      </div>
    </div>

    <!-- 清空历史 -->
    <button v-if="userId" class="clear-btn" @click="clearHistory">🗑 清空聊天记录</button>

    <!-- 输入框 -->
    <textarea
      v-if="userId"
      v-model="userInput"
      placeholder="请输入你的问题..."
      class="input-area"
      rows="3"
    ></textarea>

    <button v-if="userId" @click="sendRequest" class="send-btn">发送请求</button>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, nextTick, onMounted, watch } from 'vue'
import { marked } from 'marked'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const userId = authStore.userId

const userInput = ref('')
const output = ref('')
const chatBox = ref<HTMLElement | null>(null)
let fullContent = ''
const STORAGE_KEY = 'chat-history'

const messages = ref([{ role: 'system', content: '你是一个中文 AI 助手' }])

const visibleMessages = computed(() => messages.value.filter((msg) => msg.role !== 'system'))

const renderMarkdown = (text: string) => marked.parse(text)

const scrollToBottom = () => {
  nextTick(() => {
    chatBox.value?.scrollTo({ top: chatBox.value.scrollHeight, behavior: 'smooth' })
  })
}

onMounted(() => {
  const saved = localStorage.getItem(STORAGE_KEY)
  if (saved) {
    try {
      const parsed = JSON.parse(saved)
      messages.value.push(...parsed)
    } catch (e) {
      console.warn('本地记录解析失败', e)
    }
  }
})

watch(
  messages,
  () => {
    const save = messages.value.filter((m) => m.role !== 'system')
    localStorage.setItem(STORAGE_KEY, JSON.stringify(save))
  },
  { deep: true },
)

const clearHistory = () => {
  messages.value = [{ role: 'system', content: '你是一个中文 AI 助手' }]
  localStorage.removeItem(STORAGE_KEY)
  output.value = ''
}

const sendRequest = () => {
  const question = userInput.value.trim()
  if (!question) {
    output.value = '❗请输入内容再发送'
    return
  }

  messages.value.push({ role: 'user', content: question })
  userInput.value = ''
  output.value = '请求中，请稍候...\n'
  fullContent = ''
  scrollToBottom()

  fetch('https://momosongqing.xyz:8443/api/deepseek/chat', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      model: 'deepseek-chat',
      messages: messages.value,
    }),
  })
    .then((res) => {
      const reader = res.body?.getReader()
      const decoder = new TextDecoder('utf-8')

      function readStream() {
        if (!reader) return
        reader.read().then(({ done, value }) => {
          if (done) {
            if (fullContent.trim()) {
              messages.value.push({
                role: 'assistant',
                content: fullContent,
              })
              output.value = ''
            }
            scrollToBottom()
            return
          }

          const chunk = decoder.decode(value, { stream: true })
          const lines = chunk.split('\n')

          for (const line of lines) {
            if (line.startsWith('data:')) {
              const jsonStr = line.replace(/^data:\s*/, '').trim()
              if (jsonStr === '[DONE]') {
                messages.value.push({
                  role: 'assistant',
                  content: fullContent,
                })
                output.value = ''
                scrollToBottom()
                return
              }

              try {
                const parsed = JSON.parse(jsonStr)
                const delta = parsed.choices?.[0]?.delta?.content
                if (delta) {
                  fullContent += delta
                  output.value = fullContent
                  scrollToBottom()
                }
              } catch (e) {
                console.warn('JSON解析失败：', e)
              }
            }
          }

          readStream()
        })
      }

      readStream()
    })
    .catch((err) => {
      output.value = '❌ 请求失败：' + err.message
    })
}
</script>

<style scoped>
.container {
  font-family: Arial, sans-serif;
  padding: 30px;
  background: #f2f2f2;
  max-width: 800px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  height: 95vh;
}

h2 {
  color: #333;
  margin-bottom: 10px;
}

.chat-box {
  flex: 1;
  height: 85vh;
  overflow-y: auto;
  background: #fff;
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-bottom: 10px;
}

.message {
  margin-bottom: 10px;
}

.bubble {
  padding: 10px 14px;
  border-radius: 10px;
  max-width: 80%;
  word-break: break-word;
}

.user {
  background-color: #4f46e5;
  color: white;
  align-self: flex-end;
  margin-left: auto;
}

.assistant {
  background-color: #e5e7eb;
  color: #111;
  align-self: flex-start;
  margin-right: auto;
}

.input-area {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  font-size: 15px;
  border-radius: 6px;
  border: 1px solid #ccc;
  resize: vertical;
}

.send-btn {
  padding: 8px 16px;
  font-size: 15px;
  background-color: #4f46e5;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.send-btn:hover {
  background-color: #3730a3;
}

.clear-btn {
  background-color: #ef4444;
  color: white;
  border: none;
  padding: 6px 12px;
  margin-bottom: 12px;
  border-radius: 4px;
  cursor: pointer;
}
.clear-btn:hover {
  background-color: #b91c1c;
}

/* 登录弹窗 */
.login-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 50;
}
.login-modal {
  background: white;
  padding: 20px 30px;
  border-radius: 10px;
  font-size: 16px;
  font-weight: 500;
  color: #111827;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

.bubble :deep(h1),
.bubble :deep(h2),
.bubble :deep(h3) {
  margin: 0.6em 0 0.3em;
  font-weight: bold;
}
.bubble :deep(p) {
  margin: 0.4em 0;
}
.bubble :deep(ul) {
  padding-left: 1.4em;
  margin: 0.4em 0;
}
.bubble :deep(li) {
  margin-bottom: 0.2em;
}
</style>
