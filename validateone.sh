#!/bin/bash -x
javac -classpath json-schema/core/target/org.everit.json.schema-1.1.2-SNAPSHOT.jar:. *.java
java -classpath json-schema/core/target/org.everit.json.schema-1.1.2-SNAPSHOT.jar:. ObjectTest "$@"
node validatex3djson.js "$@"
