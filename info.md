https://stackoverflow.com/questions/40860887/spring-rest-controller-with-different-functions/40861264
https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-requestmapping.html



zookeper
docker run -d --name zookeeper -p 2181:2181 zookeeper:3.8

kafka WIN
docker run -d --name kafka -p 9092:9092 `
  -e KAFKA_BROKER_ID=1 `
-e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 `
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 `
-e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 `
  --link zookeeper:zookeeper `
confluentinc/cp-kafka:7.6.1

kafka Neon
docker run -d --name kafka -p 9092:9092 \
-e KAFKA_BROKER_ID=1 \
-e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
-e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
--link zookeeper:zookeeper \
confluentinc/cp-kafka:7.6.1

||
docker run -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=1 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 --link zookeeper:zookeeper confluentinc/cp-kafka:7.6.1

create topic WIN
docker exec kafka kafka-topics --create `
  --topic user-events `
--bootstrap-server localhost:9092 `
  --partitions 1 `
--replication-factor 1

create topic Neon
docker exec kafka kafka-topics --create \
--topic user-events \
--bootstrap-server localhost:9092 \
--partitions 1 \
--replication-factor 1

check topics
docker exec kafka kafka-topics --list --bootstrap-server localhost:9092

remove topic
docker exec kafka kafka-topics --delete --topic user-events --bootstrap-server localhost:9092


очистка и создание топика
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --delete --topic user-events
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --create --topic user-events --partitions 1 --replication-factor 1

конфликт версий
установить
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
обновить
$env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH


mvn clean install
mvn spring-boot:run


mvn clean install -DskipTests
mvn spring-boot:run

cd SpringHW\notification-service
mvn spring-boot:run

root - mvn spring-boot:run

mock mail
docker run -d --name mailhog -p 3025:1025 -p 8025:8025 mailhog/mailhog
http://localhost:8025/ - письма