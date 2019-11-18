package structure

/**
 * Created by ordogod on 16.11.2019.
 **/

class Set<E>(val array: ArrayList<E>) {

    val size: Int
        get() = array.size

    init {
        for (i in 0 until array.lastIndex) // excluding last element
            for (j in i+1 until array.size)
                if (array[i] == array[j])
                    array.removeAt(j)
    }

    fun add(element: E): Boolean {
        if (contains(element)) return false
        array.add(element)
        return true
    }

    fun remove(element: E): Boolean {
        if (contains(element)) {
            array.remove(element)
            return true
        }
        return false
    }

    fun contains(element: E): Boolean {
        for (item in array)
            if (item == element) return true
        return false
    }
}