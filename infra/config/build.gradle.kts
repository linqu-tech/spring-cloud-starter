plugins {
    id("java.infra")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.cloud:spring-cloud-config-server")
}

tasks.bootJar {
    archiveFileName.set("config.jar")
    launchScript()
}
