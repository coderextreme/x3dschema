#!/bin/bash -x
javac -cp json-schema/core/target/org.everit.json.schema-1.2.0.jar:. *.java
java -cp json-schema/core/target/org.everit.json.schema-1.2.0.jar:. NumberTest
