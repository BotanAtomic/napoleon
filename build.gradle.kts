plugins {
    kotlin("jvm") version "1.6.10"
    antlr
    `maven-publish`
}

group = "io.deepn"
version = "0.0.2"

val javaVersion = JavaVersion.VERSION_17
java.sourceCompatibility = javaVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("org.apache.commons:commons-text:1.9")

    antlr("org.antlr:antlr4:4.9.3")
    implementation("org.antlr:antlr4-runtime:4.9.3")


    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    val packageName = "io.deepn.flow.generated"
    arguments = listOf(
        "-Dlanguage=Java",
        "-package", packageName,
        "-no-listener", "-visitor"
    )
    outputDirectory = file(projectDir.absolutePath + "/src/main/java/" + packageName.replace(".", "/"))
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xemit-jvm-type-annotations")
        jvmTarget = "17"
    }
    dependsOn.add(tasks.generateGrammarSource)
}

tasks.withType<JavaCompile> {
    sourceCompatibility = javaVersion.toString()
    targetCompatibility = javaVersion.toString()
    options.forkOptions.jvmArgs?.addAll(listOf("--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {

    repositories {
        maven {
            url = uri("https://gitlab.com/api/v4/projects/33324702/packages/maven")
            name = "GitLab"
            credentials(HttpHeaderCredentials::class) {
                name = "Private-Token"
                value = System.getenv("MAVEN_GITLAB_TOKEN")
            }

            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            artifact(tasks.kotlinSourcesJar)
        }
    }
}

