// import { defineStore } from 'pinia'

// export const useAuthStore = defineStore('auth', {
//   state: () => ({
//     token: localStorage.getItem('token'),
//   }),
//   actions: {
//     setToken(token: string) {
//       this.token = token
//       localStorage.setItem('token', token)
//     },
//     clearToken() {
//       this.token = null
//       localStorage.removeItem('token')
//     },
//   },
// })
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token'),
    userId: localStorage.getItem('userId')
  }),
  actions: {
    setAuth(token: string, userId: number) {
      this.token = token
      this.userId = userId.toString()
      localStorage.setItem('token', token)
      localStorage.setItem('userId', userId.toString())
    },
    clearAuth() {
      this.token = null
      this.userId = null
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
    }
  }
})


