<template>
  <div class="tech-container">
    <h1 class="title">我的技术成长记录</h1>

    <section class="tech-block">
      <h2>🌐 前端技术</h2>
      <ul>
        <li><strong>HTML5 + CSS3</strong>：掌握语义化标签、Flex 布局、响应式设计。</li>
        <li><strong>JavaScript</strong>：熟悉 ES6+ 语法、事件处理、DOM 操作、异步编程（Promise、async/await）。</li>
        <li><strong>Vue3 + TypeScript</strong>：使用 Composition API、ref、reactive、组件通信、Pinia 状态管理。</li>
        <li><strong>Axios</strong>：进行 HTTP 请求封装与错误处理。</li>
        <li><strong>TailwindCSS</strong>：快速构建美观 UI，提高开发效率。</li>
      </ul>
    </section>

    <section class="tech-block">
      <h2>🛠️ 后端技术</h2>
      <ul>
        <li><strong>Spring Boot</strong>：搭建 RESTful API，整合 MyBatis、Spring MVC。</li>
        <li><strong>MyBatis + MySQL</strong>：熟悉 CRUD、动态 SQL、分页查询、表关联。</li>
        <li><strong>Spring Security</strong>：实现用户认证与权限控制。</li>
        <li><strong>JWT</strong>：实现无状态登录机制。</li>
      </ul>
    </section>

    <section class="tech-block">
      <h2>📦 项目管理 & 部署</h2>
      <ul>
        <li><strong>Git</strong>：版本控制、分支管理、协作开发。</li>
        <li><strong>Vite</strong>：前端项目构建工具，配合 Vue3 使用。</li>
        <li><strong>Nginx + 阿里云服务器</strong>：部署前后端项目、配置 SSL 证书、域名绑定。</li>
      </ul>
    </section>

    <section class="tech-block">
      <h2>📚 其他技术 & 实践</h2>
      <ul>
        <li><strong>微信小程序</strong>：完成虚拟试衣间功能，接入抠图 API、合成接口。</li>
        <li><strong>AI 应用</strong>：调用百炼 API，实现图文生成功能。</li>
        <li><strong>ESP8266 + 单片机</strong>：串口通信，控制 LED 灯，实现小程序远程控制。</li>
        <li><strong>软著 & 项目汇报</strong>：具备项目总结、文档撰写、PPT 演示能力。</li>
      </ul>
    </section>
  </div>
</template>

<style scoped>
.tech-container {
  max-width: 960px;
  margin: 0 auto;
  padding: 40px 16px;
}
.title {
  font-size: 2.25rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 40px;
  color: #1e293b;
}
.tech-block {
  margin-bottom: 32px;
  background: #f8fafc;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.04);
}
.tech-block h2 {
  font-size: 1.5rem;
  margin-bottom: 16px;
  color: #2563eb;
}
.tech-block ul {
  list-style-type: disc;
  padding-left: 20px;
}
.tech-block li {
  margin-bottom: 8px;
  line-height: 1.6;
}
</style>

