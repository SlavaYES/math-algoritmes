import java.io.File

class GraphReader {
    companion object {
        fun getGraph(filename: String): MutableList<Edge> {
            return File(filename).readLines().map {
                val numbers = it.split(Regex("\\s+")).map { mapper -> mapper.toInt() }
                Edge(numbers[0] - 1, numbers[1] - 1, numbers[2])
            }.toMutableList()
        }
    }
}