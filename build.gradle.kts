plugins {
    kotlin("jvm") version "1.7.21"
}

group = "br.thiago"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.22")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("io.appium:java-client:7.3.0")
    testImplementation("com.browserstack:browserstack-local-java:1.0.6")
    testImplementation("com.google.guava:guava:31.1-jre")
    testImplementation("io.rest-assured:rest-assured:5.3.0")
}

tasks.test {
    useJUnitPlatform()
    systemProperties(System.getProperties().toMap() as Map<String, Object>)
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "15"
    }
}