package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task
import org.slf4j.LoggerFactory

class RandomScheduler: Scheduler {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun score(task: Task, nodes: List<Node>): Node {
        return nodes.random()
    }

}
