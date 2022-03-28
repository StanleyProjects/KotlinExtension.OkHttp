#!/bin/bash

echo "Workflow commit start..."

GIT_BRANCH_DST="$(jq -r .base.ref assemble/vcs/pr${PR_NUMBER}.json)"
GIT_COMMIT_SRC="$(jq -r .head.sha assemble/vcs/pr${PR_NUMBER}.json)"

for it in GITHUB_RUN_NUMBER GIT_BRANCH_DST GIT_COMMIT_SRC; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

CODE=0
COMMIT_MESSAGE="CI build #$GITHUB_RUN_NUMBER | Merge ${GIT_COMMIT_SRC::7} -> $GIT_BRANCH_DST."
git -C repository commit -m "$COMMIT_MESSAGE"; CODE=$?
if test $CODE -ne 0; then
 echo "Git commit failed!"; exit 101
fi

echo "Workflow commit finish."

exit 0
