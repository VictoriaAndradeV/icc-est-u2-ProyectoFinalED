package views;

import controllers.MazeController;
import models.Cell;
import models.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MazePanel extends JPanel {

    private final int rows;
    private final int cols;
    private final Cell[][] maze;
    private MazeController controller;

    private static final int CELL_SIZE = 30;

    public MazePanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new Cell[rows][cols];
        setPreferredSize(new Dimension(cols * CELL_SIZE, rows * CELL_SIZE));
        inicializarMaze();
        listeners();
    }

    private void inicializarMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell(i, j, CellState.TRANSITABLE, false);
            }
        }
    }

    private void listeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (controller == null) return;


                int fila = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;


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

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = maze[i][j];
                g.setColor(color(cell.getState()));
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                g.setColor(Color.GRAY); // Líneas de rejilla
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

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
}
