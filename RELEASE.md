# PocketKnifeModule1-Kotlin1 RELEASE.md


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
