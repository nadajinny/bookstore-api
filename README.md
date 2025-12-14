# 📚 Bookstore API

Spring Boot 3.3 기반의 **온라인 서점 백엔드 REST API** 프로젝트입니다. 사용자 계정 관리와 JWT 인증, 도서 카탈로그, 리뷰/댓글, 장바구니, 주문, 내 서재(Library), 위시리스트(Wishlist) 기능을 제공합니다. 모든 엔드포인트는 OpenAPI 3 명세로 문서화되어 있으며 Soft Delete 정책과 공통 응답 포맷(`ApiResponse`, `PageResponse`)을 따릅니다.

---

## 📝 프로젝트 개요 & 실행 가이드
- **프로젝트 설명**: 온라인 서점 운영에 필요한 핵심 도메인(Users/Books/Reviews/Orders 등)을 모듈화해 JWT 인증, RBAC, 페이지네이션·검색/정렬, 표준 에러 응답을 지원하는 Spring Boot 기반 REST API입니다. 계층형 구조(Controller → Service → Repository)와 Soft Delete, 공통 응답 DTO로 일관된 코드를 유지합니다.
- **Swagger 주소**: `http://113.198.66.75:10175/swagger-ui/index.html`
- **API Root**: `http://113.198.66.75:10175/api`
- **코드 설치/실행**
  ```bash
  # 1) Gradle 의존성 설치 및 빌드
  ./gradlew clean build

  # 2) 애플리케이션 실행 (기본 8080, prod 프로필은 10175 포트)
  java -jar build/libs/bookstore-api-0.0.1-SNAPSHOT.jar
  # 또는
  SPRING_PROFILES_ACTIVE=prod java -jar build/libs/bookstore-api-0.0.1-SNAPSHOT.jar
  ```

---

## 🔗 서비스 주소
- **Swagger UI**  
  http://113.198.66.75:10175/swagger-ui/index.html
- **OpenAPI JSON**  
  http://113.198.66.75:10175/v3/api-docs
- **API Root**  
  http://113.198.66.75:10175/api
- **Health Check**  
  http://113.198.66.75:10175/health

> ※ 위 주소는 배포 서버(JCloud) 기준입니다. 로컬에서 실행할 경우 `localhost:8080` 으로 대체하면 됩니다.

---

## 🛠 기술 스택
### Backend
- Java 17 / Spring Boot 3.3
- Spring Web, Spring Security, Spring Data JPA, Spring Validation
- Lombok, Springdoc OpenAPI 2.6

### 인증 & 보안
- JWT (Access / Refresh Token), Stateless Authentication
- USER / SELLER / ADMIN 역할 기반 권한 제어
- Soft Delete 정책 적용 (User, Book, Review 등)

### Database & Infra
- MySQL (Flyway 마이그레이션 – 기본 비활성화)
- Gradle 빌드 툴

---

## 🧱 아키텍처 개요
```
Controller → Service → Repository(JPA) → Entity(Soft Delete)
```
- 공통 응답: `ApiResponse<T>`
- 페이징 응답: `PageResponse<T>`
- Spring Security + JWT 필터로 인증/인가 처리

---

## 🔐 인증 & 보안 정책
- `Authorization: Bearer <AccessToken>` 헤더 사용
- JWT 클레임에 `token_type` 저장 (ACCESS/REFRESH 구분)
- 공개 허용 경로: `/`, `/health`, `/api/users/register`, `/api/users/login`, `/api/users/refresh-token`, `/api/public/**`, `/swagger-ui/**`, `/v3/api-docs/**`, `GET /api/books/**`, `GET /api/reviews/**`, `GET /api/comments/**`
- SELLER/ADMIN 권한 필요: 도서 등록/수정/삭제, 배열 관리
- 나머지 보호 API: 로그인 필수

---

## 🧑‍💻 주요 기능 요약
### 👤 Users & Authentication (`/api/users`)
- 회원가입 / 로그인 / 로그아웃 / JWT 재발급
- 내 정보 조회·수정 / 회원 Soft Delete

