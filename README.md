# 🧭 Proyecto Final: Resolución de Laberintos con Algoritmos de Búsqueda

Universidad Politécnica Salesiana
<img width="897" height="269" alt="Logo_Universidad_Politécnica_Salesiana_del_Ecuador" src="https://github.com/user-attachments/assets/12a3f26b-ac13-448d-9dff-3169850019ef" />


## 📌 Información General

- **Título:**  Resolución de Laberintos con Algoritmos de Búsqueda
- **Asignatura:** Estructura de Datos
- **Carrera:** Computación
- **Estudiante:** Victoria Andrade e Isabel Ullauri
- **Correo Institucional:** iullaurib@est.ups.edu.ec & vandradev2@est.ups.edu.ec
- **Fecha:** 28/7/2025
- **Profesor:** Ing. Pablo Torres

---

## 🛠️ Descripción

El objetivo del proyecto es encontrar la ruta óptima desde un punto de inicio (A) hasta un destino (B) dentro de un laberinto representado como una matriz. Cada celda puede ser transitable o un muro. La solución debe aplicar algoritmos de búsqueda y técnicas de programación dinámica, mostrando visualmente el recorrido y los tiempos de ejecución de cada estrategia.

## 🎯 Objetivo General

Desarrollar una aplicación interactiva que implemente diferentes algoritmos de búsqueda para resolver laberintos, utilizando estructuras de datos y principios de eficiencia algorítmica.

## 🧪 Algoritmos Implementados

Se implementaron los siguientes métodos en clases separadas, siguiendo el patrón MVC:

- 🔁 Recursivo (2 direcciones)

- 🔄 Recursivo completo (4 direcciones)

- 🧠 Recursivo completo con backtracking

- 🔎 BFS (Breadth-First Search)

- 🧭 DFS (Depth-First Search)

- Cada algoritmo registra:
- Celdas visitadas
- Longitud del camino
- Tiempo de ejecución (en nanosegundos)

## 💡 Marco Teórico

DFS: Busca profundamente antes de retroceder. Puede no encontrar el camino más corto.

BFS: Explora nivel por nivel, garantiza la ruta más corta.

Recursivo: Explora a través de llamadas recursivas sin estructuras auxiliares.

Backtracking: Permite deshacer caminos equivocados, buscando todas las rutas posibles.


---
## 📊 Visualización de Resultados
Tabla: muestra nombre del algoritmo, celdas de camino y tiempo.

Gráfica: línea de tiempo con eje X (algoritmos) y eje Y (tiempo en ns).

Los resultados se almacenan en un archivo results.csv de forma persistente.

## 📘Código Ejemplo (DFS)
```
@Override
public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
    // Lista para guardar todas las celdas que fueron visitadas durante la búsqueda
    List<Cell> visited = new ArrayList<>();

    // Lista para guardar el camino final desde el inicio hasta el destino
    List<Cell> path = new ArrayList<>();

    // Medimos el tiempo de inicio en nanosegundos
    long startTime = System.nanoTime();

    // Llamamos al método recursivo DFS
    dfs(maze, start, end, visited, path);

    // Medimos el tiempo final después de completar la búsqueda
    long endTime = System.nanoTime();

    // Retornamos el resultado con las celdas visitadas, el camino y el tiempo de ejecución
    return new SolveResults(visited, path, endTime - startTime);
}


```
## 🧠 Explicación del código:

Este fragmento pertenece al algoritmo de búsqueda en profundidad (DFS).

Se inicializan dos listas:

visited para registrar todas las celdas recorridas.

path para almacenar el camino encontrado desde el punto A (inicio) hasta B (destino).

Se toma el tiempo antes y después de ejecutar la búsqueda para calcular cuánto tardó.

El método dfs(...) es el que contiene la lógica recursiva para explorar el laberinto.

Finalmente, se devuelve un objeto SolveResults que guarda todo lo necesario para mostrar los resultados y estadísticas en la interfaz.

## 🧱 Arquitectura del Proyecto

- `models/`
  - `Cell.java`: Representa cada celda del laberinto.
  - `CellState.java`: Enum con los estados posibles de una celda (inicio, fin, muro, transitable, etc.).
  - `SolveResults.java`: Contiene el resultado de una ejecución (camino, visitados, tiempo).
  - `AlgorithmResult.java`: Guarda el resumen de ejecución de un algoritmo (nombre, tiempo, longitud).
- `views/`
  - `MazeFrame.java`: Ventana principal. Permite construir y resolver el laberinto.
  - `MazePanel.java`: Panel gráfico que representa el laberinto y detecta eventos de mouse.
  - `ResultadosDialog.java`: Muestra una tabla con estadísticas y permite graficar resultados.
- `controllers/`
  - `MazeController.java`: Controlador central. Administra la lógica entre vista y modelo.
- `solvers/`
  - Clases como `MazeSolverDFS.java`, `MazeSolverBFS.java`, etc., que implementan los algoritmos.
