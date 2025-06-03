

<template>
  <div class="app">
    <header class="navbar" :class="{ 'navbar-hidden': isHidden }">
      <div class="logo">🌟HELPY</div>
      <button class="menu-toggle" @click="toggleMenu">☰</button>

      <!-- 桌面菜单 -->
      <nav class="menu desktop-menu">
        <router-link to="/">🏠 首页</router-link>
        <router-link to="/tech">💻 技术</router-link>
        <router-link to="/life">🌿 生活</router-link>
        <router-link to="/about">📖 AI助手</router-link>
      </nav>

      <div class="right desktop-menu">
        <router-link
          v-if="authStore.token"
          to="/user"
          class="user-link"
          exact-active-class="router-link-exact-active"
        >
          <img  src="https://momosongqing.xyz:8443/image/b38124aa-7d80-4eab-a179-ec1bd4d0a4f2_MVIMG_20250601_195701.jpg" class="avatar" /> 我的主页
        </router-link>
        <router-link
          v-else
          to="/login"
          class="user-link"
          exact-active-class="router-link-exact-active"
        >
          🔐 登录
        </router-link>
      </div>
    </header>

    <!-- 移动端抽屉菜单 -->
    <transition name="drawer">
      <div class="mobile-drawer" v-if="isMenuOpen" @click.self="closeMenu" ref="drawerRef">
        <div class="drawer-content">
          <router-link to="/" @click="closeMenu">🏠 首页</router-link>
          <router-link to="/tech" @click="closeMenu">💻 技术</router-link>
          <router-link to="/life" @click="closeMenu">🌿 生活</router-link>
          <router-link to="/about" @click="closeMenu">📖 AI助手</router-link>
          <router-link
            v-if="authStore.token"
            to="/user"
            class="user-link"
            @click="closeMenu"
          >
            <img class="avatar"  src="https://momosongqing.xyz:8443/image/b38124aa-7d80-4eab-a179-ec1bd4d0a4f2_MVIMG_20250601_195701.jpg"  /> 我的主页
          </router-link>
          <router-link
            v-else
            to="/login"
            class="user-link"
            @click="closeMenu"
          >
            🔐 登录
          </router-link>
        </div>
      </div>
    </transition>

    <main class="main-content">
      <router-view></router-view>
    </main>

    <footer class="footer">
      <div class="footer-links">
        <a href="#">关于我们</a> | <a href="#">联系我们</a> |
        <a href="#">隐私政策</a> | <a href="#">用户协议</a>
      </div>
      <div class="footer-beian">
        <a href="https://beian.miit.gov.cn/" target="_blank" rel="noopener">
          桂ICP备2023017246号-2
        </a>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'
import { ref, onMounted, onUnmounted } from 'vue'

const authStore = useAuthStore()
const isHidden = ref(false)
const isMenuOpen = ref(false)

let lastScrollTop = 0

const handleScroll = () => {
  const currentScrollTop = window.scrollY
  isHidden.value = currentScrollTop > lastScrollTop && currentScrollTop > 50
  lastScrollTop = currentScrollTop <= 0 ? 0 : currentScrollTop
}

const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value
}

const closeMenu = () => {
  isMenuOpen.value = false
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})

</script>

<style scoped>
.app {
  font-family: Arial, sans-serif;
  width: 100%;
  max-width: 100%;
  margin: 0;
  padding: 0;
  color: aquamarine;
  box-sizing: border-box;
  overflow-x: hidden;
}

.main-content {
  width: 100%;
  max-width: 100%;
}

.navbar {
  position: fixed;

  top: 0;
  width: 100%;
  padding: 8px 12px;
  z-index: 1000;
  background-color: rgba(255, 255, 255, 0.28);
  backdrop-filter: blur(10px);
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
  transition: top 0.3s ease;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  overflow-x: auto;
  white-space: nowrap;
  gap: 16px;
}

.navbar-hidden {
  top: -60px;
}

.logo {
  font-weight: bold;
  font-size: 20px;
  color: aquamarine;
  white-space: nowrap;
  flex-shrink: 0;
}

.menu {
  display: flex;
  gap: 12px;
  flex-shrink: 0;
}

.menu a {
  color: #111;
  font-weight: 500;
  position: relative;
  padding-bottom: 4px;
  transition: color 0.3s ease;
  text-decoration: none;
  white-space: nowrap;
  flex-shrink: 0;
}

