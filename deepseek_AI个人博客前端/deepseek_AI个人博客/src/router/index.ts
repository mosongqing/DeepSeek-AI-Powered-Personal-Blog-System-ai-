import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '@/views/HomeView.vue'
import TechView from '@/views/TechView.vue'
import LifeView from '@/views/LifeView.vue'
import AboutView from '@/views/AboutView.vue'
import LoginView from '@/views/LoginView.vue'
import ceshi from '@/views/TestPageView.vue'
import Register from '@/views/register.vue'
import UserHomeView from '@/views/UserHomeView.vue' // ✅ 用户主页



const routes = [
  { path: '/', name: 'home', component: HomeView },
  { path: '/tech', name: 'tech', component: TechView },
  { path: '/life', name: 'life', component: LifeView },
  { path: '/about', name: 'about', component: AboutView },
  { path: '/login', name: 'login', component: LoginView },
  {path:  '/ceshi', name:'teach', component: ceshi},
  { path: '/register', component: Register },
  { path: '/user', component: UserHomeView } // ✅ 用户主页

]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
