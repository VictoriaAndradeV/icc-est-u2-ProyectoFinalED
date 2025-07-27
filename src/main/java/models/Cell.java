package models;

import java.util.Objects;

/**
 * models/cell representa una celda dentro del laberinto
 * cada celda tiene una posicion, un estado (transitable o si tiene un muro)
 * bandera que indica si ha sido visitada
 */
public class Cell {
    private int row;
    private int col;
    private CellState state;
    private boolean visited;

    /**
     * @param row posicion fila de la celda
     * @param col columna en donde se ubica la celda
     * @param state estado de la celda
     */
    public Cell(int row, int col, CellState state, boolean visited) {
        this.row = row;
        this.col = col;
        this.state = state;
        this.visited = visited;
    }

    //Getters y setters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isValid(Cell[][] maze, int row, int col) {
        int rows = maze.length;
        int cols = maze[0].length;

        // Verifica si está dentro del rango de la matriz
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }

        // Verifica si es transitable (no es muro)
        return maze[row][col].rutaPermitida();
    }

    /**
     * Si devuelve el valor TRUE significa que la celda puede
     * ser recorrida por el algoritmo
     */
    public boolean rutaPermitida(){
        return state != CellState.WALL;
    }

    /**
     *Compara dos celdas por su posicion
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col;
    }

    /**
     * Basado en la posición, permite el uso en estructuras (hashmap-hashset)
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", col=" + col +
                ", state=" + state +
                '}';
    }
}
