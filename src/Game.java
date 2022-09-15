import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    public Node[] nodeSnake = new  Node[10];

    public Game(){
        this.setPreferredSize(new Dimension(480,480));
        for(int i = 0; i < nodeSnake.length; i++) {
            nodeSnake[i] = new Node(0,0);
        }
    }

    public void tick(){

    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,480,480);
        for (int i = 0; i < nodeSnake.length; i++) {
            g.setColor(Color.BLUE);
            g.fillRect(nodeSnake[i].x, nodeSnake[i].y,10,10);
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

}
