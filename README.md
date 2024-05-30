[![License](https://img.shields.io/github/license/vrglab/VrglabsLib)](LICENSE.txt) [![Releases](https://img.shields.io/github/v/release/vrglab/VrglabsLib)](https://github.com/vrglab/VrglabsLib/releases) [![Issues](https://img.shields.io/bitbucket/issues/vrglab/VrglabsLib)](https://github.com/vrglab/VrglabsLib/issues)
# Vrglab's Lib

This project has one singular goal, make Registration of things global and as easy to use as you can in the Architectury environment.

## Building

### Development
Like Architectury we support very little when it comes to IDE's, [Intellij](https://www.jetbrains.com/idea/) (Tested) and [Visual Studio Code](https://code.visualstudio.com) (Un-Tested), you will Need Java 17+.
Open The version you want to work on in the respective branch and then open the folder matching the name of the version's branch as a project

### Usage in your own mod
First add the required maven repository to your build.gradle file
``` groovy
repositories {
    mavenCentral()
    maven {
        url = uri("https://raw.githubusercontent.com/vrglab/Maven/master/")
    }
}
```
and then modify your build.gradle to include 
``` groovy
dependencies {
    implementation 'org.Vrglab:vrglabslib-common:1.0.0-mc<WANTED MINECRAFT VERSION>'
    mappings 'org.Vrglab:vrglabslib-mappings:1.0.0-mc<WANTED MINECRAFT VERSION>@tiny'
}
```
and at last run gradlew to set up your environment