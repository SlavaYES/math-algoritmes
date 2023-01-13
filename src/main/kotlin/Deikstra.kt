import java.util.PriorityQueue
import java.util.Queue

class Deikstra {

    companion object {

        private const val INF = 99999999
        fun calcLow(graph: List<List<Pair<Int, Int>>>, source: Int, destination: Int, print: Boolean) {
            val n = graph.size
            val p: MutableList<MutableList<Int>> = MutableList(n) { MutableList(n) { -1 } }
            p.forEach { i ->
                i[0] = source
            }
            val d = IntArray(n) { INF }
            val u = BooleanArray(n)
            d[source] = 0
            val rows = mutableListOf<RowTable>()
            for (i in 0 until n) {
                rows.add(RowTable(IntArray(n), 0, 0))
            }
            for (i in 0 until n) {
                rows[0].d[i] = graph[source].find { it.first == i }?.second ?: INF
            }
            rows[0].path.add(source)
            rows[0].dw = 0
            rows[0].w = source
            for (i in 0 until n) {
                rows[0].d[i] = graph[source].find { it.first == i }?.second ?: INF
            }
            rows[0].path.add(source)
            rows[0].dw = 0
            rows[0].w = source
            for (i in 1 until n) {
                val min = rows[i - 1].d.filter { it > 0 }.min()
                val w = rows[i - 1].d.indexOf(min)
                u[w] = true
                val x = p[w].indexOf(-1)
                p[w][x] = w
                val dw = rows[i - 1].d[w]
                rows[i].w = w
                rows[i].dw = dw
                rows[i].path = rows[i - 1].path
                rows[i].path.add(w)

                for (j in 0 until n) {
                    if (!u[j]) {
                        if (rows[i - 1].d[j] > dw + (graph[w].find { it.first == j }?.second ?: INF) ) {
                            rows[i].d[j] = dw + (graph[w].find { it.first == j }?.second ?: INF)
                            p.set(j, w)
                        } else {
                            rows[i].d[j] = rows[i - 1].d[j]
                        }
                    }
                }
            }

            for (i in p.indices) {
                p[i] = p[i].distinct().filter { it != -1 }.toMutableList()
            }
            if (print) {
                rows.printOutput()
                p.printMinimums()
            }
            println("\nsize = ${rows.last().dw}")
        }

        fun calcMiddle(graph: List<List<Pair<Int, Int>>>, source: Int, destination: Int, print: Boolean) {
            val p = IntArray(graph.size)
            val u = BooleanArray(graph.size)
            val paths: List<MutableList<Int>> = List(graph.size) { mutableListOf() }
            val d = IntArray(graph.size) { INF }
            for (i in graph.indices) {
                d[i] = graph[source].find { it.first == i }?.second ?: INF
            }
            d[source] = 0
            if (print) {
                print(String.format("\n%10s | %10s", "w", "d(w)"))
                for (i in graph.indices) {
                    print(String.format(" | %10d", (i + 1)))
                }
                println(String.format(" | %-50s", "s"))
                print(String.format("%10d | %10d |", source + 1, d[source]))
                for (i in graph.indices) {
                    print(String.format(" %10d |", d[i]))
                }
                println()
            }
            val pathPrint = mutableListOf<Int>()
            val start = System.currentTimeMillis()
            for (i in 1 until graph.size) {
                if (i % 10000 == 0) println("$i ${(System.currentTimeMillis() - start) / 1000}")
                var v = -1
                for (j in graph.indices) {
                    if (!u[j] && (v == -1 || d[j] < d[v])) {
                        v = j
                    }
                }
                if (d[v] == INF) break
                u[v] = true
                for (j in graph[v].indices) {
                    val to = graph[v][j].first
                    val len = graph[v][j].second
                    if (d[v] + len < d[to]) {
                        d[to] = d[v] + len
                        p[to] = v
                    }
                }
                if (print) {
                    print(String.format("%10d | %10d |", v + 1, d[v]))
                    for (it in graph.indices) {
                        print(String.format(" %10d |", d[it]))
                    }
                    pathPrint.add(v)
                    pathPrint.forEach {
                        print("${it + 1}, ")
                    }
                    println()
                }
            }

            for (i in graph.indices) {
                var v = i
                while (v != source) {
                    paths[i].add(v)
                    v = p[v]
                }
                paths[i].add(source)
                paths[i].reverse()
            }
            if (print) {
                paths.forEachIndexed { idx, it ->
                    print("${idx + 1} = ")
                    it.forEach { jt ->
                        print("${jt + 1} ")
                    }
                    println()
                }
                paths[graph.size - 1].forEach {
                    print("${it + 1} ")
                }
            }
            println("\nsize = ${d[destination]}")
        }

        fun calcFast(graph: List<List<Pair<Int, Int>>>, source: Int, destination: Int, print: Boolean) {
            val p = IntArray(graph.size)
            val paths: List<MutableList<Int>> = List(graph.size) { mutableListOf() }
            val d = IntArray(graph.size) { INF }
            d[source] = 0
            val q: Queue<Pair<Int, Int>> = PriorityQueue(
                Comparator { a, b ->
                    if (a.first < b.first) {
                        return@Comparator -1
                    } else if (a.first > b.first) {
                        return@Comparator 1
                    } else {
                        return@Comparator 0
                    }
                }
            )
            q.add(Pair(d[source], source))

            val pathPrint: MutableList<Int> = mutableListOf()
            if (print) {
                print(String.format("\n%10s | %10s", "w", "d(w)"))
                for (i in graph.indices) {
                    print(String.format(" | %10d", (i + 1)))
                }
                println()
            }

            while (!q.isEmpty()) {
                val v = q.poll().second
                for (j in graph[v].indices) {
                    val to = graph[v][j].first
                    val len = graph[v][j].second
                    if (d[v] + len < d[to]) {
                        q.remove(Pair(d[to], to))
                        d[to] = d[v] + len
                        p[to] = v
                        q.add(Pair(d[to], to))
                    }
                }
                if (print) {
                    print(String.format("%10d | %10d |", v + 1, d[v]))
                    for (it in graph.indices) {
                        print(String.format(" %10d |", d[it]))
                    }
                    pathPrint.forEach {
                        print("${it + 1}, ")
                    }
                    println()
                    pathPrint.add(v)
                }
            }
            for (i in graph.indices) {
                var v = i
                while (v != source) {
                    paths[i].add(v)
                    v = p[v]
                }
                paths[i].add(source)
                paths[i].reverse()
            }
            if (print) {
                paths.forEachIndexed { idx, it ->
                    print("${idx + 1} = ")
                    it.forEach { jt ->
                        print("${jt + 1} ")
                    }
                    println()
                }
                paths[graph.size - 1].forEach {
                    print("${it + 1} ")
                }
            }
            println("\nsize = ${d[destination]}")
        }
    }
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

private fun List<List<Int>>.printMinimums() {
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
        for (k in 0..i) {
            print("${it.path[k] + 1}, ")
        }
        println()
    }
}
