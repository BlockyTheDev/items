plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") apply(true)
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    api(projects.itemsCore)
    api(libs.bedrock.core)
}