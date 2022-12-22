
const val FILENAME = "src/main/resources/graph.txt"
const val SOURCE = 0
fun main() {
    val graph: Array<IntArray> = GraphReader.getGraph(FILENAME)
    if (!graph.isGraph()) {
        println("Not graph!")
        return
    }
    FordBellman.calc(graph, SOURCE)
}
private fun Array<IntArray>.isGraph(): Boolean {
    return this.none { it.size != this.size }
}