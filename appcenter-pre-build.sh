#!/usr/bin/env bash

VERSION_NAME=1.5
APPCENTER_SOURCE_DIRECTORY=.
APPCENTER_BUILD_ID=96

echo APPCENTER_SOURCE_DIRECTORY $APPCENTER_SOURCE_DIRECTORY
echo VERSION_NAME $VERSION_NAME
echo APPCENTER_BUILD_ID $APPCENTER_BUILD_ID


if [ -z "$VERSION_NAME" ]
then
    echo "You need to define the VERSION_NAME variable in App Center Tom"
    exit
fi

ANDROID_GRADLE_FILE=$APPCENTER_SOURCE_DIRECTORY/PocketKnifeModule1/build.gradle
echo ANDROID_GRADLE_FILE $ANDROID_GRADLE_FILE

echo "Trying updating version name to $VERSION_NAME ($APPCENTER_BUILD_ID) in build.gradle"

#if [ -e "$ANDROID_GRADLE_FILE" ]
if test  -f "$ANDROID_GRADLE_FILE";
then
    echo "Updating version name to $VERSION_NAME ($APPCENTER_BUILD_ID) in build.gradle"
    sed -i '' 's/versionName "[0-9.]*"/versionName "'$VERSION_NAME'"/' $ANDROID_GRADLE_FILE

    echo "File content:"
    cat $ANDROID_GRADLE_FILE
else
    echo "No ANDROID_GRADLE_FILE $ANDROID_GRADLE_FILE"
fi

