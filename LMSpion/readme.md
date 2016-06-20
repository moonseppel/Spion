# Tool for reading games from liga-manager.de 

# How to run the application?
    mvn spring-boot:run

# How to package and then run the application?
    mvn package
    $ java -jar target/*.jar

Basic version built from this tutorial: https://spring.io/guides/tutorials/spring-security-and-angular-js/

On replacing wro4j by grunt, bowser or such:
Aside: Wro4j is probably not the tool of choice for hard-core front end developers - they would probably be using a node-based toolchain, with bower and/or grunt. These are definitely excellent tools, and covered in great detail all over the internet, so please feel free to use them if you prefer. If you just put the outputs from those toolchains in "src/main/resources/static" then it will all work. I find wro4j comfortable because I am not a hard-core front end developer and I know how to use Java-based tooling.
