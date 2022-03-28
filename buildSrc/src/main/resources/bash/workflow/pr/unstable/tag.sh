#!/bin/bash

echo "Workflow tag start..."

VERSION_NAME="$(jq -r .version.name assemble/common.json)"

for it in VERSION_NAME; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

CODE=0
git -C repository tag "${VERSION_NAME}-UNSTABLE"; CODE=$?
if test $CODE -ne 0; then
 echo "Git tag failed!"; exit 101
fi

echo "Workflow tag finish."

exit 0
