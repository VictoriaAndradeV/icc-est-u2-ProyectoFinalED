package solver.solverImpl;

import models.Cell;
import models.SolveResults;
import solver.MazeSolver;

import java.util.*;

/**
 * DEPTH FIRST SEARCH (DFS)
 * Explora en profundidad primero. Puede encontrar un camino válido,
 * pero no garantiza que sea el más corto.
 */
public class MazeSolverDFS implements MazeSolver {
    /**
     * Ejecuta algoritmo DFS, guarda el orden de visita de celdas
     * reconstruyendo ruta encontrada y mide el tiempo de ejec.
     *
     * @param maze Matriz del laberinto
     * @param start Celda de inicio
     * @param end Celda de destino
     * @return
     */
    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        long startTime = System.nanoTime();

        int rows = maze.length;
        int cols = maze[0].length;

        Stack<Cell> pila = new Stack<>();
        Map<Cell, Cell> predecesores = new HashMap<>();
        Set<Cell> visitados = new LinkedHashSet<>();

        pila.push(start);
        visitados.add(start);

        boolean encontrado = false;

        int[][] direcciones = {
                {1, 0},  //abajo
                {0, 1},  //derecha
                {-1, 0}, //arriba
                {0, -1}  //izquierda
        };

        while (!pila.isEmpty()) {
            Cell actual = pila.pop();

            if (actual.equals(end)) {
                encontrado = true;
                break;
            }

            for (int[] dir : direcciones) {
                int nuevaFila = actual.getRow() + dir[0];
                int nuevaCol = actual.getCol() + dir[1];

                //limites
                if (nuevaFila < 0 || nuevaFila >= rows || nuevaCol < 0 || nuevaCol >= cols) {
                    continue;
                }

                Cell vecino = maze[nuevaFila][nuevaCol];

                if (vecino.rutaPermitida() && !visitados.contains(vecino)) {
                    pila.push(vecino);
                    visitados.add(vecino);
                    predecesores.put(vecino, actual);
                }
            }
        }

        // Reconstrucción del camino si se encontró
        List<Cell> path = new ArrayList<>();
        if (encontrado) {
            Cell actual = end;
            while (actual != null) {
                path.add(actual);
                actual = predecesores.get(actual);
            }
            Collections.reverse(path); // Para que vaya de inicio a fin
        }

        long endTime = System.nanoTime();
        long duracion = endTime - startTime;

        return new SolveResults(new ArrayList<>(visitados), path, duracion);
    }
}
