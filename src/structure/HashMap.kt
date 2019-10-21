package structure

/**
 * Created by ordogod on 19.10.2019.
 **/

class HashMap<K, V>(targetCapacity: Int = 16,
                    private val loadFactor: Float = 0.75f) {

    private data class Item<K, V>(
        val key: K,
        var value: V?,
        val hash: Int,
        var next: Item<K, V>?
    )

    private val MAX_CAPACITY = Int.MAX_VALUE / 2 + 1

    private var capacity = 0
    private var threshold = 0
    private var table: Array<Item<K, V>?>

    var size = 0

    init {
        require(targetCapacity >= 1) {
            "Initial capacity must be positive, but was: $targetCapacity"
        }

        this.capacity = tableSizeFor(targetCapacity)
        this.threshold = updateThreshold()
        this.table = arrayOfNulls(capacity)
    }

    operator fun get(key: K): V? {
        val hash = key.hashCode()
        val index = indexFor(hash)
        return sameKeyItemInBucket(key, hash, index)?.value
    }

    operator fun set(key: K, value: V?): V? {
        val hash = hash(key.hashCode())
        val index = indexFor(hash)

        with(sameKeyItemInBucket(key, hash, index)) {
            if (this == null)
                addItem(key, value, hash, index)
            else
                return updateItemValue(this, value)
        }

        return null
    }

    fun has(key: K): Boolean {
        val hash = hash(key.hashCode())
        val index = indexFor(hash)
        var bucketItem = table[index] ?: return false

        while (true) {
            if (hash == bucketItem.hash && key == bucketItem.key)
                return true
            if (bucketItem.next != null)
                bucketItem = bucketItem.next!!
            else
                return false
        }
    }

    infix fun remove(key: K): V? {
        if (! has(key)) return null

        val hash = hash(key.hashCode())
        val index = indexFor(hash)

        var bucketPrev: Item<K, V>? = null
        var bucketItem = table[index] ?: return null

        while (true) {
            if (hash == bucketItem.hash && key == bucketItem.key) {
                if (bucketPrev == null)
                    table[index] = bucketItem.next
                else
                    bucketPrev.next = bucketItem.next

                return bucketItem.value
            }
            if (bucketItem.next != null) {
                bucketPrev = bucketItem
                bucketItem = bucketItem.next!!
            }
            else
                return null
        }
    }

    private fun tableSizeFor(targetSize: Int): Int {
        var size = 1
        while (size < targetSize)
            size = size shl 1
        return size
    }

    private fun updateThreshold() =
        (capacity * loadFactor).toInt()

    private fun hash(hashCode: Int): Int {
        val h = hashCode xor (hashCode.ushr(20) xor hashCode.ushr(12))
        return h xor h.ushr(7) xor h.ushr(4)
    }

    private fun indexFor(hash: Int) =
        hash and (capacity - 1)

    private fun sameKeyItemInBucket(key: K, keyHash: Int, bucketIndex: Int): Item<K, V>? {
        var bucketItem = table[bucketIndex] ?: return null

        while (true) {
            if (keyHash == bucketItem.hash && key == bucketItem.key)
                return bucketItem
            else
                bucketItem = bucketItem.next ?: return null
        }
    }

    private fun addItem(key: K, value: V?, hash: Int, index: Int) {
        val bucketFirst = table[index]
        table[index] = Item(key, value, hash, bucketFirst)

        if (++size > threshold)
            resize()
    }

    private fun updateItemValue(ofItem: Item<K, V>, newValue: V?): V? {
        val oldValue = ofItem.value
        ofItem.value = newValue
        return oldValue
    }

    private fun resize() {
        if (capacity == MAX_CAPACITY)
            throw StackOverflowError()
        capacity *= 2
        migrate()
    }

    private fun migrate() {
        val newTable = arrayOfNulls<Item<K, V>?>(capacity)
        for (i in 0..table.lastIndex)
            migrateBucket(i, newTable)
        table = newTable
    }

    private fun migrateBucket(index: Int, newTable: Array<Item<K, V>?>) {
        var bucketItem = table[index] ?: return
        while (true) {
            migrateItem(bucketItem, newTable)
            if (bucketItem.next != null)
                bucketItem = bucketItem.next!!
            else
                return
        }
    }

    private fun migrateItem(item: Item<K, V>, newTable: Array<Item<K, V>?>) {
        val newIndex = indexFor(item.hash)
        val bucketFirst = newTable[newIndex]

        if (bucketFirst != null)
            item.next = bucketFirst
        newTable[newIndex] = item
    }
}