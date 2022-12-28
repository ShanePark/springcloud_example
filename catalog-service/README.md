```bash
docker run -d --network ecommerce-network \
  --name catalog-service \
  -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka/" \
  -e "logging.file=/api-logs/catalogs-ws.log" \
  kkobuk/catalog-service:0.0.1
```
