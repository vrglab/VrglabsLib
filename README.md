# Vrglab's Lib
[![License](https://img.shields.io/github/license/vrglab/VrglabsLib)](LICENSE.txt) [![Releases](https://img.shields.io/github/v/release/vrglab/VrglabsLib)](https://github.com/vrglab/VrglabsLib/releases) [![Issues](https://img.shields.io/bitbucket/issues/vrglab/VrglabsLib)](https://github.com/vrglab/VrglabsLib/issues)
<div style="margin-left: 40svw"><img src="logo.png" width="100"></img></div>
<div>This project has one singular goal, make Registration of things global and as easy to use as you can in the Architectury environment.</div>

### What does this project promise
Never to change how registartion of somthing work's in a significant way, so when you switch versions, old code works as expected with very little to absoultly no changes needed

### Why ?
You see i was tired of having to re-learn a mod loader **cough** **cough** Forge **cough** **cough** everytime it updated and i was really sick of having to rewrite registration code everytime i start a new project specially when the mod supports multiple modloaders, that i decided to make a Registration wrapper that always stays the same and supports all current modloaders.

### What Mc Versions are supported right now ?
* *1.19.2*
* *1.20.4*

## Building

### Development
Like Architectury we support very little when it comes to IDE's, [Intellij](https://www.jetbrains.com/idea/) (Tested) and [Visual Studio Code](https://code.visualstudio.com) (Un-Tested), you will Need Java 17+.
Open The version you want to work on in the respective branch and then open the folder matching the name of the version's branch as a project

### Usage in your own mod
First add the required maven repository to your build.gradle file in the root
``` groovy
allprojects {
    group = rootProject.maven_group
    version = rootProject.mod_version
    repositories {
        maven {
            url 'https://raw.githubusercontent.com/vrglab/Maven/master/'
        }
    }
}
```
and then modify your Architectury Commons project build.gradle
``` groovy
dependencies {
    implementation 'org.Vrglab:vrglabslib:common-1.0.0-mc<WANTED MINECRAFT VERSION>'
}
```
and then you need to do the same for every mod loader you would like to develop on like so:

Forge:
``` groovy
dependencies {
    implementation 'org.Vrglab:vrglabslib:forge-1.0.0-mc<WANTED MINECRAFT VERSION>'
}
```

NeoForge:
``` groovy
dependencies {
    implementation 'org.Vrglab:vrglabslib:neoforge-1.0.0-mc<WANTED MINECRAFT VERSION>'
}
```

Fabric:
``` groovy
dependencies {
    implementation 'org.Vrglab:vrglabslib:fabric-1.0.0-mc<WANTED MINECRAFT VERSION>'
}
```

Quilt:
``` groovy
dependencies {
    implementation 'org.Vrglab:vrglabslib:quilt-1.0.0-mc<WANTED MINECRAFT VERSION>'
}
```

for mappings you need add this
```groovy
dependencies {
    mappings 'org.Vrglab:vrglabslib:mappings-1.0.0-mc<WANTED MINECRAFT VERSION>@tiny'
}
```
To your build.gradle in your root project directory.


you register your object's like so:
```java
private static final Object ITEM = Registry.RegisterItem("item", "MOD_ID" ,()->new Item(new Item.Settings().group(ItemGroup.GROUP)));
```
and you run your Mod like so:

Fabric:
```java
public final class FabricBasedMod implements ModInitializer {
    @Override
    public void onInitialize() {
        VLModFabricLike.init("MODID", ()-> MODCLASS.init());
    }
}
```

Quilt:
```java
public final class QuiltBasedMod {
    @Override
    public void onInitialize(ModContainer mod) {
        VLModFabricLike.init("MODID", ()-> MODCLASS.init());
    }
}
```

Forge:
```java
@Mod("MODID")
public final class ForgeBasedMod {
    public ForgeBasedMod() {
        ForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), "MODID");
        MODCLASS.init();
    }
}
```

NeoForge:
```java
@Mod("MODID")
public final class NeoForgeBasedMod {
    public NeoForgeBasedMod() {
        NeoForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), "MODID");
        MODCLASS.init();
    }
}
```
