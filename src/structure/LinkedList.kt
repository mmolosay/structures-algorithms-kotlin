package structure

import java.lang.IndexOutOfBoundsException

/**
 * Created by ordogod on 18.10.2019.
 **/

class LinkedList<T> {
    var size = 0; private set

    var first: Item<T>? = null
    var last: Item<T>? = null

    data class Item<T>(
        var element: T?,
        var next:    Item<T>?,
        var prev:    Item<T>?
    ) {
        override fun toString(): String = element.toString()
    }

    operator fun get(position: Int): T {
        if (isIndex(position))
            return elementAt(position).element!!
        else
            throw IndexOutOfBoundsException(outOfBoundsMsg(position))
    }

    operator fun set(position: Int, element: T): T {
        if (isIndex(position)) {
            val item = elementAt(position)
            val oldElem = item.element
            item.element = element
            return oldElem!!
        }
        throw IndexOutOfBoundsException(outOfBoundsMsg(position))
    }

    override fun toString(): String {
        if (size == 0) return "[]"
        val s = StringBuilder().append('[')
        var curr = first!!
        var i = 0
        while (true) {
            s.append(curr.element.toString())
            if (i == size - 1)
                return s.append(']').toString()
            curr = curr.next!!
            s.append(", ")
            i++
        }
    }

    fun add(element: T): Boolean {
        linkLast(element)
        return true
    }

    fun remove(element: T): Boolean {
        var curr = first
        while (curr != null) {
            if (element == curr.element) {
                unlink(curr)
                return true
            }
            curr = curr.next
        }
        return false
    }

    fun removeAt(position: Int): T {
        if (isIndex(position))
            return unlink(elementAt(position))
        throw IndexOutOfBoundsException(outOfBoundsMsg(position))
    }

    private fun elementAt(index: Int): Item<T> {
        var curr: Item<T>
        if (index < size / 2) {
            curr = first!!
            for (i in 0 until index)
                curr = curr.next!!
        }
        else {
            curr = last!!
            for (i in size - 1 downTo index + 1)
                curr = curr.prev!!
        }
        return curr
    }

    private fun isIndex(position: Int) =
        position in 0 until size

    private fun linkLast(element: T) {
        val item = Item(element, null, last)
        val l = last
        last = item

        if (l == null)
            first = item
        else
            l.next = item

        size++
    }

    private fun unlink(item: Item<T>): T {
        val element = item.element!!
        val prev = item.prev
        val next = item.next

        if (prev == null)
            first = next
        else {
            prev.next = next
            item.prev = null
        }

        if (next == null)
            last = prev
        else {
            next.prev = prev
            item.next = null
        }

        item.element = null
        size--
        return element
    }

    private fun outOfBoundsMsg(position: Int) =
        "Size: $size, but index: $position"
}