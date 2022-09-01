plugins {
    `java`
    `maven-publish`
    id("org.checkerframework") apply(false)
}

allprojects {
    group = "cafe.navy.items"
    version = "0.0.1"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.checkerframework")
    apply(plugin = "maven-publish")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    configure<JavaPluginExtension> {
        java {
            toolchain.languageVersion.set(JavaLanguageVersion.of(18))
        }
    }

    publishing {
        // create maven publication using java artifacts
        publications {
            create<MavenPublication>(project.name) {
                from(components["java"])
            }
        }
    }

}


