plugins {
    id("io.papermc.paperweight.userdev") version "1.7.1"
    kotlin("plugin.serialization") version "2.0.0"
}

val ktor_version: String by project

group = "gg.aquatic.renteraislands.paper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.infernalsuite.com/repository/maven-snapshots/")
    maven("https://repo.rapture.pw/repository/maven-releases/")
    maven("https://jitpack.io")
    maven("https://nexus.phoenixdevt.fr/repository/maven-public/")
}

dependencies {

    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-websockets:$ktor_version")

    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.5")
    compileOnly ("com.ticxo.modelengine:ModelEngine:R4.0.4")
    compileOnly("io.lumine:Mythic-Dist:5.3.5")
    implementation("com.jeff-media:custom-block-data:2.2.2")
    compileOnly("com.infernalsuite.aswm:api:1.20.4-R0.1-20240524.171344-26")
    implementation(platform("com.intellectualsites.bom:bom-newest:1.45"))
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("io.lumine:MythicLib-dist:1.6.2-SNAPSHOT")
    compileOnly("net.Indyuce:MMOItems-API:6.9.5-SNAPSHOT")
    implementation(project(":api"))
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION

tasks {
    build {
        dependsOn(shadowJar)
    }

    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("RenteraIslands-${project.version}.jar")
    archiveClassifier.set("plugin")
    dependencies {
        include(dependency("com.jeff-media:custom-block-data:2.2.2"))
        include(project(":api"))
    }
}