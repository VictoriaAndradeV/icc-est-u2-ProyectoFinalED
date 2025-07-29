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
 * Avanza hacia la derecha y hacia abajo
 *
 * No nos garantiza encontrar la ruta mas corta, solo una
 * que sea valida y que exista
 */
public class MazeSolverRecursivo implements MazeSolver {
    private Set<Cell> visited = new LinkedHashSet<>();
    private List<Cell> path = new ArrayList<>();

    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        //limpia estructuras de datps que se ejecutaron antes
        visited.clear();
        path.clear();
        long inicialTime = System.nanoTime();
        findPath(maze, start.getRow(), start.getCol(), end);
        long finalTime = System.nanoTime();

        // Construir lista de ruta en orden de inicio a fin
        List<Cell> resultadoRuta = new ArrayList<>(path);
        return new SolveResults(new ArrayList<>(visited), resultadoRuta, finalTime - inicialTime);
    }

    /**
     * Metodo recursivom explora abajo y a la derecha
     * @param maze lab
     * @param row fila actual
     * @param col column actual
     * @param end
     * @return
     */
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
