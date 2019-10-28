package algoritm

/**
 * Created by ordogod on 25.10.2019.
 **/

object Sort {

    fun mergeSort(array: Array<Int>) {
        if (array.size < 2) return

        val mid = array.size / 2
        val l = Array(mid) { array[it] }
        val r = Array(array.size - mid) { array[mid + it] }

        mergeSort(l)
        mergeSort(r)

        merge(array, l, r)
    }

    fun quickSort(array: Array<Int>, start: Int = 0, end: Int = array.lastIndex) {
        if (array.size < 2) return
        if (start >= end) return

        val pivotEl = array[start + (end - start) / 2]
        var i = start
        var j = end

        while (i <= j) {
            while (array[i] < pivotEl) i++
            while (array[j] > pivotEl) j--

            if (i <= j) {
                swap(array, i, j)
                i++; j--
            }
        }

        if (start < j)
            quickSort(array, start, j)
        if (end > i)
            quickSort(array, i, end)
    }

    private fun merge(into: Array<Int>, l: Array<Int>, r: Array<Int>) {
        var i = 0
        var j = 0
        var k = 0

        while (j < l.size && k < r.size) {
            if (l[j] <= r[k])
                into[i] = l[j++]
            else
                into[i] = r[k++]
            i++
        }

        while (j < l.size)
            into[i++] = l[j++]

        while (k < r.size)
            into[i++] = r[k++]
    }

    private fun swap(array: Array<Int>, i: Int, j: Int) {
        val jElem = array[j]
        array[j] = array[i]
        array[i] = jElem
    }
}