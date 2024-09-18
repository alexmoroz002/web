# SoundStats
Сервис для просмотра статистики Spotify и создания плейлистов  
https://alexmoroz-web.onrender.com/

## Схема базы данных

![Database Scheme](db-scheme.png)

* spring_session - хранит активные сессии пользователей
* spring_session_attributes - хранит атрибуты активных сессий, например объект пользователя (SpotifyAuthorisedUser)
* oauth2_authorized_client - хранит токены доступа и их атрибуты, полученные при авторизации через Spotify
* users - хранит аккаунты пользователей класса User, сохраняемые *после* авторизации
