#!/bin/bash

echo "Workflow verify on failed start..."

for it in VCS_PAT REPOSITORY_OWNER REPOSITORY_NAME \
 GITHUB_RUN_NUMBER GITHUB_RUN_ID \
 AUTHOR_NAME_SRC AUTHOR_URL_SRC \
 WORKER_NAME WORKER_EMAIL; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 21; fi; done

REPOSITORY_URL=https://github.com/$REPOSITORY_OWNER/$REPOSITORY_NAME
VERIFY_RESULT="
 - source [${GIT_COMMIT_SRC::7}]($REPOSITORY_URL/commit/$GIT_COMMIT_SRC) by [$AUTHOR_NAME_SRC]($AUTHOR_URL_SRC)"

MESSAGE="GitHub build [#$GITHUB_RUN_NUMBER]($REPOSITORY_URL/actions/runs/$GITHUB_RUN_ID) failed!

[$REPOSITORY_OWNER](https://github.com/$REPOSITORY_OWNER) / [$REPOSITORY_NAME]($REPOSITORY_URL)

$VERIFY_RESULT"

/bin/bash repository/buildSrc/src/main/resources/bash/workflow/telegram_send_message.sh "$MESSAGE"

exit 1 # todo

exit 0
