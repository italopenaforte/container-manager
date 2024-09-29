package com.manager.container.container_manager.config

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class DockerClientConfig {

    // get env from application.properties
    @Value("\${docker.socket.path}") private val dockerSocketPath: String = ""

    @Bean
    fun buildDockerClient(): DockerClient {
        // create an configuration class for a builder
        var dockerClientConfigBuilder : DefaultDockerClientConfig.Builder = DefaultDockerClientConfig.createDefaultConfigBuilder();

        // checks if the env is an unix socket
        if (this.dockerSocketPath.isNotBlank() && this.dockerSocketPath.startsWith("unix://")) {
            dockerClientConfigBuilder.withDockerHost(this.dockerSocketPath).withDockerTlsVerify(false)
        }
        // create an object to recive the configuration
        var dockerClientConfig : DefaultDockerClientConfig = dockerClientConfigBuilder.build()

        // setup an http client for docker socket
        var dockerHttpClient: ApacheDockerHttpClient? = ApacheDockerHttpClient.Builder()
            .dockerHost(dockerClientConfig.getDockerHost())
            .maxConnections(5)
            .connectionTimeout(Duration.ofMillis(300))
            .responseTimeout(Duration.ofSeconds(3))
            .build()

        // with config from docker and the http client setup, finally you can instante the docker client class
        // using the dockerClientConfig object and the http client for the socket
        var client: DockerClient = DockerClientBuilder.getInstance(dockerClientConfig)
            .withDockerHttpClient(dockerHttpClient)
            .build()

        // do a ping just to see if is working
        client.pingCmd().exec()

        return client
    }
}