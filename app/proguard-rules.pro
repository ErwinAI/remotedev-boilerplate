# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\x\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep annotations as they are used internally for fabric / crashlytics
-keepattributes *Annotation*
# Keep line numbers
-keepattributes SourceFile,LineNumberTable
# Keep all exception code deobfuscated
-keep public class * extends java.lang.Exception
# Keep all crashlytics files deobfuscated
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
# Remove log messages
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** d(...);
    public static *** e(...);
    public static boolean isLoggable(java.lang.String, int);
}
