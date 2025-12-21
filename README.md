# NewFeed Backend
- https://www.newfeed.it.com/
- RSS 기반 개인화 뉴스 서비스의 백엔드 API 서버

## Overview
- RSS 수집 결과를 저장·가공하여 프론트엔드에 제공
- JWT 기반 인증 처리

## Tech Stack
- Java 17
- Spring Boot / Spring Security (JWT)
- JPA (Hibernate)
- PostgreSQL
- Docker / Docker Compose

## Key Features
- 게스트 로그인/로그아웃
- JWT Access / Refresh Token 발급
- 사용자 관심 키워드 관리
- 키워드 등록
- RSS 게시글 조회

## Authentication Flow
- 로그인 → Access / Refresh Token 발급
- Access Token 만료 시 Refresh Token을 사용해 Access Token을 재발급
- Refresh Token DB 관리

## Deployment
- AWS EC2 (Private Subnet)
- ALB 연동
- EC2 UserData 기반 자동 실행
- CI/CD: GitHub Actions → ECR → ASG

## Diagrams
### Architecture

#### 🦴전체 시스템 아키텍처
<img width="1050" height="915" alt="전체 설계안" src="https://github.com/user-attachments/assets/875fba3a-964f-423c-8251-c774bfdda512" />



#### 🐘ERD
<img width="1206" height="857" alt="ERD" src="https://github.com/user-attachments/assets/4e2a211d-c8da-456a-8170-06c19881c1d8" />



#### 🛞CI/CD
<img width="1284" height="373" alt="CICD 흐름" src="https://github.com/user-attachments/assets/e3ea6219-c254-4cf6-aa6b-25edc64ae8f0" />

