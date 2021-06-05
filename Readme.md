docker pull mongo:latest

docker run --name local-mongodb -d -p 27017:27017 -v stagingDB:/data/db mongo

docker run --name local-mongodb-test -d -p 27017:27017 -v testDB:/data/db mongo

sbt run
