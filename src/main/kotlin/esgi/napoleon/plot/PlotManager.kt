package esgi.napoleon.plot

import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.internal.chartpart.Chart
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class PlotManager {

    val plots = CopyOnWriteArrayList<String>()

    init {
        File("plots").deleteRecursively()
        Files.createDirectory(Paths.get("plots"))
    }

    fun addPlot(chart: Chart<*,*>) {
        val fileName = "${UUID.randomUUID()}.jpg"
        BitmapEncoder.saveJPGWithQuality(chart, "plots/$fileName", 1f)
        plots.add(fileName)
    }

    fun reset() {
        plots.clear()
    }

}
