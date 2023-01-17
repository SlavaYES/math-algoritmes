
const val FILENAME = "src/main/resources/graph.txt"
const val SOURCE = 1 - 1
const val N = 1000
const val DESTINATION = N * N - 1
const val IS_RANDOM = true

fun main() {
    val graph = if (IS_RANDOM) {
        GraphGenerator.getGraphRandom(N, N)
    } else {
        GraphReader.getGraph(FILENAME) // TODO
    }
    val print = if (N < 10) true else false
    addVertex(graph, 1001, 1005, 10)
    Deikstra.calcFast(graph, SOURCE, DESTINATION, print)
}
fun addVertex(graph: List<MutableList<Pair<Int, Int>>>, from: Int, to: Int, weight: Int) {
    val fromNorm = from - 1
    val toNorm = to - 1
    val pair = Pair(toNorm, weight)
    if (fromNorm < graph.size) {
        if (graph[fromNorm].find { toNorm == it.first } != null) {
            for (i in graph[fromNorm].indices) {
                if (graph[fromNorm][i].first == toNorm) {
                    graph[fromNorm][i] = pair
                }
            }
        } else {
            graph[fromNorm].add(pair)
        }
    }
}