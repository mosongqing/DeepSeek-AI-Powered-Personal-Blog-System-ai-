<template>
  <div class="px-4 py-8 max-w-7xl mx-auto">
    <h2 class="text-3xl font-bold mb-10 text-center text-gray-800">我的博客列表</h2>

    <!-- 多列瀑布流容器 -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
        v-for="blog in blogs"
        :key="blog.id"
        class="p-6 border border-gray-200 rounded-lg shadow-sm bg-white hover:shadow-md transition-all"
      >
        <h3 class="text-xl font-semibold text-gray-900 mb-2">
          {{ blog.title }}
          <span v-if="blog.isDraft" class="text-sm text-yellow-500 ml-2 font-normal">[草稿]</span>
        </h3>

        <p class="text-sm text-gray-400 mb-3">
          --发布时间：{{ formatDate(blog.createTime) }}
        </p>

        <img
          v-if="blog.coverImage"
          :src="blog.coverImage"
          alt="封面图"
          class="blog-cover"
        />

        <p class="text-base text-gray-700 mb-3">{{ blog.summary }}</p>

        <div class="prose max-w-none prose-sm prose-img:rounded-lg prose-a:text-blue-600 prose-a:underline" v-html="blog.content"></div>

        <div class="mt-4 text-sm text-gray-500 flex items-center gap-4">
          <span>👍 {{ blog.likes || 0 }}</span>
          <span>💬 {{ blog.comments || 0 }}</span>
          <span>👁️ {{ blog.views }}</span>
        </div>
      </div>
    </div>

    <!-- 加载更多 -->
    <div v-if="hasMore" class="text-center mt-12">
      <button @click="loadMore" class="px-6 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition">加载更多</button>
    </div>
    <div v-else class="text-center text-gray-400 mt-6">没有更多了~</div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from '@/api'
import { useAuthStore } from '@/stores/auth'

interface Blog {
  id: number
  userId: number
  title: string
  summary: string
  content: string
  coverImage: string | null
  views: number
  isDraft: boolean
  createTime: string
  updateTime: string
  likes?: number
  comments?: number
}

const blogs = ref<Blog[]>([])
const page = ref(0)
const size = 5
const hasMore = ref(true)
const authStore = useAuthStore()

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleString()
}

const loadMore = async () => {
  try {
    const res = await axios.get(`/blogs/user/${authStore.userId}/page`, {
      params: { page: page.value, size }
    })
    if (res.data.content.length > 0) {
      blogs.value.push(...res.data.content)
      page.value++
    } else {
      hasMore.value = false
    }
  } catch (err) {
    console.error('加载失败', err)
  }
}

onMounted(() => {
  loadMore()
})
</script>

<style scoped>
.grid > div {
  background: #fff;
  border-radius: 12px;
  padding: 1.25rem;
  transition: all 0.3s ease;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.grid > div:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

h3.text-xl {
  font-size: 20px;
  font-weight: 700;
  position: relative;
  padding-left: 6px;
  color: #1f2937;
}

h3.text-xl::before {
  content: '';
  position: absolute;
  left: 0;
  top: 6px;
  width: 4px;
  height: 80%;
  background: linear-gradient(to bottom, #60a5fa, #3b82f6);
  border-radius: 2px;
}

p.text-sm {
  color: #6b7280;
  font-size: 13px;
  margin-bottom: 0.5rem;
  padding-left: 6px;
}

.blog-cover {
  max-width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
  margin-bottom: 1rem;
}

.prose {
  font-size: 14.5px;
  color: #4b5563;
  line-height: 1.7;
  max-width: 100%;
  word-break: break-word;
}

.prose p {
  margin-bottom: 0.75rem;
}

p.text-base {
  color: #374151;
  line-height: 1.6;
}

div.mt-4 {
  padding-left: 6px;
  display: flex;
  gap: 12px;
  font-size: 13px;
  color: #6b7280;
}

button {
  background: linear-gradient(to right, #3b82f6, #2563eb);
  color: white;
  font-weight: 600;
  padding: 0.6rem 1.8rem;
  border-radius: 9999px;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.2);
  transition: transform 0.2s ease;
}

button:hover {
  transform: scale(1.05);
}
</style>
