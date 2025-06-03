import axios from 'axios'

const instance = axios.create({
  //baseURL: 'http://localhost:8080/api',//测试用
  // baseURL: '/api', // 🔥 注意这里是 http通过nginx代理调用
  baseURL: 'https://momosongqing.xyz:8443/api', //这里是升级通过域名调用https，处理了前端浏览器，http和https混淆报错
  timeout: 5000,
})

instance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}` // 传 token 给后端
  }
  return config
})

export default instance
