package bit.linc.simpleworkloadsimulator.workload

interface ScenarioLoader {

    fun loadWorkload(): List<Task>

    fun loadNodes(): List<Node>

}
