package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa los resultados de los algoritmos de busqueda
 * En donde se incluyen las celdas visitadas, ruta final, tiempo
 * de ejecucion
 */
public class SolveResults {
    private List<Cell> visited;
    private List<Cell> path;
    private long time;

    /**
     * listas vacías y tiempo en 0
     */
    public SolveResults() {
        this.visited = new ArrayList<>();
        this.path = new ArrayList<>();
        this.time = 0;
    }

    /**
     * Construye resultados especificando las celdas visitadas, la ruta final y el tiempo de ejecucion
     *
     * @param visited lista de celdas visitadas por el algoritmo
     * @param path lista de celdas de la ruta desde el inicio hasta el destino
     * @param time tiempo de ejecución en nanosegundos
     */
    public SolveResults(List<Cell> visited, List<Cell> path, long time) {
        this.visited = new ArrayList<>(visited);
        this.path = new ArrayList<>(path);
        this.time = time;
    }

    public List<Cell> getVisited() {
        return visited;
    }

    public void setVisited(List<Cell> visited) {
        this.visited = visited;
    }

    public List<Cell> getPath() {
        return path;
    }

    public void setPath(List<Cell> path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SolveResults{" +
                "visited=" + visited.size() +
                ", path=" + path.size() +
                ", time=" + time + "ns" +
                '}';
    }
}
