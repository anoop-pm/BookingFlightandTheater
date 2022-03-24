From openjdk:8
copy ./target/bookingsystem-0.0.1-SNAPSHOT.jar bookingsystem-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","bookingsystem-0.0.1-SNAPSHOT.jar"]