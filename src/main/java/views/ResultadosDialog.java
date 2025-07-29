package views;

import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import models.AlgorithmResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResultadosDialog extends JDialog{
    private final AlgorithmResultDAO resultDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnLimpiar;
    private JButton btnGraficar;
    private List<AlgorithmResult> resultados;

    /**
     * Carga los resultados desde el DAO y muestra en la tabla
     *
     * @param parent ventana padre para posicionamiento y modalidad
     */
    public ResultadosDialog(JFrame parent) {
        super(parent, "Resultados", true);
        resultDAO = new AlgorithmResultDAOFile("results.csv");
        this.resultados = resultDAO.findAll();

        setLayout(new BorderLayout());
        setSize(500, 300);
        setLocationRelativeTo(parent);

        tableModel = new DefaultTableModel(new Object[]{"Algoritmo", "Celdas Camino", "Tiempo (ns)"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        btnLimpiar = new JButton("Limpiar Resultados");
        btnGraficar = new JButton("Graficar Resultados");

        bottomPanel.add(btnLimpiar);
        bottomPanel.add(btnGraficar);

        add(bottomPanel, BorderLayout.SOUTH);

        cargarResultados();
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(ResultadosDialog.this, "Desea borrar todos los resultados?", "Confirmación", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    resultDAO.clear();
                    tableModel.setRowCount(0);
                }
            }
        });

        btnGraficar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarGrafica();

            }
        });
    }

    private void cargarResultados() {
        List<AlgorithmResult> lista = resultDAO.findAll();
        tableModel.setRowCount(0);

        for (AlgorithmResult ar : lista) {
            tableModel.addRow(new Object[]{
                    ar.getNombreAlgoritmo(), ar.getLongitudRuta(), ar.getTiempoEjecucion()
            });
        }
    }

    private void mostrarGrafica() {
        //dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (AlgorithmResult r : resultados) {
            dataset.addValue(r.getTiempoEjecucion(), "Tiempo(ns)", r.getNombreAlgoritmo());
        }

        //grafico
        JFreeChart chart = ChartFactory.createLineChart(
                "Tiempos de Ejecución por Algoritmo",
                "Algoritmo",
                "Tiempo (ns)",
                dataset
        );

        //detalles
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 20));
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.DARK_GRAY);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.pink);
        plot.setRenderer(renderer);

        //nuevo dialogo para grafico
        JDialog graficaDialog = new JDialog(this, "Gráfica", true);
        graficaDialog.setSize(800, 500);
        graficaDialog.setLocationRelativeTo(this);
        graficaDialog.setContentPane(new ChartPanel(chart));
        graficaDialog.setVisible(true);
    }

}
