
# 🤖 *Discord Bot _ 항공이* 
[![Spring Boot](https://img.shields.io/badge/SpringBoot-3.2-green?logo=springboot&logoColor=white&style=flat)](https://spring.io/projects/spring-boot)
[![JDA](https://img.shields.io/badge/JDA-GitHub-blue?logo=discord&logoColor=white&style=flat)](https://github.com/discord-jda/JDA)
[![Airport_API](https://img.shields.io/badge/Airport_API-Data%20.go.kr-green?logo=googlechrome&logoColor=white&style=flat)](https://www.data.go.kr/data/15095093/openapi.do)
[![Exchange_API](https://img.shields.io/badge/Exchange_API-Data%20.go.kr-green?logo=googlechrome&logoColor=white&style=flat)](https://www.data.go.kr/data/3068846/openapi.do)

![항공이 정보 이미지](/readme_img/Info.png)

### 인천공항 입ㆍ출입 항공편 정보 조회 및 환율 정보 실시간 제공 서비스 
[항공이_Discord 추가](https://discord.com/oauth2/authorize?client_id=1271753799660474439)

## 📌 명령어 사용법

![항공이 명령어 이미지](/readme_img/Instructions.png)

`/도움말`을 클릭하면 해당 봇 사용법에 대해서 자세하게 설명해주고 있습니다.

`/항공편`을 통해서 `항공편명`을 입력하면 인천공항의 입ㆍ출입하는 항공편에 대한 정보를 얻을 수 있습니다.

`/환율`을 통해서 `국가`를 입력하면 실시간 활율 정보를 조회하실 수 있습니다.

## ⚙️ 빌드 및 실행 전 설정

프로젝트를 빌드하거나 실행하기 전에, `resources/application_example.properties` 파일에 정의된 예시를 참고하여 `application.properties`를 직접 만들어야 합니다.

```bash
# 예시 파일 경로
resources/application_example.properties

#application.properties : 파일 설정 예시
spring.application.name=Discord_AirportBot

# Discord Bot TOKEN
Token = token

# Airport API
api.url = http://apis.data.go.kr/B551177/StatusOfPassengerFlightsOdp/getPassengerDeparturesOdp
api.key = key

# ExchangeRate API
api.url_1 = https://www.koreaexim.go.kr/site/program/financial/exchangeJSON
api.key_1 = key
```

