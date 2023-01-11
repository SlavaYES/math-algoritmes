class Deikstra {

    companion object {

        private const val INF = 99999999
        fun calcAndPrint(edges: Map<Int, List<Pair<Int, Int>>>, n: Int, source: Int) {
//            print(String.format("\n%10s | %10s", "w", "d(w)"))
//            for (i in 0 until n) {
//                print(String.format(" | %10d", (i + 1)))
//            }
//            println(String.format(" | %-50s", "s"))

            val path: MutableList<MutableList<Int>> = MutableList(n) { MutableList(n) { -1 } }
            path.forEach { i ->
                i[0] = source
            }
            val dist = IntArray(n) { INF }
            val mark = BooleanArray(n)
            dist[source] = 0
            val rows = mutableListOf<RowTable>()
            var prevRow = RowTable(IntArray(n), 0, 0)
            for (i in 0 until n) {
                prevRow.d[i] = edges[source]?.find { it.first == i }?.second ?: INF
            }
            prevRow.path.add(source)
            prevRow.dw = 0
            prevRow.w = source

//            for (i in 0 until n) {
//                rows.add(RowTable(IntArray(n), 0, 0))
//            }
//            for (i in 0 until n) {
//                rows[0].d[i] = edges[source]?.find { it.first == i }?.second ?: INF
//            }
//            rows[0].path.add(source)
//            rows[0].dw = 0
//            rows[0].w = source
            //prevRow.print()
            for (i in 1 until n) {
                val min = prevRow.d.filter { it > 0 }.min()
                val w = prevRow.d.indexOf(min)
                mark[w] = true
                val x = path[w].indexOf(-1)
                path[w][x] = w
                val dw = prevRow.d[w]
                val currentRow = RowTable(IntArray(n), 0, 0)
                currentRow.w = w
                currentRow.dw = dw
                currentRow.path = prevRow.path
                currentRow.path.add(w)

                for (j in 0 until n) {
                    if (!mark[j]) {
                        if (prevRow.d[j] > dw + (edges[w]?.find { it.first == j }?.second ?: INF) ) {
                            currentRow.d[j] = dw + (edges[w]?.find { it.first == j }?.second ?: INF)
                            path.set(j, w)
                        } else {
                            currentRow.d[j] = prevRow.d[j]
                        }
                    }
                }
                prevRow = currentRow
                //currentRow.print()
            }

            for (i in path.indices) {
                path[i] = path[i].distinct().filter { it != -1 }.toMutableList()
            }
//            rows.printOutput()
//            path.printMinimums()
        }
    }


}

private fun RowTable.print() {
    val inf = 99999999
    print(String.format("%10s | %10s", (this.w + 1), this.dw))
    this.d.forEach { jt ->
        when (jt) {
            inf -> {
                print(String.format(" | %10s", "-"))
            }
            0 -> {
                print(String.format(" | %10s", ""))
            }
            else -> {
                print(String.format(" | %10d", jt))
            }
        }
    }
    print(" | ")
//    for (k in 0..i) {
//        print("${it.path[k] + 1}, ")
//    }
    println()
}

private fun MutableList<MutableList<Int>>.set(j: Int, k: Int) {
    for (i in 1 until this.size) {
        if (this[k][i] != -1) {
            this[j][i] = this[k][i]
        } else {
            this[j][i] = j
            break
        }
    }
}

private fun MutableList<MutableList<Int>>.printMinimums() {
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
    val inf = 99999999
    print(String.format("\n%10s | %10s", "w", "d(w)"))
    for (i in this.indices) {
        print(String.format(" | %10d", (i + 1)))
    }
    println(String.format(" | %-50s", "s"))
    this.forEachIndexed { i, it ->
        print(String.format("%10s | %10s", (it.w + 1), it.dw))
        it.d.forEach { jt ->
            if (jt == inf) {
                print(String.format(" | %10s", "-"))
            } else if (jt == 0) {
                print(String.format(" | %10s", ""))
            } else {
                print(String.format(" | %10d", jt))
            }
        }
        print(" | ")
        for (k in 0..i) {
            print("${it.path[k] + 1}, ")
        }
        println()
    }
}
