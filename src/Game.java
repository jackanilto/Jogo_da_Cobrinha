import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable, KeyListener {
    public Node[] nodeSnake = new  Node[50];

    public boolean left, right, up, down;

    // Set o posissionamento do objeto na tela
    public Game(){
        this.setPreferredSize(new Dimension(480,480));
        for(int i = 0; i < nodeSnake.length; i++) {
            nodeSnake[i] = new Node(10,10);
        }
        this.addKeyListener(this);
    }

    public void tick(){
        // Implementa o movimento do rabo da cobra acompanahndo o corpo
        for (int i = nodeSnake.length  - 1; i > 0; i--){
            nodeSnake[i].x = nodeSnake[i-1].x;
            nodeSnake[i].y = nodeSnake[i-1].y;
        }

        // Implementa movimento
        if (right) {
            nodeSnake[0].x++;
        }else if (up){
            nodeSnake[0].y--;

        }else if (left){
            nodeSnake[0].x--;

        }else if (down){
            nodeSnake[0].y++;

        }

    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        // Set a cor e dimensão do objeto na tela
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,480,480);
        for (int i = 0; i < nodeSnake.length; i++) {
            g.setColor(Color.BLUE);
            g.fillRect(nodeSnake[i].x, nodeSnake[i].y,50,50);
        }
        g.dispose();
        bs.show();

    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame("Snake Game");
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(game).start();
    }

    @Override
    public void run(){
        while(true){
            tick();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    // Posso usar uma array para mapear estas teclas de movimentação
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = true;
            left = false;
            up = false;
            down = false;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
            right = false;
            down = false;
            up = false;
        }else if(e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
            right = false;
            left = false;
            down = false;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
            right = false;
            left = false;
            up = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
