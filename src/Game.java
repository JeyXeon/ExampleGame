import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Game extends Canvas implements Runnable {
    public static int WIDTH = 400; //ширина
    public static int HEIGHT = 300; //высота
    public static String NAME = "TUTORIAL 1"; //заголовок окна
    public static Sprite hero;
    private static final long serialVersionUID = 1L;
    private boolean running;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private static int x = 0;
    private static int y = 0;

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        long delta;

        init();

        while(running) {
            delta = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            update(delta);
            render();
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); //получаем Graphics из созданной нами BufferStrategy
        g.setColor(Color.black); //выбрать цвет
        g.fillRect(0, 0, getWidth(), getHeight()); //заполнить прямоугольник
        hero.draw(g, x, y);
        g.dispose();
        bs.show(); //показать
    }

    public void update(long delta) {
        if (leftPressed) {
            x--;
        }
        if (rightPressed) {
            x++;
        }
        if (downPressed) {
            y++;
        }
        if (upPressed) {
            y--;
        }
    }

    public void init() {
        hero = getSprite("image.png");
        addKeyListener(new KeyInputHandler());
    }

    public void start(){
        running = true;
        new Thread(this).start();
    }

    public Sprite getSprite(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            assert url != null;
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert sourceImage != null;
        return new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
    }

    public static void main(String[] args) {

        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JFrame frame = new JFrame(Game.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выход из приложения по нажатию клавиши ESC
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER); //добавляем холст на наш фрейм
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        game.start();

    }

    public class KeyInputHandler extends KeyAdapter {

        public void keyPressed(KeyEvent e) { //клавиша нажата
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
        }
        public void keyReleased(KeyEvent e) { //клавиша отпущена
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
        }

    }

}
