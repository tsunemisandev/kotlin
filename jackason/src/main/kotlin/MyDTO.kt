package org.example

import java.time.LocalDate
import java.time.LocalDateTime

data class MyDTO(
    val id: Long = 0,
    val name: String = "",
    val date: LocalDate? = null,
    val timestamp: LocalDateTime? = null
)

data class ListDTO(
    val title:String = "Test",
    val list:List<MyDTO> = mutableListOf()
)