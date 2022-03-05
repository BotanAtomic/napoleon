package io.deepn.flow.error

import io.deepn.flow.Visitor

class StackTrace {

    val traces: ArrayDeque<Visitor> = ArrayDeque()

    fun stack(visitor: Visitor) = traces.addFirst(visitor)

    fun pop() = traces.removeFirst()

}
