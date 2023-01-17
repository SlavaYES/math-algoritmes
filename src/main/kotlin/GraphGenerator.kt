class GraphGenerator {
    companion object {
        fun generate(rows: Int, cols: Int) : MutableList<Edge> {
            val graph: MutableList<Edge> = mutableListOf()
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    val index = row * cols + col
                    if (col + 1 < cols) {
                        graph.add(Edge(index, index + 1, 1))
                    }
                    if (row + 1 < rows) {
                        graph.add(Edge(index, index + cols, 1))
                    }
                }
            }
            return graph
        }
    }
}