# ImmersiveHelper
#### Android刘海屏一键适配

+ 支持Android O时代的部分厂商有刘海屏/水滴屏机型的适配
+ 支持Android P及以上版本的刘海屏/水滴屏适配
+ 支持根据屏幕方向自动适配
+ 无内存泄漏



### 添加依赖

先在项目的根build.gradle文件中添加：

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

然后在需要用到ImmersiveHelper的模块的build.gradle文件中添加：

```groovy
dependencies {
	implementation 'com.github.JerryJin93:ImmersiveHelper:2.0.0'
}
```

此外，您的项目需要支持`AndroidX`.



### 使用方法

如果您需要查看Log，请在`Application`中调用：

```java
ImmersiveHelper.enableLogger(true);
```

<b>A. 在需要适配的Activity扩展(extends)[ImmersiveActivity](../helper/src/main/java/com/jerryjin/kit/ImmersiveActivity.java)</b>

<b>(强烈推荐用此方法适配)</b>

然后重写以下的四个方法：

```java
// 必选
protected abstract OnOptimizeCallback getOptimizationCallback();
// 可选
protected OptimizationType getOptimizationType();
// 必选 true: 状态栏文字为深色 false: 状态栏文字浅色
protected abstract boolean isStatusBarTextDark();
// 可选，在适配刘海屏执行流程之前可设置Factory等
protected void onPreOptimize(ImmersiveHelper helper);
```

ImmersiveHelper将自动帮您完成适配工作。

B. 在自己的Activity的onCreate方法中调用：

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
                    // 处理刘海屏适配逻辑
                } else {
                    // 处理非刘海屏的适配逻辑
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
                    // it的类型是SystemUIInfo
					if(it.hasNotch()) {
                        // 处理刘海屏适配逻辑
                    } else {
                        // 处理非刘海屏的适配逻辑
                    }
                }.optimize()
}
```

还需在Activity的onWindowFocusChanged方法中调用ImmersiveHelper#notifyWindowFocusChanged()：

```java
@Override
public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    helper.notifyWindowFocusChanged();
}
```

若需要在屏幕方向发生变化的时候自动适配，则在你的Activity中加上以下代码：

```java
@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    helper.notifyConfigurationChanged(newConfig);
}
```



### 一些类的说明

#### OptimizationType

##### TYPE_IMMERSIVE  

沉浸式状态栏，在此模式下状态栏和导航栏(如果存在的话)会覆盖在contentView上，您需要根据回调设置margin或者padding。如果仅通过改变状态栏颜色就可达到沉浸式效果的话可以不使用ImmersiveHelper而直接调用StatusBarHelper.setStatusBarBackgroundColor(Activity activity, int backgroundColor)方法实现

##### TYPE_FULLSCREEN

全屏显示



#### OptimizationCallback的形参[SystemUIInfo](../helper/src/main/java/com/jerryjin/kit/model/SystemUIInfo.java)

属性：

|             名称              |           作用           |
| :---------------------------: | :----------------------: |
|       orientation: Int        |     指示当前屏幕方向     |
|     notchInfo: NotchInfo      | 刘海屏信息(如果存在的话) |
|       hasNotch: Boolean       |  指示屏幕是否有非功能区  |
| isNavigationBarShown: Boolean |  指示底部导航栏是否显示  |
|     statusBarHeight: Int      |        状态栏高度        |
|   navigationBarHeight: Int    |        导航栏高度        |

方法：

|              名称               |            作用            |
| :-----------------------------: | :------------------------: |
| isLandscapeLeftwards(): Boolean | True: 听筒在左侧的横屏模式 |
| isReversedLandscape(): Boolean  | True: 听筒在右侧的横屏模式 |
|      isPortrait(): Boolean      |       True: 正向竖屏       |
| isPortraitUpSideDown(): Boolean |       True: 反向竖屏       |
|     resetNotchInfo(): Unit      |       重置NotchInfo        |



#### [NotchInfo](../helper/src/main/java/com/jerryjin/kit/model/NotchInfo.java)

属性：

|       名称       |              作用              |
| :--------------: | :----------------------------: |
| notchRect: Rect  |        屏幕刘海区域坐标        |
| notchWidth: Int  | 非功能区宽度，也可视为刘海宽度 |
| notchHeight: Int | 非功能区高度，也可视为刘海高度 |



#### [Factory](../helper/src/main/java/com/jerryjin/kit/notch/Factory.java)

您可自定义抽象工厂Factory替换ImmersiveHelper的默认[NotchFactory](../helper/src/main/java/com/jerryjin/kit/notch/NotchFactory.java)以自定义适配规则。