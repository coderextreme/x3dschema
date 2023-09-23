#!/bin/bash -x
npm install
export WEB3D=C:/x3d-code/www.web3d.org/x3d/content/examples/
export CLASSPATH='.;C:/x3d-code/www.web3d.org/x3d/stylesheets/java/jars/X3DJSAIL.4.0.full.jar' 
export JAVA_HOME="C:/openjdk-20_windows-x64_bin/jdk-20/"
export PATH="${JAVA_HOME}/bin;${PATH}"
javac -cp "${CLASSPATH}" *java


# run Ajv
find $WEB3D ! -name 'package*.json' -a ! -path node_modules -a ! -path development -a ! -path originals -a -name '*.json' -print0 | xargs -0 grep -lv --null \"@name\":\"warning\" | xargs -0 grep -lv --null \"@name\":\"error\" | xargs -0 node validatex3djson.js | tee results.txt
# run X3DJSAIL Validate
find $WEB3D ! -name 'package*.json' -a ! -path node_modules -a ! -path development -a ! -path originals -a -name '*.json' -print0 | xargs -0 grep -lv --null \"@name\":\"warning\" | xargs -0 grep -lv --null \"@name\":\"error\" | xargs -0 java -cp "${CLASSPATH}" Validate 2>&1 | tee javaresults.txt
    
