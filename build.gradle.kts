import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.cloud.contract.verifier.config.TestFramework
import org.springframework.cloud.contract.verifier.config.TestMode

plugins {
    val kotlinVersion = "1.3.31"
    idea
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    jacoco
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.1.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.cloud.contract") version "2.1.2.RELEASE"
    `maven-publish`
}

group = "poc"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-starter-contract-verifier:2.1.2.RELEASE")
    }
}

val springBootVersion = "2.1.5.RELEASE"
val junitVersion = "5.5.0-RC1"
val junitPlatformVersion = "1.5.0-RC1"
val spekVersion = "2.0.5"
val kluentVersion = "1.49"
val mockitoKotlinVersion = "2.1.0"
val restAssuredVersion = "4.0.0"
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // we need both to make IDEA working
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.platform:junit-platform-engine:$junitPlatformVersion")
    testImplementation("org.junit.platform:junit-platform-commons:$junitPlatformVersion")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")

    testImplementation("org.amshove.kluent:kluent:$kluentVersion")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    // this is a trick  to use the stub generated in the same project
    testImplementation(files("libs/gradle-spring-boot-kotlin-template-1.0-SNAPSHOT-stubs.jar"))
    testImplementation("io.projectreactor:reactor-test")

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation("io.rest-assured:xml-path:$restAssuredVersion")
    testImplementation("io.rest-assured:spring-web-test-client:$restAssuredVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("junit-jupiter", "spek2")
    }

    finalizedBy("jacocoTestReport")
}

jacoco {
    toolVersion = "0.8.2"
}

contracts {
    testMode = TestMode.WEBTESTCLIENT
    testFramework = TestFramework.JUNIT5
    baseClassMappings = mapOf(
        "users" to "poc.web.reactive.router.AbstractUsersContractTest"
    )
}
