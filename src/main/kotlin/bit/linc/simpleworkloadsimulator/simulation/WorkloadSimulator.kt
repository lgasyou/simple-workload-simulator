package bit.linc.simpleworkloadsimulator.simulation

import bit.linc.simpleworkloadsimulator.workload.Node
import bit.linc.simpleworkloadsimulator.workload.Task

interface WorkloadSimulator {

    fun simulate(workload: List<Task>, nodes: List<Node>)

}
