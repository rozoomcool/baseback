plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.itabrek"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springAiVersion"] = "0.8.1"

dependencies {
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
	implementation("org.overviewproject:mime-types:0.1.3")
	implementation("org.slf4j:slf4j-api")
	implementation("ch.qos.logback:logback-classic")

	implementation("org.modelmapper:modelmapper:3.2.0")

	implementation("org.springframework.boot:spring-boot-starter-validation:3.1.2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.ai:spring-ai-ollama-spring-boot-starter")
	implementation("io.projectreactor.netty:reactor-netty")
	implementation("io.projectreactor.netty:reactor-netty-http")
//	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//	implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")

//	implementation("org.projectreactor:reactor-net")
	implementation("org.springframework.boot:spring-boot-starter-webflux")


	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-security")
	compileOnly("org.projectlombok:lombok")
//	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
