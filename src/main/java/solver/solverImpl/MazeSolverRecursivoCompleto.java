package solver.solverImpl;

import models.Cell;
import models.SolveResults;
import solver.MazeSolver;

import java.util.*;

/**
 * Version mejorada del Maze Solver Recursivo, ya que explora las rutas
 * en cuatro direcciones -> arriba, abajo, izquierda, derecha
 * NO incluye backtracking, asi que puede NO encontrar la ruta más corta ni deshacer caminos incorrectos
 */
public class MazeSolverRecursivoCompleto implements MazeSolver {
    private Set<Cell> visited = new LinkedHashSet<>();
    private List<Cell> path = new ArrayList<>();

    /**
     * Busqueda sobre el lab y nos devuelve sus resultados
     * @param maze Matriz del laberinto
     * @param start Celda de inicio
     * @param end Celda de destino
     * @return
     */
    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        visited.clear();
        path.clear();
        long inicialTime = System.nanoTime();
        findPath(maze, start.getRow(), start.getCol(), end);
        long finalTime = System.nanoTime();

        Collections.reverse(path);
        return new SolveResults(new ArrayList<>(visited), new ArrayList<>(path), finalTime - inicialTime);
    }

    private boolean findPath(Cell[][] maze, int row, int col, Cell end) {
        // validar posición y transitabilidad
        if (!new Cell(0,0, null, false).isValid(maze, row, col)) {
            return false;
        }
        Cell cell = maze[row][col];
        //
        if (visited.contains(cell))
            return false;
        visited.add(cell);

        //si es el destino, se agrega a la ruta
        if (cell.equals(end)) {
            path.add(cell);
            return true;
        }

        //EXPLORA EN 4 DIRECCIONES
        //abajo
        if (findPath(maze, row + 1, col, end)) {
            path.add(cell);
            return true;
        }
        //derecha
        if (findPath(maze, row, col + 1, end)) {
            path.add(cell);
            return true;
        }
        //arriba
        if (findPath(maze, row - 1, col, end)) {
            path.add(cell);
            return true;
        }
        //izquierda
        if (findPath(maze, row, col - 1, end)) {
            path.add(cell);
            return true;
        }

        //si ninguna celda nos sirve, no nos llevara al destino
        return false;
    }
}
