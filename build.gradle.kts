import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.cloud.contract.verifier.config.TestFramework
import org.springframework.cloud.contract.verifier.config.TestMode

plugins {
    val kotlinVersion = "1.3.31"
    idea
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    jacoco
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.springframework.cloud.contract") version "2.2.2.RELEASE"
}

group = "poc"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-starter-contract-verifier:2.2.2.RELEASE")
    }
}

val junitVersion = "5.6.0"
val junitPlatformVersion = "1.6.0"
val spekVersion = "2.0.9"
val kluentVersion = "1.59"
val mockitoKotlinVersion = "2.2.0"
val restAssuredVersion = "4.2.0"
val cucumberVersion = "5.4.2"
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
    // this is a trick to use the stub generated in the same project
    testImplementation(files("libs/gradle-spring-boot-kotlin-template-1.0-SNAPSHOT-stubs.jar"))
    testImplementation("io.projectreactor:reactor-test")

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation("io.rest-assured:xml-path:$restAssuredVersion")
    testImplementation("io.rest-assured:spring-web-test-client:$restAssuredVersion")

    testImplementation("io.cucumber:cucumber-spring:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-java8:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("org.junit.vintage:junit-vintage-engine:$junitVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("junit-jupiter", "spek2", "junit-vintage")
    }

    finalizedBy("jacocoTestReport")
}

jacoco {
    toolVersion = "0.8.5"
}

contracts {
    setTestMode(TestMode.WEBTESTCLIENT)
    setTestFramework(TestFramework.JUNIT5)
    setBaseClassMappings(
        mapOf(
            "users" to "poc.web.reactive.router.AbstractUsersContractTest"
        )
    )
}
