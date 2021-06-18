# Onequarkusapp

## Instrucciones

0. Crea un repo en github y compártelo de manera privada conmigo. Realiza un commit como mínimo al pasar cada caso test propuesto o **no corregiré** tu examen.
Realiza un `push` al repo remoto en GitHub **SOLO cuando hayas terminado el proyecto**.

1. Crea un proyecto REST Quarkus con Maven. Instala las dependencias del proyecto según las vayas necesitando. Comienza por instalar las dependencias a la conexión a la base de datos en memoria `H2`.

2. Situa los archivos proporcionados: `schema.sql` y `application.properties` en sus directorios correspondientes del proyecto. **No** modifiques el contenido de estos dos archivos.

3. Comienza implementando los casos test del archivo `ServiceTest.java`. **No** modifiques su código. Implementa la capa correspondiente al repositorio o acceso a datos con el patrón que prefieras (Active Record o DAO).

4. Los casos test del servicio involucran los contenidos mínimos del módulo necesarios para aprobar.

5. Una vez codificado el servicio, continua con los casos test del archivo `ResourcesTest.java` para implementar el controlador de la app.

```
            ___
	.-^   `--,
       /# =========`-_
      /# (--===___====\
     /#   .- --.  . --.|
    /##   |  * ) (   * ),
    |###  \    /\ \    /|
    |###   ----  \  --- |
    |####      ___)    #|
     \####            ##|
      \### ----------  /
       \###           (
        '\###         |
          \##         |
           \###.    .)
            '======/
       
       SHOW ME WHAT YOU'VE GOT! 
```
----------------------------------


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Testing the app

```shell script
./mvnw test
```


> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.
