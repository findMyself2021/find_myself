# find_myself

intellij mysql 설정
---

## 중요!!

* 데이터베이스 이름은 findmyself 로 통일 (아니면 에러남)
```
url: jdbc:mysql://localhost:3306/findmyself?useSSL=false&characterEncoding=UTF8&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
```

* mysql 비밀번호는 1234 (아니면 에러남)


### application.yml 설정 

```
server:
  address: localhost
  port: 8080
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/findmyself?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC
    username: root
    #비밀번호 통일해!
    password: 1234
  jpa:
    database: mysql
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    generate-ddl: true
    show-sql: true
```
