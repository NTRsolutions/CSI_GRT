### To enable ProGuard in your project, edit project.properties
### to define the proguard.config property as described in that file.
###
### Add project specific ProGuard rules here.
### By default, the flags in this file are appended to flags specified
### in ${sdk.dir}/tools/proguard/proguard-android.txt
### You can edit the include path and order by changing the ProGuard
### include property in project.properties.
###
### For more details, see
###   http://developer.android.com/guide/developing/tools/proguard.html
##
### Add any project specific keep options here:
##
### If your project uses WebView with JS, uncomment the following
### and specify the fully qualified class name to the JavaScript interface
### class:
###-keepclassmembers class fqcn.of.javascript.interface.for.webview {
###   public *;
###}
##-dontoptimize
##-dontpreverify
##
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
#
#-dontwarn com.hp.hpl.sparta.**
#-keep class com.hp.hpl.sparta.** { *; }
#
#-dontwarn demo.**
#-keep class demo.** { *; }
##保持不混淆
#-keep class org.achartengine.** { *; }
#
#-dontwarn com.baidu.**
#-keep class com.baidu.** { *; }
#-dontwarn com.baidu.a.a.a.a.**
#-keep class com.baidu.a.a.a.a.** { *; }
#-dontwarn com.baidu.a.a.a.b.**
#-keep class com.baidu.a.a.a.b.** { *; }
#-dontwarn com.baidu.location.**
#-keep class com.baidu.location.** { *; }
#-dontwarn com.baidu.lbsapi.auth.**
#-keep class com.baidu.lbsapi.auth.** { *; }
#
#-keep class com.xsj.crasheye.** { *; }
#-keep class com.ant.liao.** { *; }
#-keep class org.apache.http.entity.mime.** { *; }
#-keep class org.jsoup.** { *; }
#-keep class com.github.mikephil.charting.** { *; }
#-keep class net.sourceforge.pinyin4j.** { *; }
#-keep class com.nostra13.universalimageloader.** { *; }
#-keep class com.android.volley.** { *; }
#-keep class com.google.zxing.** { *; }
#-keep class com.growatt.shinephone.bean.** { *; }
#
##==================gson==========================
#-dontwarn com.google.**
#-keep class com.google.gson.** {*;}
#-keep class com.google.** {*;}
#
###==================protobuf======================
##-dontwarn com.google.**
##-keep class com.google.protobuf.** {*;}
##-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,Annotation,EnclosingMethod
##-dontusemixedcaseclassnames
##-dontskipnonpubliclibraryclasses
##-verbose
##
##-dontoptimize
##-dontpreverify
##
##-keepclasseswithmembernames class * {
##    native <methods>;
##}
### Add project specific ProGuard rules here.
### By default, the flags in this file are appended to flags specified
### in D:\Android\sdk/tools/proguard/proguard-android.txt
### You can edit the include path and order by changing the proguardFiles
### directive in build.gradle.
###
### For more details, see
###   http://developer.android.com/guide/developing/tools/proguard.html
##
### Add any project specific keep options here:
##
### If your project uses WebView with JS, uncomment the following
### and specify the fully qualified class name to the JavaScript interface
### class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
##
###指定代码的压缩级别
##-optimizationpasses 5
##
###包明不混合大小写
##-dontusemixedcaseclassnames
##
###不去忽略非公共的库类
##-dontskipnonpubliclibraryclasses
##
## #优化  不优化输入的类文件
##-dontoptimize
##
## #预校验
##-dontpreverify
##
## #混淆时是否记录日志
##-verbose
##
## # 混淆时所采用的算法
##-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
##
###保护注解
##-keepattributes *Annotation*
##
### 保持哪些类不被混淆
##-keep public class * extends android.app.Fragment
##-keep public class * extends android.app.Activity
##-keep public class * extends android.app.Application
##-keep public class * extends android.app.Service
##-keep public class * extends android.content.BroadcastReceiver
##-keep public class * extends android.content.ContentProvider
##-keep public class * extends android.app.backup.BackupAgentHelper
##-keep public class * extends android.preference.Preference
##-keep public class com.android.vending.licensing.ILicensingService
###如果有引用v4包可以添加下面这行
##-keep public class * extends android.support.v4.app.Fragment
##
##
###忽略警告
##-ignorewarning
##
####记录生成的日志数据,gradle build时在本项目根目录输出##
###apk 包内所有 class 的内部结构
##-dump proguard/class_files.txt
###未混淆的类和成员
##-printseeds proguard/seeds.txt
###列出从 apk 中删除的代码
##-printusage proguard/unused.txt
###混淆前后的映射
##-printmapping proguard/mapping.txt
##########记录生成的日志数据，gradle build时 在本项目根目录输出-end######
##
###如果引用了v4或者v7包
##-dontwarn android.support.**
##
######混淆保护自己项目的部分代码以及引用的第三方jar包library-end####
##
##
##
###保持 native 方法不被混淆
##-keepclasseswithmembernames class * {
##    native <methods>;
##}
##
###保持自定义控件类不被混淆
##-keepclasseswithmembers class * {
##    public <init>(android.content.Context, android.util.AttributeSet);
##}
##
###保持自定义控件类不被混淆
##-keepclassmembers class * extends android.app.Activity {
##   public void *(android.view.View);
##}
##
##-keep public class * extends android.view.View {
##    public <init>(android.content.Context);
##    public <init>(android.content.Context, android.util.AttributeSet);
##    public <init>(android.content.Context, android.util.AttributeSet, int);
##    public void set*(...);
##}
##
###保持 Parcelable 不被混淆
##-keep class * implements android.os.Parcelable {
##  public static final android.os.Parcelable$Creator *;
##}
##
###保持 Serializable 不被混淆
##-keepnames class * implements java.io.Serializable
##
###保持 Serializable 不被混淆并且enum 类也不被混淆
##-keepclassmembers class * implements java.io.Serializable {
##    static final long serialVersionUID;
##    private static final java.io.ObjectStreamField[] serialPersistentFields;
##    !static !transient <fields>;
##    !private <fields>;
##    !private <methods>;
##    private void writeObject(java.io.ObjectOutputStream);
##    private void readObject(java.io.ObjectInputStream);
##    java.lang.Object writeReplace();
##    java.lang.Object readResolve();
##}
##
###保持枚举 enum 类不被混淆
##-keepclassmembers enum * {
##  public static **[] values();
##  public static ** valueOf(java.lang.String);
##}
##
##-keepclassmembers class * {
##    public void *ButtonClicked(android.view.View);
##}
##
###不混淆资源类
##-keepclassmembers class **.R$* {
##    public static <fields>;
##}
##
###避免混淆泛型 如果混淆报错建议关掉
###-keepattributes Signature
##
###移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用，另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
###-assumenosideeffects class android.util.Log {
###    public static *** v(...);
###    public static *** i(...);
###    public static *** d(...);
###    public static *** w(...);
###    public static *** e(...);
###}
##
###############################################################################################
##########################                 以上通用           ##################################
###############################################################################################
##
#########################     常用第三方模块的混淆选项         ###################################
###gson
###如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
##-keepattributes Signature
### Gson specific classes
##-keep class sun.misc.Unsafe { *; }
### Application classes that will be serialized/deserialized over Gson
##-keep class com.google.gson.** { *; }
##-keep class com.google.gson.stream.** { *; }
##
###mob
##-keep class android.net.http.SslError
##-keep class android.webkit.**{*;}
##-keep class cn.sharesdk.**{*;}
##-keep class com.sina.**{*;}
##-keep class m.framework.**{*;}
##-keep class **.R$* {*;}
##-keep class **.R{*;}
##-dontwarn cn.sharesdk.**
##-dontwarn **.R$*
##
###butterknife
##-keep class butterknife.** { *; }
##-dontwarn butterknife.internal.**
##-keep class **$$ViewBinder { *; }
##
##-keepclasseswithmembernames class * {
##    @butterknife.* <fields>;
##}
##
##-keepclasseswithmembernames class * {
##    @butterknife.* <methods>;
##}
##
########引用的其他Module可以直接在app的这个混淆文件里配置
##
### 如果使用了Gson之类的工具要使被它解析的JavaBean类即实体类不被混淆。
##-keep class com.matrix.app.entity.json.** { *; }
##-keep class com.matrix.appsdk.network.model.** { *; }
##
#######混淆保护自己项目的部分代码以及引用的第三方jar包library#######
###如果在当前的application module或者依赖的library module中使用了第三方的库，并不需要显式添加规则
###-libraryjars xxx
###添加了反而有可能在打包的时候遭遇同一个jar多次被指定的错误，一般只需要添加忽略警告和保持某些class不被混淆的声明。
###以libaray的形式引用了开源项目,如果不想混淆 keep 掉，在引入的module的build.gradle中设置minifyEnabled=false
##-keep class com.nineoldandroids.** { *; }
##-keep interface com.nineoldandroids.** { *; }
##-dontwarn com.nineoldandroids.**
### 下拉刷新
##-keep class in.srain.cube.** { *; }
##-keep interface in.srain.cube.** { *; }
##-dontwarn in.srain.cube.**
### observablescrollview：tab fragment
##-keep class com.github.ksoichiro.** { *; }
##-keep interface com.github.ksoichiro.** { *; }
##-dontwarn com.github.ksoichiro.**
##
###-dontpreverify
###
###-repackageclasses ''
###
###-allowaccessmodification
###
###-optimizations !code/simplification/arithmetic
##
###-keepattributes *Annotation*
###
###-keep public class * extends android.app.Activity
###
###-keep public class * extends android.app.Application
###
###-keep public class * extends android.app.Service
###
###-keep public class * extends android.content.BroadcastReceiver
###
###-keep public class * extends android.content.ContentProvider
##
###-keep public class * extends android.view.View {
###
###    public <init>(android.content.Context);
###
###    public <init>(android.content.Context, android.util.AttributeSet);
###
###    public <init>(android.content.Context, android.util.AttributeSet, int);
###
###    public void set*(...);
###
###}
###
###-keepclasseswithmembers class * {
###
###    public <init>(android.content.Context, android.util.AttributeSet);
###
###}
###
###-keepclasseswithmembers class * {
###
###    public <init>(android.content.Context, android.util.AttributeSet, int);
###
###}
###
###-keepclassmembers class * implements android.os.Parcelable {
###
###    static android.os.Parcelable$Creator CREATOR;
###
###}
###
###-keepclassmembers class **.R$* {
###
###    public static <fields>;
###
###}
#
##############################################
##
## 对于一些基本指令的添加
##
##############################################
## 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
#-optimizationpasses 5
#
## 混合时不使用大小写混合，混合后的类名为小写
#-dontusemixedcaseclassnames
#
## 指定不去忽略非公共库的类
#-dontskipnonpubliclibraryclasses
#
## 这句话能够使我们的项目混淆后产生映射文件
## 包含有类名->混淆后类名的映射关系
#-verbose
#
## 指定不去忽略非公共库的类成员
#-dontskipnonpubliclibraryclassmembers
#
## 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
#-dontpreverify
#
## 保留Annotation不混淆
#-keepattributes *Annotation*,InnerClasses
#
## 避免混淆泛型
#-keepattributes Signature
#
## 抛出异常时保留代码行号
#-keepattributes SourceFile,LineNumberTable
#
## 指定混淆是采用的算法，后面的参数是一个过滤器
## 这个过滤器是谷歌推荐的算法，一般不做更改
#-optimizations !code/simplification/cast,!field/*,!class/merging/*
#
#
##############################################
##
## Android开发中一些需要保留的公共部分
##
##############################################
#
## 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
## 因为这些子类都有可能被外部调用
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Appliction
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService
#
#
## 保留support下的所有类及其内部类
#-keep class android.support.** {*;}
#
## 保留继承的
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.support.v7.**
#-keep public class * extends android.support.annotation.**
#
## 保留R下面的资源
#-keep class **.R$* {*;}
#
## 保留本地native方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## 保留在Activity中的方法参数是view的方法，
## 这样以来我们在layout中写的onClick就不会被影响
#-keepclassmembers class * extends android.app.Activity{
#    public void *(android.view.View);
#}
#
## 保留枚举类不被混淆
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#  }
#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------
-keep class com.growatt.shinephone.bean.** { *; }
-keep class com.vo.** { *; }
-keep class com.mining.app.zxing.** { *; }
-keep class com.example.m30.wifi.** { *; }
-keep class com.dao.** { *; }
-keep class com.danikula.videocache.** { *; }
-keep class android.backport.webp.** { *; }
-keep class com.growatt.shinephone.sqlite.** { *; }
-keep class com.growatt.shinephone.tool.** { *; }
-keep class com.growatt.shinephone.ui.** { *; }
-keep class com.growatt.shinephone.update.** { *; }
-keep class com.growatt.shinephone.util.** { *; }
-keep class com.growatt.shinephone.video.** { *; }
-keep class com.growatt.shinephone.view.** { *; }
-keep class com.growatt.shinephone.fragment.** { *; }


