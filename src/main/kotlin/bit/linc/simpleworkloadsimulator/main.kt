package bit.linc.simpleworkloadsimulator

import bit.linc.simpleworkloadsimulator.metrics.ConcurrentLogger
import bit.linc.simpleworkloadsimulator.simulation.*
import bit.linc.simpleworkloadsimulator.workload.ManualScenarioLoader
import bit.linc.simpleworkloadsimulator.workload.ManualScenarioLoader2
import bit.linc.simpleworkloadsimulator.workload.RandomScenarioLoader
import bit.linc.simpleworkloadsimulator.workload.ScenarioLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

const val TRY_TIMES = 1
const val N_THREADS = 4

val log: Logger = LoggerFactory.getLogger("main")

fun initSimulator(): WorkloadSimulator {
    val simulator = SimpleWorkloadSimulator()
    simulator.registerScheduler(LeastRequestedScheduler())
    simulator.registerScheduler(MostRequestedScheduler())
    simulator.registerScheduler(RoundRobinScheduler())
    simulator.registerScheduler(RandomScheduler())
    return simulator
}

fun simulateOnce() {
    val loader: ScenarioLoader = ManualScenarioLoader2()
    val workload = loader.loadWorkload()
    val nodes = loader.loadNodes()
    log.info("using workload $workload")
    log.info("using nodes $nodes")
    val simulator: WorkloadSimulator = initSimulator()
    simulator.simulate(workload, nodes)
}

fun main() {
    val threadPool = Executors.newFixedThreadPool(N_THREADS)
    ConcurrentLogger.nThreads = TRY_TIMES
    for (iter in 1..TRY_TIMES) {
        threadPool.submit {
            try {
                simulateOnce()
            } catch (e: Exception) {
                log.error(e.stackTraceToString())
            }
        }
    }
    threadPool.shutdown()
}
