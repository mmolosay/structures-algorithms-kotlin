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
}