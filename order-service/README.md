```bash
docker run -d --network ecommerce-network \
  --name order-service \
  -e "spring.zipkin.base-url=http://zipkin:9411" \
  -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka/" \
  -e "spring.datasource.url=jdbc:mariadb://mariadb:3306/mydb" \
  -e "logging.file=/api-logs/orders-ws.log" \
 kkobuk/order-service:0.0.1
```
