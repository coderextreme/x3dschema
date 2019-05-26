#!/bin/bash -x
git clone https://github.com/everit-org/json-schema
pushd json-schema
~/apache-maven-3.5.2/bin/mvn -Dmaven.test.skip=true install
popd
export CLASSPATH=".;C:/Users/${USERNAME}/.m2/repository/com/google/re2j/re2j/1.1/re2j-1.1.jar;C:/Users/${USERNAME}/.m2/repository/com/google/collections/google-collections/1.0/google-collections-1.0.jar;C:/Users/${USERNAME}/.m2/repository/org/json/json/20180130/json-20180130.jar;C:/Users/${USERNAME}/.m2/repository/com/damnhandy/handy-uri-templates/2.1.6/handy-uri-templates-2.1.6.jar;json-schema/core/target/org.everit.json.schema-1.10.0.jar"
export WEB3D=/c/x3d-code/www.web3d.org/x3d/content/examples/
javac -classpath $CLASSPATH *.java
java -classpath $CLASSPATH MetaSchemaTest


find $WEB3D -name '*.json' -print0 | xargs -0 grep -lv --null \"@name\":\"error\" | xargs -0 node validatex3djson.js | tee results.txt
find $WEB3D -name '*.json' -print0 | xargs -0 grep -lv --null \"@name\":\"error\" | xargs -0 java -classpath $CLASSPATH ObjectTest | tee javaresults.txt
grep -w Validation javaresults.txt| sed 's/[^:]*://'| sed 's/\/c\/x3d-code\/www.web3d.org\/x3d\/content\/examples\/\/.*//'| sort |uniq -c|sort -nr > ordered.txt
egrep 'BEGIN|json-parse' javaresults.txt > jsonparseresults.txt
