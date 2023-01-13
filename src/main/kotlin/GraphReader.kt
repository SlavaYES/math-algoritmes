import java.io.File

class GraphReader {
    companion object {
        fun getGraph(filename: String):  List<MutableList<Pair<Int, Int>>> {
            val graph: MutableList<MutableList<Pair<Int, Int>>> = mutableListOf()

            for (line in File(filename).readLines()) {
                val words = line.split(Regex("\\s+"))
                if (graph.size <= words[0].toInt() - 1) {
                    graph.add(mutableListOf(Pair(words[1].toInt() - 1, words[2].toInt())))
                } else {
                    graph[words[0].toInt() - 1].add(Pair(words[1].toInt() - 1, words[2].toInt()))
                }
            }
            return graph
        }
    }
}