#!/bin/bash

echo "Pipeline assemble common start..."

CODE=0

gradle -p repository saveCommonInfo; CODE=$?
if test $CODE -ne 0; then
 echo "Save common info error $CODE!"
 exit 11
fi

if [ ! -f "repository/build/common.json" ]; then
 echo "File $(pwd)/repository/build/common.json does not exist!"
 exit 21
fi

echo "Pipeline assemble common success."

exit 0
