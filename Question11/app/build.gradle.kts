plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}


android {
    namespace = "com.example.question11"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.question11"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.test.espresso:espresso-contrib:3.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation ("com.google.code.gson:gson:2.8.9")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:4.0.0")
    testImplementation ("androidx.test.ext:junit:1.2.1")
    testImplementation ("androidx.test:core:1.6.1")
    testImplementation ("androidx.test:runner:1.6.2")
    testImplementation("org.robolectric:robolectric:4.9")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}