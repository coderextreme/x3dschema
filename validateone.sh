#!/bin/bash -x
export CLASSPATH=".;C:/Users/${USERNAME}/.m2/repository/com/google/collections/google-collections/1.0/google-collections-1.0.jar;C:/Users/${USERNAME}/.m2/repository/org/json/json/20180130/json-20180130.jar;C:/Users/${USERNAME}/.m2/repository/com/damnhandy/handy-uri-templates/2.1.6/handy-uri-templates-2.1.6.jar;json-schema/core/target/org.everit.json.schema-1.8.0.jar"
javac *.java
java ObjectTest "$@"
node validatex3djson.js "$@"
