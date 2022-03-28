#!/bin/bash

echo "Pipeline assemble artifact start..."

if test $# -ne 1; then
 echo "Script needs for 1 argument but actual $#"; exit 11
fi

VARIANT=$1

for it in VARIANT; do
 if test -z "${!it}"; then echo "$it is empty!"; exit 21; fi; done

CODE=0

ARRAY=(Jar Source Pom)
SIZE=${#ARRAY[*]}
for ((i=0; i<SIZE; i++)); do
 gradle -p repository lib:assemble${VARIANT}${ARRAY[i]} || exit $((30+i))
done

echo "Pipeline assemble artifact finish."

exit 0
