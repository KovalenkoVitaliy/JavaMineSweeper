import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {
    private Game game;
    private JPanel jpanel;
    private JLabel jLabel;
    private final int IMAGE_SIZE = 50;
    private final int COLUMS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;

    public static void main(String[] args) {
        JavaSweeper javaSweeper = new JavaSweeper();
    }

    private JavaSweeper(){
        game = new Game(COLUMS,ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        intiLabel();
        initFrame();
    }

    private void intiLabel() {
        jLabel = new JLabel(getMessage());
        Font font = new Font("Tahoma", Font.BOLD, 18);
        jLabel.setFont(font);
        add(jLabel, BorderLayout.SOUTH);
    }

    private void initFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("MySweeperGame");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void initPanel() {
        jpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
//                g.drawLine(0,0,500,300);
                for (Coord coord: Ranges.getAllCoord()){
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x*IMAGE_SIZE,
                            coord.y*IMAGE_SIZE, this);
                }
            }
        };
        jpanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               int x = e.getX()/IMAGE_SIZE;
               int y = e.getY()/IMAGE_SIZE;
               Coord coord = new Coord(x,y);
               switch (e.getButton()) {
                   case MouseEvent.BUTTON1 : game.pressLeftButton(coord); break;
                   case MouseEvent.BUTTON3 : game.pressRightButton(coord); break;
                   case MouseEvent.BUTTON2: game.start(); break;
               }
               jLabel.setText(getMessage());
               jpanel.repaint();
            }
        });
        jpanel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,Ranges.getSize().y * IMAGE_SIZE));
        this.add(jpanel);
    }

    private void setImages(){
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
        setIconImage(getImage("icon"));
    }

    private Image getImage(String name){
        String filename = "img/" + name +".png";
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(filename));
        return imageIcon.getImage();
    }

    private String getMessage(){
        switch (game.getState()) {
            case BOMBED: return "Ba-Da-Boom! You Lose!";
            case WINNER: return "Congratulations! All bombs have been markerd";
            default:
                if (game.getTotalFlaged() == 0)
                    return "Welcome!";
                else
                    return "Think twice! Flagged " + game.getTotalFlaged() + " of " + game.getTotalBombs() + " bombs";

        }
    }
}
