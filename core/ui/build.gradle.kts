plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.fajarxfce.core.ui"
}

dependencies {
    api(libs.androidx.ui.text.google.fonts)
    api(libs.androidx.compose.material.iconsExtended)
}