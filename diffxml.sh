#!/bin/bash

for i in `ls -d examples/*.xml | grep -v tidy`
do
	echo "node xmldiff.js `dirname $i`/`basename $i .xml`.x3d $i" 
	node xmldiff.js `dirname "$i"`/`basename "$i" .xml`.x3d "$i" 
done
