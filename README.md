# FILE-LINK 변환기

- FILE을 올려주면 LINK로 변환해주는 API 서버

### Prerequisites Dependencies

- Java Development Kit (JDK) 17+
- Gradle Build Tool
- Dependency
    - Spring Boot
    - AWS S3 SDK
    - Lombok

### API Specifications

## 파일 업로드 API

-클라이언트로부터 전달받은 파일을 업로드하고, 업로드된 파일에 대한 고유한 키를 반환.

## 요청

- **요청 URL**: `/api/file`
- **요청 방식**: `POST`
- **요청 헤더**:
    - `Content-Type`: `multipart/form-data`

- **요청 바디**:
    - `file` : 업로드할 파일 데이터.

## 응답

### 성공 응답

- **상태 코드**: `200 OK`
- **응답 본문**: 업로드된 파일에 대한 UUID 문자열.

```json
{
  "code": 200
  "fileId": "6e3c607e-6bb3-4c8a-8e38-9ea69c55d1f7",
  "description": "upload success",
  "responseTime": "2023-09-16 11:00:00"
}
```

### 실패응답

- **응답 본문**: NULL 반환.

```json
{
  "code": 400
  "fileId": null,
  "description": "실패사유",
  "responseTime": "2023-09-16 11:00:00"
}
```

## 파일 조회 API

- 링크를 통해 파일을 반환

## 요청

- **요청 URL**: `/api/file/{fileId}`
- **요청 방식**: `GET`

## 응답

### 성공 응답

- **상태 코드**: `200 OK`
- **응답 본문**: 업로드된 파일의 byte array

### 실패응답

- **상태 코드**: `400 BAD REQUEST`
- **응답 본문**: 빈 파일 이미지 

