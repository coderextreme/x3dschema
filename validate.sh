#!/bin/bash -x
git clone https://github.com/everit-org/json-schema
pushd json-schema
~/apache-maven-3.5.0/bin/mvn install
popd
export CLASSPATH=".;C:/Users/coderextreme/.m2/repository/com/google/collections/google-collections/1.0/google-collections-1.0.jar;C:/Users/coderextreme/.m2/repository/org/json/json/20170516/json-20170516.jar;C:/Users/coderextreme/.m2/repository/com/damnhandy/handy-uri-templates/2.1.6/handy-uri-templates-2.1.6.jar;json-schema/core/target/org.everit.json.schema-1.6.0.jar"
javac -classpath $CLASSPATH *.java
java -classpath $CLASSPATH MetaSchemaTest


find /c/x3d-code/www.web3d.org/x3d/content/examples/ -name '*.json' -print0 |xargs -0 node validatex3djson.js | tee results.txt
find /c/x3d-code/www.web3d.org/x3d/content/examples/ -name '*.json' -print0 |xargs -0 java -classpath $CLASSPATH ObjectTest | tee javaresults.txt
grep -w Validation javaresults.txt| sed 's/[^:]*://'| sed 's/\/c\/x3d-code\/www.web3d.org\/x3d\/content\/examples\/\/.*//'| sort |uniq -c|sort -nr > ordered.txt
egrep 'BEGIN|json-parse' javaresults.txt > jsonparseresults.txt
