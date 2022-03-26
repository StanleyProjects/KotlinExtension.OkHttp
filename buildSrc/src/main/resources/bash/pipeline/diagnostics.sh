#!/bin/bash

echo "Pipeline diagnostics start..."

mkdir -p diagnostics || exit 1 # todo
echo "{\"types\":[]}" > diagnostics/summary.json

DIAGNOSTICS_FILE=repository/buildSrc/src/main/resources/json/diagnostics.json
ARRAY=($(jq -Mcer ".|keys|.[]" $DIAGNOSTICS_FILE))
SIZE=${#ARRAY[*]}
for ((i=0; i<SIZE; i++)); do
 TYPE="${ARRAY[i]}"
 CODE=0
 TASK="$(jq -Mcer ".${TYPE}.task" $DIAGNOSTICS_FILE)" || exit 1 # todo
 gradle -p repository "$TASK"; CODE=$?
 if test $CODE -ne 0; then
  mkdir -p diagnostics/report/$TYPE || exit 1 # todo
  cp repository/$(jq -Mcer ".${TYPE}.report" $DIAGNOSTICS_FILE) diagnostics/report/$TYPE/index.html || exit $((100+i))
  echo "$(jq -cM ".types+=[\"$TYPE\"]" diagnostics/summary.json)" > diagnostics/summary.json || exit $((100+i))
 fi
done

TYPES="$(jq -Mcer .types diagnostics/summary.json)" || exit 1 # todo
if test "$TYPES" == "[]"; then
 echo "Diagnostics should have determined the cause of the failure!"; exit 1
fi

echo "Diagnostics have determined the cause of the failure - this is: $TYPES."

exit 0
