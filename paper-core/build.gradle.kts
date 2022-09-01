plugins {
    id("io.papermc.paperweight.userdev") apply(true)
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    implementation(projects.itemsCore)
}