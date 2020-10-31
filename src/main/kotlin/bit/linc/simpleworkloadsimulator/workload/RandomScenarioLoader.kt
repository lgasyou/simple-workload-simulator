package bit.linc.simpleworkloadsimulator.workload

import java.util.concurrent.ThreadLocalRandom

class RandomScenarioLoader: ScenarioLoader {

    private val random = ThreadLocalRandom.current()

    override fun loadWorkload(): List<Task> {
        val tasks = mutableListOf<Task>()
        for (i in 1..100) {
            tasks.add(Task(
                name = "T${i}",
                startTimeSec = random.nextInt(0, 100).toFloat(),
                requests = random.nextInt(1, 4),
                limits = random.nextInt(4, 8),
                initLoad = random.nextInt(4, 100)
            ))
        }
        return tasks
    }

    override fun loadNodes(): List<Node> {
        return listOf(
            Node("N1", 8),
            Node("N2", 4),
        )
    }

}
