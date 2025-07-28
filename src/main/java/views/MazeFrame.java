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

    private JButton solveButton;
    private JComboBox algorithmSelector;
    private JButton pasoAPasoButton;
    private JButton btnLimpiar;

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
    }

    public MazeFrame(int rows, int columns) {
        resultDAO = new AlgorithmResultDAOFile(filePath: "results.csv");

        setTitle("Maze Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        mazePanel = new MazePanel(rows, columns);
        mazeController = new MazeController(mazePanel);
        mazePanel.setController(mazeController);
        add(mazePanel, BorderLayout.CENTER);

        //toppanel----------
        JPanel topPanel = new JPanel();
        JButton btnStart = new JButton("Start");
        JButton btnEnd = new JButton("End");
        JButton btnWall = new JButton("Wall");

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

        topPanel.add(btnStart);
        topPanel.add(btnEnd);
        topPanel.add(btnWall);
        add(topPanel, BorderLayout.NORTH);

        //bottonmpanel-------
        JPanel bottomPanel = new JPanel();
        String[] algorithms = {
                "Recursivo", "Recursivo Completo", "Recursivo Completo BT",
                "BFS", "DFS",

        };
        algorithmSelector = new JComboBox<>(algorithms);

        solveButton = new JButton("Resolver");
        pasoAPasoButton = new JButton("Paso a paso");
        JButton btnLimpiar = new JButton("Limpiar");





        bottomPanel.add(new JLabel("Algoritmo:"));
        bottomPanel.add(algorithmSelector);
        bottomPanel.add(solveButton);
        bottomPanel.add(pasoAPasoButton);
        bottomPanel.add(btnLimpiar);
        add(bottomPanel, BorderLayout.SOUTH);


        //listeners
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
                if (!resolvioPasoAPaso) {
                    SolveResults results = resolverYObtenerResultados();
                    if (results != null) {
                        pasoCeldasVisitadas = results.getVisited();
                        pasoCamino = results.getPath();
                        pasoIndex = 0;
                        resolvioPasoAPaso = true;
                    }
                } else {
                    //clic siguiente para pintar paso a paso
                    if (pasoIndex < pasoCeldasVisitadas.size()) {
                        Cell cell = pasoCeldasVisitadas.get(pasoIndex);
                        if (cell.getState() == CellState.TRANSITABLE) {
                            paintCell(cell, CellState.VISITED);
                        }
                        pasoIndex++;
                    } else if (pasoIndex - pasoCeldasVisitadas.size() < pasoCamino.size()) {
                        int caminoIdx = pasoIndex - pasoCeldasVisitadas.size();
                        Cell cell = pasoCamino.get(caminoIdx);
                        if (cell.getState() != CellState.START && cell.getState() != CellState.END) {
                            paintCell(cell, CellState.PATH);
                        }
                        pasoIndex++;
                    } else {
                        JOptionPane.showMessageDialog(MazeFrame.this, "Ya se ha mostrado todo el recorrido.");
                        resolvioPasoAPaso = false;
                    }
                }
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
