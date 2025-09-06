plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("java-library")
}

dependencies {
    implementation(project(":addon-api"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.zaxxer:HikariCP:5.1.0")

    // JDA
    implementation("net.dv8tion:JDA:5.0.0-beta.24")

    // Jackson YAML
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")

    // DB drivers (runtime)
    runtimeOnly("org.xerial:sqlite-jdbc:3.46.0.0")
    runtimeOnly("com.mysql:mysql-connector-j:9.0.0")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.4.1")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
