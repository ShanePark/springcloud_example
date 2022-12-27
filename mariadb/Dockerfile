FROM mariadb
ENV MARIADB_USER shane
ENV MYSQL_ROOT_PASSWORD pass
ENV MYSQL_DATABASE mydb
COPY ./mysql /var/lib/mysql
EXPOSE 3306
ENTRYPOINT ["mysqld", "--user=root"]
