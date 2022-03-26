#!/bin/bash

echo "Workflow verify on success start..."

WORKER_NAME="$(jq -r .name assemble/vcs/worker.json)"
WORKER_EMAIL="$(jq -r .email assemble/vcs/worker.json)"
GIT_COMMIT_SHA="$(jq -r .sha assemble/vcs/commit.json)"
AUTHOR_NAME="$(jq -r .name assemble/vcs/commit/author.json)"
AUTHOR_URL="$(jq -r .html_url assemble/vcs/commit/author.json)"

for it in REPOSITORY_OWNER REPOSITORY_NAME \
 GITHUB_RUN_NUMBER GITHUB_RUN_ID \
 GIT_COMMIT_SHA \
 AUTHOR_NAME AUTHOR_URL \
 WORKER_NAME WORKER_EMAIL; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 21; fi; done

REPOSITORY_URL=https://github.com/$REPOSITORY_OWNER/$REPOSITORY_NAME

MESSAGE="GitHub build [#$GITHUB_RUN_NUMBER]($REPOSITORY_URL/actions/runs/$GITHUB_RUN_ID)

[$REPOSITORY_OWNER](https://github.com/$REPOSITORY_OWNER) / [$REPOSITORY_NAME]($REPOSITORY_URL)

 - source [${GIT_COMMIT_SHA::7}]($REPOSITORY_URL/commit/$GIT_COMMIT_SHA) by [$AUTHOR_NAME]($AUTHOR_URL)"

/bin/bash repository/buildSrc/src/main/resources/bash/workflow/telegram_send_message.sh "$MESSAGE" || exit 1 # todo

echo "Verify commit $GIT_COMMIT_SHA success."

exit 0
