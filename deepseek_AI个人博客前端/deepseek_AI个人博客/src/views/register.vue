<template>
  <div class="register">
    <h2>注册</h2>
    <form @submit.prevent="handleRegister">
      <input v-model="username" placeholder="用户名" />
      <input v-model="password" type="password" placeholder="密码" />
      <button type="submit">注册</button>
    </form>
    <p>{{ msg }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from '@/api'
import { isAxiosError } from 'axios'

const username = ref('')
const password = ref('')
const msg = ref('')
const router = useRouter()

interface RegisterResponse {
  msg?: string
}

const handleRegister = async () => {
  try {
    const res = await axios.post<RegisterResponse>('/register', {
      username: username.value,
      password: password.value
    })

    msg.value = res.data.msg || '注册成功'
    setTimeout(() => {
      router.push('/login')
    }, 1000)
  } catch (error) {
    if (isAxiosError(error)) { // ✅ 使用导入的方法判断
      msg.value = error.response?.data?.msg || '注册失败'
    } else {
      msg.value = '网络错误或未知错误'
    }
  }
}
</script>


<style scoped>
.register {
  max-width: 300px;
  margin: 100px auto;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>
