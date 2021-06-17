```shell
docker pull mongo:latest

docker run --name local-mongodb -d -p 27017:27017 -v stagingDB:/data/db mongo

sbt run
```
