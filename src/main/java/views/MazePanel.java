package views;

import controllers.MazeController;
import models.Cell;
import models.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panel que representa el laberinto como una matriz de celdas
 * Establece un estado de inicio, final y los muros por loc clics
 */
public class MazePanel extends JPanel {
    private final int rows;
    private final int cols;
    private final Cell[][] maze;
    private MazeController controller;

    /**
     * Construye un panel de dimensiones especificadas y dibuja todas las celdas en estado
     * TRANSITABLE
     * @param rows
     * @param cols
     */
    public MazePanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new Cell[rows][cols];
        inicializarMaze();
        listeners();
    }

    /**
     * Inicializa cada posición de la matriz como celda transitable
     */
    private void inicializarMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell(i, j, CellState.TRANSITABLE, false);
            }
        }
    }
    /**
     * Registra listener de ratón para cuando se haga clic
     */
    private void listeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (controller == null) return;

                int cellWidth = getWidth() / cols;
                int cellHeight = getHeight() / rows;

                int fila = e.getY() / cellHeight;
                int col = e.getX() / cellWidth;

                if (fila >= 0 && fila < rows && col >= 0 && col < cols) {
                    controller.celdaClickeada(fila, col);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (maze == null || rows == 0 || cols == 0) return;

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = maze[i][j];
                g.setColor(color(cell.getState()));
                g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);

                g.setColor(Color.GRAY); // Líneas de rejilla
                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    /**
     * cada estado de celda tiene un color
     */
    private Color color(CellState state) {
        switch (state) {
            case START: return Color.GREEN;
            case END: return Color.RED;
            case WALL: return Color.BLACK;
            case PATH: return Color.BLUE;
            case VISITED: return Color.ORANGE;
            case TRANSITABLE:
            default: return Color.WHITE;
        }
    }
    /**
     * Retorna la matriz interna de celdas para uso del controlador.
     */
    public Cell[][] getMaze() {
        return maze;
    }

    public void pintarCelda(int row, int col, CellState estado) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            maze[row][col].setState(estado);
            repaint();
        }
    }

    public void limpiar() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j].setState(CellState.TRANSITABLE);
            }
        }
        repaint();
    }
    /**
     * Controlador encargado de la logica de los eventos
     */
    public void setController(MazeController controller) {
        this.controller = controller;
    }
}
