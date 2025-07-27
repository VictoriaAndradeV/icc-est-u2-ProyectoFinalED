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

    public SolveResults(List<Cell> visited, List<Cell> path, long time) {
        this.visited = new ArrayList<>();
        this.path = new ArrayList<>();
        this.time = 0;
    }

    /**
     * Constructor que inicia las listas de celdas visitadas
     * y su ruta
     * @param visited Lista de celdas que fueron visitadas por el algoritmo
     * @param path Ruta final desde el inicio hasta la ultima celda
     */
    public SolveResults(List<Cell> visited, List<Cell> path) {
        this.visited = visited;
        this.path = path;
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
