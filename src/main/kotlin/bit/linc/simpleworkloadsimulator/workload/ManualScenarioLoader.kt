package bit.linc.simpleworkloadsimulator.workload

/*
 * 这个场景下的调度效果，RoundRobin > MostRequested > LeastRequested
 */
class ManualScenarioLoader: ScenarioLoader {

    override fun loadWorkload(): List<Task> {
        return listOf(
            Task("T1", 2, 4, 4),
            Task("T2", 3, 4, 12),
            Task("T3", 4, 6, 24),
        )
    }

    override fun loadNodes(): List<Node> {
        return listOf(
            Node("N1", 8),
            Node("N2", 4)
        )
    }

}
