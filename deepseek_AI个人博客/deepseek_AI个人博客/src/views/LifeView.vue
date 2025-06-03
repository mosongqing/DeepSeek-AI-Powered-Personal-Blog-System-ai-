<template>
  <div id="hero-slides" ref="hero">
    <div id="header">
      <div id="logo"></div>
      <div id="menu" ref="menu">
        <div id="hamburger">
          <div class="slice"></div>
          <div class="slice"></div>
          <div class="slice"></div>
        </div>
      </div>
    </div>
    <div id="slides-cont">
      <div class="button" id="next" ref="nextBtn"></div>
      <div class="button" id="prev" ref="prevBtn"></div>

      <div id="slides" ref="slides">
        <!-- 轮播图7张 -->
        <div
          v-for="(item, index) in slideData"
          :key="index"
          class="slide"
          :style="`background-image: url(${item.url})`"
        >
          <div class="number">{{ ('0' + (index + 1)).slice(-2) }}</div>
          <div class="body">
            <div class="location">{{ item.loc }}</div>
            <div class="headline">Photo by {{ item.name }}</div>
            <a class="link" :href="item.link" target="_blank">View on Unsplash</a>
          </div>
        </div>
      </div>

      <div id="next-catch" ref="nextCatch"></div>
      <div id="prev-catch" ref="prevCatch"></div>
    </div>

    <div id="footer">
      <a href="https://dribbble.com/shots/3888265-Motion-Study" target="_blank">
        <div id="dribbble" ref="dribbble"></div>
      </a>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'

// 所有 ref 元素
const hero = ref<HTMLDivElement | null>(null)
const menu = ref<HTMLDivElement | null>(null)
const slides = ref<HTMLDivElement | null>(null)
const nextBtn = ref<HTMLDivElement | null>(null)
const prevBtn = ref<HTMLDivElement | null>(null)
const nextCatch = ref<HTMLDivElement | null>(null)
const prevCatch = ref<HTMLDivElement | null>(null)
const dribbble = ref<HTMLDivElement | null>(null)

const currentPage = ref(0)
const currentlyDemoing = ref(false)

const slideData = [
  {
    name: 'Benjamin Hung',
    loc: 'Shibuya, Japan',
    url: 'https://alca.tv/static/u/ef5af971-fc46-4ee1-a589-980346478696_opt.png',
    link: 'https://unsplash.com/photos/EYmhcdGuYmI'
  },
  {
    name: 'Ryan Tang',
    loc: 'Mong Kok, Hong Kong',
    url: 'https://alca.tv/static/u/522d6a86-0dcf-4554-8b22-7655d65a1f66_opt.png',
    link: 'https://unsplash.com/photos/ANJHXftvvJ8'
  },
  {
    name: 'Steve Roe',
    loc: 'Incheon, South Korea',
    url: 'https://alca.tv/static/u/9b5f71cb-34a3-4fb4-a3a6-0ce9557c0acd_opt.png',
    link: 'https://unsplash.com/photos/73aocAAt7rs'
  },
  {
    name: 'Sean Foley',
    loc: 'Wan Chai, Hong Kong',
    url: 'https://alca.tv/static/u/10cfd52d-e217-41e9-bb8b-ad43384a5e63_opt.png',
    link: 'https://unsplash.com/photos/aPDCEoW7B78'
  },
  {
    name: 'Alex Knight',
    loc: 'Shibuya-ku, Japan',
    url: 'https://alca.tv/static/u/b9669f2a-17fc-47dc-b6b5-3f863004efa8_opt.png',
    link: 'https://unsplash.com/photos/Akz00I_GGjU'
  },
  {
    name: 'Benjamin Hung',
    loc: 'Tokyo, Japan',
    url: 'https://alca.tv/static/u/31979576-5060-4513-aae2-b379b87e7fe6_opt.png',
    link: 'https://unsplash.com/photos/pTn26knnKVw'
  },
  {
    name: 'Jesus In Taiwan',
    loc: 'Taipei City, Taiwan',
    url: 'https://alca.tv/static/u/429b83b8-1ad4-4450-b0de-0a0c1073cf1e_opt.jpg',
    link: 'https://unsplash.com/photos/v63B_MUiFw8'
  }
]

// 滑动相关逻辑
const slidesPerPage = () => (window.innerWidth > 1700 ? 4 : window.innerWidth > 1200 ? 3 : 2)
const maxPageCount = () => slideData.length / slidesPerPage() - 1

function goToPage(page: number) {
  currentPage.value = Math.min(maxPageCount(), Math.max(0, page))
  hero.value?.style.setProperty('--page', currentPage.value.toString())
}

