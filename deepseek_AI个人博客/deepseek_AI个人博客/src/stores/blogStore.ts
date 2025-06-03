import { defineStore } from 'pinia'

export const useBlogStore = defineStore('blog', {
  state: () => ({
    shouldRefresh: false,
    lastRefreshTime: null as Date | null
  }),
  actions: {
    triggerRefresh() {
      this.shouldRefresh = true
      this.lastRefreshTime = new Date()
    },
    consumeRefresh() {
      this.shouldRefresh = false
    },
  }
})
