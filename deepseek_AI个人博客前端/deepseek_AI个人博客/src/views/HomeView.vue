<template>
  <div class="carousel">
    <!-- 打字效果 -->
    <div class="typing-box">
      {{ displayedText }}
      <span class="typing-cursor"></span>
    </div>

    <!-- 背景轮播图 -->
    <div class="carousel-container">
      <div
        v-for="(image, index) in images"
        :key="index"
        class="carousel-item"
        :style="{
          backgroundImage: 'url(' + image + ')',
          opacity: activeIndex === index ? 1 : 0,
          transform: activeIndex === index ? 'scale(1)' : 'scale(1.05)',
        }"
      ></div>
    </div>

      <!-- ✅ 加上波浪动画 -->
    <div class="wave-banner-wrapper">
      <div class="wave wave2"></div>
      <div class="wave wave1"></div>
    </div>
  </div>
  <homeContent />
  <BlogDetailView />
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import homeContent from '@/components/homeContent.vue'
import BlogDetailView from '@/components/BlogDetailView.vue'

// 图片导入
import image1 from '@/static/huohuo-honkai-star-5120x2880-13888.jpg'
import image2 from '@/static/castorice-honkai-7680x4320-22114.jpg'
import image3 from '@/static/huohuo-honkai-star-5120x2880-13888.jpg'
import image4 from '@/static/castorice-honkai-7680x4320-22114.jpg'

const images = [image1, image2, image3, image4]
const activeIndex = ref(0)

// 背景轮播
onMounted(() => {
  setInterval(() => {
    activeIndex.value = (activeIndex.value + 1) % images.length
  }, 6000)
})

// 打字文本
const texts = [
  '欢迎来到我的AI世界！🤖',
  '探索未来，感受科技的魅力！🚀',
  '你准备好开始了吗？✨',
  '在代码的宇宙中，点亮属于你的星辰🌌',
  '科技是诗，而你就是那行诗的作者📜',
  '每一行代码，都是对未来的温柔构建🧩',
  '人工智能，不只是技术，是对未知的勇敢拥抱🤝',
  '愿你在这里遇见灵感，也遇见更好的自己💡',
  '学习不是任务，而是与世界对话的方式🌍',
  '让灵魂发光的不止是梦想，还有持续成长的你🌟',
  '专注每一个当下，终将抵达心中的未来🏞️',
  '用创意连接世界，用技术改变生活💻❤️',
  '世界很大，愿你步步有光，行行生风🌈',
  '和AI一起成长，让未来由你定义🧠✨',
  '拥抱变化，也拥抱不被定义的你🌱'
]


const displayedText = ref('')
let textIndex = 0
let charIndex = 0
let isDeleting = false

function startTyping() {
  const currentText = texts[textIndex]

  if (isDeleting) {
    charIndex--
    displayedText.value = currentText.substring(0, charIndex)
  } else {
    displayedText.value = currentText.substring(0, charIndex)
    charIndex++
  }

  let delay = isDeleting ? 80 : 100

  if (!isDeleting && charIndex > currentText.length) {
    delay = 3500
    isDeleting = true
    charIndex = currentText.length
  } else if (isDeleting && charIndex === 0 ) {
    isDeleting = false
    textIndex = (textIndex + 1) % texts.length
    delay = 1500
  }

  setTimeout(startTyping, delay)
}

onMounted(() => {
  startTyping()
})
</script>

<style scoped>

/* .container1 {
  width: 100%;
  height: 1000px;
  background: radial-gradient(
    125% 125% at -2% 101%,
    rgba(245, 87, 2, 1) 10.5%,
    rgba(245, 120, 2, 1) 16%,
    rgba(245, 140, 2, 1) 17.5%,
    rgba(245, 170, 100, 1) 25%,
    rgba(238, 174, 202, 1) 40%,
    rgba(202, 179, 214, 1) 65%,
    rgb(205, 225, 238) 100%
  );
} */
.carousel {
  position: relative;
  width: 100vw;
  height: 60vh;
  overflow: hidden;
}

.carousel-container {
  display: flex;
  height: 100%;
  transition:
    transform 2s ease-in-out,
    opacity 2s ease-in-out;
}

.carousel-item {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  position: absolute;
  transition:
    opacity 2s ease-in-out,
    transform 2s ease-in-out;
}

/* 美化的打字框 */
.typing-box {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 10px 20px; /* 缩小内边距 */
  background: rgba(0, 0, 0, 0.35); /* 更加透明 */
  border-radius: 12px;
  /* backdrop-filter: blur(6px); 毛玻璃弱一些 */
  color: #fff;
  font-size: 22px;
  font-weight: bold;
  line-height: 1.6;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
  white-space: pre-wrap;
  text-align: center;
  z-index: 2;
}


/* 光标动画 */
.typing-cursor {
  display: inline-block;
  width: 1.8px;
  height: 0.9em;
  background-color: white;
  margin-left: 2px;
  animation: blink 1s steps(1) infinite;
}

@keyframes blink {
  50% {
    opacity: 0;
  }
}
 /* 波浪动画样式 */
 .wave-banner-wrapper {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 112px;
  pointer-events: none; /* 避免遮挡 */
  overflow: hidden;
  z-index: 2; /* 在背景图之上，打字文字之下 */
}
/*
波浪通用样式 */
.wave {
  position: absolute;
  bottom: 0;
  width: 250%;
  height: 100px;
  background-repeat: repeat-x;
  background-size: auto 100%;
  animation: waveScroll 60s linear infinite;
}

/* 第一层波浪 */
.wave1 {
  background-image: url('@/assets/images/bannerWave1.png');
  z-index: 8;
  opacity: 1;
  animation-duration: 30s;

}

/* 第二层波浪 */
.wave2 {
  background-image: url('@/assets/images/bannerWave2.png');
  z-index: 5;
  opacity: 1;
  animation-delay: -10s;
  animation-duration: 60s;
  margin-bottom: 12px;
}

@keyframes waveScroll {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}


</style>
