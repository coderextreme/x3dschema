#!/bin/bash

for i in `ls -d examples/*.x3d | grep -v new`
do
	echo "node xmldiff.js $i `dirname $i`/`basename $i .x3d`.tidy.new.xml" 
	node xmldiff.js "$i"  `dirname "$i"`/`basename "$i" .x3d`.tidy.new.xml
done
