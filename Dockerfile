FROM openjdk:8
ADD ./build/libs/stackunderflow-0.0.1-SNAPSHOT.jar stackunderflow.jar

ENV MYSQL_ROOT_PASSWORD root
ENV MYSQL_DATABASE stackunderflow
ENV MYSQL_USER root
ENV MYSQL_PASSWORD root

CMD ["java","-jar","stackunderflow.jar"]
