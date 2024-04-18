
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
    id("androidx.room")

   // id("com.google.gms.google-services")
}

android {
    namespace = "com.example.gradientdiary"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gradientdiary"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
/*
<major> : 컨셉이나 앱이 지향하는 기본 디자인이 변경된 경우 (앱의 큰틀이 변화된 경우 )
<minor> : 기능 추가, 기능 삭제 및 사양 등이 변경된 경우
<point> : 버그 수정,디자인 수정 등
* */
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        create("release") {
            // 서명 구성 정보를 입력합니다.
            storeFile = file("D:\\jeong\\gradientDiary\\appkey.jks")
            storePassword = "Wwjdgus11!"
            keyAlias = "key0"
            keyPassword = "Wwjdgus11!"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")

            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

}

dependencies {
    //val nav_version = rootProject.extra["nav_version"]
    val hilt_version = rootProject.extra["hilt_version"]
    val timber_version = rootProject.extra["timber_version"]
    val retrofit_version = rootProject.extra["retrofit_version"]
    val gson_version = rootProject.extra["gson_version"]

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // navigation - compose
    implementation ("androidx.navigation:navigation-compose:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-alpha03")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")


    // Hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    //datastore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-core:1.0.0")

    // Timber
    implementation("com.jakewharton.timber:timber:$timber_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson
    implementation("com.google.code.gson:gson:$gson_version")

}