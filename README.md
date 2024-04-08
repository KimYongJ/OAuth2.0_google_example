# 프로젝트 개요

- **스프링 부트와, 리액트, OAuth2 클라이언트를 사용해 구글 로그인을 직접 실습해 봅니다.**

# **리액트 실행 방법**

- **1)** node를 설치 합니다. ( 개발 버전 v20.10.0 )
- **2)** 커맨드로 oauth_example 폴더로 들어갑니다.
- **3)** [  npm install  ] 을 입력해 관련 모듈을 다운 받습니다.
- **4)** [  npm start  ] 를 입력하여 3000번 포트를 켜줍니다.

# 백엔드 기본 정보

- **빌드 툴 :** Gradle
- **JDK 버전 :** JDK 17
- **주요 라이브러리**
    - Spring Boot 2.7.7
    - Spring Boot Starter Web
    - Spring Boot Starter OAuth2 Client
    - Spring Boot Starter Test
    - Project Lombok

# **백엔드 실행 전제 조건**

- **JDK 17이 설치되어 있어야 합니다.**
- **Gradle이 시스템에 설치되어 있거나, Gradle Wrapper를 사용할 수 있어야 합니다.**
- 블로그글 ( [https://blog.naver.com/yongj326/223408750683](https://blog.naver.com/yongj326/223408750683) ) 에서 보는 바와 같이 구글 로그인을 위해서는 구글 클라우드 플랫폼에서 [ 사용자 인증 정보 ] 등록 후 “**클라이언트 ID” 와 “클라이언트 보안 비밀번호” 를 직접 yml파일에 기록해야 합니다.**

# 백엔드 실행 방법

- 버전 맞춘 후 실행
