# link-simplier
Генератор коротких ссылок

Для запуска:
Поднять в контейнере базу данных, выполнив docker-compose -f docker-compose-db.yml up -d
Поднять redis, выполнив docker run --name some-redis -d redis
Поднять в контейнере приложение, выполнив docker-compose -f docker-compose-app.yml up -d

Механизм генерации короткой ссылки: библиотека hashids, уникальность гарантируется числовым ключом, 
им являяется id ссылки из бд.

Генерация короткой ссылки. Оригинальная ссылка передается в параметрах запроса, пример:
POST api/v1/generate
Пример тела запроса:
{
“original”: “https://some-server.com/some/url?some_param=1”
}
Пример ответа:
{
“link”: “/l/some-short-name”
}

Переход по короткой ссылке, пример запроса: GET /api/v1//l/some-short-name
После этого выполняется redirect на оригинальный url

Статистика по ссылкам, пример запроса:
GET api/v1//stats/some-short-name
Пример ответа:
{
“link”: “/l/some-short-name”,
“original”: “http://some-server.com/some/url”
“rank”: 1,
“count”: 100500
}

Рейтинг ссылок, пример запроса:
Пример запроса:
GET api/v1/stats?page=1&count=2
Пример ответа:
[
{
“link”: “/l/some-short-name”,

“original”: “http://some-server.com/some/url”
“rank”: 1,
“count”: 100500
},
{
“link”: “/l/some-another-short-name”,
“original”: “http://another-server.com/some/url”
“rank”: 2,
“count”: 40000
}
]

Особенности:
Запросы на генерацию ссылки и переход по ссылкам кешируются в распределенном кеше (redis), что обеспечивает горизонтальное масштабирование.
Обновление статистики по ссылкам выполняется периодически запуском задачи по расписанию.  
В случае использования одного инстанса приложения, запустить его с профилем cron
В случае использования нескольких инстансов приложения, одно из них запустить с профилем cron