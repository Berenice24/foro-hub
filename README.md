# Foro Hub

Foro Hub es una API REST construida con Spring Boot que permite gestionar tópicos de discusión en un foro. Implementa autenticación con JWT, manejo de roles y estructura limpia siguiendo buenas prácticas de desarrollo web.

## Características

- Registro y autenticación de usuarios usando JWT
- CRUD de tópicos (temas de discusión)
- Protección de endpoints con roles
- Estructura escalable usando controladores, servicios y repositorios
- Documentación y pruebas con Postman

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT (Json Web Token)
- H2 Database (modo desarrollo)
- Maven

## Endpoints principales

### Autenticación

- `POST /auth` → Inicia sesión y devuelve un token JWT

### Tópicos

- `GET /topicos` → Lista todos los tópicos
- `GET /topicos/{id}` → Obtiene un tópico por ID
- `POST /topicos` → Crea un nuevo tópico
- `PUT /topicos/{id}` → Actualiza un tópico existente
- `DELETE /topicos/{id}` → Elimina un tópico
