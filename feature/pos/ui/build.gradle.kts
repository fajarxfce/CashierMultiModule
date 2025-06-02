plugins {
    alias(libs.plugins.nowinandroid.android.feature)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.fajarxfce.feature.pos.ui"
}

dependencies {
    api(projects.feature.pos.domain)
    api(projects.core.model)
    implementation(libs.androidx.material)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
    implementation("androidx.compose.foundation:foundation-layout:1.9.0-alpha03")
}