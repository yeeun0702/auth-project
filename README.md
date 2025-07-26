# 🛡️ Auth Project – 바로인턴 15기 과제

> **Spring Boot 기반 JWT 인증/인가 및 AWS 배포**



## ✅ 개요

* **기간**: 2025.07.25 ~ 2025.07.27
* **주제**: Spring Security + JWT 인증/인가 시스템
* **목표**: API 인증 구조 설계, JWT 보안 처리, 테스트 및 문서화, EC2 배포



## 🔧 실행 방법

**1. 프로젝트 클론 및 빌드**

```bash
git clone https://github.com/yeeun0702/auth-project.git
cd auth-project
./gradlew build
```

**2. 애플리케이션 실행**

```bash
java -jar -Dspring.profiles.active=prod build/libs/auth-project-0.0.1-SNAPSHOT.jar
```

> 기본 포트: `0.0.0.0:8080`


## 📄 API 문서

* **Swagger UI**: [http://52.78.23.43/swagger-ui/index.html](http://52.78.23.43/swagger-ui/index.html)
* **기본 URL**: `http://52.78.23.43:8080`  
  ⚠️ 루트 경로는 인증이 필요하여 접근 시 401 에러가 발생할 수 있습니다.
* **헤더 예시**:

  ```
  Authorization: <JWT Token>
  ```


## 📂 프로젝트 구조

```
com.example.authproject
├── controller         # API 컨트롤러
├── service            # 비즈니스 로직
├── domain             # 사용자, 권한 등 엔티티
├── repository         # JPA 레포지토리
├── dto                # 요청/응답 객체
├── security           # JWT 필터, 설정, Provider
├── common             # 공통 응답/예외 처리
└── config             # Swagger 등 설정 파일
```


## 🛠 기술 스택

| 분류      | 내용                          |
| ------- | --------------------------- |
| Backend | Java 17, Spring Boot 3.x    |
| 인증      | Spring Security, JWT (JJWT) |
| 테스트     | JUnit5, Spring MockMvc      |
| 문서화     | Swagger (springdoc-openapi) |
| 배포      | AWS EC2, Gradle             |

## 🔐 주요 기능 요약

| 기능          | 설명                              |
| ----------- | ------------------------------- |
| ✅ 회원가입      | 사용자 등록, 중복 검사, USER 기본 권한       |
| ✅ 로그인       | JWT 발급 및 반환 (HS256 기반)          |
| ✅ 관리자 권한 부여 | ADMIN 유저만 가능, Role 수정 API       |
| ✅ 인증/인가 처리  | JWT 기반 인증, `@PreAuthorize` 인가   |
| ✅ API 문서화   | Swagger UI 적용 완료                |
| ✅ 테스트 코드    | 회원가입/로그인/권한 테스트 포함              |
| ✅ EC2 배포    | 퍼블릭 IP 기반 외부 접근 가능 (Swagger 포함) |



## 🧪 테스트

| 테스트 항목 | 설명                                      |
| ------ | --------------------------------------- |
| 회원가입   | 정상 가입, 중복 가입 에러                         |
| 로그인    | 올바른 자격 vs 실패 케이스 검증                     |
| 권한 부여  | ADMIN vs 일반 유저 접근 테스트, 존재하지 않는 유저 예외 처리 |


### 📌 PR 트래킹

| PR 번호 | 제목 |
|--------|-------|
| [#1](https://github.com/yeeun0702/auth-project/pull/1) | 🎉 [init] 프로젝트 초기 설정 및 배포 환경 구성 INIT |
| [#4](https://github.com/yeeun0702/auth-project/pull/4) | ✨ [feat] 공통 응답형식 및 예외 핸들링 세팅 FEAT |
| [#6](https://github.com/yeeun0702/auth-project/pull/6) | ✨ [feat] 회원가입 API 구현 FEAT |
| [#8](https://github.com/yeeun0702/auth-project/pull/8) | ✨ [feat] 로그인 API 구현 FEAT |
| [#10](https://github.com/yeeun0702/auth-project/pull/10) | ✨ [feat] Spring Security & JWT 구현 FEAT |
| [#13](https://github.com/yeeun0702/auth-project/pull/13) | ✨ [feat] 관리자 권한 부여 API 구현 FEAT |
| [#15](https://github.com/yeeun0702/auth-project/pull/15) | ✅ [test] 회원가입/로그인/권한 부여 테스트 코드 작성 TEST |
| [#16](https://github.com/yeeun0702/auth-project/pull/16) | 📝 [docs] Swagger 설정 및 API 문서화 정리 DOCS |
| [#18](https://github.com/yeeun0702/auth-project/pull/18) | 👷 [deploy] AWS EC2 배포 및 서버 실행 설정 DEPLOY |

## ☁️ 배포 정보

* **EC2 퍼블릭 주소**: [http://52.78.23.43](http://52.78.23.43)
  ⚠️ 루트 경로는 인증이 필요하여 접근 시 401 에러가 발생할 수 있습니다.
* **Swagger 문서 확인**: [http://52.78.23.43/swagger-ui/index.html](http://52.78.23.43/swagger-ui/index.html)
