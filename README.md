Требования для запуска.

- PostgreSQL 9.x или 10.x
- JRE 8

Настройка PostgreSQL:
```
su postgres;
$psql

CREATE ROLE motiv WITH LOGIN PASSWORD 'mysecret';
CREATE DATABASE myrest WITH OWNER motiv ENCODING 'UTF8';
\c myrest motiv
\i /home/user/myrest.sql
\i /home/user/fill.sql
\q
```

![ER](https://github.com/Anoncheg1/myrest/blob/master/myrest.png "ER diagram")

```
1) Создание проекта			rest/projects/create
2) Изменение проекта			rest/projects/{id: [0-9]*}/update
3) Удаление проекта			rest/projects/{id: [0-9]*}/delete
4) Создание работы			rest/projects/{id: [0-9]*}/jobs/create
5) Изменение работы			rest/jobs/{id: [0-9]*}/update
6) Удаление работы			rest/jobs/{id: [0-9]*}/delete
v Получение требуемого проекта rest/projects/{id: [0-9]*}
v Получение всех проектов		rest/projects
v Получение всех работ требуемого проекта rest/projects/{id: [0-9]*}/jobs
v Получение всех проектов с нераспределенным бюджетом rest/projects/nerasp
v Получение всех просроченных работ rest/jobs/prosroch

1)
curl -i -X POST -H "Content-Type: application/json" -d "{ \
\"name\": \"value1\", \
\"fb\": 20000, \
\"rb\": 20000, \
\"idState\": 1 \
}" http://localhost:8080/rest/projects/create

2) обязательное поле только id
curl -i -X PUT -H "Content-Type: application/json" -d "{ \
\"id\": 1, \
\"name\": \"Project 1\", \
\"fb\": 200, \
\"rb\": 200 \
}" http://localhost:8080/rest/projects/1/update

3)
curl -i -X DELETE http://localhost:8080/rest/projects/4/delete

4)
curl -i -X POST -H "Content-Type: application/json" -d "{ \
\"name\": \"job for 3 project\", \
\"fb\": 50, \
\"rb\": 50, \
\"nach\": \"2018-10-01\", \
\"dliteln\": \"10\" \
}" http://localhost:8080/rest/projects/3/jobs/create


5) обязательное поле только id
curl -i -X PUT -H "Content-Type: application/json" -d "{ \
\"id\": 6, \
\"name\": \"job for 1 project\", \
\"fb\": 20, \
\"rb\": 20, \
\"nach\": \"2018-10-18\", \
\"okonch\": null, \
\"dliteln\": 20, \
\"vipPr\": 1 \
}" http://localhost:8080/rest/jobs/6/update

6) curl -i -X DELETE http://localhost:8080/rest/jobs/1/delete

```
