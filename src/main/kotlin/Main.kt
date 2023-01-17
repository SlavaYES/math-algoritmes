
const val FILENAME = "src/main/resources/graph.txt"
const val SOURCE = 1 - 1
const val DESTINATION = 1000000 - 1
const val N = 1000
const val IS_RANDOM = true
fun main() {
    val graph: MutableList<Edge> = if (IS_RANDOM) {
        GraphGenerator.generate(N, N)
    } else {
        GraphReader.getGraph(FILENAME)
    }
    val print = if (N < 9) true else false
    addVertex(graph, 3, 3010, -1)
    FordBellman.calc(graph, N * N, SOURCE, DESTINATION, print)
}
fun addVertex(graph: MutableList<Edge>, from: Int, to: Int, weight: Int) {
    val fromNorm = from - 1
    val toNorm = to - 1
    val edge = Edge(fromNorm, toNorm, weight)

    val exists = graph.find { it.from == fromNorm && it.to == toNorm }
    if (exists != null) {
        graph.find { it.from == fromNorm && it.to == toNorm }?.weight = weight
        return
    }
    graph.add(edge)
}
