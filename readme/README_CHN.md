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

	implementation 'com.github.JerryJin93:ImmersiveHelper:1.0.5'
}
```



### 使用方法



A. 已有项目中的BaseActivity extends [ImmersiveActivity](../helper/src/main/java/com/jerryjin/kit/ImmersiveActivity.java)

然后重写以下的三个方法：

```java
// 必选
protected abstract OnOptimizeCallback getOptimizationCallback();
// 可选
protected OptimizationType getOptimizationType();
// 必选 true: 状态栏文字为深色 false: 状态栏文字浅色
protected abstract boolean isStatusBarTextLight();
```



B. 在

