<template>
  <div class="user-home">
    <h2>欢迎回来！</h2>
    <p v-if="authStore.token">当前 Token：{{ authStore.token }}</p>
    <p v-if="authStore.token">当前 userid：{{ authStore.userId }}</p>

    <button @click="handleLogout">退出登录</button>
  </div>

  <BolgEdit />

  <div class="upload">
    <h2>上传文件</h2>
    <input type="file" @change="handleFileChange" />
    <select v-model="fileType">
      <option value="image">图片</option>
      <option value="video">视频</option>
      <option value="blog">博客文档</option>
      <option value="zip">压缩包</option>
    </select>
    <button @click="uploadFile">上传</button>
    <p v-if="message">{{ message }}</p>
  </div>

  <div class="file-view">
    <h2>我的文件</h2>
    <div class="tabs">
      <button @click="loadFiles('image')">图片</button>
      <button @click="loadFiles('video')">视频</button>
      <button @click="loadFiles('blog')">博客</button>
      <button @click="loadFiles('zip')">压缩包</button>
    </div>

    <div v-if="fileType === 'image'" class="image-list">
      <div v-for="file in files" :key="file.url" class="file-card">
        <img :src="getUrl(file.url)" :alt="file.name" width="200" />
        <p>{{ file.name }}</p>
        <a :href="getUrl(file.url)" download>下载</a>
      </div>
    </div>

    <div v-else-if="fileType === 'video'" class="video-list">
      <div v-for="file in files" :key="file.url" class="file-card">
        <video :src="getUrl(file.url)" controls width="400"></video>
        <p>{{ file.name }}</p>
        <a :href="getUrl(file.url)" download>下载</a>
      </div>
    </div>

    <ul v-else>
      <li v-for="file in files" :key="file.url">
        <a :href="getUrl(file.url)" target="_blank">{{ file.name }}</a>
        <a :href="getUrl(file.url)" download style="margin-left: 10px;">下载</a>
      </li>
    </ul>
  </div>
</template>


<script setup lang="ts">
import BolgEdit from '@/components/bolgEdit.vue'

import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import axios from '@/api'

const authStore = useAuthStore()
const router = useRouter()

const files = ref<{ name: string; url: string }[]>([])
const selectedFile = ref<File | null>(null)
const fileType = ref('image')
const message = ref('')
const uploadUrl = ref('')

const handleLogout = () => {
  authStore.clearAuth()
  router.push('/login')
}

const handleFileChange = (e: Event) => {
  const target = e.target as HTMLInputElement
  if (target.files && target.files.length > 0) {
    selectedFile.value = target.files[0]
  }
}

const uploadFile = async () => {
  if (!selectedFile.value) {
    message.value = '请先选择文件'
    return
  }

  const formData = new FormData()
  formData.append('file', selectedFile.value)
  formData.append('userId', authStore.userId || '')
  formData.append('fileType', fileType.value)

  try {
    const res = await axios.post('/upload/userfile', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
    message.value = res.data
    uploadUrl.value = '' // 上传成功后不立即预览
    loadFiles(fileType.value) // 自动刷新列表
  } catch (error) {
    console.error('上传失败', error)
    message.value = '上传失败，请检查网络或服务器'
  }
}

const loadFiles = async (type: string) => {
  fileType.value = type
  const res = await axios.get('/user/files', {
    params: { userId: authStore.userId, fileType: type }
  })
  files.value = res.data
}

const getUrl = (url: string) => {
  return url.startsWith('http') ? url : '/' + url
}
</script>

<style scoped>
.user-home {
  margin: 100px auto;
  max-width: 500px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}
.upload-section {
  margin-top: 20px;
}
.upload-section input[type="file"] {
  margin-bottom: 10px;
}
.upload {
  max-width: 400px;
  margin: 50px auto;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}
.image-list,
.video-list {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}
.file-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
}
</style>
