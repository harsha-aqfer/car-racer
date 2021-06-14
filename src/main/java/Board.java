import java.awt.EventQueue;
import javax.swing.*;

public class Board extends JFrame {

    private Board() {
        initUI();
    }

    private void initUI() {

        this.add(new RoadFighter());

        this.setResizable(false);
        this.pack();

        this.setTitle("RaceTrack");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Board().setVisible(true);
            }
        });
    }
}
