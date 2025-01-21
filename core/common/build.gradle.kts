plugins {
    alias(libs.plugins.developmentway.android.library.common)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.core.common"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
}
