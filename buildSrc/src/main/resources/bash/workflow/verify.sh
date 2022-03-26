#!/bin/bash

echo "Workflow verify start..."

/bin/bash repository/buildSrc/src/main/resources/bash/workflow/assemble/vcs.sh || exit 11
/bin/bash repository/buildSrc/src/main/resources/bash/workflow/assemble/commit.sh || exit 12
/bin/bash repository/buildSrc/src/main/resources/bash/pipeline/prepare.sh || exit 21
/bin/bash repository/buildSrc/src/main/resources/bash/workflow/assemble/common.sh || exit 22

CODE=0
/bin/bash repository/buildSrc/src/main/resources/bash/pipeline/verify.sh; CODE=$?
if test $CODE -ne 0; then
 /bin/bash repository/buildSrc/src/main/resources/bash/pipeline/diagnostics.sh && \
  /bin/bash repository/buildSrc/src/main/resources/bash/workflow/vcs/diagnostics/report.sh && \
  /bin/bash repository/buildSrc/src/main/resources/bash/workflow/verify/on_failed.sh || exit 1 # todo
 exit 31
fi

/bin/bash repository/buildSrc/src/main/resources/bash/workflow/verify/on_success.sh || exit 41

echo "Workflow verify finish."

exit 0
