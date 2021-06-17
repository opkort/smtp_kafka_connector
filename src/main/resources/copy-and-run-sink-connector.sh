#!/bin/bash

# sudo cp ~/gpk/projects/task/kafka-jdbc-connector/kafka-connect-jdbc/target/kafka-connect-jdbc-5.4.1.jar /usr/share/java/ && connect-standalone ./connect-standalone.properties ./sink-postgre.properties

# have to specify USER_NAME

USER_NAME=some_user

sudo cp /home/$USER_NAME/prj/java_prj/smtp-connector/target/smtp-connector-1.0-SNAPSHOT.jar /usr/share/java/ && connect-standalone /home/$USER_NAME/prj/java_prj/smtp-connector/src/main/resources/connect-standalone.properties /home/$USER_NAME/prj/java_prj/smtp-connector/src/main/resources/connector.properties
