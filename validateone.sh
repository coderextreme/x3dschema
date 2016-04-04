#!/bin/bash -x
javac -classpath json-schema/core/target/org.everit.json.schema-1.2.0.jar:. *.java
java -classpath json-schema/core/target/org.everit.json.schema-1.2.0.jar:. ObjectTest "$@"
node validatex3djson.js "$@"
