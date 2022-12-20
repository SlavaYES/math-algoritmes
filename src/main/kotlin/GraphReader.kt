import java.io.File

class GraphReader {
    companion object {
        fun getGraph(filename: String): MutableList<IntArray>{
            val graph: MutableList<IntArray> = mutableListOf()

            File(filename).readLines().forEach{ line ->
                val words = line.split(Regex("\\s+"))
                val row = words.map { it.toInt() }.toIntArray()
                graph.add(row)
            }
            return graph
        }
    }
}