import utils.Vers
import utils.jooqTask

plugins {
    id("java.service")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.security.experimental:spring-security-oauth2-authorization-server:${Vers.authorizationServer}")
    testImplementation(project(":lib:tests"))
}

tasks.bootJar {
    archiveFileName.set("auth-service.jar")
    launchScript()
}

tasks.javadoc {
    exclude("tech/linqu/spring/cloud/starter/auth/schema/**")
}

tasks.checkstyleMain {
    exclude("tech/linqu/spring/cloud/starter/auth/schema/**")
}

jooqTask("jooq.xml")
