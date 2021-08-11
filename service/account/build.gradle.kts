import utils.Vers
import utils.jooqTask

plugins {
    id("java.service")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    testImplementation("org.mock-server:mockserver-netty:${Vers.mockServer}")
    testImplementation(project(":lib:tests"))
}

tasks.bootJar {
    archiveFileName.set("account-service.jar")
    launchScript()
}

tasks.javadoc {
    exclude("tech/linqu/spring/cloud/starter/account/schema/**")
}

tasks.checkstyleMain {
    exclude("tech/linqu/spring/cloud/starter/account/schema/**")
}

jooqTask("jooq.xml")
