#!/bin/bash

echo "Workflow tag start..."

if test $# -ne 1; then
 echo "Script needs for 1 argument but actual $#"; exit 11
fi

TAG=$1

for it in TAG; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

CODE=0
git -C repository tag "$TAG"; CODE=$?
if test $CODE -ne 0; then
 echo "Git tag failed!"; exit 101
fi

echo "Workflow tag finish."

exit 0
