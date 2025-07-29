package dao;

import models.AlgorithmResult;
import java.util.List;

/**
 * Interfaz para los resultados de ejecucion del algoritmo
 * Define operaciones básicas de guardado, recupera y limpia los datos
 */
public interface AlgorithmResultDAO {
    void save(AlgorithmResult result);

    List<AlgorithmResult> findAll();

    void clear();
}
