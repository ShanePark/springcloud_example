```bash
docker run -d -p 3306:3306 \
  --network ecommerce-network \
  --name mariadb \
  kkobuk/my_mariadb:0.0.1
```
