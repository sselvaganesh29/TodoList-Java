plugins {
    id("java")
}

group = "login"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.4")

    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20241224")

    // Jackson for JSON processing
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")

    // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
    implementation("io.jsonwebtoken:jjwt:0.12.6")
}

tasks.test {
    useJUnitPlatform()
}