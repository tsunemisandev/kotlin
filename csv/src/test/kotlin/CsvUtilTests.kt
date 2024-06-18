import org.apache.commons.io.file.PathUtils.deleteOnExit
import org.example.CsvHeaderInfo
import org.example.Person
import org.example.writeCsv
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.Test

fun createTempCsvFile(): File {
    return File.createTempFile("test", ".csv").apply {
        deleteOnExit() // Cleanup after tests
    }
}

class CsvUtilTests {
    @Test
    fun `test writeCsv with nullable fields and custom formatting`() {
        val people = listOf(
            Person(
                firstName = "John",
                lastName = "Doe",
                age = 30,
                salary = BigDecimal("50000.50"),
                birthDate = LocalDateTime.of(1993, Month.JUNE, 15, 10, 15),
                hireDate = LocalDate.of(2021, Month.JANUARY, 10)
            ),
            Person(
                firstName = "Jane",
                lastName = null,
                age = null,
                salary = null,
                birthDate = LocalDateTime.of(1998, Month.APRIL, 12, 8, 45),
                hireDate = null
            ),
            Person(
                firstName = null,
                lastName = "Smith",
                age = 25,
                salary = BigDecimal("45000.75"),
                birthDate = null,
                hireDate = LocalDate.of(2022, Month.MARCH, 15)
            )
        )

        val file = createTempCsvFile()
        writeCsv(file, people)

        val expectedContent = """
            First Name,Last Name,Age,Salary,Birth Date,Hire Date
            John,Doe,30,"50,000.50",1993-06-15 10:15:00,2021-01-10
            Jane,,,,1998-04-12 08:45:00,
            "",Smith,25,"45,000.75",,2022-03-15
        """.trimIndent()

        assertEquals(expectedContent, file.readText().trim())
    }

    @Test
    fun `test writeCsv with nullable fields and custom formatting (With Config List)`() {

        val personHeaderInfoList = listOf(
            CsvHeaderInfo("First Name", Person::firstName),
            CsvHeaderInfo("Last Name", Person::lastName),
            CsvHeaderInfo("Age", Person::age, numberFormat = "#"),
            CsvHeaderInfo("Salary", Person::salary, numberFormat = "#,##0.00"),
            CsvHeaderInfo("Birth Date", Person::birthDate, dateTimeFormat = "yyyy-MM-dd HH:mm:ss"),
            CsvHeaderInfo("Hire Date", Person::hireDate, dateFormat = "yyyy-MM-dd") // LocalDate field
        )

        val people = listOf(
            Person(
                firstName = "John",
                lastName = "Doe",
                age = 30,
                salary = BigDecimal("50000.50"),
                birthDate = LocalDateTime.of(1993, Month.JUNE, 15, 10, 15),
                hireDate = LocalDate.of(2021, Month.JANUARY, 10)
            ),
            Person(
                firstName = "Jane",
                lastName = null,
                age = null,
                salary = null,
                birthDate = LocalDateTime.of(1998, Month.APRIL, 12, 8, 45),
                hireDate = null
            ),
            Person(
                firstName = null,
                lastName = "Smith",
                age = 25,
                salary = BigDecimal("45000.75"),
                birthDate = null,
                hireDate = LocalDate.of(2022, Month.MARCH, 15)
            )
        )

        val file = createTempCsvFile()
        writeCsv(file, people, personHeaderInfoList)

        val expectedContent = """
            First Name,Last Name,Age,Salary,Birth Date,Hire Date
            John,Doe,30,"50,000.50",1993-06-15 10:15:00,2021-01-10
            Jane,,,,1998-04-12 08:45:00,
            "",Smith,25,"45,000.75",,2022-03-15
        """.trimIndent()

        assertEquals(expectedContent, file.readText().trim())
    }

}