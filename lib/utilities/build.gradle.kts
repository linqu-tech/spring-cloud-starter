import utils.Vers

plugins {
    id("io.spring.dependency-management")
    id("java.library")
    id("org.springframework.boot")
}

dependencies {
    compileOnly("org.jooq:jooq-codegen:${Vers.jooq}")
    compileOnly("org.mapstruct:mapstruct:${Vers.mapstruct}")
    implementation("org.jooq:jooq-codegen:${Vers.jooq}")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework:spring-web")
    testImplementation(project(":lib:tests"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
