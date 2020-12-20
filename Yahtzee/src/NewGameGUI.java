import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class NewGameGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 300;

    public static final int HEIGHT = 300;

    private TicTacToe game;
    public NewGameGUI(TicTacToe game){
        this.game = game;

    
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Tic Tac Toe");
    }

    public void actionPerformed(ActionEvent e){
        
    }
}