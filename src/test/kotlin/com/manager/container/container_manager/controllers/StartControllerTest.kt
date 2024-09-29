package com.manager.container.container_manager.controllers

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest(classes = [StartController::class])
@AutoConfigureMockMvc
class StartControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    fun shouldReturnDefaultMessage() {
        this.mockMvc?.perform(get("/"))?.andDo(print())?.andExpect(status().isOk)?.andExpect(
            content().string(
                containsString("Hello")
            )
        )
    }

}