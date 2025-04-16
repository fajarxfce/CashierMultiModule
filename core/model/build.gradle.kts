plugins {
    alias(libs.plugins.nowinandroid.jvm.library)
    id("kotlinx-serialization")
}

dependencies {
    api(libs.kotlinx.datetime)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.converter.gson)
}