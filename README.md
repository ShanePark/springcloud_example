# Spring Cloud
## MessageQueue
### Rabbitmq
```bash
docker run -d \
  --name rabbitmq \
  --network ecommerce-network \
  -p 5672:5672 \
  -p 15672:15672 \
  -p 4369:4369 \
  -e RABBITMQ_DEFAULT_USER=guest \
  -e RABBITMQ_DEFAULT_PASS=guest \
  rabbitmq:management
```

### Kafka
```bash
# Zookeeper, Kafka
./bin/zookeeper-server-start.sh ./config/zookeeper.properties
./bin/kafka-server-start.sh ./config/server.properties

 # Check topics
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```

#### Confluent

pwd: `/confluent-7.3.0` 

```bash
./bin/connect-distributed ./etc/kafka/connect-distributed.properties
```

- Connector

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

- Sink Connect

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

- Order Sink Connect

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

## Monitoring

### Zipkin

```bash
docker run -d -p 9411:9411 \
  --network ecommerce-network \
  --name zipkin \
  openzipkin/zipkin
```

### Prometheus

```bash
docker run -d -p 9090:9090 \
  --network ecommerce-network \
  --name prometheus \
  -v /Users/shane/Documents/GitHub/springcloud_example/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus
```

### Grafana

```bash
docker run -d -p 3000:3000 \
--network ecommerce-network \
--name grafana \
grafana/grafana
```

- id : admin

- pass: admin

1. Add Datasource

configuration - add datasource - prometheus

- URL : http://host.docker.internal:9090

2. Add Dashboard

- JVM: Dashboard - Import - ID: `4701` - LOAD
- Prometheus: Dashboard - Import - ID: `3663` - LOAD
- Spring Cloud Gateway: ID `11506` - LOAD

3. Change Metric broswer in Spring Cloud Gateway Dashboard

**Total Request Served** -> Edit

- Before : `sum(gateway_requests_seconds_count{job=~"$gatewayService"})`

- After: `sum(spring_cloud_gateway_requests_seconds_count{job=~"apigateway-service"})`

> If it shows N/A , change legend option to Custom

**Total Successful Requests Served** -> Edit

- Before: `sum(gateway_requests_seconds_count{outcome="SUCCESSFUL", job=~"$gatewayService"})`
- After: `sum(spring_cloud_gateway_requests_seconds_count{outcome="SUCCESSFUL", job=~"apigateway-service"})`

**Total Unsuccessful Requests Served**

- Before: `sum(gateway_requests_seconds_count{outcome!="SUCCESSFUL", job=~"$gatewayService"})`
- After: `sum(spring_cloud_gateway_requests_seconds_count{outcome!="SUCCESSFUL", job=~"apigateway-service"})`

**Successful Api Calls**

- After : `spring_cloud_gateway_requests_seconds_count{outcome="SUCCESSFUL", routeId=~"user-service", job=~"apigateway-service"}`
