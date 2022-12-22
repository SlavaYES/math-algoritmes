import java.io.File

class GraphReader {
    companion object {
        fun getGraph(filename: String): Array<IntArray> {
            return File(filename).readLines().map { it.split(Regex("\\s+")).map { i -> i.toInt() }.toIntArray() }
                .toTypedArray()
        }
    }
}