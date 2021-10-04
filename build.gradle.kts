import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
}

group = "com.fast"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    //	basic spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.session:spring-session-core")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.com.fast.api.security:spring-com.fast.api.security-test")

    //networking
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")// TODO if problem version might be an issue

    //db
    runtimeOnly("org.postgresql:postgresql")

    //documenation
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")

    //firebase admin and jwt
    implementation("com.google.firebase:firebase-admin:6.11.0")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    implementation("joda-time:joda-time:2.10.5")

    implementation("com.google.code.gson:gson:2.8.8")

    implementation("org.modelmapper:modelmapper:2.4.0")

    runtimeOnly("com.h2database:h2") //h2 if we want in mem testing


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
