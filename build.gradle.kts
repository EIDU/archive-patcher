import java.util.Properties

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    java
    signing
    `maven-publish`
    id("com.palantir.git-version") version "3.0.0"
}

val localPropertiesFile = project.rootProject.file("local.properties")
val localProperties = Properties()
if (localPropertiesFile.canRead()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val gitVersion: groovy.lang.Closure<String> by extra

version = gitVersion()

subprojects {
    apply(plugin = "java")
}

tasks.jar {
    subprojects.forEach { subproject ->
        from(subproject.the<SourceSetContainer>()["main"].output)
    }
    from("LICENSE")
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn(tasks.classes)
    archiveClassifier.set("sources")
    subprojects.forEach { subproject ->
        from(subproject.the<SourceSetContainer>()["main"].allSource)
    }
    from("LICENSE")
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(tasks.javadoc)
    archiveClassifier.set("javadoc")
    subprojects.forEach { subproject ->
        from(subproject.tasks.named("javadoc").get().outputs)
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from("LICENSE")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

artifacts {
    archives(tasks.jar)
    archives(sourcesJar)
    archives(javadocJar)
}

signing {
    val keyId = System.getenv("MAVEN_CENTRAL_KEY_ID")
    val key = System.getenv("MAVEN_CENTRAL_KEY")
    val password = System.getenv("MAVEN_CENTRAL_KEY_PASSWORD")

    if (keyId != null && key != null && password != null) {
        useInMemoryPgpKeys(keyId, key, password)
        sign(publishing.publications)
    } else
        logger.warn("Signing credentials not found. Skipping signing.")
}

publishing {
    repositories {
        maven {
            name = "MavenCentral"
            setUrl("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_CENTRAL_USERNAME")
                password = System.getenv("MAVEN_CENTRAL_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.eidu"
            artifactId = "archive-patcher"
            version = gitVersion()

            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set("archive-patcher")
                description.set("Google Archive Patcher (EIDU fork)")
                url.set("https://github.com/EIDU/archive-patcher")
                licenses {
                    license {
                        name.set("Apache 2.0 License")
                        url.set("https://raw.githubusercontent.com/EIDU/archive-patcher/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("berlix")
                        name.set("Felix Engelhardt")
                        url.set("https://github.com/berlix/")
                    }
                }
                scm {
                    url.set("https://github.com/EIDU/archive-patcher")
                    connection.set("scm:git:git://github.com/EIDU/archive-patcher.git")
                    developerConnection.set("scm:git:ssh://git@github.com/EIDU/archive-patcher.git")
                }
            }
        }
    }
}
