import utils.Vers
import utils.jooqTask

plugins {
    id("io.spring.dependency-management")
    id("java.library")
    id("org.springframework.boot")
}

dependencies {
    implementation("org.jooq:jooq-codegen:${Vers.jooq}")
    implementation("org.junit.jupiter:junit-jupiter-api:${Vers.jupiter}")
    implementation("org.junit.jupiter:junit-jupiter-engine")
    implementation("org.mapstruct:mapstruct:${Vers.mapstruct}")
    implementation("org.mapstruct:mapstruct:${Vers.mapstruct}")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.data:spring-data-commons")
    implementation("tech.linqu.webpb:webpb-runtime:${Vers.webpb}")
}

tasks.bootJar {
    enabled = false
}

tasks.processResources {
    exclude("db/migration/**")
}

tasks.jar {
    enabled = true
}

tasks.javadoc {
    exclude("tech/linqu/spring/cloud/starter/tests/schema/**")
}

tasks.checkstyleMain {
    exclude("tech/linqu/spring/cloud/starter/tests/schema/**")
}

jooqTask("jooq.xml")
