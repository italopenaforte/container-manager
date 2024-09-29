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
    @Value("\${docker.socket.path}")
    private val dockerSocketPath: String = ""

    @Bean
    fun buildDockerClient(): DockerClient {
        // create a configuration class for a builder
        var dockerClientConfigBuilder: DefaultDockerClientConfig.Builder =
            DefaultDockerClientConfig.createDefaultConfigBuilder();

        // checks if the env is a unix socket
        this.dockerSocketPath.takeIf { it.isNotBlank() && it.startsWith("unix://") }?.let {
            dockerClientConfigBuilder.withDockerHost(it).withDockerTlsVerify(false)
        }
        // create an object to receive the configuration
        var dockerClientConfig: DefaultDockerClientConfig = dockerClientConfigBuilder.build()

        // setup an http client for docker socket
        var dockerHttpClient: ApacheDockerHttpClient? = null
        try {
            dockerHttpClient = ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .maxConnections(5)
                .connectionTimeout(Duration.ofMillis(300))
                .responseTimeout(Duration.ofSeconds(3))
                .build()
        } catch (e: Exception) {
            print("Error while setup the docker http client $e")
        }

        // with config from docker and the http client setup, finally you can instance the docker client class
        // using the dockerClientConfig object and the http client for the socket
        var client: DockerClient = DockerClientBuilder.getInstance(dockerClientConfig)
            .withDockerHttpClient(dockerHttpClient)
            .build()

        // do a ping just to see if is working
        client.pingCmd().exec()

        return client
    }
}