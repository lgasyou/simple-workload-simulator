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
        val copiedWorkload = workload.map { it.copy() }

        val nodeSimulator = NodeManager(copiedNodes)
        for (task in copiedWorkload) {
            val selectedNode = scheduler.schedule(task, nodeSimulator.nodes)
            log.info("selected node ${selectedNode.name}")
            nodeSimulator.bind(task, selectedNode)
        }
        startRunning(nodeSimulator)
    }

    private fun startRunning(nodeManager: NodeManager) {
        log.info("start running")
        for (node in nodeManager.nodes) {
            log.info("node ${node.name} has ${node.tasks.size} tasks ${node.tasks}")
        }
        runner.run(nodeManager)
        metricsLogger.completeOnce()
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
