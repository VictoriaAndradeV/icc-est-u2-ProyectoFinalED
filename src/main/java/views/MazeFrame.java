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

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    private final AlgorithmResultDAO resultDAO;
    private JButton solveButton;
    private JComboBox algorithmSelector;
    private JButton pasoAPasoButton;
    private JButton btnLimpiar;

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
        controller = new MazeController(mazePanel);
        mazePanel.setController(controller);
        add(mazePanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        JButton btnStart = new JButton("Start");
        JButton btnEnd = new JButton("End");
        JButton btnWall = new JButton("Wall");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setMode(MazeController.Mode.START);
            }
        });

        btnEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setMode(MazeController.Mode.END);
            }
        });

        btnWall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setMode(MazeController.Mode.WALL);
            }
        });

        topPanel.add(btnStart);
        topPanel.add(btnEnd);
        topPanel.add(btnWall);

        add(topPanel, BorderLayout.NORTH);

        String[] algorithms = {
                "Recursivo", "Recursivo Completo", "Recursivo Completo BT",
                "BFS", "DFS",

        };

        algorithmSelector = new JComboBox<>(algorithms);
        solveButton = new JButton("Resolver");
        pasoAPasoButton = new JButton("Paso a paso");




        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Algoritmo:"));
        bottomPanel.add(algorithmSelector);
        bottomPanel.add(solveButton);
        bottomPanel.add(pasoAPasoButton);


        add(bottomPanel, BorderLayout.SOUTH);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SolveResults results = resolverYObtenerResultados();
                if(results != null){
                    animarVisitadas(results.getVisited(), results.getPath());
                }
            }
        });

        pasoAPasoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!resolvioPasoAPaso){
                    SolveResults results = resolverYObtenerResultados();
                    if(results != null){
                        pasoCeldasVisitadas = results.getVisited();
                        pasoCamino = results.getPath();
                        pasoIndex = 0;
                    }
                }else{
                    //clic siguiente para pintar paso a paso
                    if(pasoIndex < pasoCeldasVisitadas.size() < pasoCamino.size()){
                        Cell cell = pasoCeldasVisitadas.get(pasoIndex);
                        if(cell.getState() == CellState.TRANSITABLE){
                            paintCell(cell, CellState.TRANSITABLE);
                        }
                    }else if(pasoIndex - pasoCeldasVisitadas.size() < pasoCamino.size()){
                        int caminoIdx = pasoIndex - pasoCeldasVisitadas.size();
                        Cell cell = pasoCamino.get(caminoIdx);
                        pasoIndex++;
                        if(cell.getState() != CellState.START && cell.getState() != CellState.END){
                            paintCell(cell, CellState.PATH);
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "Ya se ha mostrado todo");
                        resolvioPasoAPaso = false;
                    }
                }
            }
        });

        JButton btnLimpiar = new JButton("Limpiar");

    }

}
