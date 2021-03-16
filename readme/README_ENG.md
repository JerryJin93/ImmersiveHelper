# ImmersiveHelper
#### A convenient tool for immersive UI experience on Android devices.

##### Features

* Support optimization for devices base on Android O with notch.
* Support optimization for devices whose OS version above Android P.
* Optimizing automatically while screen is rotating.
* No memory leaks.



### Dependency

Add these to build.gradle in your root project firstly:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Then add these to the build.gradle of the specific module you need:

```groovy
dependencies {
	implementation 'com.github.JerryJin93:ImmersiveHelper:2.0.0'
}
```

P.S. You app need to use AndroidX artifact.



### Usage

First of all, If you want to check the log while debugging, please add this line below to your Application:

```java
ImmersiveHelper.enableLogger(true);
```

**Then let's get down to the business.**

There are two ways to use this library.

<b>A.</b> Extend the Activity you want to optimize directly or indirectly to the ImmersiveActivity, **which I highly recommend.**

In ImmersiveActivity, **there are four methods you can override**.

```java
// It's imperative to override this.
protected abstract OnOptimizeCallback getOptimizationCallback();
// Optional.
protected OptimizationType getOptimizationType();
// Essential. True if you want to set the text color of status bar to dark, false otherwise.
protected abstract boolean isStatusBarTextDark();
// Optional，you can do some extra work before the optimization process, replace the factory if you need, for instance.
protected void onPreOptimize(ImmersiveHelper helper);
```

The rest of work will then be done by ImmersiveHelper.

<b>B.</b> Add these codes below to **onCreate** method in the particular Activity you want to optimize.

**Java**

```java
private ImmersiveHelper helper;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(xxx);
    xxx;
    xxx;
    ...;
    ...;
    ...;
    helper = new ImmersiveHelper(this);
    helper.setStatusBarDarkMode(true)
        .setCallback(new OptimizationCallback() {
            @Override
            public void onOptimized(SystemUIInfo info) {
                if (info.hasNotch()) {
                    // Optimize the ui by the given parameter when there is notch presented.
                } else {
                    // Optimize the ui by the given parameter when there isn't notch presented.
                }
            }
        }).setOptimizationType(type)
        .optimize();
}
```

**Kotlin**

```kotlin
private val helper: ImmersiveHelper by lazy {
        ImmersiveHelper(this)
    }

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(xxx);
    xxx;
    xxx;
    ...;
    ...;
    ...;
    helper.setStatusBarDarkMode(mode)
                .setOptimizationType(type)
                .setCallback {
                    // the parameter it is the instance of SystemUIInfo whose document is below.
					if(it.hasNotch()) {
                        // Optimize the ui by the given parameter when there is notch presented.
                    } else {
                        // Optimize the ui by the given parameter when there isn't notch presented.
                    }
                }.optimize()
}
```

Furthermore, you have to add these to the **onWindowFocusChanged** method in your Activity.

```java
@Override
public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    helper.notifyWindowFocusChanged();
}
```

The last thing you may need to do if you want the Activity to be optimized automatically when screen orientation or other configuration changes is to add these in your Activity.

```java
@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    helper.notifyConfigurationChanged(newConfig);
}
```



### Document

#### OptimizationType

type enum:

|      Type       |                         Description                          |
| :-------------: | :----------------------------------------------------------: |
| TYPE_IMMERSIVE  | immersive status bar, which means the status bar is totally transparent |
| TYPE_FULLSCREEN |                  to make the UI full screen                  |

Particularly, in TYPE_IMMERSIVE mode, the content view of the Activity will be covered by the system UI including status bar and navigation bar. Under this condition, you might need to adjust the paddings or margins of your UI component according to the given parameter in callback. If it seems immersive just by setting the color of the status bar, you can bypass the ImmersiveHelper and use setStatusBarBackgroundColor(Activity activity, int backgroundColor) in [StatusBarHelper](../helper/src/main/java/com/jerryjin/kit/utils/statusBar/StatusBarHelper.java) instead to achieve this goal.



#### [SystemUIInfo](../helper/src/main/java/com/jerryjin/kit/model/SystemUIInfo.java)

properties:

|             Name              |                    Description                    |
| :---------------------------: | :-----------------------------------------------: |
|       orientation: Int        |       the current orientation of the screen       |
|     notchInfo: NotchInfo      |               notch info if exists                |
|       hasNotch: Boolean       |     true if there is a notch, false otherwise     |
| isNavigationBarShown: Boolean | true if the navigation bar shows, false otherwise |
|     statusBarHeight: Int      |           the height of the status bar            |
|   navigationBarHeight: Int    |         the height of the navigation bar          |

methods: 

|              Name               |                         Description                          |
| :-----------------------------: | :----------------------------------------------------------: |
| isLandscapeLeftwards(): Boolean |   true if the screen is in landscape mode, false otherwise   |
| isReversedLandscape(): Boolean  | true if the screen is in reversed landscape mode, false otherwise |
|      isPortrait(): Boolean      |   true if the screen is in portrait mode, false otherwise    |
| isPortraitUpSideDown(): Boolean | true if the screen is in reversed portrait mode, false otherwise |
|     resetNotchInfo(): Unit      |                     reset the NotchInfo                      |



#### [NotchInfo](../helper/src/main/java/com/jerryjin/kit/model/NotchInfo.java)

properties:

|       Name       |            Description            |
| :--------------: | :-------------------------------: |
| notchRect: Rect  | the coordinates of the notch area |
| notchWidth: Int  |      the width of the notch       |
| notchHeight: Int |      the height of the notch      |



#### [Factory](../helper/src/main/java/com/jerryjin/kit/notch/Factory.java)

You can use your own abstract factory to replace the default one in ImmersiveHelper so that you can apply your own optimization rules.



### Issues

I am happy to hear from you if there are any problems. :)