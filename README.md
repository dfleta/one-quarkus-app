# Onequarkusapp

## Instrucciones

0. Crea un repo en github y compártelo de manera privada conmigo. Realiza un commit como mínimo al pasar cada caso test propuesto o **no corregiré** tu examen.
Realiza un `push` al repo remoto en GitHub **SOLO cuando hayas terminado el proyecto**.

1. Crea un proyecto REST Quarkus con Maven. Instala las dependencias del proyecto según las vayas necesitando.

2. Situa los archivos proporcionados: `schema.sql` y `application.properties` en sus directorios correspondientes del proyecto. **No** modifiques el contenido de estos dos archivos.

3. Comienza implementando los casos test del archivo `ServiceTest.java`. **No** modifiques su código. Implementa la capa correspondiente al repositorio o acceso a datos con el patrón que prefieras (Active Record o DAO).

4. Los casos test del servicio involucran los contenidos mínimos del módulo necesarios para aprobar.

5. Una vez codificado el servicio, continua con los casos test del archivo `ResourcesTest.java` para implementar el controlador de la app.


            ___
	    . -^    `--,
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

----------------------------------


This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
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

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/onequarkusapp-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Related guides

- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more

## Provided examples

### RESTEasy JAX-RS example

REST is easy peasy with this Hello World RESTEasy resource.

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

### RESTEasy JSON serialisation using Jackson

This example demonstrate RESTEasy JSON serialisation by letting you list, add and remove quark types from a list. Quarked!

[Related guide section...](https://quarkus.io/guides/rest-json#creating-your-first-json-rest-service)
