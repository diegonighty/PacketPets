plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version("1.5.3")
    id("xyz.jpenilla.run-paper") version("2.1.0")
    id("com.github.johnrengelman.shadow") version("7.1.2")
}

group = "com.fadecloud"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")

    mavenLocal()
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    implementation("org.spongepowered:configurate-yaml:4.2.0-SNAPSHOT")
    implementation("me.devnatan:inventory-framework-bukkit:3.0.0-rc.1")
    implementation("me.devnatan:inventory-framework-paper:3.0.0-rc.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    shadowJar {
        relocate("me.devnatan", "com.fadecloud.devnatan")
    }

    runServer {
        minecraftVersion("1.20.1")
    }

    test {
        useJUnitPlatform()
    }
}