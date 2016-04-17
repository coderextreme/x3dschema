#!/bin/bash -x
find own/ -name '*.json' -print0 |xargs -0 node validatex3djson.js | tee ownresults.txt

javac -cp json-schema/core/target/org.everit.json.schema-1.2.0.jar:. *.java
java -classpath json-schema/core/target/org.everit.json.schema-1.2.0.jar:. MetaSchemaTest

find own/ -name '*.json' -print0 |xargs -0 java -classpath json-schema/core/target/org.everit.json.schema-1.2.0.jar:. ObjectTest | tee ownjavaresults.txt
grep -w Validation ownjavaresults.txt| sed 's/[^:]*://'| sed 's/own\/\/.*//'| sort |uniq -c|sort -nr | tee ownordered.txt
egrep 'BEGIN|json-parse' ownjavaresults.txt | tee ownjsonparseresults.txt
