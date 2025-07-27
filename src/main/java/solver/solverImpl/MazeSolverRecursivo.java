package solver.solverImpl;

import models.Cell;
import models.SolveResults;
import solver.MazeSolver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementacion basica para buscar rutas en el laberinto
 * Avanza hacia la derecha y hacia abajo de cada celda
 */
public class MazeSolverRecursivo implements MazeSolver {
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
        if(!end.isValid(maze, row, col))
            return false;
        Cell cell = maze[row][col];
        if(visited.contains(cell))
            return false;

        visited.add(cell);

        if(cell.equals(end)){
            path.add(cell);
            return true;
        }

        if(findPath(maze, row + 1, col, end)|| findPath(maze, row, col+1, end)){
            path.add(cell);
            return true;
        }
        return false;
    }
}
