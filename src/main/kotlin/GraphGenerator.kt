class GraphGenerator {
    companion object {
        fun getGraphRandom(rows: Int, cols: Int) : MutableMap<Int, MutableList<Pair<Int, Int>>> {
            val graph: MutableMap<Int, MutableList<Pair<Int, Int>>> = linkedMapOf()

            for (row in 0 until rows) {
                for (col in row until cols) {
                    var tmp: Pair<Int, Int>? = null
                    val index = row * cols + col
                    if (col + 1 < cols) {
                        tmp = Pair(index + 1, 1)
                    }
                    if (tmp != null) {
                        if (!graph.containsKey(index)) {
                            graph[index] = mutableListOf(tmp)
                        } else {
                            graph[index]?.add(tmp)
                        }
                    }
                    tmp = null
                    if (row + 1 < rows) {
                        tmp = Pair(index + cols, 1)
                    }
                    if (tmp != null) {
                        if (!graph.containsKey(index)) {
                            graph[index] = mutableListOf(tmp)
                        } else {
                            graph[index]?.add(tmp)
                        }
                    }
                }
            }
            return graph
        }
    }
}