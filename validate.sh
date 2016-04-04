#!/bin/bash -x
find /www.web3d.org/x3d/content/examples/ -name '*.json' -print0 |xargs -0 node validatex3djson.js | tee results.txt
javac -classpath json-schema/core/target/org.everit.json.schema-1.2.0.jar:. *.java
#node validatex3djson.js CC.json
#java -classpath json-schema/core/target/org.everit.json.schema-1.2.0.jar:. ObjectTest CC.json
java -classpath json-schema/core/target/org.everit.json.schema-1.2.0.jar:. MetaSchemaTest
find /www.web3d.org/x3d/content/examples/ -name '*.json' -print0 |xargs -0 java -classpath json-schema/core/target/org.everit.json.schema-1.2.0.jar:. ObjectTest | tee javaresults.txt
grep -w Validation javaresults.txt| sed 's/[^:]*://'| sed 's/\/www.web3d.org\/x3d\/content\/examples\/\/.*//'| sort |uniq -c|sort -nr > ordered.txt
egrep 'BEGIN|json-parse' javaresults.txt > jsonparseresults.txt
