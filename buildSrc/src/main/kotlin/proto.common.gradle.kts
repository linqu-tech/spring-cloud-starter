import utils.Props
import utils.Vers

plugins {
    id("conventions.common")
    java
}

Props.initialize(project)
Vers.initialize(project)

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri(Props.snapshotRepo) }
}

dependencies {
    compileOnly("tech.linqu.webpb:webpb-proto:${Vers.webpb}")
}
