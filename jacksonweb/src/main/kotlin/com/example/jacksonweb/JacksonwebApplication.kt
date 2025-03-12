package com.example.jacksonweb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JacksonwebApplication

fun main(args: Array<String>) {
//    runApplication<JacksonwebApplication>(*args)
    val hello = jsonMapper().writeValueAsString(Test(name = "Thiago"))
    println(hello)
    val obj = jacksonObjectMapper().readValue<Test>(hello)
    println(obj.name)
}

@JsonIgnoreProperties(ignoreUnknown=true)
data class Test(val name: String) {

    fun getTest(): String {
        return "Hello ${name}"
    }
}