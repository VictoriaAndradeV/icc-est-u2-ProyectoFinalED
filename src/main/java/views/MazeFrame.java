package views;

import controllers.MazeController;
import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import models.Cell;
import models.CellState;
import models.SolveResults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    private MazeController mazeController;
    private final AlgorithmResultDAO resultDAO;

    private final JTextField txtCols = new JTextField(4);
    private final JTextField txtRows = new JTextField(4);
    private final JButton btnGenerar = new JButton("Generar Laberinto");
    private final JButton btnStart = new JButton("Inicio");
    private final JButton btnEnd = new JButton("Fin");
    private final JButton btnWall = new JButton("Pared");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JButton btnModoRap = new JButton("Modo Rápido: OFF");

    private final JComboBox<String> algorithmSelector;
    private final JButton solveButton;
    private final JButton pasoAPasoButton = new JButton("Paso a paso");

    private List<Cell> pasoCeldasVisitadas;
    private List<Cell> pasoCamino;
    private int pasoIndex = 0;
    private boolean resolvioPasoAPaso = false;

    private static final Map<CellState, Color> COLOR_MAP = new HashMap<>();

    static{
        COLOR_MAP.put(CellState.TRANSITABLE, Color.LIGHT_GRAY);
        COLOR_MAP.put(CellState.WALL, Color.BLACK);
        COLOR_MAP.put(CellState.PATH, Color.BLUE);
        COLOR_MAP.put(CellState.START, Color.GREEN);
        COLOR_MAP.put(CellState.END, Color.RED);
        COLOR_MAP.put(CellState.VISITED, Color.ORANGE);
    }

    public MazeFrame() {
        super("Laberinto");
        resultDAO = new AlgorithmResultDAOFile("results.csv");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior de configuración
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.CYAN);
        topPanel.add(new JLabel("Ancho:"));
        topPanel.add(txtCols);
        topPanel.add(new JLabel("Alto:"));
        topPanel.add(txtRows);
        topPanel.add(btnGenerar);
        topPanel.add(btnStart);
        topPanel.add(btnEnd);
        topPanel.add(btnWall);  // Pared
        topPanel.add(btnLimpiar);
        topPanel.add(btnModoRap);
        add(topPanel, BorderLayout.NORTH);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeController.setMode(MazeController.Mode.START);
            }
        });

        btnEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeController.setMode(MazeController.Mode.END);
            }
        });

        btnWall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeController.setMode(MazeController.Mode.WALL);
            }
        });

        // Panel central vacío hasta generar
        mazePanel = new MazePanel(0, 0);
        mazeController = new MazeController(mazePanel);
        mazePanel.setController(mazeController);
        add(mazePanel, BorderLayout.CENTER);

        //Panel inferior con selector y botones de ejecución
        JPanel bottomPanel = new JPanel();
        String[] algorithms = {"Recursivo","Recursivo Completo","Recursivo Completo BT","BFS","DFS"};
        algorithmSelector = new JComboBox<>(algorithms);
        solveButton = new JButton("Resolver");
        bottomPanel.add(new JLabel("Algoritmo:"));
        bottomPanel.add(algorithmSelector);
        bottomPanel.add(solveButton);
        bottomPanel.add(pasoAPasoButton);
        add(bottomPanel, BorderLayout.SOUTH);

        btnGenerar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                try {
                    int cols = Integer.parseInt(txtCols.getText());
                    int rows = Integer.parseInt(txtRows.getText());

                    remove(mazePanel); // remover el anterior

                    mazePanel = new MazePanel(rows, cols); // nuevo laberinto
                    mazeController = new MazeController(mazePanel); // nuevo controlador
                    mazePanel.setController(mazeController);

                    add(mazePanel, BorderLayout.CENTER);
                    revalidate(); repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MazeFrame.this,
                            "Ancho y Alto deben ser enteros positivos.",
                            "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //cuando se hace clic en resolver
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SolveResults results = resolverYObtenerResultados();
                if (results != null) {
                    animarVisitadas(results.getVisited(), results.getPath());
                    JOptionPane.showMessageDialog(MazeFrame.this,
                            "Camino encontrado. Longitud: " + results.getPath().size());
                } else {
                    JOptionPane.showMessageDialog(MazeFrame.this,
                            "No se pudo encontrar un camino.");
                }
            }
        });

        pasoAPasoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //primer clic, se calcula y almacenan los pasos
                if (!resolvioPasoAPaso) {
                    SolveResults results = resolverYObtenerResultados();
                    if (results != null) {
                        pasoCeldasVisitadas = results.getVisited();
                        pasoCamino = results.getPath();
                        pasoIndex = 0;
                        resolvioPasoAPaso = true;
                    }
                    return;
                }

                //se avanza paso a paso
                if (pasoCeldasVisitadas == null || pasoCamino == null) {
                    return;
                }

                //pinta las celdas visitadas en orden
                if (pasoIndex < pasoCeldasVisitadas.size()) {
                    Cell cell = pasoCeldasVisitadas.get(pasoIndex++);
                    if (cell.getState() == CellState.TRANSITABLE) {
                        paintCell(cell, CellState.VISITED);
                    }
                    return;
                }

                //se traza la ruta final
                int offset = pasoIndex - pasoCeldasVisitadas.size();
                if (offset < pasoCamino.size()) {
                    Cell cell = pasoCamino.get(offset);
                    if (cell != mazeController.getStartCell() && cell != mazeController.getEndCell()) {
                        paintCell(cell, CellState.PATH);
                    }
                    pasoIndex++;
                    return;
                }
                //si ya se ha mostrado completo, se deshabilita
                JOptionPane.showMessageDialog(MazeFrame.this, "Recorrido completado.");
                resolvioPasoAPaso = false;
                pasoAPasoButton.setEnabled(false);
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazePanel.limpiar();
                resolvioPasoAPaso = false;
                pasoIndex = 0;
            }
        });
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private SolveResults resolverYObtenerResultados() {
        String algoritmo = (String) algorithmSelector.getSelectedItem();
        return mazeController.resolver(algoritmo);
    }

    private void animarVisitadas(List<Cell> visitadas, List<Cell> camino) {
        for (Cell c : visitadas) {
            if (c.getState() == CellState.TRANSITABLE) {
                c.setState(CellState.VISITED);
            }
        }
        for (Cell c : camino) {
            if (c.getState() != CellState.START && c.getState() != CellState.END) {
                c.setState(CellState.PATH);
            }
        }
        mazePanel.repaint();
    }

    private void paintCell(Cell c, CellState state) {
        c.setState(state);
        mazePanel.repaint();
    }
}
