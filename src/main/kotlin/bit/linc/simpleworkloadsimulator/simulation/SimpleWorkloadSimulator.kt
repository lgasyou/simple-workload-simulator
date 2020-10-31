package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.metrics.MetricsLogger
import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task
import org.slf4j.LoggerFactory

class SimpleWorkloadSimulator: WorkloadSimulator {

    private val log = LoggerFactory.getLogger(javaClass)
    private val schedulers = mutableListOf<Scheduler>()
    private val metricsLogger = MetricsLogger()
    private val runner = Runner(metricsLogger)

    fun registerScheduler(scheduler: Scheduler) {
        schedulers.add(scheduler)
        log.info("scheduler ${scheduler.name} registered")
    }

    private fun simulateOnce(workload: List<Task>, nodes: List<Node>, scheduler: Scheduler) {
        val copiedNodes = nodes.map { it.copy() }
        val sortedWorkload = workload.map { it.copy() }.sortedBy { it.startTimeSec }

        val nodeManager = NodeManager(copiedNodes)
        runner.currentTimeSec = 0f
        for (task in sortedWorkload) {
            if (task.startTimeSec > runner.currentTimeSec) {
                val nextStartTimeSec = task.startTimeSec
                log.info("start running from ${runner.currentTimeSec}")
                for (node in nodeManager.nodes) {
                    log.info("node ${node.name} now has ${node.tasks.size} tasks ${node.tasks}")
                }
                runner.runUntil(nodeManager, nextStartTimeSec)
            }

            if (!tryScheduleTask(scheduler, nodeManager, task)) {
                nodeManager.addPendingTask(task)
            }
        }

        runner.runUntilFinish(nodeManager)
        val jct = runner.currentTimeSec
        metricsLogger.jct(jct)
        metricsLogger.completeOnce()
        log.info("pending tasks ${nodeManager.pendingTasks}")
    }

    private fun tryScheduleTask(scheduler: Scheduler, nodeManager: NodeManager, task: Task): Boolean {
        val selectedNode = scheduler.schedule(task, nodeManager.nodes)
        if (selectedNode != null) {
            log.info("selected node ${selectedNode.name}")
            nodeManager.bind(task, selectedNode)
            return true
        }
        log.info("no node available for ${task.name} now")
        return false
    }

    override fun simulate(workload: List<Task>, nodes: List<Node>) {
        for (scheduler in schedulers) {
            log.info("scheduling using scheduler ${scheduler.name}")
            metricsLogger.schedulerName(scheduler.name)
            metricsLogger.tasks(workload)
            metricsLogger.nodes(nodes)
            simulateOnce(workload, nodes, scheduler)
            log.info("scheduling round finished")
        }
        log.info("simulation finished")
        metricsLogger.complete()
    }

}
