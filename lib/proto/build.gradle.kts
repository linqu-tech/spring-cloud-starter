import com.google.protobuf.gradle.*
import utils.Vers

plugins {
    id("com.google.protobuf")
    id("io.spring.dependency-management")
    id("java.library")
    id("org.springframework.boot")
}

dependencies {
    compileOnly("org.mapstruct:mapstruct:${Vers.mapstruct}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Vers.jackson}")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework:spring-web")
    implementation("tech.linqu.webpb:webpb-proto:${Vers.webpb}")
    implementation("tech.linqu.webpb:webpb-runtime:${Vers.webpb}")
    protobuf(project(":proto:api"))
    protobuf(project(":proto:rpc"))
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
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
