#!/bin/bash -x
javac -classpath json-schema/core/target/org.everit.json.schema-1.1.2-SNAPSHOT.jar:. *.java
java -classpath json-schema/core/target/org.everit.json.schema-1.1.2-SNAPSHOT.jar:. MetaSchemaTest
find examples/ -name '*.json' -print0 |xargs -0 java -classpath json-schema/core/target/org.everit.json.schema-1.1.2-SNAPSHOT.jar:. ObjectTest | tee javaresults.txt

## node validatex3djson.js HelloWorld.json Dodecahedron.output.json > results.txt
find examples/ -name '*.json' -print0 |xargs -0 node validatex3djson.js > results.txt
