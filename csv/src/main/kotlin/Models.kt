package org.example

import java.math.BigDecimal
import java.time.LocalDateTime

data class Person(
    @CsvHeader(name = "First Name", order = 1)
    val firstName: String?,

    @CsvHeader(name = "Last Name", order = 2)
    val lastName: String?,

    @CsvHeader(name = "Age", order = 3, numberFormat = "#")
    val age: Int?,

    @CsvHeader(name = "Salary", order = 4, numberFormat = "#,##0.00")
    val salary: BigDecimal?,

    @CsvHeader(name = "Birth Date", order = 5, dateTimeFormat = "yyyy-MM-dd HH:mm:ss")
    val birthDate: LocalDateTime?
)