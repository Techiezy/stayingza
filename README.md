# setup steps

1) ./mvnw package -DskipTests=true
2) docker build -t stayingza .
3) docker run --name stay-app -p8080:8080 -d stayingza
4) curl http://localhost/api/guests