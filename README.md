# Literalura

Literalura es una aplicación Java basada en Spring Boot que permite buscar libros por título, registrarlos en una base de datos y obtener información sobre ellos utilizando la API de Gutendex. La aplicación también permite listar libros y autores, así como consultar autores vivos en un año específico y libros por idioma.

## Características

1. **Buscar libro por título:** La persona usuaria ingresa el título del libro y la aplicación lo busca en la API Gutendex. Si lo encuentra, lo registra en la base de datos.
2. **Listar libros:** Muestra todos los libros registrados en la base de datos.
3. **Listar autores:** Muestra todos los autores registrados en la base de datos.
4. **Listar autores vivos en un año específico:** Muestra los autores que estaban vivos en un año determinado.
5. **Listar libros por idioma:** Muestra los libros en un idioma específico.

## Requisitos

- Java 17 o superior
- Maven 3.6.3 o superior
- PostgreSQL 12 o superior

## Instalación

1. Clona el repositorio:

git clone https://github.com/tu-usuario/literalura.git
cd literalura

2. Configura la base de datos PostgreSQL:

CREATE DATABASE literalura;
CREATE USER literalura_user WITH PASSWORD 'tu_password';
GRANT ALL PRIVILEGES ON DATABASE literalura TO literalura_user;

3.Configura las propiedades de la base de datos en "src/main/resources/application.properties:"

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=literalura_user
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update

Uso
Al ejecutar la aplicación, se presentará un menú en la consola con las siguientes opciones:

Consultar libro por título
Listar todos los libros
Listar libros por idioma
Listar todos los autores
Listar autores vivos en un año determinado
Salir
Selecciona una opción ingresando el número correspondiente y sigue las instrucciones en pantalla.

Ejemplo de Uso
Buscar libro por título

Ingrese el título del libro: Romeo and Juliet

Libro encontrado y agregado al catálogo:
Título: Romeo and Juliet
Autor: Shakespeare, William (1564 - 1616)
Idiomas: en
Número de Descargas: 77782

Listar todos los libros

Título: Romeo and Juliet
Autor: Shakespeare, William (1564 - 1616)
Idiomas: en
Número de Descargas: 77782

Listar libros por idioma

Ingrese el idioma (código de idioma, ej. 'en'): en
Título: Romeo and Juliet
Autor: Shakespeare, William (1564 - 1616)
Idiomas: en
Número de Descargas: 77782

Listar todos los autores

Autor: Shakespeare, William (1564 - 1616)

Listar autores vivos en un año específico

Ingrese el año para buscar autores vivos: 1600
Autor: Shakespeare, William (1564 - 1616)

