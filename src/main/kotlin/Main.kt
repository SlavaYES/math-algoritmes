
const val FILENAME = "src/main/resources/graph.txt"
const val SOURCE = 1

fun main() {
    val graph: MutableList<IntArray> = GraphReader.getGraph(FILENAME)
    if (!graph.isGraph()) {
        println("Not graph!")
        return
    }
    graph.printInput()
    Deikstra.calcAndPrint(graph, SOURCE)
}

fun MutableList<IntArray>.isGraph() : Boolean {
    return this.none { it.size != this.size }
}

fun MutableList<IntArray>.printInput() {
    this.forEach {
        it.forEach { itInner ->
            print(String.format(" %7d ", itInner))
        }
        println()
    }
}