# ImmersiveHelper
### Android刘海屏一键适配



### 添加依赖

先在项目的build.gradle文件中添加：

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```



然后在需要用到ImmersiveHelper的模块的build.gradle文件中添加：

```
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

A. 在需要适配的Activity扩展(extends)[ImmersiveActivity](../helper/src/main/java/com/jerryjin/kit/ImmersiveActivity.java)

然后重写以下的三个方法：

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

B. 在自己的Activity的onCreate方法中调用(若需要适配的Activity不是AppCompatActivity的派生类，则只能用此方法，否则会引发内存泄漏)：

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
    helper.setStatusBarDarkMode(mode)
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



### OptimizationType

```java
TYPE_IMMERSIVE;  // 沉浸式状态栏
TYPE_FULLSCREEN; // 沉浸式全屏
```



### [SystemUIInfo](../helper/src/main/java/com/jerryjin/kit/model/SystemUIInfo.java)

#### 属性：

|             名称              |           作用           |
| :---------------------------: | :----------------------: |
|       orientation: Int        |     指示当前屏幕方向     |
|     notchInfo: NotchInfo      | 刘海屏信息(如果存在的话) |
|       hasNotch: Boolean       |  指示屏幕是否有非功能区  |
| isNavigationBarShown: Boolean |  指示底部导航栏是否显示  |
|     statusBarHeight: Int      |        状态栏高度        |
|   navigationBarHeight: Int    |        导航栏高度        |

#### 方法：

|              名称               |            作用            |
| :-----------------------------: | :------------------------: |
| isLandscapeLeftwards(): Boolean | True: 听筒在左侧的横屏模式 |
| isReversedLandscape(): Boolean  | True: 听筒在右侧的横屏模式 |
|      isPortrait(): Boolean      |       True: 正向竖屏       |
| isPortraitUpSideDown(): Boolean |       True: 反向竖屏       |
|     resetNotchInfo(): Unit      |       重置NotchInfo        |



### [NotchInfo](../helper/src/main/java/com/jerryjin/kit/model/NotchInfo.java)

#### 属性：

|       名称       |              作用              |
| :--------------: | :----------------------------: |
| notchRect: Rect  |        屏幕刘海区域坐标        |
| notchWidth: Int  | 非功能区宽度，也可视为刘海宽度 |
| notchHeight: Int | 非功能区高度，也可视为刘海高度 |



### Factory

您可自定义Factory替换ImmersiveHelper的默认NotchFactory