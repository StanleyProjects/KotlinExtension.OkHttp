#!/bin/bash

echo "Workflow assemble common start..."

CODE=0

/bin/bash repository/buildSrc/src/main/resources/bash/pipeline/assemble/common.sh; CODE=$?

if test $CODE -ne 0; then
 echo "Pipeline assemble common error $CODE!"
 exit 11
fi

mkdir -p assemble
cp repository/build/common.json assemble/common.json

if [ ! -f "assemble/common.json" ]; then
 echo "File $(pwd)/assemble/common.json does not exist!"
 exit 21
fi

ACTUAL="$(jq -r .repository.owner assemble/common.json)"
if test -z "$ACTUAL"; then
 echo "Repository owner is empty!"
 exit 41
fi
if test "$REPOSITORY_OWNER" != "$ACTUAL"; then
 echo "Actual repository owner is $ACTUAL, but expected is $REPOSITORY_OWNER!"
 exit 42
fi

echo "Workflow assemble common success."

exit 0
