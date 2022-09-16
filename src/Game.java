import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {
    // Seta o tamanho da cobrinha
    public Node[] nodeSnake = new  Node[35];

    public boolean left, right, up, down;

    public int score = 0;
    // Criando a maçã
    public int macaX = 0, macaY = 0;
    // Velocidade da cobrinha de acordo com os pontos coletados
    public int speed = 1;

    // Set o posissionamento do objeto na tela
    public Game(){
        this.setPreferredSize(new Dimension(480,480));
        for(int i = 0; i < nodeSnake.length; i++) {
            nodeSnake[i] = new Node(0,0);
        }
        this.addKeyListener(this);
    }

    public void tick() {
        // Implementa o movimento do rabo da cobra acompanhado o corpo

        for (int i = nodeSnake.length - 1; i > 0; i--) {
            nodeSnake[i].x = nodeSnake[i - 1].x;
            nodeSnake[i].y = nodeSnake[i - 1].y;
        }

        if (nodeSnake[0].x + 10 < 0) {
            nodeSnake[0].x = 480;
        } else if (nodeSnake[0].x >= 480) {
            nodeSnake[0].x = -10;
        }
        if (nodeSnake[0].y + 10 < 0) {
            nodeSnake[0].y = 240;
        } else if (nodeSnake[0].y >= 480) {
            nodeSnake[0].y = -10;
        }

        if (right) {
            nodeSnake[0].x += speed;
        } else if (up) {
            nodeSnake[0].y -= speed;
        } else if (down) {
            nodeSnake[0].y += speed;
        } else if (left) {
            nodeSnake[0].x -= speed;
        }

        if (new Rectangle(nodeSnake[0].x, nodeSnake[0].y, 10, 10).intersects(new Rectangle(macaX, macaY, 10, 10))) {
            macaX = new Random().nextInt(480 - 10);
            macaY = new Random().nextInt(480 - 10);
            score++;
            speed++;
            System.out.println("Pontos: " + score);

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
            g.fillRect(nodeSnake[i].x, nodeSnake[i].y,10,10);

        }
        // Colocando a Maçã na tela ( Cor e Tamanho )
        g.setColor(Color.red);
        g.fillRect(macaX, macaY, 10, 10);

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
