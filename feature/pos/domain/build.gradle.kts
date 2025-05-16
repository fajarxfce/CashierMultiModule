plugins {
    alias(libs.plugins.nowinandroid.jvm.library)
}

group = "com.fajarxfce.feature.pos.domain"

dependencies {
    implementation(projects.core.common)
    implementation(libs.javax.inject)
}