# SoundStats
Сервис для просмотра статистики Spotify и создания плейлистов  
https://alexmoroz-web.onrender.com/

## Схема базы данных

![Database Scheme](db-scheme.png)

* spring_session - хранит активные сессии пользователей. Контролируется Spring
* spring_session_attributes - хранит атрибуты активных сессий, например объект пользователя (SpotifyAuthorisedUser). Контролируется Spring
* oauth2_authorized_client - хранит токены доступа и их атрибуты, полученные при авторизации через Spotify. Контроллируется Spring
* users - хранит аккаунты пользователей класса User, сохраняемые *после* авторизации
