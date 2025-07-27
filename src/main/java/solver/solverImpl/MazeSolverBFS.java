package solver.solverImpl;

import models.Cell;
import models.SolveResults;
import solver.MazeSolver;

import java.util.*;

/**
 * BREATH FIRST SEARCH
 * Su objetivo es encontrar la ruta más corta desde el inicio hasta a la celda
 * final en el laberinto
 */
public class MazeSolverBFS implements MazeSolver {
    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        int rows = maze.length; //permite calcular tot filas
        int cols = maze[0].length; //total columnas

        Queue<Cell> cola = new LinkedList<>();
        Map<Cell, Cell> map = new HashMap<>();
        Set<Cell> visited = new LinkedHashSet<>();

        cola.offer(start);
        visited.add(start);

        boolean found = false;
        //arriba, abajo, izq y dere
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        while(!cola.isEmpty()) {
            Cell currentCell = cola.poll();

            // Si llegamos al destino, dejamos de buscar
            if (currentCell.equals(end)) {
                found = true;
                break;
            }

            for (int[] direc : directions) {
                int newRow = currentCell.getRow() + direc[0];
                int newCol = currentCell.getCol() + direc[1];

                //valida que la nueva celda se encuentre dentro de la matriz
                if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                    continue;
                }

                Cell vecino = maze[newRow][newCol];

                if(vecino.rutaPermitida() && !visited.contains(vecino)) {
                    cola.offer(vecino);
                    visited.add(vecino);
                    map.put(vecino, currentCell);
                }
            }
        }

        List<Cell> path = new ArrayList<>();
        if (found) {
            Cell currentCell =end;

            while (currentCell != null) {
                path.add(currentCell);
                currentCell = map.get(currentCell);
            }
            Collections.reverse(path); // Para que vaya de inicio a fin

        }
        return new SolveResults(new ArrayList<>(visited), path);
    }
}
