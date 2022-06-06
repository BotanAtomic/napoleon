import esgi.napoleon.error.ValueError
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.full.declaredFunctions

object A {
    fun myFunc() {
        println("OK")

        throw ValueError("lol")
    }
}


fun main() {

    val error = runCatching {
        A::class.declaredFunctions.find {
            it.name == "myFunc"
        }?.call(A::class.objectInstance)
    }.exceptionOrNull()!! as InvocationTargetException

    println(error.targetException)

}
