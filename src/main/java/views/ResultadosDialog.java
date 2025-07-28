package views;

import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import models.AlgorithmResult;

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

    public ResultadosDialog(JFrame parent) {
        super(parent, "Resultados", true);
        resultDAO = new AlgorithmResultDAOFile("results.csv");

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
                JOptionPane.showMessageDialog(ResultadosDialog.this, "Función de graficar aún no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE);
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
}
