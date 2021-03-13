# ImmersiveHelper
#### A convenient tool for immersive UI experience on Android devices.

**Features**
* Support optimization for devices base on Android O with notch.
* Support optimization for devices whose OS version above Android P.
* Optimizing automatically while screen is rotating.
* No memory leaks.

[English Document](/readme/README_ENG.md)

[中文版文档](readme/README_CHN.md)

## How to use

### Step 1: Add it in your root build.gradle at the end of repositories.

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### Step 2: Add the dependency.

```groovy
dependencies {
	implementation 'com.github.JerryJin93:ImmersiveHelper:2.0.0'
}
```
