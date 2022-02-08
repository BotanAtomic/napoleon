import org.junit.jupiter.api.Test
import java.util.*

class ListTest {

    @Test
    fun `list creation`() {
        scriptAssert("[1, 2, 3, 4, 5 + 7]", LinkedList(listOf(1, 2, 3, 4, 12)))
    }

    @Test
    fun `list index access`() {
        scriptAssert("([1, 2, 3, [[[[4**4]]]], 5 + 7])[3][0][0][0][0]", 256)
    }


}
