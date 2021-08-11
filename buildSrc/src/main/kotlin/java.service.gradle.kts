import com.google.protobuf.gradle.*
import utils.Vers

plugins {
    id("com.google.protobuf")
    id("java.infra")
}

val k8s: String by project

dependencies {
    annotationProcessor("org.mapstruct:mapstruct-processor:${Vers.mapstruct}")
    annotationProcessor("tech.linqu.webpb:webpb-processor:${Vers.webpb}")
    developmentOnly("org.jooq:jooq-codegen:${Vers.jooq}")
    developmentOnly("org.jooq:jooq-meta-extensions:${Vers.jooq}")
    implementation("com.google.protobuf:protobuf-java:${Vers.protobufJava}")
    implementation("org.apache.commons:commons-lang3:${Vers.commonsLang3}")
    implementation("org.flywaydb:flyway-core:${Vers.flyway}")
    implementation("org.mapstruct:mapstruct:${Vers.mapstruct}")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    if (k8s == "true") {
        implementation("org.springframework.cloud:spring-cloud-starter-kubernetes:1.1.10.RELEASE")
        implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    } else {
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    }
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.data:spring-data-commons")
    implementation("tech.linqu.webpb:webpb-runtime:${Vers.webpb}")
    implementation(project(":lib:proto"))
    implementation(project(":lib:utilities"))
    runtimeOnly("mysql:mysql-connector-java:${Vers.mysqlConnector}")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:${Vers.mapstruct}")
    testImplementation("com.h2database:h2:${Vers.h2}")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(
        arrayOf(
            "-Xlint:deprecation",
            "-Xlint:unchecked",
            "-Amapstruct.unmappedTargetPolicy=IGNORE"
        )
    )
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${Vers.protoc}"
    }
    plugins {
        id("webpb") {
            artifact = "tech.linqu.webpb:protoc-webpb-java:${Vers.webpb}:all@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.builtins {
                remove("java")
            }
            it.plugins {
                id("webpb")
            }
        }
    }
}
