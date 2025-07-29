package dao.daoImpl;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmResultDAOFile implements AlgorithmResultDAO {
    private final File file;

    /**
     * @param filename ruta del archivo CSV de resultados
     */
    public AlgorithmResultDAOFile(String filename) {
        this.file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo crear el archivo de resultados", e);
        }
    }

    @Override
    public void save(AlgorithmResult result) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            // result.toString() devuelve "nombreAlgoritmo,tiempoEjecucion,longitudRuta"
            bw.write(result.toString());
            bw.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException("Error al guardar resultado", e);
        }
    }

    @Override
    public List<AlgorithmResult> findAll() {
        List<AlgorithmResult> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String nombre = partes[0].trim();
                    long tiempo = Long.parseLong(partes[1].trim());
                    int longitud = Integer.parseInt(partes[2].trim());
                    lista.add(new AlgorithmResult(nombre, tiempo, longitud));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Error al leer resultados", e);
        }
        return lista;
    }

    @Override
    public void clear() {
        try (FileWriter fw = new FileWriter(file, false)) {
            // Abriendo en modo no append trunca el archivo
        } catch (IOException e) {
            throw new UncheckedIOException("Error al limpiar resultados", e);
        }
    }
}
