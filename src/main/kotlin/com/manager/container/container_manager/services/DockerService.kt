package com.manager.container.container_manager.services

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.*
import com.github.dockerjava.core.InvocationBuilder
import org.springframework.stereotype.Service


@Service
class DockerService(private val dockerClient: DockerClient) {

    // Dealing with images
    fun listAllImages(): List<Image> {
        return dockerClient.listImagesCmd().exec()
    }

    fun deleteImage(imageId: String): Void? {
        return dockerClient.removeImageCmd(imageId).exec()
    }

    // Dealing with stats
    fun retrieveStats(containerId: String): Statistics? {
        val asyncResultCallback = InvocationBuilder.AsyncResultCallback<Statistics>()

        dockerClient.statsCmd(containerId).withNoStream(true).exec(asyncResultCallback)

        return try {
            val stats = asyncResultCallback.awaitResult()
            stats
        } catch (e: Exception) {
            print("Exception occurred while executing container $containerId")
            null
        } finally {
            asyncResultCallback.close()
        }

    }

    // Dealing with container
    fun listAllContainers(all: Boolean): List<Container> {
        return dockerClient.listContainersCmd().withShowAll(all).exec()
    }

    fun stopContainer(containerId: String) {

        if (this.findContainer(containerId) != null) {
            dockerClient.stopContainerCmd(containerId).exec()
        }
    }

    fun startContainer(containerId: String) {
        if (this.findContainer(containerId) != null) {
            dockerClient.startContainerCmd(containerId).exec()
        }
    }

    fun deleteContainer(containerId: String) {
        if (this.findContainer(containerId) != null) {
            dockerClient.startContainerCmd(containerId).exec()
        }
    }

    fun findContainer(containerId: String): Container? {
        return dockerClient.listContainersCmd().exec().find { it.id == containerId }
    }
}