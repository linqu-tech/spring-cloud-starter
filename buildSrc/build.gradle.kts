import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    gradlePluginPortal()
}

val properties = Properties()
FileInputStream(file("../gradle.properties")).use {
    properties.load(it)
}

for (key in properties.stringPropertyNames()) {
    ext.set(key, properties.getProperty(key))
}

val versionJooq: String by project
val versionPluginCoveralls: String by project
val versionPluginDependencyManagement: String by project
val versionPluginProtobuf: String by project
val versionPluginSpringBoot: String by project
val versionPluginVersions: String by project

dependencies {
    implementation("com.github.ben-manes:gradle-versions-plugin:${versionPluginVersions}")
    implementation("com.github.kt3k.coveralls:com.github.kt3k.coveralls.gradle.plugin:${versionPluginCoveralls}")
    implementation("com.google.protobuf:protobuf-gradle-plugin:${versionPluginProtobuf}")
    implementation("io.spring.gradle:dependency-management-plugin:${versionPluginDependencyManagement}")
    implementation("org.jooq:jooq-codegen:${versionJooq}")
    implementation("org.jooq:jooq-meta-extensions:${versionJooq}")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${versionPluginSpringBoot}")
    implementation(kotlin("script-runtime"))
}
