plugins {
    id("java")
}

dependencies {
    compileOnly("net.dv8tion:JDA:5.0.0-beta.24")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}