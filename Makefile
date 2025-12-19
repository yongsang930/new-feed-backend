# ===== 기본 변수 =====
AWS_REGION := ap-northeast-2
AWS_ACCOUNT_ID := 277905774993
ECR_REPO := newfeed-backend
ECR_URI := $(AWS_ACCOUNT_ID).dkr.ecr.$(AWS_REGION).amazonaws.com/$(ECR_REPO)

# Git 커밋 해시 (로컬 / CI 공용)
GIT_SHA := $(shell git rev-parse --short HEAD)

# ===== Targets =====

.PHONY: build
build:
	./gradlew clean build -x test

.PHONY: docker-build
docker-build:
	docker build -t $(ECR_REPO):$(GIT_SHA) .

.PHONY: docker-tag
docker-tag:
	docker tag $(ECR_REPO):$(GIT_SHA) $(ECR_URI):$(GIT_SHA)

.PHONY: docker-login
docker-login:
	aws ecr get-login-password --region $(AWS_REGION) \
	| docker login --username AWS --password-stdin $(AWS_ACCOUNT_ID).dkr.ecr.$(AWS_REGION).amazonaws.com

.PHONY: docker-push
docker-push:
	docker push $(ECR_URI):$(GIT_SHA)

.PHONY: deploy
deploy: build docker-build docker-tag docker-login docker-push
