<template>
  <div class="login">
    <h2>登录</h2>
    <form @submit.prevent="handleLogin">
      <input v-model="username" placeholder="用户名" />
      <input v-model="password" type="password" placeholder="密码" />
      <button type="submit">登录</button>
    </form>
    <p v-if="error">{{ error }}</p>
    <router-link to="/register">没有账号？去注册</router-link>

    <!-- ✅ 显示 token（从 pinia 中） -->
    <p v-if="authStore.token">当前 Token：{{ authStore.token }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from '@/api'
import { isAxiosError } from 'axios'
import { useAuthStore } from '@/stores/auth'

const username = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()
const authStore = useAuthStore()

const handleLogin = async () => {
  try {
    const res = await axios.post<{ token: string; userId: number }>('/login', {
      username: username.value,
      password: password.value,
    })
    authStore.setAuth(res.data.token, res.data.userId)  // ✅ 设置 token + userId
    router.push('/user')
  } catch (err: unknown) {
    if (isAxiosError(err)) {
      error.value = err.response?.data?.msg || '账号或密码错误'
    } else {
      error.value = '网络异常或未知错误'
    }
  }
}
</script>

<style scoped>
.login {
  max-width: 300px;
  margin: 100px auto;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>
