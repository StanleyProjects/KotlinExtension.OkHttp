#!/bin/bash

echo "Workflow merge start..."

for it in PR_NUMBER; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

WORKER_NAME="$(jq -r .name assemble/vcs/worker.json)"
WORKER_EMAIL="$(jq -r .email assemble/vcs/worker.json)"
GIT_BRANCH_DST="$(jq -r .base.ref assemble/vcs/pr${PR_NUMBER}.json)"
GIT_COMMIT_SRC="$(jq -r .head.sha assemble/vcs/pr${PR_NUMBER}.json)"

for it in WORKER_NAME WORKER_EMAIL GIT_BRANCH_DST GIT_COMMIT_SRC; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 11; fi; done

git -C repository config user.name "$WORKER_NAME" && \
 git -C repository config user.email "$WORKER_EMAIL"; CODE=$?
if test $CODE -ne 0; then
 echo "Git config error!"; exit 101
fi
git -C repository checkout $GIT_BRANCH_DST; CODE=$?
if test $CODE -ne 0; then
 echo "Git checkout to \"$GIT_BRANCH_DST\" error!"; exit 102
fi
git -C repository merge --no-ff --no-commit $GIT_COMMIT_SRC; CODE=$?
if test $CODE -ne 0; then
 echo "Git merge with ${GIT_COMMIT_SRC::7} failed!"; exit 103
fi

echo "Workflow merge ${GIT_COMMIT_SRC::7} -> $GIT_BRANCH_DST finish."

exit 0