.menu a::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: 0;
  height: 2px;
  width: 100%;
  background-color: #4f46e5;
  transform: scaleX(0);
  transform-origin: right;
  transition: transform 0.3s ease;
}

.menu a:hover::after {
  transform: scaleX(1);
  transform-origin: left;
}

.menu .router-link-exact-active::after {
  transform: scaleX(1);
  transform-origin: left;
}

.menu .router-link-exact-active {
  color: #4f46e5;
}

.right {
  margin-left: auto;
  flex-shrink: 0;
}

.right a {
  color: #111;
  font-weight: 500;
  text-decoration: none;
  transition: color 0.3s ease;
  white-space: nowrap;
  flex-shrink: 0;
}

.right a:hover {
  color: #2563eb;
}

.user-link {
  position: relative;
  font-weight: 500;
  color: #111;
  padding-bottom: 4px;
  text-decoration: none;
  transition: color 0.3s ease;
  display: flex;
  align-items: center;
}

.user-link::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: 0;
  height: 2px;
  width: 100%;
  background-color: #4f46e5;
  transform: scaleX(0);
  transform-origin: right;
  transition: transform 0.3s ease;
}

.user-link:hover::after {
  transform: scaleX(1);
  transform-origin: left;
}

.user-link.router-link-exact-active {
  color: #4f46e5;
}

.user-link.router-link-exact-active::after {
  transform: scaleX(1);
  transform-origin: left;
}

.avatar {
  width: 30px;         /* 或 24px 更小一点 */
  height: 30px;
  border-radius: 50%;
  object-fit: cover;   /* 防止图片变形 */
  margin-right: 6px;
}




.menu-toggle {
  display: none;
  background: none;
  border: none;
  font-size: 24px;
  color: #111;
  cursor: pointer;
  margin-left: auto;
}

.mobile-drawer {
  /* 现有样式 */
  position: fixed;
  top: 0;
  right: 0;
  height: 100%;
  width: 30vw;
  max-width: 320px;
  background-image: url('/src/static/castorice-honkai-7680x4320-22114.jpg');
  background-size: cover;
  background-position: center;
  box-shadow: -2px 0 8px rgba(0, 0, 0, 0.2);
  z-index: 999;

  /* ✅ 添加圆角样式 */
  border-top-left-radius: 16px;
  border-bottom-left-radius: 16px;
  overflow: hidden; /* 防止子元素溢出破坏圆角 */
}


.drawer-content {
  margin-top: 8vw;
  display: flex;
  flex-direction: column;
  padding: 24px;
  backdrop-filter: blur(1px);
  height: 100%;
}

.drawer-content a {
  padding: 12px 0;
  color: #8d7b7b;
  font-weight: 500;
  text-decoration: none;
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
  display: flex;
  align-items: center;
}

.drawer-enter-active, .drawer-leave-active {
  transition: transform 0.4s ease, opacity 0.4s ease;
}

.drawer-enter-from, .drawer-leave-to {
  transform: translateX(100%);
  opacity: 0;
}

.footer {
  width: 100%;
  padding: 24px 16px;
  background: linear-gradient(to right, #f7f8fa, #e9ecf3);
  border-top: 1px solid #ddd;
  font-size: 14px;
  text-align: center;
  color: #666;
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.05);
  box-sizing: border-box;
}

.footer-links {
  margin-bottom: 8px;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 12px;
}

.footer-links a {
  color: #666;
  text-decoration: none;
  transition: all 0.2s ease;
  position: relative;
  white-space: nowrap;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
}

.footer-links a:hover {
  color: #007aff;
}

.footer-links a::after {
  content: "";
  display: block;
  height: 2px;
  background-color: #007aff;
  width: 0;
  transition: width 0.3s ease;
  position: absolute;
  bottom: -4px;
  left: 50%;
  transform: translateX(-50%);
}

.footer-links a:hover::after {
  width: 60%;
}

.footer-beian {
  margin-top: 4px;
}

.footer-beian a {
  color: #999;
  font-size: 13px;
  text-decoration: none;
}

.footer-beian a:hover {
  color: #007aff;
  text-decoration: underline;
}

@media (max-width: 768px) {
  .desktop-menu {
    display: none;
  }

  .menu-toggle {
    display: block;
  }
}
</style>

<style>
html, body {
  margin: 0;
  padding: 0;
  overflow-x: hidden;
}
</style>
