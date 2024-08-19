import org.example.FixedLengthField
import org.example.writeFixedLengthString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

class TestWriteFixLenghText {

    private val charset = Charset.forName("UTF-8")

    @Test
    fun testSimpleCase() {
        val fields = listOf(
            FixedLengthField("John Doe", start = 0, end = 9),
            FixedLengthField("25", start = 10, end = 12, padChar = '0', alignLeft = false),
            FixedLengthField("USA", start = 13, end = 15)
        )
        val result = writeFixedLengthString(fields, totalLength = 16, charset = charset)
        assertEquals("John Doe  025USA", result)
    }

    @Test
    fun testLeftAligned() {
        val fields = listOf(
            FixedLengthField("John", start = 0, end = 9, padChar = '-', alignLeft = true),
            FixedLengthField("25", start = 10, end = 12, padChar = '0', alignLeft = false),
            FixedLengthField("USA", start = 13, end = 20)
        )
        val result = writeFixedLengthString(fields, totalLength = 21, charset = charset)
        assertEquals("John------025USA     ", result)
    }

    @Test
    fun testRightAligned() {
        val fields = listOf(
            FixedLengthField("John", start = 0, end = 9, padChar = '-', alignLeft = false),
            FixedLengthField("25", start = 10, end = 12, padChar = '0', alignLeft = false),
            FixedLengthField("USA", start = 13, end = 20, padChar = '.')
        )
        val result = writeFixedLengthString(fields, totalLength = 21, charset = charset)
        assertEquals("------John025USA.....", result)
    }

    @Test
    fun testUtf8Characters() {
        val fields = listOf(
            FixedLengthField("こん", start = 0, end = 9, padChar = ' ', alignLeft = true),
            FixedLengthField("世界", start = 10, end = 15, padChar = ' ', alignLeft = true)
        )
        val result = writeFixedLengthString(fields, totalLength = 16, charset = charset)
        assertEquals("こん    世界", result)
    }

    @Test
    fun testEmptyFields() {
        val fields = listOf(
            FixedLengthField("", start = 0, end = 9, padChar = '-', alignLeft = true),
            FixedLengthField("25", start = 10, end = 12, padChar = '0', alignLeft = false),
            FixedLengthField("", start = 13, end = 20, padChar = '.', alignLeft = true)
        )
        val result = writeFixedLengthString(fields, totalLength = 21, charset = charset)
        assertEquals("----------025........", result)
    }

    @Test
    fun testSingleCharacterPadding() {
        val fields = listOf(
            FixedLengthField("A", start = 0, end = 2, padChar = ' ', alignLeft = true),
            FixedLengthField("B", start = 3, end = 5, padChar = '-', alignLeft = true),
            FixedLengthField("C", start = 6, end = 8, padChar = '0', alignLeft = false)
        )
        val result = writeFixedLengthString(fields, totalLength = 9, charset = charset)
        assertEquals("A  B--00C", result)
    }
}