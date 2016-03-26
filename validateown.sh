#!/bin/bash -x
find own/ -name '*.json' -print0 |xargs -0 java -classpath json-schema/core/target/org.everit.json.schema-1.1.2-SNAPSHOT.jar:. ObjectTest | tee ownjavaresults.txt
grep -w Validation ownjavaresults.txt| sed 's/[^:]*://'| sed 's/own\/\/.*//'| sort |uniq -c|sort -nr > ownordered.txt
egrep 'BEGIN|json-parse' ownjavaresults.txt > ownjsonparseresults.txt
find own/ -name '*.json' -print0 |xargs -0 node validatex3djson.js > ownresults.txt
