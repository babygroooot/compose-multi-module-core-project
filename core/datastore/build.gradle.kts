plugins {
    alias(libs.plugins.developmentway.android.library.core)
    alias(libs.plugins.developmentway.android.hilt)
}

android {
    namespace = "com.core.datastore"
}

dependencies {

    implementation(libs.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)

}
