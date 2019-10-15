From openjdk:8
copy ./build/libs/stackunderflow-0.0.1-SNAPSHOT.jar stackunderflow-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","stackunderflow-0.0.1-SNAPSHOT.jar"]
