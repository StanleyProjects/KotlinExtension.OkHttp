#!/bin/bash

echo "Workflow pr to unstable start..."

SCRIPTS=buildSrc/src/main/resources/bash

ARRAY=(repository worker pr)
SIZE=${#ARRAY[*]}
for ((i=0; i<SIZE; i++)); do
 it="${ARRAY[i]}"
 /bin/bash repository/$SCRIPTS/workflow/assemble/${it}.sh || exit $((10+i))
done

/bin/bash repository/$SCRIPTS/workflow/pr/merge.sh || exit 21

exit 1 # todo

/bin/bash repository/$SCRIPTS/pipeline/prepare.sh || exit 21
/bin/bash repository/$SCRIPTS/workflow/assemble/common.sh || exit 22

exit 1 # todo

echo "Workflow pr to unstable finish."

exit 0
