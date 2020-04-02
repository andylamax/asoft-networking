import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

fun KotlinDependencyHandler.coreLibs(platform: String) {
    api(asoftCore("io", platform))
}

android {
    configureAndroid()
}

kotlin {
    android {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
        publishLibraryVariants("release")
    }

    jvm {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
    }

    js {
        compilations.all {
            kotlinOptions {
                metaInfo = true
                sourceMap = true
                moduleKind = "commonjs"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                coreLibs("metadata")
                api("io.ktor:ktor-client-core:${versions.ktor}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoftTest("metadata"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                api("io.ktor:ktor-client-android:${versions.ktor}")
                coreLibs("android")
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(asoftTest("android"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                api("io.ktor:ktor-client-cio:${versions.ktor}")
                coreLibs("jvm")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(asoftTest("jvm"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                api("io.ktor:ktor-client-js:${versions.ktor}")
                coreLibs("js")
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(asoftTest("js"))
            }
        }
    }
}