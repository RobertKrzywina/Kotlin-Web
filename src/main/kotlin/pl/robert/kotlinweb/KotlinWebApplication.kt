package pl.robert.kotlinweb

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KotlinWebApplication

fun main(args: Array<String>) {
    runApplication<KotlinWebApplication>(*args)
}
