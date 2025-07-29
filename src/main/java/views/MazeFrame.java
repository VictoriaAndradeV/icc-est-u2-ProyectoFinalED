package views;

import controllers.MazeController;
import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import models.AlgorithmResult;
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

/**
 * Clase principal de la interfaz gráfica que representa el marco del laberinto
 * Permite al usuario configurar un laberinto, seleccionar un algoritmo y visualizar
 * los resultados de la resolución (normal o paso a paso).
 *
 * Usa MVC donde el MazePanel es la vista del laberinto,
 * y MazeController maneja la lógica de interacción.
 */
public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    private MazeController mazeController;
    private final AlgorithmResultDAO resultDAO;

    // Componentes de interfaz
    private final JTextField txtCols = new JTextField(4);
    private final JTextField txtRows = new JTextField(4);
    private final JButton btnGenerar = new JButton("Generar Laberinto");
    private final JButton btnStart = new JButton("Inicio");
    private final JButton btnEnd = new JButton("Fin");
    private final JButton btnWall = new JButton("Pared");
    private final JButton btnLimpiar = new JButton("Limpiar");

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
    /**
     * Constructor principal que configura toda la interfaz de la aplicación.
     */
    public MazeFrame() {
        super("Laberinto");
        resultDAO = new AlgorithmResultDAOFile("results.csv");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //menu de arriba
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemNuevo = new JMenuItem("Nuevo laberinto");
        JMenuItem itemResultados = new JMenuItem("Ver resultados");

        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemResultados);
        menuBar.add(menuArchivo);

        setJMenuBar(menuBar);

        itemNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                        MazeFrame.this,
                        "¿Deseas limpiar y crear un nuevo laberinto?",
                        "Nuevo Laberinto",
                        JOptionPane.YES_NO_OPTION
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    mazePanel.limpiar();
                    txtCols.setText("");
                    txtRows.setText("");
                }
            }
        });

        itemResultados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultadosDialog dialog = new ResultadosDialog(MazeFrame.this);
                dialog.setVisible(true);
            }
        });

        // Panel superior de configuración de dimensiones y herramientas
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
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int cols = Integer.parseInt(txtCols.getText());
                    int rows = Integer.parseInt(txtRows.getText());

                    if (cols < 5 || cols > 30 || rows < 5 || rows > 30) {
                        JOptionPane.showMessageDialog(MazeFrame.this,
                                "Las dimensiones deben estar entre 5 y 30.",
                                "Tamaño inválido", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Reiniciar paso a paso si se genera nuevo laberinto
                    resolvioPasoAPaso = false;
                    pasoIndex = 0;
                    pasoAPasoButton.setEnabled(true);

                    remove(mazePanel); // Remover el laberinto anterior
                    mazePanel = new MazePanel(rows, cols); // Nuevo laberinto
                    mazeController = new MazeController(mazePanel); // Nuevo controlador
                    mazePanel.setController(mazeController);

                    add(mazePanel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MazeFrame.this,
                            "Ancho y alto deben ser números enteros válidos.",
                            "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
                pasoAPasoButton.setText("Paso a paso");
                pasoAPasoButton.setEnabled(true);
                solveButton.setEnabled(true);
            }
        });

        //cuando se hace clic en resolver
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SolveResults results = resolverYObtenerResultados();
                if (results != null) {
                    animarVisitadas(results.getVisited(), results.getPath());

                    String algoritmo = (String) algorithmSelector.getSelectedItem();
                    long tiempo = results.getTime();
                    int longitud = results.getPath().size();

                    AlgorithmResult result = new AlgorithmResult(algoritmo, tiempo, longitud);
                    resultDAO.save(result);

                    JOptionPane.showMessageDialog(MazeFrame.this,
                            "Camino encontrado. Longitud: " + longitud);
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
                    if (mazeController.getStartCell() == null || mazeController.getEndCell() == null) {
                        JOptionPane.showMessageDialog(MazeFrame.this,
                                "Debes marcar una celda de inicio y una de fin antes de resolver.",
                                "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Resuelve pero no pinta, solo almacena
                    SolveResults results = mazeController.resolver((String) algorithmSelector.getSelectedItem());

                    if (results == null || results.getPath().isEmpty()) {
                        JOptionPane.showMessageDialog(MazeFrame.this,
                                "No se pudo encontrar un camino.");
                        return;
                    }

                    pasoCeldasVisitadas = results.getVisited();
                    pasoCamino = results.getPath();
                    pasoIndex = 0;
                    resolvioPasoAPaso = true;
                    pasoAPasoButton.setText("Siguiente paso");
                    solveButton.setEnabled(false); // desactiva resolver

                    // Guardamos el resultado
                    String algoritmo = (String) algorithmSelector.getSelectedItem();
                    long tiempo = results.getTime();
                    int longitud = results.getPath().size();
                    AlgorithmResult result = new AlgorithmResult(algoritmo, tiempo, longitud);
                    resultDAO.save(result);
                }

                // Pinta paso a paso
                if (pasoIndex < pasoCeldasVisitadas.size()) {
                    Cell c = pasoCeldasVisitadas.get(pasoIndex);
                    if (c.getState() == CellState.TRANSITABLE) {
                        paintCell(c, CellState.VISITED);
                    }
                } else {
                    int i = pasoIndex - pasoCeldasVisitadas.size();
                    if (i < pasoCamino.size()) {
                        Cell c = pasoCamino.get(i);
                        if (c != mazeController.getStartCell() && c != mazeController.getEndCell()) {
                            paintCell(c, CellState.PATH);
                        }
                    }
                }
                pasoIndex++;

                int total = pasoCeldasVisitadas.size() + pasoCamino.size();
                if (pasoIndex >= total) {
                    JOptionPane.showMessageDialog(MazeFrame.this,
                            "Recorrido completado.");
                    pasoAPasoButton.setEnabled(false);
                    pasoAPasoButton.setText("Paso a paso");
                    solveButton.setEnabled(true);
                }
            }
        });


        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazePanel.limpiar();

                resolvioPasoAPaso = false;
                pasoIndex = 0;
                pasoAPasoButton.setText("Paso a paso");
                pasoAPasoButton.setEnabled(true);
                solveButton.setEnabled(true);
            }
        });
        setSize(850, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Ejecuta el algoritmo seleccionado y obtiene los resultados.
     */
    private SolveResults resolverYObtenerResultados() {
        String algoritmo = (String) algorithmSelector.getSelectedItem();
        return mazeController.resolver(algoritmo);
    }
    /**
     * Pinta todas las celdas visitadas y la ruta encontrada.
     */
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
    /**
     * Pinta una celda individualmente
     */
    private void paintCell(Cell c, CellState state) {
        c.setState(state);
        mazePanel.repaint();
    }
}