- `dao/`
  - `AlgorithmResultDAO.java`: Interfaz para la persistencia de resultados.
  - `AlgorithmResultDAOFile.java`: Implementación que guarda y lee desde un archivo CSV.


<img width="844" height="546" alt="Screenshot 2025-07-28 235641" src="https://github.com/user-attachments/assets/43208eca-51a2-45c0-9c2b-c6979dae3507" />




---

## 🧑‍💻 Ejemplo de Uso

- Selecciona START, END, y WALL y dibuja tu laberinto.

- Escoge el algoritmo a ejecutar.

- Presiona Resolver o Paso a paso.

- Abre el botón Resultados para ver tabla y gráfica.

- Puedes limpiar el laberinto o vaciar los resultados.


### 🧰 Requisitos

- Java 17+

- Swing para la interfaz gráfica (UI)

- JFreeChart para visualización gráfica

- Maven para gestión de dependencias

- CSV para almacenamiento de resultados

## 🖥️ Interfaz de Usuario
- Selección de modo: Start, End, Wall

- Elección del algoritmo desde JComboBox

- Botones: Resolver, Paso a paso, Limpiar, Resultados

- Visualización gráfica del laberinto

- Tabla de estadísticas de ejecuciones anteriores

- Gráfica de tiempos (línea roja, fondo gris, títulos estilizados)

### ▶️ Cómo correr el proyecto

1. Si es Maven, asegúrate de incluir JFreeChart en el `pom.xml`:

    ```xml
    <dependencies>
        <dependency>
            <groupId>org.jfree</groupId>
            <artifactId>jfreechart</artifactId>
            <version>1.5.4</version>
        </dependency>
    </dependencies>
    ```

2. Ejecuta desde tu IDE (como IntelliJ, NetBeans o Eclipse) o compila por terminal:

    ```bash
    javac -cp ".;ruta/jfreechart.jar" Main.java
    java -cp ".;ruta/jfreechart.jar" Main
    ```

> Reemplaza `ruta/jfreechart.jar` con la ubicación real del `.jar` si no usas Maven.

---

## 🖱️ Funcionalidades en la Interfaz

1. **Diseñar Laberinto**: Clic en los botones `Start`, `End`, `Wall` para definir las celdas.
2. **Seleccionar Algoritmo**: Usar el combo de selección.
3. **Botón Resolver**: Ejecuta el algoritmo y pinta el recorrido final.
4. **Paso a Paso**: Muestra las celdas visitadas y camino uno por uno.
5. **Ver Resultados Guardados**: Abre una ventana con las ejecuciones anteriores.
6. **Graficar Resultados**: Muestra un gráfico comparativo de los tiempos.
7. **Limpiar Resultados**: Borra el historial de ejecuciones.

---

## 📈 Ejemplo de Ejecución en Tabla

```plaintext
[INFO] Algoritmo: DFS
[INFO] Celdas visitadas: 28
[INFO] Longitud del camino: 16
[INFO] Tiempo de ejecución: 245800 ns
```

## 📸 Capturas
<img width="975" height="923" alt="image" src="https://github.com/user-attachments/assets/ed55ca58-2dcb-4c19-b403-f2ecc34e02e0" />
<img width="975" height="922" alt="image2" src="https://github.com/user-attachments/assets/1ca32b88-0736-4543-9907-c7ab9e57594e" />


## ✅ Conclusiones

Victoria:

Durante el desarrollo del programa y a partir de los resultados obtenidos, el algoritmo BFS(Breadth-First-Search) demostró ser el más óptimo, ya que encuentra la ruta más corta. Esto se da, debido a que BFS explora laberintos por niveles, lo que garantiza que se descubra el destino en la menor cantidad de pasos posibles si las celdas tienen igual peso.
Por otro lado:
DFS (Depth-First Search) y los algoritmos recursivos suelen encontrar rutas más largas o subóptimas, ya que profundizan en una sola rama antes de explorar otras opciones. DFS es rápido en laberintos pequeños, pero no garantiza el camino más corto.
MazeSolverRecursivo y RecursivoCompleto funcionan bien como modelos didácticos. Sin embargo, carecen de mecanismos para evitar caminos largos o repetidos.
MazeSolverRecursivoCompletoBT (Backtracking) mejora la calidad de la ruta al deshacer decisiones erróneas, pero a costa de mayor uso de memoria y tiempo, especialmente en laberintos grandes.

Isabel:

Conclusión:
En mi opinión el algoritmo de Backtracking es el más óptimo porque explora todas las posibles rutas hasta encontrar la mejor. Aunque puede demorar más en algunos casos, garantiza que se prueban todos los caminos, por lo que siempre encuentra una solución si existe. Es muy útil cuando queremos estar seguros de que estamos tomando el mejor camino posible en laberintos complejos.

## 💬 Recomendaciones

Es importante entender bien cómo funciona cada algoritmo (solver) antes de implementarlo. Se recomienda probarlos primero en papel o en un laberinto pequeño para comprobar cómo avanzan paso a paso. Además, organizar el código usando el patrón MVC ayuda mucho a mantener todo claro y fácil de modificar.
