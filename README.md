Just to use same repo I have uploaded in hury for your review. 

1.) virtual camera application go inside it -- mvn clean compile assembly:single 

2.) virtual-camera-server go inside it -- mvn package spring-boot:repackage 

3.) Just import the #1 and #2 in eclipse

4.) Start the server from command line -- java -jar target\virtual-camera-server-0.0.1-SNAPSHOT.jar

5.) Start the virtual camera app from command line -- java -jar target\virtual-camera-0.0.1-SNAPSHOT-jar-with-dependencies

6.) Use hosted swagger  http://localhost:8443/swagger-ui.html#/ to hit getLogs API

You can verify from api output and camera app logs as soon as you hit get logs you will get actual generated logs from App

