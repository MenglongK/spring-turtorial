# Understand Instruction
FROM ghcr.io/graalvm/jdk-community:25i2
WORKDIR /workspace
COPY build/libs/spring-webmvc-1.0.jar /workspace/api.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/workspace/api.jar"]