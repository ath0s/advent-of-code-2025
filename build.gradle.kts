import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.jetbrains:annotations:26.0.2-1")
    implementation("tools.aqua:z3-turnkey:4.14.1")

    testImplementation(platform("org.junit:junit-bom:6.0.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation(kotlin("test"))

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {

    wrapper {
        gradleVersion = "9.2.1"
    }

    withType<JavaCompile> {
        enabled = false
        options.release = 21
    }

    withType<KotlinCompile> {
        compilerOptions{
            jvmTarget = JVM_21
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
        }
    }

    test {
        useJUnitPlatform()
    }

}
