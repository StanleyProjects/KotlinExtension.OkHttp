#!/bin/bash

echo "Pipeline verify start..."

CODE=0

ARRAY=(CodeStyle License Readme Service)
SIZE=${#ARRAY[*]}
for ((i=0; i<SIZE; i++)); do
 it="${ARRAY[i]}"
 gradle -p repository verify$it; CODE=$?
 if test $CODE -ne 0; then
  echo "gradle verify $it error"; exit $((100+i))
 fi
done

ARRAY=(CodeQuality)
SIZE=${#ARRAY[*]}
for ((i=0; i<SIZE; i++)); do
 it="${ARRAY[i]}"
 gradle -p repository lib:verify$it; CODE=$?
 if test $CODE -ne 0; then
  echo "gradle verify lib $it error"; exit $((200+i))
 fi
done

ARRAY=(test jacocoTestReport jacocoTestCoverageVerification)
SIZE=${#ARRAY[*]}
for ((i=0; i<SIZE; i++)); do
 it="${ARRAY[i]}"
 gradle -p repository lib:$it; CODE=$?
 if test $CODE -ne 0; then
  echo "gradle lib $it error"; exit $((200+i))
 fi
done

echo "Pipeline verify success."

exit 0
