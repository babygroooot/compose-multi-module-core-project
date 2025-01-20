plugins {
    alias(libs.plugins.developmentway.android.application)
    alias(libs.plugins.developmentway.android.application.compose)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.madskill.mad_skill"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.designsystem)
}
