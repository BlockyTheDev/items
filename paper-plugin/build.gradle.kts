plugins {
    id("com.github.johnrengelman.shadow") apply(true)
    id("io.papermc.paperweight.userdev") apply(true)
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    implementation(projects.itemsCore)
    implementation(projects.itemsPaperCore)
    implementation(libs.cloud.paper)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    reobfJar {
        dependsOn(shadowJar)
    }
}