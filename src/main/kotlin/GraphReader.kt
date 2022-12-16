import java.io.File

class GraphReader {
    companion object {
        fun getGraph(filename: String):  MutableMap<Int, MutableList<Pair<Int, Int>>> {
            val graph: MutableMap<Int, MutableList<Pair<Int, Int>>> = linkedMapOf()

            var prevVertex = -1
            for (line in File(filename).readLines()) {
                val words = line.split(Regex("\\s+"))
                val vertex = words[0].toInt()
                if (prevVertex != vertex) {
                    graph[vertex] = mutableListOf(Pair(words[1].toInt(), words[2].toInt()))
                } else {
                    graph[vertex]?.add(Pair(words[1].toInt(), words[2].toInt()))
                }
                prevVertex = vertex

            }
            return graph
        }
    }
}