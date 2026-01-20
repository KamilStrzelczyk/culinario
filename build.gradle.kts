plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.detekt) apply true
}

group = "com.ks"
version = "0.0.1-SNAPSHOT"
description = "REST API do zarządzania przepisami kulinarnymi"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(20)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.kotlin.reflect)
    implementation(libs.springdoc.openapi.ui)

    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)

    runtimeOnly(libs.h2.database)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.web.test)
    testImplementation(libs.spring.boot.starter.data.jpa.test)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

detekt {
    buildUponDefaultConfig = true
    autoCorrect = true

    dependencies {
        detektPlugins(libs.detekt.formatting)
    }
}
