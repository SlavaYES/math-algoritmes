import java.util.Deque
import java.util.LinkedList

const val FILENAME = "src/main/resources/graph.txt"

const val SOURCE = 1
const val DESTINATION = 1000000
const val N = 1000
const val IS_RANDOM = true

const val SOURCE_FILE = 1
const val DESTINATION_FILE = 11
const val N_FILE = 11

/**
 * BFS
 */
fun main() {
    val size: Int
    val destination: Int
    val source: Int
    if (IS_RANDOM) {
        size = N * N
        source = SOURCE - 1
        destination = DESTINATION - 1
    } else {
        size = N_FILE * N_FILE
        source = SOURCE_FILE
        destination = DESTINATION_FILE
    }

    val d = IntArray(size)
    val p = IntArray(size)
    val mask = BooleanArray(size)
    val graph: MutableMap<Int, MutableList<Pair<Int, Int>>>

    if (IS_RANDOM) {
        graph = GraphGenerator.getGraphRandom(N, N)
        addVertex(graph, 1017, 200001, 1) // Задаю относительно удобочитаемости, с единицы
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
        if (IS_RANDOM) print("${it + 1} ") else print("$it ")
    }
    println("\nСтоимость: ${d[destination]}")
//    graph.print()
}

fun addVertex(graph: MutableMap<Int, MutableList<Pair<Int, Int>>>, from: Int, to: Int, weight: Int) {
    if (graph.containsKey(from - 1)) {
        graph[from - 1]?.add(Pair(to - 1, weight))
    } else {
        graph[from - 1] = mutableListOf(Pair(to - 1, weight))
    }
}

private fun MutableMap<Int, MutableList<Pair<Int, Int>>>.print() {
    this.forEach {
        print("${it.key + 1} ")
        it.value.forEach { inner ->
            print("(${inner.first + 1} ${inner.second}) ")
        }
        println()
    }
}
