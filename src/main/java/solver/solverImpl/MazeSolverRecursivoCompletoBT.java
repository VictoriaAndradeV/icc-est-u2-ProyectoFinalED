package solver.solverImpl;

import models.Cell;
import models.SolveResults;
import solver.MazeSolver;

import java.util.*;

/**
 * Algoritmo Recursivo Completo con Backtracking
 * Explora en 4 direcciones: arriba, abajo, izquierda, derecha
 * Permite retroceder cuando un camino no lleva a la solución
 */
public class MazeSolverRecursivoCompletoBT implements MazeSolver {

    private Set<Cell> visited = new LinkedHashSet<>();
    private List<Cell> path = new ArrayList<>();

    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        visited.clear();
        path.clear();

        findPath(maze, start.getRow(), start.getCol(), end);

        return new SolveResults(new ArrayList<>(visited), new ArrayList<>(path));
    }

    private boolean findPath(Cell[][] maze, int row, int col, Cell end) {

        if (!end.isValid(maze, row, col)) {
            return false;
        }

        Cell cell = maze[row][col];

        if (!cell.rutaPermitida() || visited.contains(cell)) {
            return false;
        }

        visited.add(cell);

        //caso base
        if (cell.equals(end)) {
            path.add(cell);
            return true;
        }


        if (findPath(maze, row + 1, col, end) ||  //Abajo
                findPath(maze, row, col + 1, end) ||  //Derecha
                findPath(maze, row - 1, col, end) ||  //Arriba
                findPath(maze, row, col - 1, end)) {  //Izquierda

            path.add(cell);
            return true;
        }

        //BT desmarca la celda si no lleva al destino
        visited.remove(cell);
        return false;
    }
}
