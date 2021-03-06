#!/bin/bash

echo "Workflow assemble worker start..."

for it in VCS_PAT; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

CODE=0
VCS_DOMAIN="https://api.github.com"
CODE=$(curl -w %{http_code} -o assemble/vcs/worker.json \
 "$VCS_DOMAIN/user" \
 -H "Authorization: token $VCS_PAT")
if test $CODE -ne 200; then
 echo "Get worker error!"
 echo "Request error with response code $CODE!"
 exit 22
fi
echo "The worker $(jq -r .html_url assemble/vcs/worker.json) is ready."

exit 0
