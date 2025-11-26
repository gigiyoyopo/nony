plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.appnony"
    compileSdk = 36

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    defaultConfig {
        applicationId = "com.example.appnony"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    dependencies {
        // --- COMPATIBILIDAD (XML + Activity Clásica) ---
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.activity:activity-ktx:1.8.2")

<<<<<<< Updated upstream
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Foundation (LazyVerticalGrid, clip, clickable…)
    implementation("androidx.compose.foundation:foundation")
=======
        // --- JETPACK COMPOSE (UI Moderna) ---
        // El BOM controla las versiones para que no choquen
        val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
        implementation(composeBom)
        androidTestImplementation(composeBom)

        // Librerías de Compose (Texto directo, sin 'libs.')
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")

        // 👇 ESTA ES LA QUE TE DABA ERROR, AHORA ESTÁ CORREGIDA
        implementation("androidx.compose.foundation:foundation")

        implementation("androidx.compose.ui:ui-text")

        // Navegación
        implementation("androidx.navigation:navigation-compose:2.7.7")
        implementation("androidx.activity:activity-compose:1.8.2")
>>>>>>> Stashed changes

        // --- RED (Retrofit + Moshi) ---
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
        implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

<<<<<<< Updated upstream
    // Coroutines (delay, isActive, etc.)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
=======
        // --- IMÁGENES (Coil) ---
        implementation("io.coil-kt:coil-compose:2.6.0")

        // --- GOOGLE LOGIN & FIREBASE ---
        implementation("com.google.android.gms:play-services-auth:21.0.0")
        implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
        implementation("com.google.firebase:firebase-analytics")

        // --- OTROS ---
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

        // Testing y Debug
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
        implementation("androidx.compose.material:material:1.6.0")
    }}

>>>>>>> Stashed changes
