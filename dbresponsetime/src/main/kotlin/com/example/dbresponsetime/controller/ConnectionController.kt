package com.example.dbresponsetime.controller

import com.example.dbresponsetime.service.ConnectionTimingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/connections")
class ConnectionController(private val connectionTimingService: ConnectionTimingService) {

    @GetMapping("/time")
    fun getConnectionTime(
        @RequestParam(name = "connections", defaultValue = "10") connections: Int
    ): String {
        val timeTaken = connectionTimingService.measureConnectionTime(connections)
        return timeTaken
    }
}
