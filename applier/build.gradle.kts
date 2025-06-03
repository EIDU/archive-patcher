plugins {
    java
}

dependencies {
    implementation(project(":shared"))

    testImplementation("junit:junit:4.13.1")
    testImplementation(project(":sharedtest"))
}
