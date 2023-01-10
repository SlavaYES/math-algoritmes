
const val FILENAME = "src/main/resources/graph.txt"
const val SOURCE = 1
const val DESTINATION = 1000000
const val N = 100
const val IS_RANDOM = true

const val SOURCE_FILE = 1
const val DESTINATION_FILE = 11
const val N_FILE = 11

fun main() {
    val size: Int
    val destination: Int
    val source: Int
    if (IS_RANDOM) {
        size = N * N
        source = SOURCE - 1
        destination = DESTINATION - 1
    } else {
        size = N_FILE * N_FILE
        source = SOURCE_FILE
        destination = DESTINATION_FILE
    }

    val graph: MutableMap<Int, MutableList<Pair<Int, Int>>>

    if (IS_RANDOM) {
        graph = GraphGenerator.getGraphRandom(N, N)
    } else {
        graph = GraphReader.getGraph(FILENAME)
    }

}
fun addVertex(graph: MutableMap<Int, MutableList<Pair<Int, Int>>>, from: Int, to: Int, weight: Int) {
    if (graph.containsKey(from - 1)) {
        graph[from - 1]?.add(Pair(to - 1, weight))
    } else {
        graph[from - 1] = mutableListOf(Pair(to - 1, weight))
    }
}
private fun MutableMap<Int, MutableList<Pair<Int, Int>>>.print() {
    this.forEach {
        print("${it.key + 1} ")
        it.value.forEach { inner ->
            print("(${inner.first + 1} ${inner.second}) ")
        }
        println()
    }
}