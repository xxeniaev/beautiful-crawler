# Web Crawler

Web Crawler - это мини приложение на языке Scala, которое осуществляет HTTP-запросы к списку URL и извлекает содержимое тега `<title>` с каждой страницы. Приложение предоставляет HTTP эндпоинт для получения списка URL и возврата найденных заголовков.

## Используемые библиотеки

- **Cats Effect**: для работы с эффектами и асинхронным кодом.
- **http4s**: для создания HTTP-сервера и маршрутизации.
- **Circe**: для работы с JSON (парсинг и генерация).
- **Jsoup**: для парсинга HTML.
- **ip4s**: для работы с IP-адресами и портами.

## Установка и запуск

### Требования

- Scala 2.13.x
- SBT (Scala Build Tool)

### Сборка и запуск

1. Клонируйте репозиторий:

    ```sh
    git clone https://github.com/yourusername/beautiful-crawler.git
    cd beautiful-crawler
    ```

2. Запустите сервер:

    ```sh
    sbt run
    ```

### Использование

После запуска сервер будет доступен по адресу `http://localhost:8080`.

#### Эндпоинт

- **POST /extract-titles**: принимает JSON с списком URL и возвращает JSON с заголовками страниц.

#### Пример запроса с `curl`

Отправьте POST запрос на эндпоинт `/extract-titles` с JSON телом, содержащим список URL и в ответе получите список названий сайтов

```sh
curl -X POST http://localhost:8080/extract-titles \
  -H "Content-Type: application/json" \
  -d '{"urls":["https://en.wikipedia.org/wiki/Ancient_Egypt", "https://en.wikipedia.org/wiki/Nile"]}'
```

```sh
{
  "results": {
    "https://en.wikipedia.org/wiki/Ancient_Egypt":"Ancient Egypt - Wikipedia",
    "https://en.wikipedia.org/wiki/Nile":"Nile - Wikipedia"
    }
}
```
