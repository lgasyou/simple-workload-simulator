package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task

interface Scheduler {

    val name: String
        get() = javaClass.simpleName

    fun schedule(task: Task, nodes: List<Node>): Node {
        val availableNodes = filter(task, nodes)
        return score(task, availableNodes)
    }

    fun filter(task: Task, nodes: List<Node>): List<Node> {
        return nodes.filter { it.availableResource > 0 && it.availableResource >= task.requests }
    }

    fun score(task: Task, nodes: List<Node>): Node

}
