package structure

import java.lang.IllegalArgumentException
import kotlin.math.max

/**
 * Created by ordogod on 22.10.2019.
 **/

class Node<V>(var value: V, var parent: Node<V>? = null, var children: ArrayList<Node<V>> = arrayListOf()) {

    val root: Node<V>
        get() {
            var root = this
            while (true)
                root = root.parent ?: return root
        }

    val size: Int
        get() {
            if (this.children.size == 0)
                return 1
            else {
                var subsize = 1
                for (child in this.children)
                    subsize += child.size
                return subsize
            }
        }

    val height: Int
        get() {
            if (this.children.size == 0)
                return 1
            else {
                var h = 0
                for (child in this.children)
                    h = max(h, child.height)
                return h + 1
            }
        }

    fun addChild(child: Node<V>): Node<V> = apply {
        this.children.add(child)
        child.parent = this
    }

    fun addChild(childValue: V): Node<V> = apply {
        this.children.add(Node(childValue, this))
    }

    fun addChildren(vararg children: Node<V>): Node<V> = apply {
        for (child in children)
            this.addChild(child)
    }

    fun addChildren(vararg children: V): Node<V> = apply {
        for (child in children)
            this.addChild(child)
    }

    fun remove(): Node<V> = apply {
        if (this != this.root) {
            this.parent!!.children.remove(this)
        }
        else
            throw IllegalArgumentException("Root node can not be removed")
    }

    fun findFirst(searchValue: V): Node<V>? {
        if (this.value == searchValue)
            return this
        else
            for (child in this.children)
                return child.findFirst(searchValue) ?: continue
        return null
    }

    fun contains(subnode: Node<V>): Boolean {
        if (this.children.contains(subnode))
            return true
        else
            for (child in this.children) {
                if (child.contains(subnode))
                    return true
        }
        return false
    }

    fun traverse(path: Array<Int>): Node<V>? {
        return if (path.isEmpty())
            this
        else
            runCatching {
                this.children[path[0]].traverse(path.sliceArray(1 until path.size))
            }.getOrNull()
    }

    private fun toNodeString() =
        "value=$value, children=$children"

    override fun toString(): String =
        "[${this.toNodeString()}]"
}