function sleep(ms: number) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

async function demo() {
  if (currentlyDemoing.value) return
  currentlyDemoing.value = true
  if (currentPage.value !== 0) {
    goToPage(0)
    await sleep(800)
  }
  const s = slidesPerPage()
  const pageSeq = { 2: [1, 2, 1], 3: [1, 2, 1/3], 4: [1, 1, 0] }[s] || [1,1,0]
  await sleep(300)
  goToPage(pageSeq[0])
  await sleep(500)
  await sleep(1200)
  goToPage(pageSeq[1])
  dribbble.value?.classList.add('hover')
  await sleep(1200)
  goToPage(pageSeq[2])
  dribbble.value?.classList.remove('hover')
  currentlyDemoing.value = false
}

onMounted(() => {
  nextBtn.value?.addEventListener('click', () => !currentlyDemoing.value && goToPage(currentPage.value + 1))
  prevBtn.value?.addEventListener('click', () => !currentlyDemoing.value && goToPage(currentPage.value - 1))
  menu.value?.addEventListener('click', demo)
  sleep(100).then(demo)
})
</script>
<style scoped lang="scss">
@import url('https://fonts.googleapis.com/css?family=Roboto:100,100i,400,900,800i');

$accent-color: hsl(204, 90%, 50%);
$curve: cubic-bezier(0.7, 0, 0.3, 1);

body {
	--slides-per-page: 2;
	margin: 0;
	overflow: hidden;
	height: 100vh;
	font-family: 'Roboto Condensed', sans-serif;
	color: hsl(0, 0%, 100%);
}

a {
	text-decoration: none;
	color: inherit;
}

