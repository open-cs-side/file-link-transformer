# File-Link Transformer

- 파일을 업로드하면 링크로 변환해주는 API 서버

### Prerequisites Dependencies

- Java Development Kit (JDK) 17+
- Gradle Build Tool
- Dependency
    - Spring Boot Web MVC
    - AWS S3 SDK
    - Lombok

<hr>

## API Specifications

1. [파일 업로드 API](#파일-업로드-api)
  - [정상 응답](#정상-응답)
  - [예외시 응답](#예외시-응답)
2. [파일 조회 API](#파일-조회-api)
  - [정상 응답](#정상-응답)
  - [예외시 응답](#예외시-응답)

<hr>

## 파일 업로드 API

- 클라이언트로부터 전달받은 파일을 업로드하고, 업로드된 파일에 대한 고유한 키를 반환.

### 요청

- **요청 URL**: `/api/file`
- **요청 방식**: `POST`
- **요청 헤더**:
    - `Content-Type`: `multipart/form-data`

- **요청 바디**:
    - `file` : 업로드할 파일 데이터.

### 응답

#### 성공 응답

- **상태 코드**: `200 OK`
- **응답 본문**: 업로드된 파일에 대한 UUID 문자열
- 응답 예시

```json
{
  "data": {
    "fileId": "80f4b3bd-39ec-46b3-a7c6-592fe307f627"
  },
  "responseTIme": "2023-09-17T19:10:46.115775",
  "code": 200,
  "description": "success"
}
```


#### 실패응답

- **응답 본문**:  NULL 반환 , 실패 사유 반환
- 응답 예시

```json
{
  "data": null,
  "responseTIme": "2023-09-17T19:13:58.508024",
  "code": 400,
  "description": "File does not Exist"
}
```

## 파일 조회 API

- 링크를 통해 파일을 반환

### 요청

- **요청 URL**: `/api/file/{fileId}`
- **요청 방식**: `GET`

### 응답

##### 성공 응답

- **상태 코드**: `200 OK`
- **응답 본문**: 업로드된 파일의 byte array

##### 실패응답

- **상태 코드**: `400 BAD REQUEST`
- **응답 본문**: 빈 파일 아이콘이 든 이미지 

