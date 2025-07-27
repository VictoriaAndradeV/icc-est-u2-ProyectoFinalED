package models;


/**
 * Representa el resultado de ejecucion de cada algoritmo en el laberinto
 */
public class AlgorithmResult {
    private String nombreAlgoritmo;
    private long tiempoEjecucion; //en nanosegundos
    private int longitudRuta;

    public AlgorithmResult() {
    }

    /**
     * Constructor principal
     * @param nombreAlgoritmo Nombre del algoritmo
     * @param tiempoEjecucion Tiempo de ejecución en nanosegundos
     * @param longitudRuta Longitud de la ruta encontrada
     */
    public AlgorithmResult(String nombreAlgoritmo, long tiempoEjecucion, int longitudRuta) {
        this.nombreAlgoritmo = nombreAlgoritmo;
        this.tiempoEjecucion = tiempoEjecucion;
        this.longitudRuta = longitudRuta;
    }

    //getters y setters
    public String getNombreAlgoritmo() {
        return nombreAlgoritmo;
    }

    public void setNombreAlgoritmo(String nombreAlgoritmo) {
        this.nombreAlgoritmo = nombreAlgoritmo;
    }

    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(long tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public int getLongitudRuta() {
        return longitudRuta;
    }

    public void setLongitudRuta(int longitudRuta) {
        this.longitudRuta = longitudRuta;
    }

    @Override
    public String toString() {
        return nombreAlgoritmo + "," + tiempoEjecucion + "," + longitudRuta;
    }
}
