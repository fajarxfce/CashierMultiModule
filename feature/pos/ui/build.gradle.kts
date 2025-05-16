plugins {
    alias(libs.plugins.nowinandroid.android.feature)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.fajarxfce.feature.pos.ui"
}

dependencies {
    implementation(projects.feature.pos.domain)
    implementation(libs.androidx.material)
}