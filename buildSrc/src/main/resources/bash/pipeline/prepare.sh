#!/bin/bash

echo "Pipeline prepare start..."

CODE=0

gradle -p repository clean; CODE=$?
if test $CODE -ne 0; then
 echo "Gradle script error $CODE!"
 exit 11
fi

echo "Pipeline prepare success."

exit 0
