package org.example

import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime

data class Person(
    @CsvHeader(name = "First Name")
    val firstName: String,

    @CsvHeader(name = "Last Name")
    val lastName: String,

    @CsvHeader(name = "Age", numberFormat = "#")
    val age: Int,

    @CsvHeader(name = "Salary", numberFormat = "#,##0.00")
    val salary: BigDecimal,

    @CsvHeader(name = "Birth Date", dateTimeFormat = "yyyy-MM-dd HH:mm:ss")
    val birthDate: LocalDateTime
)
fun main() {
    val people = listOf(
        Person("John", "Doe", 30, BigDecimal("50000.50"), LocalDateTime.of(1993, 6, 15, 10, 15)),
        Person("Jane", "Smith", 25, BigDecimal("45000.75"), LocalDateTime.of(1998, 4, 12, 8, 45))
    )

    val file = File("people.csv")
    writeCsv(file, people)
}