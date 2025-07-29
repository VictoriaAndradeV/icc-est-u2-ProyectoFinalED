package controllers;

import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.solverImpl.*;
import views.MazePanel;

public class MazeController {
    private final MazePanel mazePanel;
    private Cell[][] maze;
    private Cell startCell;
    private Cell endCell;
    public enum Mode {
        START, END, WALL
    }
    private Mode currentMode = Mode.WALL;

    public MazeController(MazePanel mazePanel) {
        this.mazePanel = mazePanel;
        this.maze = mazePanel.getMaze();
    }

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

        //pinta celdas
        for (Cell c : results.getVisited()) {
            if (c.getState() == CellState.TRANSITABLE) {
                c.setState(CellState.VISITED);
            }
        }

        //pinta camino
        for (Cell c : results.getPath()) {
            if (c != startCell && c != endCell) {
                c.setState(CellState.PATH);
            }
        }

        mazePanel.repaint();
        return results;
    }
}
