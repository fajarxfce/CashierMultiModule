plugins {
    alias(libs.plugins.nowinandroid.android.feature)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.fajarxfce.feature.home.ui"
}

dependencies {
    implementation(projects.feature.home.domain)
    implementation("androidx.compose.material:material:1.9.0-alpha01")
}