# blog-api

使用 Spring Boot 建立 Post (貼文) 和 Comment (留言) 基本 CRUD 功能

輔以 Spring Security + JWT 實作註冊登入功能

支援 Swagger 互動式 API 文件 

依身份權限有不同功能操作 (ROLE_ADMIN, ROLE_USER)

ROLE_ADMIN: username: jay, email:jay@gmail.com, password: password -> 能新增、更新、刪除 Post

ROLE_USER: username: jenny, email:jenny@gmail.com, password: password
