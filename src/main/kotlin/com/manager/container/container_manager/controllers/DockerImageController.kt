package com.manager.container.container_manager.controllers

import com.github.dockerjava.api.model.Image
import com.manager.container.container_manager.services.DockerService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/images")
class DockerImageController {

    private val dockerService: DockerService;

    constructor(dockerService: DockerService) {
        this.dockerService = dockerService
    }

    @GetMapping("")
    fun getAllImages(): List<Image> {
        return this.dockerService.listAllImages()
    }

    @GetMapping("/{imageId}/delete")
    fun deleteImage(@PathVariable imageId: String): Void? {
        return this.dockerService.deleteImage(imageId)
    }
}