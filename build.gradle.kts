plugins {
    java
    application
}

group = "com.example"
version = "1.0.0"

application {
    mainClass.set("com.example.App")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}
