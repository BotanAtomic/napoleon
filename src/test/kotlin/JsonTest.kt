import io.deepn.flow.error.SyntaxErrorEnum
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable
import org.junit.jupiter.api.Test

class JsonTest {

    @Test
    fun json() {
        scriptAssertThrowable("""json = { "a": ok""", SyntaxErrorEnum.INPUT_MISMATCH)
        scriptAssert(
            """
            json = { a: "ok" }
            json["a"]
        """.trimIndent(), "ok"
        )
        scriptAssert(
            """
            json = { a: { b: "ok" } }
            json["a"]["c"] = { d: "yolo" }
            json["a"]["c"]["d"]
        """.trimIndent(), "yolo"
        )
        scriptAssert(
            """
            json = { a: { b: "ok" } }
            delete json["a"]["b"]
            json["a"]
        """.trimIndent(), mapOf<Variable<*>, Variable<*>>()
        )
        scriptAssert(
            """
            json = { a: { b: "ok" } }
            json["a"]["b"]
        """.trimIndent(), "ok"
        )
        scriptAssert(
            """
            json = { a: { b: "ok" } }
            json["a"]["c"]
        """.trimIndent(), Null
        )
        scriptAssert(
            """
            json = { a: { b: "ok" } }
            json["a"]["b"] = "btc"
            json["a"]["b"]
        """.trimIndent(), "btc"
        )
        scriptAssert(
            """
            json = { a: { b: "ok" } }
            json["a"]["c"] = "btc"
            json["a"]["c"]
        """.trimIndent(), "btc"
        )
        scriptAssert(
            """
            json = { a: { b: "ok" }, c: { d: "ok" } }
            json["e"] = json["a"]["b"] + json["c"]["d"]
            json["e"]
        """.trimIndent(), "okok"
        )

        scriptAssert(
            """
            json = { a: "ok" }
            json["a"] += "ok"
            json["a"]
        """.trimIndent(), "okok"
        )
        scriptAssert(
            """
            json = { a: 5 }
            json["a"] += 10
            json["a"]
        """.trimIndent(), 15
        )
        scriptAssert(
            """
            json = { a: 5 }
            json["a"] += "ok"
            json["a"]
        """.trimIndent(), "5ok"
        )
        scriptAssert(
            """
            json = { a: 5.0 }
            json["a"] += 10
            json["a"]
        """.trimIndent(), 15.0
        )

        scriptAssert(
            """
            function test()
                return 1 + 1
            end
            
            json = { a: test() }
            json["a"]
        """.trimIndent(), 2
        )
        scriptAssert(
            """
            function test()
                return 1 + 1
            end
            
            json = { a: test }
            json["a"]()
        """.trimIndent(), 2
        )
        scriptAssert(
            """
            function test(x)
                return 1 + 1 + x
            end

            json = { a: function x -> test(x) }
            json["a"](20)
        """.trimIndent(), 22
        )

        scriptAssert(
            """
            string = "{ a: \"10\" }"
            string.isJson()
        """.trimIndent(), true
        )
        scriptAssert(
            """
            json = "{ a: 22"
            json.isJson()
        """.trimIndent(), false
        )
        scriptAssert(
            """
            json = { a: 10, b: 5.0, c: "this is c" }
            json.stringify()
        """.trimIndent(), "{\"a\":10,\"b\":5.0,\"c\":\"this is c\"}"
        )

        val stringJson = "\"\"\"{\"a\":10,\"b\":5.0,\"c\":\"this is c\"}\"\"\""
        scriptAssert(
            """
            string = $stringJson
            string.toJson()["a"]
        """.trimIndent(), 10
        )
        scriptAssert(
            """
            string = "{\"a\":10,\"b\":5.0,\"c\":\"this is c\", \"d\": 5.1}"
            json = string.toJson()
            json.stringify()
        """.trimIndent(), """{"a":10,"b":5,"c":"this is c","d":5.1}"""
        )
        scriptAssert(
            """
            string = "hello"
            string.toJson()
        """.trimIndent(), "hello"
        )
        scriptAssert(
            """
            string = "{ a: 10 }"
            string.toJson()["a"]
        """.trimIndent(), 10
        )
        scriptAssert(
            """
            string = "{ a: \"10\" }"
            string.toJson()["a"]
        """.trimIndent(), "10"
        )
    }

}
