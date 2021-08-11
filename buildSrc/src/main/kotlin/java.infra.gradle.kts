import gradle.kotlin.dsl.accessors._1dc87f5aa74e2d0370f57591834dec89.implementation
import utils.Vers

plugins {
    `maven-publish`
    application
    id("io.spring.dependency-management")
    id("java.common")
    id("org.springframework.boot")
    java
    signing
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${Vers.springCloud}")
        }
    }
}
