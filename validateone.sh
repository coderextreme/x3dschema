#!/bin/bash -x
export CLASSPATH=".:/home/${USER}/.m2/repository/com/google/collections/google-collections/1.0/google-collections-1.0.jar:/home/${USER}/.m2/repository/org/json/json/20190722/json-20190722.jar:/home/${USER}/.m2/repository/com/damnhandy/handy-uri-templates/2.1.8/handy-uri-templates-2.1.8.jar:json-schema/core/target/org.everit.json.schema-0.0.0-develop.jar"
javac *.java
java ObjectTest "$@"
node validatex3djson.js "$@"
# node validatehandjson.js "$@"

ls "$@" | xargs grep -lv --null \"@name\":\"error\" | xargs -0 java -classpath ${CLASSPATH} ObjectTest
ls "$@" | xargs grep -lv --null \"@name\":\"error\" | xargs -0 java -classpath ${CLASSPATH} ObjectTestHand
