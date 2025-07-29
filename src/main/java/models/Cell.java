package models;

import java.util.Objects;

/**
 * Representa una celda del laberinto
 * cada celda tiene una posicion(fila y columna), un estado (transitable, si es un muro, inicio, fin, etc)
 * bandera que indica si ha sido visitada
 */
public class Cell {
    private int row;
    private int col;
    private CellState state;
    private boolean visited;

    /**Crea una celda en la posicion indicada y con el estado especificado
     *
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

    /**
     * Obtiene el indice de la fila
     * @return valor de la fila
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtiene el indice de la columna de la celda
     * @return valor de la columna
     */
    public int getCol() {
        return col;
    }

    /**
     * Nos devuelve el estado aactual de la celda
     * @return (Cell state)
     */
    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    /**
     * Nos ayuda a validar si la posicion indicada esta dentro del laberinto
     * y si la celda es un muro o no
     *
     * @param maze arreglo de las celdas que representa el laberinto
     * @param row
     * @param col
     * @return {@code true} si la celda existe y es transitable; {@code false} en otro caso
     */
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
     * ser recorrida por el algoritmo, es decir que la celda no representa un muro
     */
    public boolean rutaPermitida(){
        return state != CellState.WALL;
    }

    /**
     *Compara dos celdas por su posicion
     * Dos celdas son iguales si tienen la misma
     * fila y columna
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
