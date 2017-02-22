#!/bin/bash -x
export CLASSPATH=".;C:/Users/coderextreme/.m2/repository/com/google/collections/google-collections/1.0/google-collections-1.0.jar;C:/Users/coderextreme/.m2/repository/org/json/json/20160810/json-20160810.jar;json-schema/core/target/org.everit.json.schema-1.5.0.jar"
find ../X3DJSONLD/www.web3d.org/x3d/content/examples/ -name '*.json' -print0 |xargs -0 node validatex3djson.js | tee results.txt
javac -classpath $CLASSPATH *.java
#node validatex3djson.js CC.json
#java -classpath $CLASSPATH ObjectTest CC.json
java -classpath $CLASSPATH MetaSchemaTest
find ../X3DJSONLD/www.web3d.org/x3d/content/examples/ -name '*.json' -print0 |xargs -0 java -classpath $CLASSPATH ObjectTest | tee javaresults.txt
grep -w Validation javaresults.txt| sed 's/[^:]*://'| sed 's/..\/X3DJSONLD\/www.web3d.org\/x3d\/content\/examples\/\/.*//'| sort |uniq -c|sort -nr > ordered.txt
egrep 'BEGIN|json-parse' javaresults.txt > jsonparseresults.txt
