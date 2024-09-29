package com.manager.container.container_manager.controllers

import com.github.dockerjava.api.model.Image
import com.manager.container.container_manager.services.DockerService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootTest(classes = [DockerImageController::class])
@AutoConfigureMockMvc
@EnableWebMvc
class DockerImageControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val dockerService: DockerService? = null

    @Test
    fun shouldReturnListOfDockerImages() {
        val image: Image = Image()
        `when`(dockerService?.listAllImages()).thenReturn(arrayListOf(image))
        this.mockMvc?.perform(get("/api/images")
            .accept(MediaType.APPLICATION_JSON))?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun shouldDeleteImage() {
        val imageId: String = "containerId"

        this.mockMvc?.perform(get("/api/images/{imageId}/delete", imageId).accept(MediaType.APPLICATION_JSON))
            ?.andDo(print())
            ?.andExpect(status().isOk)
    }

}