plugins {
    java
}

val mainClassName = "com.google.archivepatcher.tools.FileByFileTool"

tasks.jar {
    manifest {
        attributes("Main-Class" to mainClassName)
    }

    from(configurations.compileClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
}

dependencies {
    implementation(project(":applier"))
    implementation(project(":explainer"))
    implementation(project(":generator"))
    implementation(project(":shared"))
}
