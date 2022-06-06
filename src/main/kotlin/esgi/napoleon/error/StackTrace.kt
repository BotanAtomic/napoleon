package esgi.napoleon.error

import esgi.napoleon.Visitor

class StackTrace {

    val traces: ArrayDeque<Visitor> = ArrayDeque()

    fun stack(visitor: Visitor) = traces.addFirst(visitor)

    fun pop() = traces.removeFirst()

    fun reset() = traces.clear()

}
