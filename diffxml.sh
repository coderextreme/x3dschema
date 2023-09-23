#!/bin/bash

for i in `ls -d examples/*.xml`
do
	echo "node xmldiff.js $i `dirname $i`/`basename $i .xml`.x3d"
	node xmldiff.js "$i" `dirname "$i"`/`basename "$i" .xml`.x3d
done
