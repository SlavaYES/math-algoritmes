class GraphGenerator {
    companion object {
        fun getGraphRandom(rows: Int, cols: Int) : List<MutableList<Pair<Int, Int>>> {
            val graph: List<MutableList<Pair<Int, Int>>> = List(rows * cols) { mutableListOf() }

            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    var tmp: Pair<Int, Int>? = null
                    val index = row * cols + col
                    if (col + 1 < cols) {
                        tmp = Pair(index + 1, 1)
                    }
                    if (tmp != null) {
                        graph[index].add(tmp)
                    }
                    tmp = null
                    if (row + 1 < rows) {
                        tmp = Pair(index + cols, 1)
                    }
                    if (tmp != null) {
                        graph[index].add(tmp)
                    }
                }
            }
            return graph
        }
    }
}