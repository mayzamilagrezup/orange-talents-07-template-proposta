FROM openjdk:11
ADD target/proposta.jar proposta.jar
ENTRYPOINT ["java", "-jar", "/proposta.jar"]