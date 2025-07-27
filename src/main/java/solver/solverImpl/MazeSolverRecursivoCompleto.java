package solver.solverImpl;

import models.Cell;
import models.SolveResults;
import solver.MazeSolver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Version mejorada del Maze Solver Recursivo, ya que explora las rutas
 * en cuatro direcciones -> arriba, abajo, izquierda, derecha
 * NO incluye backtracking, asi que puede NO encontrar la ruta más corta ni deshacer caminos incorrectos
 */
public class MazeSolverRecursivoCompleto implements MazeSolver {
    private Set<Cell> visited = new LinkedHashSet<>();
    private List<Cell> path = new ArrayList<>();

    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        visited.clear();
        path.clear();

        findPath(maze, start.getRow(), start.getCol(), end);
        return null;
    }

    private boolean findPath(Cell[][] maze, int row, int col, Cell end) {
        if (!end.isValid(maze, row, col))
            return false;

        Cell cell = maze[row][col];

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
