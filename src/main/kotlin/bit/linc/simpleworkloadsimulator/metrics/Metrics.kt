package bit.linc.simpleworkloadsimulator.metrics

import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task

data class Metrics(
    var schedulerName: String? = null,
    var jct: Float? = null,
    var tasks: List<Task>? = null,
    var nodes: List<Node>? = null,
) {
    var taskCompleteTimes = mutableListOf<Float>()
}
