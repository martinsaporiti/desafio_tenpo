# Desafío Tenpo.
![Java CI with Maven](https://github.com/martinsaporiti/desafio_tenpo/workflows/Java%20CI%20with%20Maven/badge.svg)
![Publish Docker image](https://github.com/martinsaporiti/desafio_tenpo/workflows/Publish%20Docker%20image/badge.svg)

## Requerimientos del Desafío.
Debes desarrollar una API REST en Spring Boot con las siguientes funcionalidades:

1. Sign up usuarios.
2. Login usuarios.
3. Sumar dos números. Este endpoint debe retornar el resultado de la suma y puede ser consumido solo por usuarios logueados. 
4. Historial de todos los llamados al endpoint de suma. Responder en Json, con data paginada y el límite se encuentre en properties.
5. Logout usuarios.
6. El historial y la información de los usuarios se debe almacenar en una database PostgreSQL.
7. Incluir errores http. Mensajes y descripciones para la serie 400.


## Lombok
Este desafío fue desarrollado utilizando [Lombok](https://projectlombok.org/). Si usted desea importar este código en 
un IDE deberá tener instalado el plugin correspondiente. En la página oficial de [Lombok](https://projectlombok.org/) 
encontrará como instalar el plugin para cada IDE.

## Tener en cuenta.
El código de este desafío no incluye scripts o código que cargue información en la base de datos cuando la aplicación 
se ejecuta por primera vez. Por esto, se recomienda primero registrarse como usuario y luego autenticarse para obtener 
el token de acceso a la API REST.

## Estructura de directorios del código fuente
Dentro del directorio *src/main/java* se encuentra el paquete principal de la aplicación **cl.tenpo.desafio** el cual 
contiene en su interior la siguiente estructura de paquetes:
```bash
├── config # Paquete con clases de configuración para la seguridad (Spring Security) y Swagger.
├── domain # Entidades de Dominio.
├── exception # Paquete con excepciones de dominio
├── repository # Paquete con los repositorios JPA de la aplicación. 
├── security # Contiene clases útiles que segurizan la API REST.
    ├── jwt # Paquete con la clase JwtTokenUtil para la manipulación del token JWT.
├── services # Paquete con los servicios (y sus interfces. 
└── web # Este paquete contiene todas las clases de la capa "web", capa que expone la API REST de la aplicación.
    ├── aspect # Paquete con los los aspectos de la capa web.
    ├── controller # Paquete con los Rest Controllers.
    ├── exception # Este paquete contiene a la clase RestExceptionHandler que maneja las excepciones provenientes de 
                  # otras capas de la aplicación.
    ├── mapper # Este paquete contiene los "mapeadores" de objetos de dominio a DTOs y visceversa. 
    └── model # El modelo que se le restorna a los clientes de la API REST (DTOs).
```

## Configuración
Se pueden configurar las propiedades de la aplicación modificando los valores por defecto en el archivo 
application.properties. Estos son algunos de las propiedades que se pueden customizar:

```
# Cantidad de resultados a retornar cuando se lista el historial de operaciones.
page.size=4

# Secret utilizado para la generación del token JWT.
jwt.secret=desafio-tenpo

# Tiempo de validez del token JWT.
jwt.token.validity=18000
```

La modificación de los valores de las propiedades del archivo application.properties es útil para ambientes de desarrollo. 
En un ambiente de testing o producción es conveniente configurar tales valores mediante la definición de variables de 
entorno siguiendo los lineamientos de las aplicaciones que cumplen con 
los [12 factores](https://12factor.net/es/). 

## Construyendo la imagen Docker de la aplicación.
Para construir la imagen docker de la aplicación simplemente hay que ejecutar el siguiente comando:
```bash
# Debemos estar parados en el directorio raíz de la aplicación.
$ docker build -t desafio:latest .
```

**El archivo Docker contiene la configuración necesaria para compilar la aplicación, empaquetarla y generar la imagen 
Docker.** Para esto, utiliza una estrategia de *multi-stage builds*.

## Poniendo a correr la aplicación mediante Docker Compose.
Para ejecutar la aplicación mediante docker compose, utilizando Postgres como base de datos se debe ejecutar el 
siguiente comando:

```bash
$ docker-compose up
```

*Nota: El archivo docker.compose.yaml no genera una imagen nueva, sino que utiliza la imagen del repositorio 
martinsaporiti de [DockerHub](https://hub.docker.com/repository/docker/martinsaporiti/desafio)*  

## Swagger para probar la API.
La API de la aplicación puede ser testeada utilizando la interface web que provee Swagger (el puerto debe ser el mismo 
que el configurado para la aplicación):
```
http://localhost:8080/swagger-ui.html
```

### API REST
En este apartado listan los endpoints implementados indicando el body que espera recibir cada uno de ellos.

### API de Usuarios
<br>

1. **/api/v1/user/login [POST] - No Segurizado <br>**
Endpoint para autenticarse. <br>
Body:
```json
{
  "password": "string",
  "username": "string"
}
```

2. **/api/v1/user/signup [POST] - No Segurizado <br>**
Endpoint para crear un usuario. <br>
Body:
```json
{
  "password": "string",
  "username": "string"
}
```

3. **/api/v1/user/signup [GET] - Segurizado (necesita del envío del token en el Header)**

### API de Operaciones
<br>

1. **/api/v1/operation/ [GET] - Segurizado (necesita del envío del token en el Header) <br>**
Endpoint que retorna el historial de operaciones.
Se debe invocar especificando el valor para el parámtro *pageNumber*
Ejemplo:
```bash
/api/v1/operation/?pageNumber=1
```

2. **/api/v1/operation/plus [POST] - Segurizado (necesita del envío del token en el Header) <br>**
Enpoint que retorna la suma de los números que recibe en el body:
```json
{
  "num1": Integer,
  "num2": Integer
}
```



