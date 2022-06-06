package esgi.napoleon

import org.http4k.core.*
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.CorsPolicy
import org.http4k.filter.ServerFilters
import org.http4k.format.Gson.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.ConcurrentHashMap


data class Session(val uuid: String)


data class ExecutionRequest(val session: String, val code: String)

fun main() {

    val environments: MutableMap<String, ExecutionEnvironment> = ConcurrentHashMap()

    fun registerSession(uuid: String) {
        environments[uuid] = DefaultExecutionEnvironment()
    }

    val app = routes(
        "/session" bind Method.GET to {
            val session = Session(UUID.randomUUID().toString())
            registerSession(session.uuid)
            Response(OK).with(Body.auto<Session>().toLens() of session)
        },
        "/execute" bind Method.POST to {
            val body = Body.auto<ExecutionRequest>().toLens().invoke(it)

            if (environments.containsKey(body.session).not())
                registerSession(body.session)

            val env = environments[body.session]

            if (env == null) Response(BAD_REQUEST).body("No session found")
            else Response(OK).with(Body.auto<NapoleonExecutionResult>().toLens() of env.execute(body.code))
        },
        "/plot/{filename}" bind Method.GET to {
            try {
                val filename = it.path("filename")
                if (filename == null)
                    Response(BAD_REQUEST)
                else Response(OK).body(FileInputStream(Paths.get("plots", filename).toFile()))
            } catch (e : Exception ) {
                e.printStackTrace()
                Response(BAD_REQUEST)
            }
        }
    )


    val server = ServerFilters.Cors(CorsPolicy.UnsafeGlobalPermissive).then(app).asServer(SunHttp(9000)).start()
}
