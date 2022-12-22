class FordBellman {
    companion object {
        private const val INF = -1
        fun calc(graph: Array<IntArray>, source: Int) {
            val n = graph.size
            val d = Array(n + 1) { IntArray(n) }

            for (i in graph.indices) {
                d[0][i] = INF
            }
            d[0][source] = 0
            d.print()

            val paths = Array(n) { IntArray(n) }
            for (i in paths.indices) {
                for (j in paths[0].indices) {
                    paths[i][j] = -1
                }
                paths[i][0] = source
            }
            val dist = IntArray(n)
            for (i in dist.indices) {
                dist[i] = INF
            }
            dist[0] = 0
            for (i in 1 until n) {
                var ism = 0
                val dPrior = dist.toList()
                for (j in 0 until n) {
                    var min = dPrior[j]
                    for (k in 0 until n) {
                        if ((min > dPrior[k] + graph[k][j] && dPrior[k] != INF && graph[k][j] != INF)
                            || (min == INF && dPrior[k] != INF && graph[k][j] != INF)
                        ) {
                            min = dPrior[k] + graph[k][j]
                            paths.setWay(j, k)
                        }
                    }
                    if (dist[j] != min) {
                        ism++
                    }
                    dist[j] = min
                }
                dist.print(i, ism)
                var flag = 0
                for (j in 0 until n) {
                    if (dPrior[j] == dist[j]) {
                        flag++
                    }
                }
                if (flag == n) {
                    break
                }
            }
            paths.printWays()
        }

        private fun Array<IntArray>.print() {
            print("\nD(0) = (")
            print(this[0].map { if (it == INF) "-" else it }.joinToString { String.format(" %4s", it) })
            println(")")
        }

        private fun IntArray.print(i: Int, ism: Int) {
            print("\nD($i) = (")
            print(this.map { if (it == INF) "-" else it }.joinToString { String.format(" %4s", it) })
            println(") $ism")
        }

        private fun Array<IntArray>.printWays() {
            for (j in this.indices) {
                println("\nv(${(j + 1)}) = " + this[j].joinToString(" ") { if (it > INF) it.toString() else "" })
            }
        }

        private fun Array<IntArray>.setWay(j: Int, k: Int) {
            for (i in 1 until this.size) {
                if (this[k][i] != -1) {
                    this[j][i] = this[k][i]
                } else {
                    this[j][i] = j
                    break
                }
            }
        }
    }
}