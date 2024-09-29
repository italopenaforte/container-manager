package com.manager.container.container_manager.controllers

import com.manager.container.container_manager.services.DockerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/containers")
class DockerContainerController {

    private val dockerService: DockerService

    constructor(dockerService: DockerService) {
        this.dockerService = dockerService
    }

    @GetMapping("")
    fun getAllContainers(): List<com.github.dockerjava.api.model.Container> {
        return this.dockerService.listAllContainers(true)
    }

    @PutMapping("/{containerId}/stop")
    fun stopContainer(@PathVariable containerId: String) {
        return this.dockerService.stopContainer(containerId)
    }

    @PutMapping("/{containerId}/start")
    fun startContainer(@PathVariable containerId: String) {
        return this.dockerService.startContainer(containerId)
    }

    @DeleteMapping("/{containerId}/delete")
    fun deleteContainer(@PathVariable containerId: String) {
        return this.dockerService.deleteContainer(containerId)
    }

}