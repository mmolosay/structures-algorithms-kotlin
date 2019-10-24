import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import structure.LinkedList

/**
 * Created by ordogod on 23.10.2019.
 **/

class LinkedListTests {

    private val ll = LinkedList<Int>()

    init {
        ll.add(0)
        ll.add(1)
        ll.add(2)
        ll.add(3)
        ll.add(4)
        ll.add(5)
    }

    @Test
    fun testAddInEnd() {
        val initSize = ll.size

        assertTrue(ll.add(6))
        assertEquals(ll.size, initSize + 1)
        assertEquals(ll[initSize], 6)
    }

    @Test
    fun testRemoveByElement() {
        val initSize = ll.size

        assertTrue(ll.remove(0))
        assertEquals(ll.size, initSize - 1)
        assertEquals(ll[0], 1)
    }

    @Test
    fun testAddInPos() {
        val initSize = ll.size

        assertTrue(runCatching { ll[100] = 0 }.isFailure)
        assertEquals(ll[0], ll.set(0, 1))
        assertEquals(ll[0], 1)
        assertEquals(ll.size, initSize)
    }

    @Test
    fun testRemoveByPos() {
        val initSize = ll.size

        assertTrue(runCatching { ll.removeAt(100) }.isFailure)
        assertEquals(ll[1], ll.removeAt(1))
        assertEquals(ll.size, initSize - 1)
    }
}