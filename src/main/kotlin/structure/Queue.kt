package structure

import java.lang.IndexOutOfBoundsException

/**
 * Created by ordogod on 24.10.2019.
 **/
class Queue<E> {

    private data class Item<E>(
        var value: E,
        var next: Item<E>?
    )

    private var first: Item<E>? = null
    private var last: Item<E>? = null

    var size = 0

    operator fun get(position: Int): E {
        if (isIndex(position))
            return itemAt(position).value
        throw IndexOutOfBoundsException()
    }

    fun enqueue(value: E) {
        val item = Item(value, null)
        val l = last
        last = item

        if (l == null)
            first = item
        else
            l.next = item

        size++
    }

    fun dequeue(): E {
        val value = first?.value ?: throw NoSuchElementException("Queue is empty")
        if (first!!.next != null)
            first = first!!.next
        else
            first = null

        size--
        return value
    }

    private fun itemAt(index: Int): Item<E> {
        var item = first!!
        for (i in 0 until index)
            item = item.next!!
        return item
    }

    private fun isIndex(position: Int) =
        position in 0 until size
}