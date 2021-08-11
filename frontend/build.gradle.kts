import com.google.protobuf.gradle.protobuf
import utils.Vers

plugins {
    id("ts.frontend")
}

dependencies {
    implementation("tech.linqu.webpb:webpb-proto:${Vers.webpb}")
    protobuf(project(":proto:api"))
}

tasks.clean {
    enabled = false
}
