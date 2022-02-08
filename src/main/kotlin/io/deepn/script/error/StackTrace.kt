package io.deepn.script.error

import io.deepn.script.Visitor

class StackTrace {

    val traces: ArrayDeque<Visitor> = ArrayDeque()

    fun stack(visitor: Visitor) = traces.addFirst(visitor)

    fun pop() = traces.removeFirst()

}
