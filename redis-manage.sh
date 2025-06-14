#!/bin/bash

case "$1" in
  "start")
    echo "Starting Redis..."
    docker-compose up -d
    ;;
  "stop")
    echo "Stopping Redis..."
    docker-compose down
    ;;
  "restart")
    echo "Restarting Redis..."
    docker-compose restart
    ;;
  "cli")
    echo "Connecting to Redis CLI..."
    docker exec -it redis-cache redis-cli
    ;;
  "logs")
    echo "Showing Redis logs..."
    docker-compose logs -f redis
    ;;
  "status")
    echo "Redis container status:"
    docker-compose ps
    ;;
  *)
    echo "Usage: $0 {start|stop|restart|cli|logs|status}"
    exit 1
    ;;
esac 