#!/bin/bash

echo "Workflow assemble commit start..."

GIT_COMMIT_SHA="$(git -C repository rev-parse HEAD)" || exit 1 # todo
VCS_DOMAIN="https://api.github.com"
for it in REPOSITORY_OWNER REPOSITORY_NAME GIT_COMMIT_SHA; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

CODE=0
CODE=$(curl -w %{http_code} -o assemble/vcs/commit.json \
 "$VCS_DOMAIN/repos/$REPOSITORY_OWNER/$REPOSITORY_NAME/commits/$GIT_COMMIT_SHA")
if test $CODE -ne 200; then
 echo "Get commit $GIT_COMMIT_SHA info error!"
 echo "Request error with response code $CODE!"
 exit 32
fi
echo "The commit source $(cat assemble/vcs/commit.json | jq -r .html_url) is ready."

AUTHOR_LOGIN="$(jq -r .author.login assemble/vcs/commit.json)"
if test -z "$AUTHOR_LOGIN"; then
 echo "Author login is empty!"
 exit 41
fi
mkdir -p assemble/vcs/commit || exit 1 # todo
CODE=0
CODE=$(curl -w %{http_code} -o assemble/vcs/commit/author.json \
 "$VCS_DOMAIN/users/$AUTHOR_LOGIN")
if test $CODE -ne 200; then
 echo "Get author info error!"
 echo "Request error with response code $CODE!"
 exit 42
fi
echo "The author source $(jq -r .html_url assemble/vcs/commit/author.json) is ready."

echo "Workflow assemble commit $GIT_COMMIT_SHA success."

exit 0
