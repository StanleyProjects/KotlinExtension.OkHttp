#!/bin/bash

echo "Workflow pr to unstable start..."

SCRIPTS=buildSrc/src/main/resources/bash

ARRAY=(repository worker pr)
SIZE=${#ARRAY[*]}
for ((i=0; i<SIZE; i++)); do
 /bin/bash repository/$SCRIPTS/workflow/assemble/${ARRAY[i]}.sh || exit $((10+i))
done

/bin/bash repository/$SCRIPTS/workflow/pr/merge.sh || exit 21
/bin/bash repository/$SCRIPTS/pipeline/prepare.sh || exit 31
/bin/bash repository/$SCRIPTS/workflow/assemble/common.sh || exit 32

TAG="$(jq -r .version.name assemble/common.json)-UNSTABLE"

/bin/bash repository/$SCRIPTS/workflow/vcs/tag_test.sh "$TAG" || exit 49
/bin/bash repository/$SCRIPTS/workflow/pr/commit.sh || exit 41
/bin/bash repository/$SCRIPTS/workflow/pr/tag.sh "$TAG" || exit 42
/bin/bash repository/$SCRIPTS/pipeline/assemble/artifact.sh "Unstable" || exit 51
/bin/bash repository/$SCRIPTS/workflow/vcs/release.sh || exit 51
# todo push
# todo pr close

exit 1 # todo

echo "Workflow pr to unstable finish."

exit 0