#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn com.hp.hpl.sparta.**
-keep class com.hp.hpl.sparta.** { *; }

-dontwarn demo.**
-keep class demo.** { *; }
#保持不混淆
-dontwarn org.achartengine.**
-keep class org.achartengine.** { *; }

-dontwarn com.baidu.**
-keep class com.baidu.** { *; }
-dontwarn com.baidu.a.a.a.a.**
-keep class com.baidu.a.a.a.a.** { *; }
-dontwarn com.baidu.a.a.a.b.**
-keep class com.baidu.a.a.a.b.** { *; }
-dontwarn com.baidu.location.**
-keep class com.baidu.location.** { *; }
-dontwarn com.baidu.lbsapi.auth.**
-keep class com.baidu.lbsapi.auth.** { *; }

-dontwarn  com.xsj.crasheye.**
-dontwarn  com.ant.liao.**
-dontwarn  org.apache.http.entity.mime.**
-dontwarn  org.jsoup.**
-dontwarn  com.github.mikephil.charting.**
-dontwarn  net.sourceforge.pinyin4j.**
-dontwarn  com.nostra13.universalimageloader.**
-dontwarn  com.android.volley.**
-dontwarn  com.google.zxing.**
-dontwarn  com.growatt.shinephone.bean.**

-keep class com.xsj.crasheye.** { *; }
-keep class com.ant.liao.** { *; }
-keep class org.apache.http.entity.mime.** { *; }
-keep class org.jsoup.** { *; }
-keep class com.github.mikephil.charting.** { *; }
-keep class net.sourceforge.pinyin4j.** { *; }
-keep class com.nostra13.universalimageloader.** { *; }
-keep class com.android.volley.** { *; }
-keep class com.google.zxing.** { *; }
-keep class com.growatt.shinephone.bean.** { *; }
#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.** {*;}


#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------



#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

