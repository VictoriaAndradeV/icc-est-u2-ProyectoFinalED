package solver;

import models.Cell;
import models.SolveResults;

/**
 * Interfaz general que representa el comportamiento comun
 * de los algoritmos de busqueda de las rutas de el laberinto
 */
public interface MazeSolver {
    /**
     *
     * @param maze Matriz del laberinto
     * @param start Celda de inicio
     * @param end Celda de destino
     * @return resultados de la busqueda, la ruta, los nodos visitados
     */
    SolveResults getPath(Cell[][] maze, Cell start, Cell end);
}
