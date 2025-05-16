plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.hilt)
    alias(libs.plugins.nowinandroid.android.feature)
    id("kotlinx-serialization")
}

android {
    namespace = "com.fajarxfce.feature.splash.data"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.feature.splash.domain)
    implementation(libs.retrofit.core)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.converter.gson)
}