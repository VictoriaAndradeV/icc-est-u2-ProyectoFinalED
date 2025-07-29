package models;

/**
 * Representa el resultado de ejecucion de cada algoritmo en el laberinto
 * Contiene: nombre, tiempo de ejecucion en NANOSEGUNDOS
 * longitud(numero de celdas) y la ruta encontrada
 */
public class AlgorithmResult {
    private String nombreAlgoritmo;
    private long tiempoEjecucion; //en nanosegundos
    private int longitudRuta;

    public AlgorithmResult() {
    }

    /**
     * Constructor principal, obtiene nuevo resultado de ejecucion
     * @param nombreAlgoritmo Nombre del algoritmo
     * @param tiempoEjecucion Tiempo de ejecución en nanosegundos
     * @param longitudRuta Longitud de la ruta encontrada, num de celdas
     */
    public AlgorithmResult(String nombreAlgoritmo, long tiempoEjecucion, int longitudRuta) {
        this.nombreAlgoritmo = nombreAlgoritmo;
        this.tiempoEjecucion = tiempoEjecucion;
        this.longitudRuta = longitudRuta;
    }

    //getters y setters

    /**
     * @return nombre del algoritmo
     */
    public String getNombreAlgoritmo() {
        return nombreAlgoritmo;
    }

    /**
     * @return tiempo de ejecu
     */
    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    /**
     * @return longitud de la ruta
     */
    public int getLongitudRuta() {
        return longitudRuta;
    }

    @Override
    public String toString() {
        return nombreAlgoritmo + "," + tiempoEjecucion + "," + longitudRuta;
    }
}
