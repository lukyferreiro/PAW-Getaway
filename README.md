# Getaway

## Autores
- [Tomas Alvarez Escalante](https://github.com/tomalvarezz)
- [Lucas Agustin Ferreiro](https://github.com/lukyferreiro)
- [Roman Gomez Kiss](https://github.com/rgomezkiss)
- [Agustina Sol Ortu](https://github.com/aortu22)

## Introducción
Este proyecto consiste en el desarrollo de una aplicación web de varios módulos que agrupa tanto el frontend (cliente SPA) como el backend (API REST).

### Backend

La estructura general del proyecto sigue el patrón de MVC, donde:

- interfaces: declara los "contratos" para las implementaciones de `/services` y `/persistence`.
- models: declara los modelos/entidades utilizados
- persistence: es el "puente" entre la base de datos y la aplicación. 
- services: es el "puente" entre los controllers y la capa de persistencia.
- webapp: implementa los endpoints de la API REST, accediendo a los servicios provistos por el modulo con el mismo nombre.

#### Build

Para construir el proyecto correr:
```
mvn clean install 
```

### Frontend

Para levantar el frontend, dirigirse a la carpeta '/frontend' y correr:

```
npm start
```
Nota: Revisar la baseUrl de la API en `/src/common/index.js`

Para ejecutar los tests correr:
```
npm test
```
Para ejecutar el build de producción correr:
```
npm run build
```

### Generación del war

Para crear el app.war que debe ser deployado en el servidor de la catedra se debe ejecutar: 
```
mvn clean package
```

## Correciones

- Si bien los endpoints de creación y lectura de entidades declaran usar el mismo mime type, en la práctica usan entidades distintas, con mapeos distintos (ie: al crear una experiencia category es un long, al leerlo es una url a la category correspondiente). Lo correcto sería de mínima declarar mime types distintos, o idealmente, realmente que sean el mismo objeto. Esto es un error conceptual grave.
- La SPA no maneja los errores 401, dejando que salte el modal nativo sin reintentar automáticamente el request con el refresh token. En lugar de esto, buscan validar “a priori” el token antes de enviarlo o mandar el de refresh en su defecto. Esto sin embargo es incorrecto, ya que asume que la hora del cliente está perfectamente sincronizada con la del servidor, y que los requests son instantáneos. Cualquier desfasaje por diferencia horaria o latencia indefectiblemente podría terminar en estos errores en forma natural. Esto es un error grave.
