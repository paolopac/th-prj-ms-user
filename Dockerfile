# ==============================================================
# ==== Docker File Creazione Immagine MICRO-SERVICE-USER =======
# ==============================================================

FROM openjdk:11-jre-slim
LABEL maintainer="Paolo Acquaviva <paoloacqua@hotmail.it>"

ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
# ARG Xmx # Abilitare nel caso si voglia resettare, rivalorizzare tale valore in fase di build
# ARG Xss # Abilitare nel caso si voglia resettare, rivalorizzare tale valore in fase di build
ENV Xmx=-XX:MaxRAM=2048m Xss=-Xss512m Xms=-Xms2048m
ENV LC_ALL it_IT.UTF-8
ENV LANG it_IT.UTF-8
ENV LANGUAGE it_IT.UTF-8

WORKDIR /webapi

VOLUME ["/logs"]

RUN apt-get update -y && apt-get install -y locales locales-all

COPY /target/MICRO-SERVICE-USER-0.0.1-SNAPSHOT.jar th-prj-ms-user.jar

ENTRYPOINT exec java $JAVA_OPTS $Xmx -XX:+UseSerialGC $Xss -jar th-prj-ms-user.jar

#Generazione Immagine:
# docker build -t th-prj-ms-user .

# Upload in dockerhub:
  
# docker login 

# docker tag 1b2dc3b06d1d33a48e54f1c33441bd82b7ae7f41b12412ad33f5fc8babdf42ef paoloacqua/th-prj-ms-user

# docker push paoloacqua/th-prj-ms-user

