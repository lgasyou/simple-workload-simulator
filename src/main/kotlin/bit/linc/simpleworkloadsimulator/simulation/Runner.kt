package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.metrics.MetricsLogger
import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task
import org.slf4j.LoggerFactory

class Runner(
    private val metricsLogger: MetricsLogger
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private var nodeTime: Float = 0f

    fun run(nodeManager: NodeManager) {
        var jct = 0f
        for (node in nodeManager.nodes) {
            nodeTime = 0f
            while (node.tasks.isNotEmpty()) {
                runNodeUntilOneTaskDone(node)
            }
            if (nodeTime > jct) {
                jct = nodeTime
            }
        }
        log.info("JCT is ${jct}s")
        metricsLogger.jct(jct)
    }

    // node.tasks is always not empty
    private fun runNodeUntilOneTaskDone(node: Node) {
        allocateResourceToTasks(node)
        val task: Task = findFirstEndTask(node.tasks)
        moveAhead(task, node)
        metricsLogger.taskCompleteTime(nodeTime)
        log.info("task ${task.name} finished at ${nodeTime}s")
    }

    private fun allocateResourceToTasks(node: Node) {
        if (node.taskSumLimited > node.resource) {
            val oneShareLimit = node.resource.toFloat() / node.taskSumLimited
            for (task in node.tasks) {
                task.allocatedResource = task.limits * oneShareLimit
            }
        } else {
            for (task in node.tasks) {
                task.allocatedResource = task.limits.toFloat()
            }
        }
    }

    private fun findFirstEndTask(tasks: List<Task>): Task {
        var task: Task = tasks[0]
        var minRemainTime = Float.MAX_VALUE
        for (t in tasks) {
            if (t.remainTime < minRemainTime) {
                task = t
                minRemainTime = t.remainTime
            }
        }
        return task
    }

    // moves timer and removes finished task
    private fun moveAhead(toFinishTask: Task, node: Node) {
        val remainTime = toFinishTask.remainTime
        nodeTime += remainTime
        node.tasks.remove(toFinishTask)
        for (task in node.tasks) {
            task.remainLoad -= remainTime * task.allocatedResource
            log.info("task ${task.name} remains load ${task.remainLoad}")
        }
    }

}