#hero-slides {
	--page: 0;
	height: 100vh;
	background: hsl(210, 23%, 19%);
	background: linear-gradient(90deg, hsl(210, 13%, 28%) 0%, hsl(210, 23%, 19%) 100%);

	#header {
		height: 12vh;
		line-height: 12vh;
		padding: 0 3vw;
		position: relative;

		#logo {
			font-size: 2.5vh;
			font-style: italic;

			&:before {
				content: 'The';
				text-transform: uppercase;
				font-weight: 100;
				margin-right: 0.4em;
			}
			&:after {
				content: 'Wall';
				text-transform: uppercase;
				font-weight: 800;
			}
		}
		#menu {
			position: absolute;
			top: 0;
			right: 0;
			bottom: 0;
			cursor: pointer;
			padding: 0 3vw;

			&:before {
				font-size: 1.75vh;
				content: 'Play Demo';
				margin-right: 0.5em;
				text-transform: uppercase;
			}
			#hamburger {
				display: inline-block;

				.slice {
					background: hsl(0, 0%, 100%);
					height: 0.2vh;
					width: 1vw;

					&:not(:last-child) {
						margin-bottom: 0.5vh;
					}
				}
			}
		}
	}
	#slides-cont {
		position: relative;
		// overflow: hidden;
		--button-height: 6vh;
		--button-spacing: 0.2vh;

		.button {
			width: 5vw;
			height: var(--button-height);
			background: $accent-color;
			// text-align: center;
			position: absolute;
			right: 5.375vw;
			top: 38vh;
			z-index: 100;
			cursor: pointer;

			&:before,
			&:after {
				line-height: var(--button-height);
				position: absolute;
				margin-left: -0.25vw;
				pointer-events: none;
				transform: scale(0.75, 1.5);
				transition: 125ms ease-in-out;
			}
			&:before {
				left: 50%;
			}
			&:after {
				opacity: 0;
			}
			&:hover {
				&:before,
				&:after {
					transition: 250ms ease-in-out;
				}
				&:before {
					opacity: 0;
				}
				&:after {
					left: 50% !important;
					opacity: 1;
				}
			}
		}
		#next {
			margin-top: calc(-1 * (var(--button-height) + var(--button-spacing)));

			&:before,
			&:after {
				content: '>';
			}
			&:after {
				left: 30%;
			}
			&:hover:before {
				left: 70%;
			}
		}
		#prev {
			margin-top: var(--button-spacing);
			opacity: calc(var(--page) + 0.5);
			transition: 500ms opacity;

			&:before,
			&:after {
				content: '<';
			}
			&:after {
				left: 70%;
			}
			&:hover:before {
				left: 30%;
			}
		}
		#next-catch,
		#prev-catch {
			width: 10vw;
			height: 76vh;
			position: absolute;
			top: 0;
			z-index: 90;
		}
		#next-catch {
			right: 0;
		}
		#prev-catch {
			left: 0;
		}
	}
	#slides {
		--slides-height: 76vh;
		width: auto;
		height: var(--slides-height);
		padding: 0 10vw;
		font-size: 0;
		white-space: nowrap;
		position: absolute;
		transform: translate3D(calc(var(--page) * -80vw), 0, 0);
		transition: 1500ms transform $curve;

		.slide {
			display: inline-block;
			vertical-align: top;
			font-size: 1.5vw;
			width: 24em;
			height: var(--slides-height);
			margin: 0 1.333em;
			background: hsl(210, 23%, 8%);
			color: white;
			background-size: cover;
			background-position: center;
			white-space: normal;
			word-break: break-word;
			position: relative;

			&:before {
				content: '';
				display: block;
				background: linear-gradient(180deg, hsla(209, 11%, 38%, 0) 0%, hsla(209, 36%, 20%, 0.7) 100%);
				opacity: 0;
				position: absolute;
				top: 0;
				left: 0;
				right: 0;
				bottom: 0;
			}
			.number {
				position: absolute;
				top: 2em;
				left: 2em;
				filter: drop-shadow(0 2px 1px hsla(0, 0%, 0%, 0.5));

				&,
				&:before,
				&:after {
					vertical-align: middle;
				}
				&:before,
				&:after {
					display: inline-block;
					content: '';
					height: 0.133em;
					margin-top: -0.2em;
					background: hsl(0, 0%, 100%);
				}
				&:before {
					width: 0;
					margin-left: 0;
				}
				&:after {
					width: 3em;
					margin-left: 1em;
				}
			}
			.body {
				position: absolute;
				bottom: 2em;
				left: 2em;
				right: 2em;
			}
			.location,
			.headline {
				position: relative;
				bottom: 0;
				cursor: default;
			}
			&:before,
			.number:before,
			.number:after,
			.location,
			.headline,
			.link {
				transition: 375ms $curve;
			}
			.location {
				font-weight: 100;
				margin-bottom: 1.5em;
				transition-delay: 60ms;
			}
			.headline {
				font-size: 2.667em;
				font-weight: 900;
				transition-delay: 50ms;
			}
			.link {
				display: inline-block;
				background: $accent-color;
				padding: 0.5em 1.25em;
				font-size: 1.33em;
				opacity: 0;
				position: absolute;
				bottom: -2em;
				pointer-events: none;
				transition-delay: 25ms;
			}
			&.hover,
			&:hover {
				&:before {
					opacity: 1;
				}
				&:before,
				.number:before,
				.number:after,
				.location,
				.headline,
				.link {
					transition: 500ms $curve;
				}
				.number:before {
					width: 3em;
					margin-right: 1em;
				}
				.number:after {
					width: 0;
					margin-right: 0;
				}
				.location {
					transition-delay: 0;
					bottom: 4em;
				}
				.headline {
					transition-delay: 100ms;
					bottom: 1.5em;
				}
				.link {
					bottom: 0;
					opacity: 1;
					transition-delay: 250ms;
					pointer-events: auto;
				}
			}
		}
	}
	#footer {
		height: 12vh;
		font-size: 1vh;

// 		#page-counter {

// 		}
		#dribbble {
			border-radius: 2vh;
			position: absolute;
			bottom: 4vh;
			right: 4vh;
			transition: 300ms $curve;
			padding-left: 1.5vh;

			&:before,
			&:after {
				vertical-align: middle;
				transition: inherit;
			}
			&:before {
				display: inline;
				content: 'View original Dribbble';
				font-size: 2vh;
				opacity: 0;
				transform: translate3D(-200px, 0, 0);
			}
			&:after {
				content: '';
				display: inline-block;
				width: 4vh;
				height: 4vh;
				margin-left: 1vh;
				background-image: url(https://alca.tv/static/u/82fde61b-28ef-4f17-976e-8f1abb5a1165.png);
				background-size: contain;
				background-position: center;
			}
			&.hover,
			&:hover {
				background: hsl(337, 78%, 61%);

				&:before {
					opacity: 1;
					transform: translate3D(0, 0, 0);
					transition-delay: 50ms;
				}
				&:after {
					filter: saturate(0%) contrast(200%) brightness(200%) invert(100%);
				}
			}
		}
	}
}

@media (min-width: 1200px) and (max-width: 1699px) {
	body {
		--slides-per-page: 3;
	}
	#hero-slides #slides .slide {
		font-size: 1vw;
	}
}

@media (min-width: 1700px) {
	body {
		--slide-per-age: 4;
	}
	#hero-slides #slides .slide {
		font-size: 0.75vw;
	}
}
</style>

