# find_myself
### 나 혼자 찾는다
implement in intellij (springboot, jpa) **개발 진행 중**

<img width="400" src=https://user-images.githubusercontent.com/66946182/117409625-58f8ae80-af4c-11eb-810f-0073f528998d.png>

#### 공공데이터를 활용하여 사용자의 요구에 맞는 행정동을 추천해주는 프로그램 

### 세부 기능 
* 카카오맵 api 를 이용, 조건에 맞는 행정동을 색칠하는 기능 
* 카카오 로그인 api,JPA 를 이용한 로그인 / 회원가입 기능
* 사용자 조회수 기반 추천 기능 
* 군집 분석을 통한 행정동 추천 기능 
* 교통 관련 : Tmap API 를 사용한 자동차와 보행자의 경로 제공, 오디세이 API 를 사용한 대중교통 경로 제공


### Screen Shots

0 ) 소개 화면 

<img width="800" src=https://user-images.githubusercontent.com/66946182/120468666-6d5d8900-c3dc-11eb-953f-802da7873952.png>

1 ) 메인 화면

<img width="800" src=https://user-images.githubusercontent.com/66946182/117404014-23e85e00-af44-11eb-8eaa-4daf75481875.png>

2 ) 로그인 화면 

<img width="600" src=https://user-images.githubusercontent.com/66946182/117404975-d836b400-af45-11eb-8127-1c2e92204131.png>

3 ) 검색 버튼을 누른 경우 

- 조건에 맞는 행정동을 지도에 폴리곤으로 표시해 추천

<img width="800" src=https://user-images.githubusercontent.com/66946182/117405224-3cf20e80-af46-11eb-94b8-c164521f977e.png>
<img width="800" src=https://user-images.githubusercontent.com/66946182/117405270-5430fc00-af46-11eb-8a0f-c3347980500f.png>

4 ) 지도에서 특정 행정동을 클릭 할 시 분석 화면으로 이동 

- 큰 화면에서 볼 경우 

<img width="800" src=https://user-images.githubusercontent.com/66946182/117406019-724b2c00-af47-11eb-9ccc-298473ce813a.png>
<img width="800" src=https://user-images.githubusercontent.com/66946182/117407307-56488a00-af49-11eb-83ff-b05974515c06.png>

- 작은 화면에서 볼 경우 


<div>
<img width="49%" src=https://user-images.githubusercontent.com/66946182/117407541-a9bad800-af49-11eb-8346-22f453771d24.png>
<img width="49%" src=https://user-images.githubusercontent.com/66946182/117406347-e554a280-af47-11eb-9603-5bff9f04ed5c.png>
</div>
<div>
<img width="49%" src=https://user-images.githubusercontent.com/66946182/117406826-92c7b600-af48-11eb-9970-df38ea936850.png>
<img width="49%" src=https://user-images.githubusercontent.com/66946182/117407733-edaddd00-af49-11eb-88b0-08253b5e613e.png>
</div>

* push, clone 시 토큰 이용해야 organization repo push, clone 가능함...

  https://velog.io/@ruddms936/github-토큰-생성

* 행정동 기준 json 파일 출처 

  https://github.com/vuski/admdongkor

