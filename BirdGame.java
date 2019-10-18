
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Font;

public class BirdGame extends JPanel {
    /*Add birds to the interface*/
    public Bird bird;
    /*Add the left column of the two columns to the interface.*/
    public obstacle column1;
    /*Add the right column of the two columns to the interface.*/
    public obstacle column2;

    /*Main interface background image*/
    public BufferedImage background;

    /*scores*/
    public int score;

    /*游戏结束*/
    public boolean gameOver;//游戏是否结束
    public BufferedImage gameOverImage;//游戏结束画面


    /*Initialize the property variables of BirdGame*/
    public BirdGame() throws Exception {
        bird = new Bird();
        column1 = new obstacle(1);
        column2 = new obstacle(2);
        /*Initialize the game background image*/
        background = ImageIO.read(getClass().getResource("UMC background.jpg"));

        /*初始化游戏结束*/
        gameOver=false;
        gameOverImage= ImageIO.read(getClass().getResource("gameover.png"));
    }

    /*Rewrite the paint method, draw the interface*/
    public void paint(Graphics g) {
        /*Drawing background*/
        g.drawImage(background, 0, 0, null);
        /*Drawing a column*/
        g.drawImage(column1.image, column1.x - column1.width / 2, column1.y - column1.height / 2+200, null);
        g.drawImage(column1.image1, column1.x - column1.width / 2, column1.y - column1.height / 2-200, null);
        g.drawImage(column2.image, column2.x - column2.width / 2, column2.y - column2.height / 2+200, null);
        g.drawImage(column2.image1, column2.x - column2.width / 2, column2.y - column2.height / 2-200, null);
        /*Achieve the bird drop */
        Graphics2D g2=(Graphics2D) g;
        g2.rotate(-bird.alpha, bird.x, bird.y);
        /*Draw a bird*/
        g.drawImage(bird.image, bird.x - bird.width/2, bird.y - bird.height/2, null);
        g2.rotate(bird.alpha, bird.x, bird.y);

        /*draw the score*/
        Font f=new Font(Font.SANS_SERIF, Font.BOLD, 40);
        g.setFont(f);
        g.drawString(""+score, 40, 60);
        g.setColor(Color.WHITE);
        g.drawString(""+score, 40-3, 60-3);

        /*游戏结束处理逻辑*/
        if(gameOver) {
            g.drawImage(gameOverImage, 30, 110, null);
        }
    }

    /*Add action method to achieve the effect of the interface object*/
    public void action() throws Exception {
        while(true) {
            if(!gameOver) {
                /*修改柱子位置*/
                column1.step();
                column2.step();
                /*修改鸟的位置(鸟的上抛)*/
                bird.step();
                /*实现鸟的振翅*/
                bird.fly();
            }
            /*碰撞地面*/
            /*碰撞地面和柱子*/
            if(bird.hit(485) || bird.hit(column1) || bird.hit(column2)) {
                gameOver=true;
            }
            /*Modify the position of the column*/
            column1.step();
            column2.step();
            /*Modify the position of the bird (the bird's throw)*/
            bird.step();
            /*Realize the wings of birds*/
            bird.fly();
            /*Bind mouse to listen for events*/
            MouseListener listener = new MouseAdapter() {
                /*click the mouse*/
                public void mousePressed(MouseEvent e) {
                    bird.flappy();
                }
            };
            /*put the listener on the BirdGame*/
            addMouseListener(listener);

            /*the logic of calculate score*/
            if((bird.x-bird.width/2) ==(column1.x+ column1.width/2 ) || (bird.x-bird.width/2)==(column2.x+ column2.width/2 )) {
                score++;
            }

            /*Redraw the interface*/
            repaint();
            /*Set the screen refresh rate to 1 second 30 times*/
            Thread.sleep(1000/30);
        }
    }

    /*Method of starting the software*/
    public static void main(String[] args) throws Exception {
        /*Initialize an operation window*/
        JFrame frame = new JFrame();
        /*Initialize the game main panel*/
        BirdGame game = new BirdGame();
        /*Add the game main interface panel to the action window*/
        frame.add(game);
        /*Set the main interface to be wide and high*/
        frame.setSize(470, 485);
        /*Let the main interface be centered on the computer screen*/
        frame.setLocationRelativeTo(null);
        /*Set the window close mode, click the window to close the x number, and the java program will automatically end the run.*/
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*Settings window shows visible*/
        frame.setVisible(true);
        /*action*/
        game.action();
    }
}