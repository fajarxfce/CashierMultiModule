plugins {
    alias(libs.plugins.nowinandroid.android.feature)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.fajarxfce.feature.transactionhistory.ui"
}

dependencies {
    implementation(projects.feature.transactionHistory.domain)
    implementation(libs.androidx.material)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
}