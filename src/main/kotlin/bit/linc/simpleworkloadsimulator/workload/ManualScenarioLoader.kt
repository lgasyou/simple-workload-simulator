package bit.linc.simpleworkloadsimulator.workload

/*
 * 这个场景下的调度效果，RoundRobin > MostRequested > LeastRequested
 */
class ManualScenarioLoader: ScenarioLoader {

    override fun loadWorkload(): List<Task> {
        return listOf(
            Task("T1", 0f, 2, 4, 4),
            Task("T2", 0f, 3, 4, 12),
            Task("T3", 0f, 4, 6, 24),
            Task("T4", 0f, 2, 4, 4),
            Task("T5", 0f, 3, 4, 12),
            Task("T6", 0f, 4, 6, 24),
            Task("T7", 20f, 2, 4, 4),
            Task("T8", 20f, 3, 4, 12),
            Task("T9", 20f, 4, 6, 24),
        )
    }

    override fun loadNodes(): List<Node> {
        return listOf(
            Node("N1", 8),
            Node("N2", 4)
        )
    }

}
