package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task
import org.slf4j.LoggerFactory

class RoundRobinScheduler: Scheduler {

    private val log = LoggerFactory.getLogger(javaClass)
    private var idx = 0

    override fun score(task: Task, nodes: List<Node>): Node {
        return nodes[idx++ % nodes.size]
    }

}
