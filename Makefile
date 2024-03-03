PASSWORD=hello
DATABASE=first
CONTAINER=first-mysql
ROOT=$(shell pwd)
DATE=$(shell date "+%Y-%m-%d")
# Override IP by using `export MYSQLIP=127.0.0.2`
ifndef MYSQLIP
MYSQLIP=127.0.0.1
endif
VERSION=$(shell git describe --tags)

.PHONY: mysql-start
mysql-start:
	docker run -e LANG=C.UTF-8 -i -t --rm -p 3306:3306 --name $(CONTAINER) -e MYSQL_ROOT_PASSWORD=$(PASSWORD) -e MYSQL_DATABASE=$(DATABASE) -v $(ROOT)/docs/db/structure.sql:/docker-entrypoint-initdb.d/00-structure.sql -d arm64v8/mysql:8 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

.PHONY: mysql-logs
mysql-logs:
	docker logs --follow $(CONTAINER)

.PHONY: mysql-stop
mysql-stop:
	docker stop $(CONTAINER)

.PHONY: mysql-client
mysql-client:
	mysql -h$(MYSQLIP) -uroot -p$(PASSWORD) $(DATABASE)

.PHONY: mysql-dump-db
mysql-dump-db:
	mysqldump -h$(MYSQLIP) -uroot -p$(PASSWORD) $(DATABASE) | gzip > $(DATE)-$(DATABASE).gz

.PHONY: mysql-reset-db
mysql-reset-db:
	mysql -h$(MYSQLIP) -uroot -p$(PASSWORD) $(DATABASE) -e "DROP DATABASE $(DATABASE); CREATE DATABASE $(DATABASE);"
	mysql -h$(MYSQLIP) -uroot -p$(PASSWORD) $(DATABASE) < $(ROOT)/docs/db/structure.sql
	mysql -h$(MYSQLIP) -uroot -p$(PASSWORD) $(DATABASE) < $(ROOT)/docs/db_updates/0000.sql
	mysql -h$(MYSQLIP) -uroot -p$(PASSWORD) $(DATABASE) < $(ROOT)/docs/db_updates/0001.sql
	mysql -h$(MYSQLIP) -uroot -p$(PASSWORD) $(DATABASE) < $(ROOT)/docs/db/initial_data.sql
