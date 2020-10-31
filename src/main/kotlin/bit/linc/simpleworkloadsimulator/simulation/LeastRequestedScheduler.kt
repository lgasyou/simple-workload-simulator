package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task
import org.slf4j.LoggerFactory

class LeastRequestedScheduler: Scheduler {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun score(task: Task, nodes: List<Node>): Node {
        val scores = nodes.map { 1f - task.requests.toFloat() / it.availableResource.toFloat() }
        log.info("scores $scores")
        val maxScore = scores.maxOrNull() ?: error("no node available")
        val idx = scores.indexOfFirst { it == maxScore }
        return nodes[idx]
    }

}
