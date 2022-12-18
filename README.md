# Spring Cloud

## Rabbitmq

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.11-management
```

## MariaDB

```bash
docker run \\
	--detach \\
	--name mariadb \\
  --env MARIADB_USER=shane \\
  --env MARIADB_PASSWORD=pass \\
  --env MARIADB_ROOT_PASSWORD=pass \\
  --publish 3306:3306 \\
  mariadb:latest
```

## Kafka

```bash
# Zookeeper, Kafka
./bin/zookeeper-server-start.sh ./config/zookeeper.properties
./bin/kafka-server-start.sh ./config/server.properties

 # Check topics
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```

## Confluent

pwd: `/confluent-7.3.0` 

```bash
./bin/connect-distributed ./etc/kafka/connect-distributed.properties
```

## Connector

```bash
echo '
{
"name" : "my-source-connect",
"config" : {
  "connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
  "connection.url":"jdbc:mysql://localhost:3306/mydb",
  "connection.user":"root",
  "connection.password":"pass",
  "mode": "incrementing",
  "incrementing.column.name" : "id",
  "table.whitelist":"users",
  "topic.prefix" : "my_topic_",
  "tasks.max" : "1"
	}
}
' | curl -X POST -d @- http://localhost:8083/connectors --header "content-Type:application/json"
```

### Sink Connect

```bash
echo '
{
"name":"my-sink-connect",
"config":{
    "connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector",
    "connection.url":"jdbc:mysql://localhost:3306/mydb",
    "connection.user":"root",
    "connection.password":"pass",
    "auto.create":"true",
    "auto.evolve":"true",
    "delete.enabled":"false",
    "tasks.max":"1",
    "topics":"my_topic_users"
  }
}
'| curl -X POST -d @- http://localhost:8083/connectors --header "content-Type:application/json"
```

### Order Sink Connect

```bash
echo '
{
"name": "my-order-sink-connect",
"config": {
    "connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector",
    "connection.url": "jdbc:mysql://localhost:3306/mydb",
    "connection.user": "root",
    "connection.password": "pass",
    "auto.create": "true",
    "auto.evolve": "true",
    "delete.enabled": "false",
    "tasks.max": "1",
    "topics": "orders"
  }
}
'| curl -X POST -d @- http://localhost:8083/connectors --header "content-Type:application/json"
```

## Zipkin

```bash
docker run -d -p 9411:9411 openzipkin/zipkin
```

