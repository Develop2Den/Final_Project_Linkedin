### what is running now
```shell
docker ps
```

### stop container
```shell
docker stop {container-name}
```

### start container (docker-compose.yaml) DON'T CLOSE
```shell
docker-compose up
```

### jump into container (pg_2)
```shell
docker exec -ti step_2 bash
```

### inside container:

#### become postgres user
```shell
su user_tinder
```

#### create database (fs8)
```shell
createdb DB_tinder
```

### exit from user postgres
```shell
exit
```

### exit from container
```shell
exit
```

### Пароли для OAuth2  Google Зайти в файл - application-prod.properties и скопировать в него эти строчки (только то, что после равно): 
```
id=143568406207-ghs0s1apg6pn92a71qu72vgqgcojla5p.apps.googleusercontent.com
secret=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
```
### Данные для входа в удаленную базу данных:
```
 url=jdbc:postgresql://ep-wandering-wind-a5r8acut.us-east-2.aws.neon.tech/LinkedinDB?sslmode=require;
 username=LinkedinDB_owner;
 password=cg9vWeo3yrVF;

```
### Токен SonarQube
```
token - a9f815397eaf09418739108a689c5aa2b98cc9bb

```

### Сваггер-адрес
```
http://localhost:9000/swagger-ui/index.html

```



