# 🎉 DeepSeek AI 个人博客系统 v1.0.0

这是本项目的第一个稳定发布版本，集成了 AI 聊天、博客发布、用户权限控制、Markdown 编辑器等核心功能，支持 PC 和移动端访问，部署方式为 Nginx + HTTPS，全栈自研，适合技术展示和生产部署。

👉 **在线体验地址**：  
🔗 [https://momosongqing.asia/](https://momosongqing.asia/)

---

## 🖼️ 项目截图

示例界面：

![首页](https://github.com/user-attachments/assets/b972afbf-efd9-4cfa-b570-8a8c051e88f7)
![编辑器](https://github.com/user-attachments/assets/f89f6315-e598-4ea4-90af-3be5fabdb2fa)
![聊天页](https://github.com/user-attachments/assets/57d79f4e-484c-4e28-b485-5b2ca4cf7ff1)
![移动端界面](https://github.com/user-attachments/assets/200697db-cb88-468e-8769-8d1c68332bba)
![用户中心](https://github.com/user-attachments/assets/70fea68b-176d-4066-9ff5-188b010437d2)

---

## 🚀 功能简介

- ✅ 用户注册 / 登录（Spring Boot + MySQL）
- ✅ 博客文章发布、查看、编辑
- ✅ 支持 Markdown 编辑器撰写文章
- ✅ DeepSeek AI 聊天功能（流式输出 SSE 实现）
- ✅ 聊天上下文记忆、语音朗读
- ✅ 聊天记录本地持久化保存
- ✅ Vue 路由权限控制 + Pinia 状态管理（token / userId）
- ✅ Nginx 部署 + HTTPS SSL 证书配置

---

## 🧰 技术栈

| 前端                           | 后端                | 数据库     | AI 接口             | 部署方式      |
|--------------------------------|---------------------|------------|----------------------|----------------|
| Vue3 + TypeScript + Pinia + Vite | Spring Boot 3.2.5   | MySQL 8.x | DeepSeek Chat API   | Nginx + HTTPS 或本地部署 |

---

## 📁 项目结构（部分）


```
📦 DeepSeek-AI-Blog-System
├── 📁 deepseek_AI个人博客                  # 前端 Vue3 项目
│   ├── 📁 src
│   │   ├── 📁 api              # 请求封装
│   │   ├── 📁 assets           # 资源文件
│   │   ├── 📁 components       # 组件模块（如博客编辑/展示）
│   │   │   ├── BlogDetailView.vue
│   │   │   ├── blogEdit.vue
│   │   │   └── homeContent.vue
│   │   ├── 📁 router           # Vue Router 路由管理
│   │   ├── 📁 static           # 静态资源
│   │   ├── 📁 stores           # Pinia 状态管理（token、userId）
│   │   ├── 📁 views            # 页面视图
│   │   │   ├── AboutView.vue
│   │   │   ├── HomeView.vue
│   │   │   ├── LifeView.vue
│   │   │   ├── LoginView.vue
│   │   │   ├── register.vue
│   │   │   ├── TechView.vue
│   │   │   ├── TestPageView.vue
│   │   │   └── UserHomeView.vue
│   │   ├── App.vue             # 根组件
│   │   └── main.ts             # 项目入口
│   └── vite.config.ts          # Vite 配置
│
├── 📁 studytwo                   # 后端 Spring Boot 项目
│   ├── 📁 src
│   │   └── main
│   │       ├── 📁 java
│   │       │   └── com.example.studytwo
│   │       │       ├── config           # 配置类
│   │       │       ├── controller       # 控制器层（API）
│   │       │       ├── model            # 实体类（用户、博客等）
│   │       │       ├── repository       # DAO 层（MyBatis / JPA）
│   │       │       └── StudytwoApplication.java # 启动类
│   │       └── 📁 resources
│   │           ├── application.yml      # 主配置文件
│   │           ├── application.properties
│   │           ├── static.uploads       # 文件上传目录
│   │           ├── templates            # Thymeleaf 模板（如有）
│   │           └── keystore.p12         # HTTPS SSL 证书文件（已集成）
│   └── pom.xml                         # Maven 配置文件

```
---

## 🛠️ 使用说明（前后端一体化）

本系统采用前后端分离架构，前端基于 Vue3 + Vite，后端基于 Spring Boot + MySQL，API 通过 Axios 请求，部署使用 Nginx + HTTPS或本地部署。

---

### 💻 前端启动（Vue3 + Vite）

#### 第一步：配置后端请求地址

打开文件 `src/api/index.ts`，设置 `baseURL`：

```ts
// 本地开发推荐使用：
baseURL: 'http://localhost:8080/api'

// 部署生产环境时使用（已通过 Nginx 和 SSL 配置）：
baseURL: 'https://momosongqing.xyz:8443/api'

# 使用 pnpm（推荐）
pnpm install
pnpm run dev

# 或使用 npm
npm install
npm run dev
```

---

### 🔧 后端启动（Spring Boot）

#### 第二步：启动后端服务

1. 打开文件
   `src/main/java/com/example/studytwo/controller/DeepseekController.java`，配置你的 **DeepSeek API Key**：

   <img src="https://github.com/user-attachments/assets/1b8007c8-22a1-41ca-9271-078e562a177d" width="500"/>

2. 将提供的 SQL 文件导入至本地 MySQL 数据库，确保包含：

   * `user` 表
   * `blog` 表
   * `user_file` 等核心表结构

3. 编辑后端配置文件
   `studytwo/src/main/resources/application.yml`，设置数据库连接信息：

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/你的数据库名字?useSSL=false&serverTimezone=Asia/Shanghai
       username: root
       password: 你的密码
       driver-class-name: com.mysql.cj.jdbc.Driver
   ```

4. 使用 Maven 重载依赖：

   * 如果你使用 IDEA：右侧 Maven 面板点击刷新 🔄
   * 或命令行运行：

     ```bash
     mvn clean install
     ```

5. 启动 IDEA，运行后端项目（`StudytwoApplication.java` 主类）或命令行执行：

   ```bash
   mvn spring-boot:run
   ```

---

## ✅ 系统配置完成，前后端即可联动运行 🎉

访问地址示例：

* 前端： [http://localhost:5173/](http://localhost:5173/)
* 后端： [http://localhost:8080/](http://localhost:8080/)


🚀 部署建议
若上线部署，请配置：

✅ Nginx 反向代理

✅ HTTPS SSL 证书

✅ 防跨域 / 接口转发 / 静态资源托管

<img src="https://github.com/user-attachments/assets/dd8a9d0b-db8f-426e-88fb-ed6aa87b338d" width="200" />

✅感谢阅读本项目，如有帮助欢迎 Star ⭐，也欢迎提 Issues 或 PR 改进！

---






