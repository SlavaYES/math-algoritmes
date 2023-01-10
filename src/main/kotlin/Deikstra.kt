class Deikstra {

    companion object {

        private const val INF = 99999
        fun calcAndPrint(matrix: MutableList<IntArray>, source: Int) {
            val n = matrix.size
            val path: MutableList<IntArray> = MutableList(matrix.size) { IntArray(matrix.first().size) }
            path.forEach { i ->
                for (j in i.indices) {
                    i[j] = -1
                }
                i[0] = source
            }

            val rows = mutableListOf<RowTable>()
            for (i in 0 until n) {
                rows.add(RowTable(IntArray(n), 0, 0))
            }
            for (i in 0 until n) {
                rows[0].d[i] = matrix[source][i]
            }
            rows[0].path.add(source)
            rows[0].dw = INF
            rows[0].w = INF

            val mark = BooleanArray(n) { false }

            for (i in 1 until n) {
                val min = rows[i - 1].d.filter { it > 0 }.min()
                val w = rows[i - 1].d.indexOf(min)
                mark[w] = true
                val x = path[w].indexOf(-1)
                path[w][x] = w
                val dw = rows[i - 1].d[w]
                rows[i].w = w
                rows[i].dw = dw
                rows[i].path = rows[i - 1].path
                rows[i].path.add(w)

                for (j in 0 until n) {
                    if (!mark[j]) {
                        if (rows[i - 1].d[j] > dw + matrix[w][j]) {
                            rows[i].d[j] = dw + matrix[w][j]
                            path.set(j, w)
                        } else {
                            rows[i].d[j] = rows[i - 1].d[j]
                        }
                    }
                }
            }
            for (i in path.indices) {
                path[i] = path[i].distinct().filter { it != -1 }.toIntArray()
            }
            rows.printOutput()
            path.printMinimums()
        }
    }


}
private fun List<IntArray>.set(j: Int, k: Int) {
    for (i in 1 until this.size) {
        if (this[k][i] != -1) {
            this[j][i] = this[k][i]
        } else {
            this[j][i] = j
            break
        }
    }
}

private fun MutableList<IntArray>.printMinimums() {
    this.forEachIndexed { i, it ->
        print("\nv ${i + 1} = ")
        it.forEach { jt ->
            if (jt > -1) {
                print("${jt + 1} ")
            }
        }
    }
}

private fun MutableList<RowTable>.printOutput() {
    print(String.format("\n%10s | %10s", "w", "d(w)"))
    for (i in this.indices) {
        print(String.format(" | %10d", (i + 1)))
    }
    println(String.format(" | %50s", "s"))
    this.forEachIndexed { i, it ->
        print(String.format("%10s | %10s", (it.w + 1), it.dw))
        if (i == 0) {
            it.d.forEach { jt ->
                print(String.format(" | %10d", jt))
            }
        } else {
            it.d.forEachIndexed { j, jt ->
                if (j == 0) {
                    print(String.format(" | %10s", ""))
                } else {
                    print(String.format(" | %10d", jt))
                }
            }
        }
        print(" | ")
        for (k in 0..i) {
            print("${it.path[k] + 1}, ")
        }
        println()
    }
}
