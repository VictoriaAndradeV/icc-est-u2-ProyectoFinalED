package controllers;

import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.solverImpl.*;
import views.MazePanel;

/**
 * Controlador principal del laberinto.
 * Gestiona eventos del usuario, ejecución de algoritmos y actualizaciones gráficas.
 */
public class MazeController {
    private final MazePanel mazePanel;
    private Cell[][] maze;
    private Cell startCell;
    private Cell endCell;
    /**
     * Modos de edición del laberinto:
     * - START: colocar inicio
     * - END: colocar destino
     * - WALL: colocar o quitar paredes
     */
    public enum Mode {
        START, END, WALL
    }
    private Mode currentMode = Mode.WALL;
    /**
     * Constructor que asocia el controlador al panel visual.
     *
     * @param mazePanel el panel del laberinto
     */
    public MazeController(MazePanel mazePanel) {
        this.mazePanel = mazePanel;
        this.maze = mazePanel.getMaze();
    }
    /**
     * Lógica cuando el usuario hace clic sobre una celda.
     * Cambia su estado según el modo seleccionado.
     *
     * @param row fila clickeada
     * @param col columna clickeada
     */
    public void celdaClickeada(int row, int col) {
        Cell cell = maze[row][col];

        switch (currentMode) {
            case START:
                if (startCell != null) {
                    startCell.setState(CellState.TRANSITABLE);
                }
                cell.setState(CellState.START);
                startCell = cell;
                break;

            case END:
                if (endCell != null) {
                    endCell.setState(CellState.TRANSITABLE);
                }
                cell.setState(CellState.END);
                endCell = cell;
                break;

            case WALL:
                if (cell.getState() == CellState.WALL) {
                    cell.setState(CellState.TRANSITABLE);
                } else if (cell.getState() == CellState.TRANSITABLE) {
                    cell.setState(CellState.WALL);
                }
                break;
        }

        mazePanel.repaint();
    }
    /**
     * Establece el modo actual (inicio, fin o pared)
     *
     * @param mode nuevo modo
     */
    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public Cell[][] getMaze() {
        return maze;
    }

    /**
     * Asocia un nombre de algoritmo con su implementación real.
     *
     * @param nombre nombre del algoritmo seleccionado
     * @return objeto MazeSolver correspondiente
     */
    private MazeSolver obtenerAlgoritmo(String nombre) {
        switch (nombre) {
            case "Recursivo":
                return new MazeSolverRecursivo();
            case "Recursivo Completo":
                return new MazeSolverRecursivoCompleto();
            case "Recursivo Completo BT":
                return new MazeSolverRecursivoCompletoBT();
            case "DFS":
                return new MazeSolverDFS();
            case "BFS":
                return new MazeSolverBFS();
            default:
                return null;
        }
    }

    /**
     * Ejecuta el algoritmo sobre el laberinto actual,
     * pinta celdas visitadas y camino, y devuelve los resultados.
     *
     * @param algoritmoNombre nombre del algoritmo elegido
     * @return objeto SolveResults con los datos de ejecución
     */
    public SolveResults resolver(String algoritmoNombre) {
        if (startCell == null || endCell == null) {
            System.out.println("Inicio o fin no definidos.");
            return null;
        }

        MazeSolver solver = obtenerAlgoritmo(algoritmoNombre);
        if (solver == null) {
            System.out.println("Algoritmo no encontrado.");
            return null;
        }

        //ejecuta
        SolveResults results = solver.getPath(maze, startCell, endCell);
        if (results == null) {
            System.out.println("No se pudo resolver el laberinto.");
            return null;
        }


        mazePanel.repaint();
        return results;
    }
}
