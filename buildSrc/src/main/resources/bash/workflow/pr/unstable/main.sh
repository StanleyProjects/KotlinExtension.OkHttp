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
/bin/bash repository/$SCRIPTS/workflow/pr/commit.sh || exit 41
/bin/bash repository/$SCRIPTS/workflow/pr/unstable/tag.sh || exit 42
/bin/bash repository/$SCRIPTS/pipeline/assemble/artifact.sh "Unstable" || exit 51

ls -al repository/lib/build/libs
exit 1 # todo

echo "Workflow pr to unstable finish."

exit 0
