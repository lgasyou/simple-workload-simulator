package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task
import org.slf4j.LoggerFactory

class NodeManager(
    val nodes: List<Node>
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun bind(task: Task, selectedNode: Node) {
        log.info("binding task ${task.name} to node ${selectedNode.name}")
        log.info("task requests ${task.requests}, limits ${task.limits}, load ${task.initLoad}")
        val node = this.nodes.find { it.name == selectedNode.name } ?: error("node ${selectedNode.name} not found")
        log.info("task bind, node available resource ${node.availableResource} -> ${node.availableResource - task.requests}")

        if (node.availableResource < 0) {
            error("node ${node.name} available resource < 0!!!")
        }
        node.tasks.add(task)
    }

}
