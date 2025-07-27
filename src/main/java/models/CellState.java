package models;

public enum CellState {
    TRANSITABLE, //cuando la celda esta vacia
    WALL,
    PATH,
    START,
    END,
    VISITED
}
