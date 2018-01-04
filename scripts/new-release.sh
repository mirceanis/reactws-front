#!/bin/bash
set -e

### This script is meant to go through git flow and create a new release with bumped versions
### It will fail if the current working dir is not clean when starting the process
### Since version codes can manually fall out of sync between the 2 platforms,
### iOS takes precedence and is used as the base to increment from

SCRIPTS_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ROOT_DIR="$SCRIPTS_DIR/.."
ANDROID_DIR="$ROOT_DIR/android"
IOS_DIR="$ROOT_DIR/ios"

echo "check clean repo"
cd ${ANDROID_DIR} ; ./gradlew checkCleanRepo ; cd -

echo "get current version"
cd ${IOS_DIR} ; current_version=$(xcrun agvtool what-version -terse) ; cd -

echo "increment"
new_version="$(($current_version + 1))"

echo "git flow release start $new_version"
git fetch --all
git flow release start ${new_version}

echo "set android version to $new_version"
cd ${ANDROID_DIR} ; ./gradlew bumpVersion -P_BUILD_NUMBER=${new_version}

echo "set ios version to $new_version"
cd ${IOS_DIR} ; agvtool new-version -all ${new_version} ; cd -

echo "commit"
git commit -m "bump build version to $new_version"

echo "git flow release finish $new_version"
git flow release finish -k -m "release r$new_version" ${new_version}

#echo "pushing branches"
#git push origin develop master
#git push origin --tags