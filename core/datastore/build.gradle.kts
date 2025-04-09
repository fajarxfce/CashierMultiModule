plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.library.jacoco)
    alias(libs.plugins.nowinandroid.hilt)
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "com.fajarxfce.core.datastore"
}

dependencies {
    api(libs.androidx.dataStore)
    api(projects.core.model)

    implementation(projects.core.common)

    testImplementation(libs.kotlinx.coroutines.test)
}