### 📘 Books (`/api/books`)
- 도서 등록·수정·삭제 (SELLER/ADMIN)
- 도서 목록(페이징·검색·필터) / 상세 조회 (평균 평점, 리뷰 수)

### ⭐ Reviews (`/api/reviews`)
- 리뷰 작성·수정·삭제 (Soft Delete)
- 도서별 리뷰 조회 (정렬·필터) / 내가 작성한 리뷰 / 좋아요한 리뷰
- 리뷰 좋아요 / 취소

### 💬 Comments (`/api/comments`)
- 리뷰 댓글 작성·수정·삭제
- 댓글 좋아요 / 취소 / 리뷰별 댓글 조회

### ❤️ Wishlist (`/api/wishlist`)
- 위시리스트 추가 / 조회 / 삭제

### 📚 Library (`/api/library`)
- 구매/보유 도서 추가 / 조회 / 삭제

### 🛒 Carts (`/api/carts`)
- 장바구니 항목 조회 / 추가 / 수량 변경 / 삭제 *(Swagger 시연용 더미 데이터)*

### 🧾 Orders (`/api/orders`)
- 주문 생성 / 상태 변경 / 내 주문 항목 조회 *(Swagger 시연용 Stub 구현)*

각 엔드포인트의 상세 파라미터와 응답 스키마는 Swagger UI에서 확인할 수 있습니다.

---

## 📦 프로젝트 실행 방법

### 1️⃣ 의존성 설치 및 빌드
```bash
./gradlew clean build -x test
```
> 테스트 환경(DB, Flyway 등)을 구성했다면 `-x test` 옵션을 제거하고 실행할 수 있습니다.

### 2️⃣ 서버 실행
```bash
java -jar build/libs/bookstore-api-0.0.1-SNAPSHOT.jar
# 또는 프로필 지정
SPRING_PROFILES_ACTIVE=prod java -jar build/libs/bookstore-api-0.0.1-SNAPSHOT.jar
```

### 3️⃣ Swagger 사용 방법
1. http://113.198.66.75:10175/swagger-ui/index.html 접속
2. 우측 상단 **Authorize** 클릭
3. `Bearer <AccessToken>` 형식으로 JWT 입력
4. 보호된 API 호출 시험

---

## 📮 Postman Collection
Postman에서 API를 테스트하려면 아래 방법 중 하나를 사용할 수 있습니다.

### 방법 1: OpenAPI 링크 Import
- Postman → **Import** → **Link** 탭 → `http://113.198.66.75:10175/v3/api-docs`

### 방법 2: 컬렉션 파일 Import
- `postman/Bookstore-API.postman_collection.json` 파일을 Import

#### 권장 환경 변수
- `base_url` = `http://113.198.66.75:10175`
- `access_token` = (로그인 후 발급받은 JWT Access Token)

---

## 🌍 환경 변수 (.env)
| Key | 설명 | 예시 값 |
| --- | --- | --- |
| `DB_URL` | MySQL 커넥션 문자열 (`jdbc:mysql://host:port/db`) | `jdbc:mysql://113.198.66.75:3306/bookstore` |
| `DB_USERNAME` | 애플리케이션 DB 계정 | `bookstore_app` |
| `DB_PASSWORD` | DB 계정 비밀번호 | `change-me` |
| `JWT_SECRET` | HS256 서명용 Base64 인코딩 시크릿 | `please-update-this-secret` |
| `SPRING_PROFILES_ACTIVE` *(선택)* | 실행 프로필(dev/prod) | `prod` |

> 루트에 제공된 `.env.example` 파일을 복사해 `.env`를 만든 뒤, 실제 환경에 맞는 값으로 교체하세요. 민감 정보(.env, PEM 키 등)는 Git에 커밋하지 않습니다.

```env
# .env.example
DB_URL=jdbc:mysql://localhost:3306/bookstore
DB_USERNAME=bookstore
DB_PASSWORD=changeme

JWT_SECRET=please-update-this-secret
```

---


25학년도 2학기 웹서비스 설계 과제 2 프로젝트입니다. 
