import views.MazeFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //corre la ui
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MazeFrame();
            }
        });
    }
}