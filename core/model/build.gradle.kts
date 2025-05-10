plugins {
    alias(libs.plugins.nowinandroid.android.library)
    id("kotlinx-serialization")
}
android {
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    namespace = "com.fajarxfce.core.model"
}
dependencies {
    api(libs.kotlinx.datetime)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.converter.gson)
}