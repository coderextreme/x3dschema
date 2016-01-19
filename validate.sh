#!/bin/bash -x
# node validatex3djson.js HelloWorld.json Dodecahedron.output.json > results.txt
find examples/ -name '*.json' -print0 |xargs -0 node validatex3djson.js > results.txt
