import java.util.Deque
import java.util.LinkedList

lateinit var graph: MutableMap<Int, MutableList<Pair<Int, Int>>>

const val FILENAME = "src/main/resources/graph.txt"
const val SOURCE = 1
const val DESTINATION = 1000000
const val N = 1000
const val IS_RANDOM = true

/**
 * BFS
 */
fun main() {
    val size = N * N
    val source = SOURCE - 1
    val destination = DESTINATION - 1
    val d = IntArray(size)
    val p = IntArray(size)
    val mask = BooleanArray(size)

    if (IS_RANDOM) {
        graph = GraphGenerator.getGraphRandom(N, N)
        addVertex(graph, 1, 2, 1) // Задаю относительно удобочитаемости, с единицы
    } else {
        graph = GraphReader.getGraph(FILENAME)
    }

    val queue: Deque<Int> = LinkedList()
    queue.add(source)
    d[source] = 0
    p[source] = -1
    mask[source] = true
    while (queue.isNotEmpty()) {
        val v = queue.pop()
        if (!graph.containsKey(v)) {
            continue
        }
        val nearList: MutableList<Pair<Int, Int>>? = graph[v]
        if (nearList.isNullOrEmpty()) {
            continue
        }

        nearList.forEach { pair ->
            if (!mask[pair.first] || (d[v] + pair.second < d[pair.first])) {
                d[pair.first] = d[v] + pair.second
                p[pair.first] = v
                mask[pair.first] = true
                queue.add(pair.first)
            }
        }
    }

    var i = destination
    val path = mutableListOf<Int>()
    while (i != -1) {
        path.add(i)
        i = p[i]
    }
    path.reverse()
    path.forEach {
        print("${it + 1} ")
    }
    println("\nСтоимость: ${d[destination]}")

}

fun addVertex(graph: MutableMap<Int, MutableList<Pair<Int, Int>>>, from: Int, to: Int, weight: Int) {
    if (graph.containsKey(from - 1)) {
        graph[from - 1]?.add(Pair(to - 1, weight))
    } else {
        graph[from - 1] = mutableListOf(Pair(to - 1, weight))
    }
}
