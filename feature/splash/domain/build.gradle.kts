plugins {
    alias(libs.plugins.nowinandroid.android.feature)
}

android {
    namespace = "com.fajarxfce.feature.splash.domain"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.javax.inject)
}