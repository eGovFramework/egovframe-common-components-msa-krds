# 표준프레임워크 Open Search 연동 검색

![java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=JAVA&logoColor=white)
![Spring_boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![swagger](https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) 
![opensearch](https://img.shields.io/badge/opensearch-005EB8?style=for-the-badge&logo=OPENSEARCH&logoColor=white)
![rabbitmq](https://img.shields.io/badge/rabbitmq-ff6600?style=for-the-badge&logo=RABBITMQ&logoColor=white)
![workflow](https://github.com/eGovFramework/egovframe-template-simple-backend/actions/workflows/maven.yml/badge.svg)

## 기본 환경

프로젝트에서 사용된 환경 프로그램 정보는 다음과 같다.

| 프로그램 명 | 버전 명   |
| :----- | :----- |
| Java   | 1.8 이상 |
| Docker | 4.38.0 |
| Python | 3.11.5 |

## Open Search 설정

- Open Search의 실행은 [Download & Get Started](https://opensearch.org/downloads.html) 페이지에서 `docker-compose.yml`을 다운로드 받거나, 해당 프로젝트의 파일을 사용한다.
- 기본적으로 opensearch-node1, opensearch-node2, opensearch-dashboard 의 3개 서비스로 구성되어 있다.
- Java heap size는 `OPENSEARCH_JAVA_OPTS` 에서 설정 가능하며 ram의 50%까지는 설정하는 것을 추천한다.

### Docker Compose 실행

패스워드는 `docker-compose up` 실행 전에 임의로 환경 변수를 설정하거나, `.env` 파일을 활용하여 설정할 수 있다. 단순 문자열은 실행 시 오류가 발생하므로 대소문자 및 특문을 넣어 설정하여 주도록 한다.

```bash
# 패스워드를 임의로 설정 후 실행
export OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>

docker-compose up
```
![capture01](https://github.com/user-attachments/assets/fe4361d5-0664-42e9-a0f3-1bbe1295103c)

### Dashboard

- Dashboard의 기본 설정 포트는 5601이다.
- `http://localhost:5601/app/login?`
- `Docker-compose.yml` 에 설정하였던 패스워드로 로그인하여 open search 관리자 메뉴를 확인 가능하다.   

![capture02](https://github.com/user-attachments/assets/7d1fd695-3f7e-4426-8016-96a68a6201e2)

### SSL 환경 KeyStore 설정

- 클라이언트 측, 즉 개발자가 본인 PC의 KeyStore에 SSL인증서를 등록해야 한다.
- usr/share/opensearch/config 내의 root-ca.pem을 서버로부터 `jdk 내부 lib > security`에 복사한다.
![capture03](https://github.com/user-attachments/assets/b27c407e-47d0-4fa1-b9eb-4628e2c904be)

- CMD를 관리자 권한으로 열고 해당 폴더로 접근 후 KeyStore 등록 및 확인 절차를 진행한다.

```bash
# 여기서 opensearch는 등록하는 alias 명이며 맨 뒤의 root-ca.pem이 루트 인증서이다
> keytool -importcert -alias opensearch -keystore ./cacerts -file root-ca.pem

# 위 명령어를 실행하면 패스워드를 입력하는데, 임의로 등록해 주면 된다
> Enter keystore password:

# KeyStore 등록이 완료되면 다음과 같이 확인한다
> keytool -list -keystore cacerts

...(생략)...
opensearch, 2024. 4. 19., trustedCertEntry
...(생략)...

```

### 한국어 분석기 설치

- 한국어 형태소 분석을 위해 분석기를 설치해 주어야 한다.

```bash
# 컨테이너 아이디 확인
docker container ls

# 컨테이너 내에서 bash 실행
docker exec -it 컨테이너 아이디 /bin/bash

# opensearch-plugin 디렉토리가 있는 경로로 제대로 왔는지 확인
cd bin
ls

# 한국어 분석기 설치, node 2개에 전부 수행해 주어야 한다
./opensearch-plugin install analysis-nori

# 설치 후 각 노드를 재시작한다
```
![capture04](https://github.com/user-attachments/assets/471d8dec-4169-443d-9c97-175290f9a69d)

- 설치가 완료되었다면 Open Search Dash Board 페이지의 `좌측 메뉴 > Management > Dev Tools` 에서 `GET _cat/plugins`으로 설치된 플러그인 리스트를 확인 가능하다.

![capture05](https://github.com/user-attachments/assets/b380f6b7-97df-431b-ab93-9a6dc3614076)   
![capture06](https://github.com/user-attachments/assets/1a1cd29a-c742-481e-af2f-f47042b59c26)


### 기타 파일 설정

- 프로젝트 루트 경로의 example 디렉토리 내에 위치한 파일은 Open Search의 검색 옵션에 관련된 내용이 기재되어 있다. 필요에 따라 임의로 설정하여 주면 된다.

1. dictionaryRules.txt : 기본적으로 Open Search는 형태소를 분해하여 저장(ex : '동해물과' -> '동해', '물', '과')하지만, 분해를 원하지 않는 경우는 해당 파일에 명시하면 된다.
2. synonyms.txt :  동의어 처리할 단어를 열거하여 준다.
3. stoptags.txt : 한글 검색에서는 보통 명사, 동명사 정도만을 검색하고 조사, 형용사 등은 제거하는 것이 바람직하므로 제거할 품사를 해당 파일에 명시해 준다. 품사의 정보는 [해당 페이지](https://esbook.kimjmin.net/06-text-analysis/6.7-stemming/6.7.2-nori)를 참고한다.

- `동해물과`라는 단어를 분해하는 예시   
![capture07](https://github.com/user-attachments/assets/4bac6e74-f595-4a01-a011-f5b3928bb296)   
- 일반적으로 형태소 분해를 수행한 경우  
![capture08](https://github.com/user-attachments/assets/07a891e1-fbc9-4812-831c-3bab67af5401)   
- `해물`이라는 단어를 분해 금지 설정한 경우, 분해되지 않는 것을 확인 가능하다   
![capture09](https://github.com/user-attachments/assets/33c7894c-5719-4d3c-bfa3-c49c20476e29)   

## RabbitMQ 설정

- 게시판 애플리케이션에서 작성된 데이터를 Open Search Index에 반영하기 위해 `spring-cloud-stream-binder-rabbit`을 이용한 메시지 기반 연동을 수행하고 있다.
- 루트 경로의 `docker-compose\RabbitMQ` 내의 `docker-compose.yml`에는 `RabbitMQ`를 Docker Container에서 실행하기 위한 설정이 기재되어 있다. 초기 id 와 password는 `guest/guest`이다.
- `Queue` 및 `Binder` 설정은 `application.properties`에서 확인 가능하다.

## Onnx 모델 익스포트

- `Onnx (Open Neural Network Exchange)`는 기계학습 모델을 다른 딥러닝 프레임워크 환경 (ex. Tensorflow, PyTorch, etc..)에서 서로 호환되게 사용할 수 있도록 만들어진 공유 플랫폼이다.
- 다양한 프레임워크 간의 호환성 문제를 해결하고, 모델 배포 및 활용에 유연성을 제공할 수 있다.
- 로컬 환경에서 Embedding 작업을 수행하기 위해서는 Embedding 모델을 onnx로 익스포트하여 사용하여야 할 필요가 있으므로 그 과정을 소개한다.

### 사용 모델 소개

-  Huggingface 에 배포되는 여러 모델 중 적합한 모델을 취사 선택하여 `Onnx (Open Neural Network Exchange)`로 변환 후, 로컬에서 사용하는 것이 가능하다.
- 본 프로젝트에서는 [ko-sroberta-multitask](https://huggingface.co/jhgan/ko-sroberta-multitask) 모델을 사용하는 방법을 소개한다.
- Python에서 가상 환경을 설정하고 필요한 패키지를 설치하며, 모델을 `Onnx` 포맷으로 익스포트한다.

```bash
# 패키지 버전 충돌을 방지하기 위해 venv 라는 이름으로 가상 환경을 생성한다
python3 -m venv venv

# 윈도우 환경에서 가상환경을 활성화 한다
.\venv\Scripts\activate

# 리눅스, macOS 환경에서 가상환경을 활성화 한다
source ./venv/bin/activate //

# 가상 환경이 활성화 된 상태에서 pip를 최신 버전으로 업그레이드한다
python -m pip install --upgrade pip

# 필요한 패키지들을 설치한다
pip install optimum onnx onnxruntime sentence-transformers

# 현재 경로에 jhgan/ko-sroberta-multitask 모델을 onnx 포맷으로 내보낸다
optimum-cli export onnx -m jhgan/ko-sroberta-multitask .
```

- 익스포트가 완료되면 Embedding에 사용되는 `tokenizer.json` 및 `model.onnx` 파일이 생성된다.
- 해당 파일들은 프로젝트 루트 경로의 model 디렉토리에 위치시켜 준다.
- 상세 정보는 Spring에서 제공하는 [공식 문서](https://docs.spring.io/spring-ai/reference/api/embeddings/onnx.html#_prerequisites) 를 참조 가능하다.

## Open Search 관련 API 설명

- 기능 확인 및 테스트 편의를 위해, Open Search의 Index 생성 및 삭제, 초기 데이터 입력을 수행하는 API를 Springdoc (구 Swagger)로 제공한다.
- 초기 설정 기준으로 `http://localhost:9993/swagger-ui/index.html#/` 에서 확인 및 실행이 가능하다.

  ![capture10](https://github.com/user-attachments/assets/cd191b8c-8042-4c5e-8993-47675c597499)   

1. `/createEmbeddingIndex` : Embedding 필드가 포함된 Index 를 생성한다. Index 자체만 생성하므로 실행 완료 시점에서는 Index 내 문서 데이터는 존재하지 않는다. 인덱스 명은 `application.properties` 파일에 정의된 `opensearch.embedding.indexname` 값을 따른다.
2. `/createTextIndex` : 텍스트 필드만 존재하는 Index 를 생성한다. 초기 설정 기준으로 두 Index를 모두 만들어 줘야 한다. 인덱스 명은 `application.properties` 파일에 정의된 `opensearch.text.indexname` 값을 따른다.
3. `/insertEmbeddingData` : mysql의 `COMTNBBS`(게시판 데이터) 테이블 의 전체 데이터와 질의 내용을 Vector로 변환한 값을 Index에 넣는다. Index 생성 및 `Onnx` 모델의 익스포트 작업이 선행되어야 한다.
4. `/insertTextData` : mysql의 `COMTNBBS`(게시판 데이터) 테이블 의 전체 데이터를 Index에 넣는다. Index 생성이 선행되어야 한다.
5. `/deleteIndex/{indexName}` : 만들어진 Index를 삭제한다.

## Application.properties

### 기본 설정

- `server.port` : 포트 번호
- `spring.application.name` : 애플리케이션 명
- `spring.main.allow-bean-definition-overriding` : 수동 bean 이 자동 bean을 overriding 하도록 설정
- `logging.level.org.springframework.boot.autoconfigure` : Spring Boot의 자동 구성 관련 로그 설정
- `logging.level.org.springframework.web` : Spring Web 모듈의 로그 설정
- `logging.level.egovframework.rte` : 표준프레임워크의 런타임 환경 로그 설정
- `logging.level.egovframework.com` : 표준프레임워크의 공통 모듈 로그 설정
- `server.tomcat.connection-timeout` : Tomcat 서버의 연결 타임아웃 설정. -1 이면 비활성화

### Data Source 설정

- `spring.datasource.driver-class-name` : 데이터베이스 Driver Class명. 초기 설정은 mySql이며 `com.mysql.cj.jdbc.Driver` 로 설정되어 있다.
- `spring.datasource.url` : 데이터베이스 Driver Class Url. 초기 설정은 mySql이며 `jdbc:mysql://localhost:3306/comall?serverTimezone=Asia/Seoul&characterEncoding=UTF-8` 로 설정되어 있다.
- `spring.datasource.username` : 유저명
- `spring.datasource.password` : 패스워드

### Jpa 설정

- `spring.jpa.open-in-view` : false 로 설정할 시 transaction을 종료할 때 영속성 컨텍스트가 닫히게 된다.
- `spring.jpa.show-sql` : Hibernate가 생성하고 실행하는 SQL 쿼리의 콘솔 출력 여부
- `spring.jpa.properties.hibernate.format_sql` : 쿼리 로그의 formatting 설정

### Springdoc (구 Swagger) 설정

- `springdoc.version` : 생성된 API 문서에 표시될 버전 정보
- `springdoc.packages-to-scan` : 문서화할 API 대상 패키지를 명시
- `springdoc.swagger-ui.tags-sorter` : API 그룹 정렬 방식. `alpha`면 알파벳 순으로 정렬되게 된다
- `springdoc.swagger-ui.operations-sorter` : API 정렬 방식. `alpha`면 알파벳 순으로 정렬되게 된다
- `springdoc.swagger-ui.doc-expansion` : API 문서의 초기 확장 상태 설정. `none` 이면 최초에 모든 API가 접혀 있는 상태로 표시된다.
- `springdoc.api-docs.path` : OpenAPI 문서의 json 엔드포인트 경로를 명시
- `springdoc.api-docs.groups.enabled` : API 문서의 그룹별 분류 여부
- `springdoc.cache.disabled` : API 문서의 캐싱을 설정. `true`라면 매 요청마다 최신 API 문서가 생성된다.

### Open Search 관련 설정

- `opensearch.protocol` : Open Search 서버와의 통신 프로토콜 설정. 기본적으로 `https`로 지정되어 있다.
- `opensearch.url` : Open Search 서버의 호스트 주소
- `opensearch.port` : Open Search 서버가 리스닝하고 있는 포트 번호. 기본 설정은 9200 이다.
- `opensearch.username` : Open Search 유저명. 기본 설정은 `admin`
- `opensearch.password` : Open Search 패스워드. `docker-compose.yml`에서 최초 설정한 비번을 설정한다.
- `opensearch.keystore` : SSL/TLS 연결에 사용할 키스토어 파일의 경로
- `opensearch.keystore.password` : 키스토어 파일에 접근하기 위한 패스워드

### 기타 설정

- `opensearch.text.indexname` :  API로 OpenSearch Index를 생성했을 경우 디폴트 인덱스 명
- `opensearch.embedding.indexname` :  API로 Embedding 필드가 포함된 OpenSearch Index를 생성했을 경우 디폴트 인덱스 명
- `index.batch.size`: API로 mySql의 데이터를 OpenSearch Index에 넣는 작업 수행 시, 분할 처리할 데이터 수
- `egov.textsearch.count` : 통합 검색 시, 제공할 결과의 전체 데이터 수
- `egov.textsearch.page.size` : 통합 검색 시, 한 페이지에 보여 줄 데이터 수
- `egov.embeddingsearch.count` : 벡터 검색 시, 제공할 결과의 전체 데이터 수
- `egov.embeddingsearch.page.size` : 벡터 검색 시, 한 페이지에 보여 줄 데이터 수

## 구동 및 확인

- 초기 설정 기준으로 브라우저에서 `http://localhost:9993/` 로 검색 메인 화면을 확인 가능하다. 해당 화면에서  Open Search Index에 대한 match 검색 및 Vector 검색을 수행할 수 있다.
- 화면의 `통합 검색` 버튼은 Open Search 의 Index에서 full-text 검색을 수행한 결과를 제공한다.
- 화면의 `벡터 검색` 버튼은 질의어를 Vector 값으로 변환하여 가장 근접한 결과를 제공한다.

## 검색 방식

- 본 Open Search 연동 검색에서는 Match Query 방식을 사용한 Full-Text 검색과 벡터 검색 2가지의 방식을 제공하고 있다.
  
###  Match Query

- 질의어를 분석하여 지정된 필드에서 전체 텍스트를 검색한다.
- 분석기의 문자 필터(Character Filters), 토크나이저(Tokenizers), 그리고 토큰 필터(Token Filters) 설정에 따라 다양한 구현이 가능하다.
- 오타의 여부를 고려하여 `fuzziness` 옵션을 주는 것도 가능하다.
- `AUTO`로 설정한 경우 3~5자의 문자열은 1회의 수정, 5자보다 길면 2번 수정이 허용된다.

### Vector Search

- 검색 대상 필드 및 질의어를 Vector 값으로 변환 (Embedding) 하여 검색을 수행한다.
- Embedding : 자연어를 기계가 이해할 수 있는 숫자의 나열인 Vector로 바꾸는 과정을 의미한다.
- Vector로 간 거리를 계산하여 제일 가까운 값을 가진 문서를 제일 유사한 문서라고 판단한다.
- 검색에 이용되는 알고리즘으로는 K-NN(K-최근접 이웃) 알고리즘을 사용한다.
- 인덱스의 벡터 값과 검색어의 벡터 값 사이의 거리를 계산하는 데 사용되는 함수는 디폴트 값인 코사인 유사도(Cosine Similarity)를 사용하며 정에 따라 유클리드 거리(Euclidean Distance)나 맨해튼 거리(Manhattan Distance)와 같은 다른 거리 계산 방법도 지원한다.
- K-NN(K-최근접 이웃) 알고리즘에 대한 상세한 설명은 [해당 문서](https://aws.amazon.com/ko/blogs/tech/choose-the-k-nn-algorithm-for-your-billion-scale-use-case-with-opensearch/ ) 를 참고 가능하다.
