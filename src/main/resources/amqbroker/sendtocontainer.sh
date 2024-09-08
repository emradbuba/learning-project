#!/bin/bash

CONTAINER_ID=f0917478abaf858d391b098146ace3a66050da5516750719e31440fabb843872
LOCATION="/opt/activemq-artemis/bin"

docker cp rmad.sh $CONTAINER_ID:$LOCATION
docker cp rmqu.sh $CONTAINER_ID:$LOCATION
docker cp addr_to_delete.txt $CONTAINER_ID:$LOCATION
docker cp queues_to_delete.txt $CONTAINER_ID:$LOCATION
docker cp cleanup.sh $CONTAINER_ID:$LOCATION