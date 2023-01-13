
const val FILENAME = "src/main/resources/graph.txt"
const val SOURCE = 1 - 1
const val N = 1000
const val IS_RANDOM = true

fun main() {
    val graph = if (IS_RANDOM) {
        GraphGenerator.getGraphRandom(N, N)
    } else {
        GraphReader.getGraph(FILENAME) // TODO
    }
    Deikstra.calc(graph, SOURCE)
}
fun addVertex(graph: MutableMap<Int, MutableList<Pair<Int, Int>>>, from: Int, to: Int, weight: Int) {
    if (graph.containsKey(from - 1)) {
        graph[from - 1]?.add(Pair(to - 1, weight))
    } else {
        graph[from - 1] = mutableListOf(Pair(to - 1, weight))
    }
}