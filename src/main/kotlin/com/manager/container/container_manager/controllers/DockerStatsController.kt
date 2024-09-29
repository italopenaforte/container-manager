package com.manager.container.container_manager.controllers

import com.github.dockerjava.api.model.Statistics
import com.manager.container.container_manager.services.DockerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class DockerStatsController {

    private val dockerService: DockerService

    constructor(dockerService: DockerService) {
        this.dockerService = dockerService
    }

    @GetMapping("/api/{containerId}/stats")
    fun getStatsContainers(@PathVariable containerId: String): Statistics? {
        return this.dockerService.retrieveStats(containerId)
    }
}