package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.metrics.MetricsLogger
import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task
import org.slf4j.LoggerFactory

class Runner(
    private val metricsLogger: MetricsLogger
) {

    private val log = LoggerFactory.getLogger(javaClass)

    var currentTimeSec: Float = 0f

    fun runUntil(nodeManager: NodeManager, endSec: Float) {
        var realEndSec = 0f
        for (node in nodeManager.nodes) {
            val nodeEndSec = runNodeUntil(node, currentTimeSec, endSec)
            if (realEndSec < nodeEndSec) {
                realEndSec = nodeEndSec
            }
        }
        currentTimeSec = realEndSec
    }

    fun runUntilFinish(nodeManager: NodeManager) {
        runUntil(nodeManager, Float.MAX_VALUE)
    }

    private tailrec fun runNodeUntil(node: Node, currentTimeSec: Float, endSec: Float): Float {
        if (node.tasks.isEmpty()) {
            return currentTimeSec
        }

        allocateResourceToTasks(node)
        val task: Task = findFirstEndTask(node.tasks)
        val nextTaskFinishTime = currentTimeSec + task.remainTime
        if (nextTaskFinishTime <= endSec) {
            moveForwardUntilTaskDone(task, node)
            log.info("task ${task.name} finished at ${nextTaskFinishTime}s")
            return runNodeUntil(node, nextTaskFinishTime, endSec)
        }

        val timeToRunSec = endSec - currentTimeSec
        runNodeSeconds(node, timeToRunSec)
        return endSec
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

    private fun runNodeSeconds(node: Node, timeSec: Float) {
        for (task in node.tasks) {
            task.remainLoad -= timeSec * task.allocatedResource
        }
    }

    // moves timer and removes finished task
    private fun moveForwardUntilTaskDone(toFinishTask: Task, node: Node) {
        val remainTime = toFinishTask.remainTime
        node.tasks.remove(toFinishTask)
        for (task in node.tasks) {
            task.remainLoad -= remainTime * task.allocatedResource
            log.info("task ${task.name} remains load ${task.remainLoad}")
        }
    }

}
