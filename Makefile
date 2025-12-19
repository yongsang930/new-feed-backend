AWS_REGION := ap-northeast-2
AWS_ACCOUNT_ID := 277905774993
ECR_REPO := newfeed-backend
ECR_URI := $(AWS_ACCOUNT_ID).dkr.ecr.$(AWS_REGION).amazonaws.com/$(ECR_REPO)

.PHONY: build
build:
	./gradlew clean build -x test

.PHONY: docker-build
docker-build:
	docker build -t $(ECR_URI):latest .

.PHONY: docker-login
docker-login:
	aws ecr get-login-password --region $(AWS_REGION) \
	| docker login --username AWS --password-stdin $(AWS_ACCOUNT_ID).dkr.ecr.$(AWS_REGION).amazonaws.com

.PHONY: docker-push
docker-push:
	docker push $(ECR_URI):latest

.PHONY: deploy
deploy: build docker-build docker-login docker-push