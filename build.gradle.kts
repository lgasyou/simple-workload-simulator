import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}
group = "bit.linc"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenLocal()
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test-junit5"))
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.apache.logging.log4j:log4j-core:2.13.3")
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "12"
}
application {
    mainClassName = "MainKt"
}