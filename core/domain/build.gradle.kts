plugins {
    alias(libs.plugins.nowinandroid.jvm.library)
}
dependencies {
    api(projects.core.model)
    implementation(libs.kotlinx.coroutines.core)
}