# PocketKnifeModule1-Kotlin1 RELEASE.md

[![](https://jitci.com/gh/tompearson/PocketKnifeModule/svg)](https://jitci.com/gh/tompearson/PocketKnifeModule)
dev: [![Build status](https://build.appcenter.ms/v0.1/apps/6913fed6-37ec-4dca-8c4f-a02ec97b0371/branches/dev/badge)](https://appcenter.ms)
test: [![Build status](https://build.appcenter.ms/v0.1/apps/6913fed6-37ec-4dca-8c4f-a02ec97b0371/branches/test/badge)](https://appcenter.ms)
master: [![Build status](https://build.appcenter.ms/v0.1/apps/6913fed6-37ec-4dca-8c4f-a02ec97b0371/branches/master/badge)](https://appcenter.ms)


## Update Version Code for

 If using com.github.dcendents.android-maven then use git to tag the repository and gradle to confirm

- git tag version_number
- ./gradlew AndroidGitVersion (will show the version_number from the git tag command above


If not using com.github.dcendents.android-maven then update version number in the project level build.gradle

in build.gradle or  
--  
-- #1.0.1

- Update version number in README.md.
- Run ./gradlew clean check publishPlugins
- Commit any changes and tag the release version number with git tag $VERSION
- Run git push origin master --tags to sync with github.com
