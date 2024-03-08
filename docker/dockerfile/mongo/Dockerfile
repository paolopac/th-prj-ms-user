FROM ubuntu:18.04

LABEL maintainer="paoloacqua@hotmail.it"

RUN apt-get update && apt-get install -y mongodb && apt-get install nano

# Create the MongoDB data directory
RUN mkdir -p /data/db

# Expose port #37016 from the container to the host
EXPOSE 37016

#Start MongoDb
CMD ["mongod", "-f", "/etc/mongodb.conf"]



