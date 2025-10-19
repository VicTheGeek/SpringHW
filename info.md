mvn clean install -DskipTests
mvn spring-boot:run

cd SpringHW\notification-service
mvn spring-boot:run

root - mvn spring-boot:run

mock mail
docker run -d --name mailhog -p 3025:1025 -p 8025:8025 mailhog/mailhog
http://localhost:8025/ - письма