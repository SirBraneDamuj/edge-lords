plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.21"
    kotlin("kapt") version "1.4.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.javalin:javalin:3.12.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.3")
    implementation("com.google.dagger:dagger:2.30.1")
    implementation("org.jetbrains.exposed:exposed-core:0.28.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.28.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.28.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.28.1")
    implementation("org.xerial:sqlite-jdbc:3.34.0")
    implementation("org.postgresql:postgresql:42.2.16")
    implementation("org.mindrot:jbcrypt:0.4")
    kapt("com.google.dagger:dagger-compiler:2.30.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}
