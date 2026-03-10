https://0a8300c9034c094b803d6c8c00d5003f.web-security-academy.net/filter?category=Pets%27+OR+1=1--

Pets' OR 1=1--

Recuperación de datos ocultos

Imagine una aplicación de compra que muestra productos en diferentes categorías. Cuando el usuario hace clic en la categoría de regalos, su navegador solicita la URL:
https://insecure-website.com/products?category=Gifts

Esto hace que la aplicación realice una consulta SQL para recuperar detalles de los productos relevantes de la base de datos:
SELECT * FROM products WHERE category = 'Gifts' AND released = 1

Esta consulta SQL pide a la base de datos que devuelva:

    Todos los detalles (*)
    Desde el productsMesa
    Donde el categoryEs Gifts
    y releasedEs 1.

La restricción released = 1Se está utilizando para ocultar productos que no se liberan. Podríamos asumir productos inéditos, released = 0.

La aplicación no implementa ninguna defensa contra los ataques de inyección SQL. Esto significa que un atacante puede construir el siguiente ataque, por ejemplo:
https://insecure-website.com/products?category=Gifts'--

Esto se traduce en la consulta SQL:
SELECT * FROM products WHERE category = 'Gifts'--' AND released = 1

Crucialmente, tenga en cuenta que --Es un indicador de comentarios en SQL. Esto significa que el resto de la consulta se interpreta como un comentario, eliminándolo efectivamente. En este ejemplo, esto significa que la consulta ya no incluye AND released = 1. Como resultado, se muestran todos los productos, incluidos los que aún no se han lanzado.

Puede usar un ataque similar para hacer que la aplicación muestre todos los productos en cualquier categoría, incluidas las categorías que no conocen:
https://insecure-website.com/products?category=Gifts'+OR+1=1--

Esto se traduce en la consulta SQL:
SELECT * FROM products WHERE category = 'Gifts' OR 1=1--' AND released = 1

La consulta modificada devuelve todos los elementos donde el categoryEs Gifts, o 1Es igual a 1. Como 1=1Siempre es cierto, la consulta devuelve todos los elementos. 
