plugins {
    alias(libs.plugins.nowinandroid.jvm.library)
}

group = "com.fajarxfce.feature.splash.domain"

dependencies {
    implementation(projects.core.common)
    implementation(libs.javax.inject)
}