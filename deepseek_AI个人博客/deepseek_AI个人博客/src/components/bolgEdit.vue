<template>
  <div class="p-6 max-w-4xl mx-auto">
    <h2 class="text-2xl font-bold mb-4">发布博客</h2>

    <input v-model="title" class="border p-2 w-full mb-4" placeholder="请输入博客标题" />

    <textarea v-model="summary" class="border p-2 w-full mb-4" rows="2" placeholder="请输入摘要"></textarea>

    <div class="mb-4">
      <label class="block mb-2 font-medium">博客内容（支持富文本）：</label>
      <QuillEditor
        v-model:content="content"
        contentType="html"
        class="bg-white border rounded"
        style="min-height: 200px"
      />
    </div>

    <div class="mb-4">
      <label class="block mb-2 font-medium">封面图上传：</label>
      <input type="file" @change="handleCoverUpload" />
      <div v-if="coverUrl" class="mt-2 text-green-600">已上传：{{ coverUrl }}</div>
    </div>

    <div class="flex gap-4">
      <button @click="submitBlog" class="px-4 py-2 bg-blue-500 text-white rounded">发布博客</button>
      <button @click="clearForm" class="px-4 py-2 bg-red-500 text-white rounded">清空表单</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import axios from '@/api'
import { useAuthStore } from '@/stores/auth'
import { useBlogStore } from '@/stores/blogStore'

const authStore = useAuthStore()
const blogStore = useBlogStore()

const title = ref('')
const summary = ref('')
const content = ref('')
const coverUrl = ref('')

const STORAGE_KEY = 'draft-blog'

// 加载草稿
onMounted(() => {
  const saved = localStorage.getItem(STORAGE_KEY)
  if (saved) {
    try {
      const draft = JSON.parse(saved)
      title.value = draft.title || ''
      summary.value = draft.summary || ''
      content.value = draft.content || ''
      coverUrl.value = draft.coverUrl || ''
    } catch (e) {
      console.warn('读取本地草稿失败', e)
    }
  }
})

// 自动保存草稿
watch([title, summary, content, coverUrl], () => {
  const draft = {
    title: title.value,
    summary: summary.value,
    content: content.value,
    coverUrl: coverUrl.value
  }
  localStorage.setItem(STORAGE_KEY, JSON.stringify(draft))
})

// 上传封面
const handleCoverUpload = async (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0]
  if (!file) return

  const formData = new FormData()
  formData.append('file', file)
  formData.append('userId', authStore.userId || '')

  try {
    const res = await axios.post('/upload/blogcover', formData)
    coverUrl.value = res.data.url
  } catch (err) {
    console.error('上传封面失败', err)
    alert('封面上传失败')
  }
}

// 发布博客
const submitBlog = async () => {
  if (!title.value || !content.value) {
    alert('标题和内容不能为空')
    return
  }

  const blogData = {
    userId: authStore.userId,
    title: title.value,
    summary: summary.value,
    content: content.value,
    coverImage: coverUrl.value,
    isDraft: false
  }

  try {
    await axios.post('/blogs', blogData)
    alert('发布成功')
    blogStore.triggerRefresh()
    clearForm()
  } catch (err) {
    console.error('发布失败', err)
    alert('发布失败，请检查网络或稍后重试')
  }
}

// 清空表单 + 清除本地草稿
const clearForm = () => {
  title.value = ''
  summary.value = ''
  content.value = ''
  coverUrl.value = ''
  localStorage.removeItem(STORAGE_KEY)
}
</script>

<style scoped>
.p-6 {
  background-color: #ffffff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border-radius: 12px;
  margin-top: 2rem;
}
h2 {
  font-size: 1.875rem;
  font-weight: 700;
  color: #1f2937;
  text-align: center;
  margin-bottom: 1.5rem;
}
input,
textarea {
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  padding: 0.75rem 1rem;
  font-size: 1rem;
  width: 100%;
  transition: border-color 0.2s, box-shadow 0.2s;
}
input:focus,
textarea:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.3);
}
.ql-editor {
  min-height: 200px;
  padding: 1rem;
  font-size: 1rem;
  line-height: 1.75;
  color: #374151;
}
.ql-container {
  border-radius: 0.5rem;
}
.text-green-600 {
  word-break: break-all;
  font-size: 0.875rem;
}
button {
  margin-top: 1.5rem;
  background-color: #3b82f6;
  padding: 0.6rem 1.5rem;
  font-size: 1rem;
  font-weight: 500;
  border-radius: 0.5rem;
  transition: all 0.2s;
}
button:hover {
  background-color: #2563eb;
  transform: translateY(-1px);
  box-shadow: 0 4px 10px rgba(59, 130, 246, 0.3);
}
.bg-red-500 {
  background-color: #ef4444;
}
.bg-red-500:hover {
  background-color: #dc2626;
}
</style>
