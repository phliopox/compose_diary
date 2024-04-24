// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript{
    extra.apply{
        set("kotlin_version", "1.8.10")
        set("nav_version", "2.5.3")
        set("hilt_version", "2.45")
        set("timber_version", "5.0.1")
        //set("lifecycle_version", "2.6.1")
        set("retrofit_version", "2.9.0")
        set("gson_version", "2.10.1")
        set("room_version" , "2.6.0")
       // set("compose_ui_version", "1.3.2")
       // set("activity_compose_version", "1.6.1")
       // set("compose_material_version", "1.0.1")

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.3.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:"+rootProject.extra["kotlin_version"])
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:"+rootProject.extra["nav_version"])
        classpath ("com.google.dagger:hilt-android-gradle-plugin:"+rootProject.extra["hilt_version"])
    }
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("androidx.room") version "2.6.0" apply false

}