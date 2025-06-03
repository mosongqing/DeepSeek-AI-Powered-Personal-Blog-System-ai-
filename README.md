# 🎉 DeepSeek AI 个人博客系统 v1.0.0

这是本项目的第一个稳定发布版本，集成了 AI 聊天、博客发布和 Markdown 编辑器等核心功能，支持 PC 和移动端访问。

👉 **在线体验地址**：  
🔗 [https://momosongqing.asia/](https://momosongqing.asia/)

---

## 🖼️ 项目截图

示例：
![image](https://github.com/user-attachments/assets/b972afbf-efd9-4cfa-b570-8a8c051e88f7)
![image](https://github.com/user-attachments/assets/f89f6315-e598-4ea4-90af-3be5fabdb2fa)
![image](https://github.com/user-attachments/assets/57d79f4e-484c-4e28-b485-5b2ca4cf7ff1)
![image](https://github.com/user-attachments/assets/200697db-cb88-468e-8769-8d1c68332bba)
![image](https://github.com/user-attachments/assets/70fea68b-176d-4066-9ff5-188b010437d2)
![博客首页截图](https://user-images.githubusercontent.com/你的图片路径.png) <!-- 拖入图片后 GitHub 会自动生成地址 -->

---

## 🚀 功能简介
- 用户注册 / 登录（Spring Boot + MySQL）
- 博客文章发布、查看、编辑、删除
- Markdown 编辑器
- DeepSeek AI 聊天（流式输出）
- 聊天上下文记忆、语音合成、发送图片
- 聊天历史本地持久化
- Vue3 + TS 响应式 UI

---

## 🧰 技术栈

| 前端 | 后端 | 数据库 | AI |
|------|------|--------|----|
| Vue3 + TypeScript + Pinia + Vite | Spring Boot 3.2.5 | MySQL 8.x | DeepSeek Chat API |

---

## 📁 项目结构（部分）
📦 root
┣ 📁 src # 前端源代码
┣ 📁 public # 静态资源
┣ 📁 backend # Spring Boot 后端代码
┣ 📄 README.md
┣ 📄 package.json
┗ 📄 vite.config.ts

---

## 🛠️ 使用说明

### 前端启动：
```bash
npm install
npm run dev
