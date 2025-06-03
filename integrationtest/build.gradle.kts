plugins {
    java
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":applier"))
    implementation(project(":generator"))

    testImplementation("junit:junit:4.13.1")
    testImplementation(project(":sharedtest"))
}
