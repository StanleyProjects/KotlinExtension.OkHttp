#!/bin/bash

echo "Workflow vcs tag test start..."

if test $# -ne 1; then
 echo "Script needs for 1 argument but actual $#"; exit 11
fi

TAG=$1

for it in TAG; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

CODE=0
VCS_DOMAIN="https://api.github.com"
CODE=$(curl -w %{http_code} -o assemble/vcs/repository.json \
 "$VCS_DOMAIN/repos/$REPOSITORY_OWNER/$REPOSITORY_NAME/git/refs/tags/$TAG")
if test $CODE -eq 404; then
 echo "Tag $TAG is not found."; exit 0
fi

echo "Unexpected response code $CODE!"; exit 21
