#!/bin/bash

echo "Workflow assemble pr start..."

VCS_DOMAIN="https://api.github.com"
for it in REPOSITORY_OWNER REPOSITORY_NAME PR_NUMBER; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

CODE=0
CODE=$(curl -w %{http_code} -o assemble/vcs/pr${PR_NUMBER}.json \
 "$VCS_DOMAIN/repos/$REPOSITORY_OWNER/$REPOSITORY_NAME/pulls/$PR_NUMBER")
if test $CODE -ne 200; then
 echo "Get pr $PR_NUMBER info error!"
 echo "Request error with response code $CODE!"
 exit 32
fi
echo "The pr $(jq -r .html_url assemble/vcs/pr${PR_NUMBER}.json) is ready."

echo "Workflow assemble pr $PR_NUMBER success."

exit 0
