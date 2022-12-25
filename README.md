# Spring Cloud

## Database

### MariaDB

```bash
docker run \
  --detach \
  --name mariadb \
  --env MARIADB_USER=shane \
  --env MARIADB_PASSWORD=pass \
  --env MARIADB_ROOT_PASSWORD=pass \
  --publish 3306:3306 \
  mariadb:latest
```

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
docker run -d -p 9411:9411 openzipkin/zipkin
```

### Prometheus

```bash
docker run \
    --name prometheus \
    -p 9090:9090 \
    -v /Users/shane/Documents/dev/prometheus.yml:/etc/prometheus/prometheus.yml \
    prom/prometheus
```

change `/etc/prometheus/prometheus.yml` or mount this file by setting up volume

```yaml
# my global config
global:
  scrape_interval: 15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
    - static_configs:
        - targets:
          # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: "prometheus"

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
      - targets: ["localhost:9090"]
      
### Here is the contents added !!! ###
  - job_name: 'user_service'
    scrape_interval: 15s
    metrics_path: '/user-service/actuator/prometheus'
    static_configs:
    - targets: ['host.docker.internal:8000']
  - job_name: 'order-service'
    scrape_interval: 15s
    metrics_path: '/order-service/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8000']
  - job_name: 'apigateway-service'
    scrape_interval: 15s
    metrics_path: '/actuator/prometheus'
    static_configs:
    - targets: ['host.docker.internal:8000']


```

### Grafana

```bash
docker run -d -p 3000:3000 --name grafana grafana/grafana
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
