plugins {
    id("java.infra")
}

val k8s: String by project

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    if (k8s == "true") {
        implementation("org.springframework.cloud:spring-cloud-starter-kubernetes:1.1.10.RELEASE")
        implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    } else {
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    }
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
}

tasks.bootJar {
    archiveFileName.set("gateway.jar")
    launchScript()
}
