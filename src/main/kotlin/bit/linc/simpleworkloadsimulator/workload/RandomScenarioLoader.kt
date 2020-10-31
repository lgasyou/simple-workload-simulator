package bit.linc.simpleworkloadsimulator.workload

import java.util.concurrent.ThreadLocalRandom

class RandomScenarioLoader: ScenarioLoader {

    private val random = ThreadLocalRandom.current()

    override fun loadWorkload(): List<Task> {
        return listOf(
            Task("T1", 2, random.nextInt(4, 10), 4),
            Task("T2", 3, random.nextInt(4, 10), 6),
            Task("T3", 4, random.nextInt(4, 10), 8),
            Task("T4", 5, random.nextInt(5, 10), 10),
            Task("T5", 2, random.nextInt(5, 10), 20),
            Task("T6", 2, random.nextInt(5, 10), 30),
        )
    }

    override fun loadNodes(): List<Node> {
        return listOf(
            Node("N1", 20),
            Node("N2", 20),
            Node("N3", 10),
            Node("N4", 10),
        )
    }

}
