package bit.linc.simpleworkloadsimulator.workload

data class Task(
    val name: String,
    val requests: Int,
    val limits: Int,
    val initLoad: Int
) {
    var allocatedResource: Float = 0f
    var remainLoad: Float = initLoad.toFloat()
    val remainTime: Float get() { return remainLoad / allocatedResource }
}

data class Node(
    val name: String,
    val resource: Int
) {
    val availableResource: Int get() { return resource - taskSumRequested }
    val taskSumLimited: Int get() { return tasks.sumBy { it.limits } }
    val tasks: MutableList<Task> = mutableListOf()
    private val taskSumRequested: Int get() { return tasks.sumBy { it.requests } }
}
