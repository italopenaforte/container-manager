package com.manager.container.container_manager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContainerManagerApplication

fun main(args: Array<String>) {
	runApplication<ContainerManagerApplication>(*args)
}
