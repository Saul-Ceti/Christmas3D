import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class View extends JFrame implements Runnable, MouseListener, ActionListener, MouseMotionListener {
    private BufferedImage background;
    public BufferedImage buffer;
    public MyGraphics mgM, mgB;
    public Thread thread;
    public ScaleThread scalePrism, scalePrismFill, scaleCube;
    public TranslationThread translateCube, translatePrismFill, translateWood, translateFloor;
    public RotationThread rotatePrism;
    public Shapes shape;
    public int xPressed, yPressed;
    public int xDirInit, yDirInit;
    public enum Axis {X, Y, Z, noRotation}

    public View() {
        setTitle("Árbol de navidad 3D");
        setResizable(false);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                mgM.director[2] += notches;
                repaint();
            }
        });

        setVisible(true);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        mgM = new MyGraphics(this, buffer);
        shape = new Shapes();

        thread = new Thread(this);
        thread.start();

        scalePrism = new ScaleThread(2, true);
        scalePrism.start();

        scalePrismFill = new ScaleThread(.5, true);
        scalePrismFill.start();

        scaleCube = new ScaleThread(2.5, false);
        scaleCube.start();

        translateCube = new TranslationThread(new int[]{100, 100, 100}, new int[]{400, 400, 200}, true);
        translateCube.start();

        translatePrismFill = new TranslationThread(new int[]{400, -300, 0}, new int[]{400, 1800, 0}, false);
        translatePrismFill.start();

        translateWood = new TranslationThread(new int[]{400, 400, 0}, new int[]{400, 2120, 0}, false);
        translateWood.start();

        translateFloor = new TranslationThread(new int[]{400, 620, 0}, new int[]{400, 1900, 0}, false);
        translateFloor.start();

        rotatePrism = new RotationThread(1, true);
        rotatePrism.start();

    }

    @Override
    public void paint(Graphics g) {
        if (background == null) {
            background = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            mgB = new MyGraphics(this, background);
            Graphics2D gBack = background.createGraphics();
            gBack.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gBack.setClip(0, 0, getWidth(), getHeight());
            gBack.setColor(Color.black);
            gBack.fillRect(0, 0 , getWidth(), getHeight());

            gBack.dispose();
        }
        update(g);
    }

    public void update(Graphics g) {
        Graphics2D gBuffer = buffer.createGraphics();
        gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gBuffer.setClip(0, 0, getWidth(), getHeight());
        gBuffer.drawImage(background, 0, 0, this);

        int[] noPivot = {0,0,0};

        // Aquí se dibujan las figuras
        // Dibujar el piso
        mgM.polygonOblique(shape.verticesRectangularPrism(1800, 1800, 10), shape.sidesRectangularPrism(), translateFloor.getX(), translateFloor.getY(), translateFloor.getZ(), 1, 110, noPivot, Axis.X, CustomColors.BROWN, CustomColors.WOOD, true, false);

        // Tronco del árbol
        mgM.polygonOblique(shape.verticesHexagonalPrism(150, 400), shape.sidesHexagonalPrism(), translateWood.getX(), translateWood.getY(), translateWood.getZ(), 1, 110, noPivot, Axis.X, CustomColors.DARK_BROWN, CustomColors.BROWN, true, false);

        // Árbol de navidad
        mgM.polygonOblique(shape.verticesHexagonalPyramid(400, 1500), shape.sidesHexagonalPyramid(), translatePrismFill.getX(), translatePrismFill.getY(), translatePrismFill.getZ(), 1, 110, noPivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(400, 1400), shape.sidesHexagonalPyramid(), translatePrismFill.getX(), translatePrismFill.getY() - 100, translatePrismFill.getZ(), 1, 110, noPivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(350, 1200), shape.sidesHexagonalPyramid(), translatePrismFill.getX(), translatePrismFill.getY() - 300, translatePrismFill.getZ(), 1, 110, noPivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(300, 1000), shape.sidesHexagonalPyramid(), translatePrismFill.getX(), translatePrismFill.getY() - 500, translatePrismFill.getZ(), 1, 110, noPivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(200, 700), shape.sidesHexagonalPyramid(), translatePrismFill.getX(), translatePrismFill.getY() - 800, translatePrismFill.getZ(), 1, 110, noPivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(130, 500), shape.sidesHexagonalPyramid(), translatePrismFill.getX(), translatePrismFill.getY() - 1000, translatePrismFill.getZ(), 1, 110, noPivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(60, 300), shape.sidesHexagonalPyramid(), translatePrismFill.getX(), translatePrismFill.getY() - 1200, translatePrismFill.getZ(), 1, 110, noPivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);

        g.drawImage(buffer, 0, 0, this);


    }

    @Override
    public void run() {
        while (true) {
            try {
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("x = " + e.getX() + " | y = " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.xPressed = e.getX();
        this.yPressed = e.getY();

        this.xDirInit = mgM.director[0];
        this.yDirInit = mgM.director[1];
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        int xDirector = -(e.getX() - xPressed);
        int yDirector = -(e.getY() - yPressed);

        mgM.director[0] = xDirInit + xDirector;
        mgM.director[1] = yDirInit + yDirector;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
