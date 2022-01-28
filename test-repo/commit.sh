#!/usr/bin/bash
clear

FILENAME=$( cat /dev/urandom | tr -dc '[:alpha:]' | fold -w ${1:-20} | head -n1 )

echo $FILENAME
echo


echo $FILENAME > $FILENAME.txt


echo "Adding file.."
\git add $FILENAME".txt"  || exit 1
echo


echo "Commit file.."
\git commit -m "Added file: $FILENAME"  || exit 1
echo


echo "Pushing.."
\git push  || exit 1
