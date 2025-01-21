package com.example.dbresponsetime.service

import org.springframework.stereotype.Service
import javax.sql.DataSource
import kotlin.system.measureTimeMillis

@Service
class ConnectionTimingService(private val dataSource: DataSource) {

    fun measureConnectionTime(connections: Int): String {
        val query = "SELECT 1;"
        val times = mutableListOf<Long>()

        repeat(connections) {
            val timeTaken = measureTimeMillis {
                dataSource.connection.use { connection ->
                    connection.createStatement().use { statement ->
                        statement.executeQuery(query).use { rs ->
                            while (rs.next()) {
                                rs.getInt(1) // Retrieve the result of the query
                            }
                        }
                    }
                }
            }
            times.add(timeTaken)
        }

        val avgTime = times.average()
        return "Measured $connections connections. Average connection time: $avgTime ms."
    }
}
