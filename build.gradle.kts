plugins {
    kotlin("jvm") version "1.6.10"
    antlr
    `maven-publish`
}

group = "esgi"
version = "0.0.4"

val javaVersion = JavaVersion.VERSION_17
java.sourceCompatibility = javaVersion

repositories {
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven {
        url = uri("https://dl.bintray.com/http4k/maven")
    }
}

val xchangeVersion = "5.0.13"

dependencies {

    implementation("org.apache.commons:commons-math3:3.6.1")

    implementation("com.influxdb:influxdb-client-java:6.0.0")
    implementation("com.influxdb:flux-dsl:6.0.0")

    implementation("org.knowm.xchange:xchange-core:$xchangeVersion")
    implementation("org.knowm.xchange:xchange-binance:$xchangeVersion")
    implementation("org.knowm.xchart:xchart:3.8.1")

    implementation(platform("org.http4k:http4k-bom:4.25.16.1"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-format-gson")

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.ta4j:ta4j-core:0.15-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("org.apache.commons:commons-text:1.9")

    antlr("org.antlr:antlr4:4.10.1")
    implementation("org.antlr:antlr4-runtime:4.10.1")


    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    val packageName = "esgi.napoleon.generated"
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

