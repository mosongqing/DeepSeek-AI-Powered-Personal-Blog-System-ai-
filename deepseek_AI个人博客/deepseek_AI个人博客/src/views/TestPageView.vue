<template>
  <div>
    <!-- 点击按钮打开抽屉 -->
    <button @click="toggleDrawer" class="open-btn">打开抽屉</button>

    <!-- 抽屉容器 -->
    <div v-if="isOpen" class="drawer">
      <div class="drawer-content">
        <h2>抽屉内容</h2>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>

        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>

        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>

        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <p>这里是抽屉内容，可以放置菜单、设置或其他信息。</p>
        <button @click="toggleDrawer" class="close-btn">关闭抽屉</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 控制抽屉的打开和关闭
const isOpen = ref(false)

const toggleDrawer = () => {
  isOpen.value = !isOpen.value
}
</script>

<style scoped>
/* 弹出的抽屉 */
.drawer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 300px; /* 高度可以调整 */
  background-color: rgba(0, 0, 0, 0.8);
  color: white;
  transition: transform 0.3s ease-in-out;
  transform: translateY(100%); /* 默认隐藏，位于页面下方 */
  z-index: 1000;
}

/* 当抽屉打开时，抽屉从下方滑入 */
.drawer.open {
  transform: translateY(0); /* 滑入 */
}

/* 抽屉的内容 */
.drawer-content {
  padding: 20px;
}

/* 打开抽屉的按钮 */
.open-btn {
  position: absolute;
  top: 20px;
  right: 20px;
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  cursor: pointer;
}

/* 关闭按钮 */
.close-btn {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  cursor: pointer;
  position: absolute;
  bottom: 20px;
  left: 20px;
}
</style>

