
mvn clean package
docker container rm disseckill-rabbitmq
docker container rm disseckill-zookeeper
docker container rm disseckill-redis
docker container rm disseckill-order

docker build -t jylkelvin/disseckill-order ./order/
docker build -t jylkelvin/disseckill-mq ./mq/
docker build -t jylkelvin/disseckill-cache ./cache/

