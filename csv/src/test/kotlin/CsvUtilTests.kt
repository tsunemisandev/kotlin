import org.apache.commons.io.file.PathUtils.deleteOnExit
import org.example.Person
import org.example.writeCsv
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.math.BigDecimal
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
            Person("John", "Doe", 30, BigDecimal("50000.50"), LocalDateTime.of(1993, Month.JUNE, 15, 10, 15)),
            Person("Jane", null, null, null, LocalDateTime.of(1998, Month.APRIL, 12, 8, 45)),
            Person(null, "Smith", 25, BigDecimal("45000.75"), null)
        )

        val file = createTempCsvFile()
        writeCsv(file, people)

        val expectedContent = """
            First Name,Last Name,Age,Salary,Birth Date
            John,Doe,30,"50,000.50",1993-06-15 10:15:00
            Jane,,,,1998-04-12 08:45:00
            "",Smith,25,"45,000.75",
        """.trimIndent()

        assertEquals(expectedContent, file.readText().trim())
    }

}