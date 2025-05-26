plugins {
    java
    application
}

group = "com.engine"
version = "1.0.0"

application {
    mainClass.set("com.engine.App")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}
