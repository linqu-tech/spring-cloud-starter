plugins {
    id("java.infra")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}

tasks.bootJar {
    archiveFileName.set("registry.jar")
    launchScript()
}
