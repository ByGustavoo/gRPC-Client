plugins {
    java
    id("jacoco")
    id("com.google.protobuf") version "0.9.6"
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
}

version = "1.0.0"
group = "br.com.grpc.client"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

tasks.named<Jar>("jar") {
    enabled = false
}

configurations.configureEach {
    exclude(group = "ch.qos.logback", module = "logback-classic")
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

dependencies {

    // Spring Boot
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-grpc-client")

    // gRPC - Protobuf
    implementation("io.grpc:grpc-protobuf")
    implementation("com.google.protobuf:protobuf-java")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Logging
    implementation("org.slf4j:slf4j-api")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")

    // Testes
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")

}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.34.2"
    }

    plugins {
        named("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.83.1"
        }
    }

    generateProtoTasks {
        all().configureEach {
            plugins {
                named("grpc")
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)

    reports {
        html.required.set(true)
    }

    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        "**/config/**",
                        "**/ClientApplication.class"
                    )
                }
            }
        )
    )
}