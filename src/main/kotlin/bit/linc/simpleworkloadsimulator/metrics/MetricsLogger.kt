package bit.linc.simpleworkloadsimulator.metrics

import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task
import org.slf4j.LoggerFactory
import java.io.File
import java.lang.StringBuilder
import java.text.DateFormat
import java.util.*

class MetricsLogger {

    private var lines = StringBuilder()

    var metricsList = mutableListOf<Metrics>()
    lateinit var currentMetrics: Metrics

    init {
        init()
    }

    private fun init() {
        currentMetrics = Metrics()
    }

    fun jct(jct: Float) {
        currentMetrics.jct = jct
    }

    fun schedulerName(name: String) {
        currentMetrics.schedulerName = name
    }

    fun tasks(tasks: List<Task>) {
        currentMetrics.tasks = tasks
    }

    fun nodes(nodes: List<Node>) {
        currentMetrics.nodes = nodes
    }

    fun taskCompleteTime(completeTime: Float) {
        currentMetrics.taskCompleteTimes.add(completeTime)
    }

    fun completeOnce() {
        metricsList.add(currentMetrics)
        init()
    }

    fun complete() {
//        lines.append("Scheduler Name,Job Complete Time,Average Task CompleteTime, Tasks, Nodes\n")
        for (metrics in metricsList) {
            val tct = String.format("%.2f", metrics.taskCompleteTimes.average())
            lines.append("${metrics.schedulerName},")
            lines.append("${String.format("%.2f", metrics.jct)}s,")
            lines.append("${tct}s,")
            for (t in metrics.tasks!!) {
                lines.append("${t.requests}-${t.limits}-${t.initLoad},")
            }
            for (n in metrics.nodes!!) {
                lines.append("${n.resource},")
            }
            lines.append("\n")
        }
        ConcurrentLogger.log(lines.toString())
    }

}
