plugins {
    alias(libs.plugins.nowinandroid.android.feature)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.fajarxfce.feature.cart.ui"
}

dependencies {
    implementation(projects.feature.cart.domain)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(libs.androidx.material)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
}