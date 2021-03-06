package bit.linc.simpleworkloadsimulator.workload

/*
 * 增多节点，并增加任务的数量
 */
class ManualScenarioLoader2: ScenarioLoader {

    override fun loadWorkload(): List<Task> {
        return listOf(
            Task("T1", 0f, 2, 4, 4),
            Task("T2", 0f, 3, 4, 12),
            Task("T3", 0f, 4, 6, 24),
            Task("T4", 0f, 2, 4, 4),
            Task("T5", 0f, 3, 4, 12),
            Task("T6", 0f, 4, 6, 24),
            Task("T7", 0f, 2, 4, 4),
            Task("T8", 0f, 3, 4, 12),
            Task("T9", 0f, 4, 6, 24),
            Task("T10", 0f, 2, 4, 4),
            Task("T11", 0f, 3, 4, 12),
            Task("T12", 0f, 4, 6, 24),
        )
    }

    override fun loadNodes(): List<Node> {
        return listOf(
            Node("N1", 8),
            Node("N2", 4),
            Node("N3", 8),
            Node("N4", 4),
            Node("N5", 8),
            Node("N6", 4),
            Node("N7", 8),
            Node("N8", 4),
        )
    }

}
