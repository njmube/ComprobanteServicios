VERSION=0.0.2
IMAGEN=kster/comprobante_servicios
NAME=comprobante_servicios
docker service rm $NAME  || true
mvn package
docker service rm $NAME  || true
docker rmi $IMAGEN:$VERSION || true
docker build -t $IMAGEN:$VERSION -f Dockerfile.dev .

docker service create \
        --name $NAME \
        --network revnet \
        --network appnet \
        --restart-condition any \
        --replicas=1  \
        --restart-delay 5s \
        --update-delay 10s \
        --update-parallelism 1 \
        -p 57783:57783  \
        $IMAGEN:$VERSION
