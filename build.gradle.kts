plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialize) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
}