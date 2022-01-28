#!/usr/bin/bash


PATH="$1"
REMOTE="$2"


if [[ -z $PATH ]] || [[ -z $REMOTE ]]; then
	echo "Required arguments: <path> <remote>"
	exit 1
fi


\pushd "$PATH"  || exit 1
	/usr/bin/git push -v --mirror "$REMOTE"  || exit 1
\popd
