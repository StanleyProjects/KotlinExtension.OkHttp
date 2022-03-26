#!/bin/bash

echo "Workflow assemble vcs start..."

VCS_DOMAIN="https://api.github.com"
for it in REPOSITORY_OWNER REPOSITORY_NAME VCS_PAT; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

mkdir -p assemble/vcs

CODE=0
CODE=$(curl -w %{http_code} -o assemble/vcs/repository.json \
 "$VCS_DOMAIN/repos/$REPOSITORY_OWNER/$REPOSITORY_NAME")
if test $CODE -ne 200; then
 echo "Get repository $REPOSITORY_NAME error!"
 echo "Request error with response code $CODE!"
 exit 21
fi
echo "The repository $(jq -r .html_url assemble/vcs/repository.json) is ready."

CODE=0
CODE=$(curl -w %{http_code} -o assemble/vcs/worker.json \
 "$VCS_DOMAIN/user" \
 -H "Authorization: token $VCS_PAT")
if test $CODE -ne 200; then
 echo "Get worker error!"
 echo "Request error with response code $CODE!"
 exit 22
fi
echo "The worker $(jq -r .html_url assemble/vcs/worker.json) is ready."

echo "Workflow assemble repository $REPOSITORY_OWNER/$REPOSITORY_NAME vcs success."

exit 0
