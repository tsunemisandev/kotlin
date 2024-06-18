package org.example

import java.io.File
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

fun main() {
    val people = listOf(
        Person("John", "Doe", 30, BigDecimal("50000.50"), LocalDateTime.of(1993, Month.JUNE, 15, 10, 15), LocalDate.of(2021, Month.JANUARY, 10)),
        Person("Jane", null, null, null, LocalDateTime.of(1998, Month.APRIL, 12, 8, 45), null),
        Person(null, "Smith", 25, BigDecimal("45000.75"), null, LocalDate.of(2022, Month.MARCH, 15))
    )

    val file = File("people.csv")
//    writeCsv(file, people)

    val personHeaderInfoList = listOf(
        CsvHeaderInfo("First Name", Person::firstName),
        CsvHeaderInfo("Last Name", Person::lastName),
        CsvHeaderInfo("Age", Person::age, numberFormat = "#"),
        CsvHeaderInfo("Salary", Person::salary, numberFormat = "#,##0.00"),
        CsvHeaderInfo("Birth Date", Person::birthDate, dateTimeFormat = "yyyy-MM-dd HH:mm:ss"),
        CsvHeaderInfo("Hire Date", Person::hireDate, dateFormat = "yyyy-MM-dd")
    )

    writeCsv(file, people, personHeaderInfoList)
}