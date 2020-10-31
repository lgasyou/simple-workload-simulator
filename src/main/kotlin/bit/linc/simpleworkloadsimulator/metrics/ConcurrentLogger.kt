package bit.linc.simpleworkloadsimulator.metrics

import org.slf4j.LoggerFactory
import java.io.File
import java.text.DateFormat
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger

object ConcurrentLogger {

    private val log = LoggerFactory.getLogger(javaClass)

    var nThreads: Int = 0

    private val iterNum = AtomicInteger()
    private val dateFormat = DateFormat.getDateTimeInstance()
    private val queue = ConcurrentLinkedQueue<String>()

    fun log(message: String) {
        queue.add(message)
        if (nThreads == iterNum.addAndGet(1)) {
            iterNum.set(0)
            File("results").mkdir()
            val filename = "results/${dateFormat.format(Date())}.csv"
            val file = File(filename)
            log.info("writing to file $filename")
            while (queue.isNotEmpty()) {
                file.appendText(queue.remove())
            }
            log.info("wrote $nThreads items")
        }
    }

}
