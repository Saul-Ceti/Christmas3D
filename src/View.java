import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class View extends JFrame implements Runnable, MouseListener, ActionListener, MouseMotionListener {
    private BufferedImage background;
    public BufferedImage buffer;
    public MyGraphics mgM, mgB;
    public Thread thread;
    public ScaleThread scalePrismFill, scaleCube;
    public TranslationThread translateGift, translateTree, translateWood, translateFloor;
    public RotationThread rotateTrain, rotateStar, rotateGifts;
    public Shapes shape;
    public int xPressed, yPressed;
    public int xDirInit, yDirInit;
    public enum Axis {X, Y, Z, noRotation}
    private Timer timerGeneral;
    private int tiempoTranscurrido = 0;

    public View() {
        setTitle("Árbol de navidad 3D");
        setResizable(false);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        initializeTimers();
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

        //--------------------------------- ANIMACIONES ---------------------------------//
        //-------------------------------- ESCALACIONES ---------------------------------//
        scalePrismFill = new ScaleThread(.5, true);
        scalePrismFill.start();

        scaleCube = new ScaleThread(2, false);
        scaleCube.start();

        //--------------------------------- TRANSLACIONES ---------------------------------//
        translateGift = new TranslationThread(new int[]{400, 650, 0}, new int[]{400, 1000, 0}, false);
        translateGift.start();

        translateTree = new TranslationThread(new int[]{400, -300, 0}, new int[]{400, 1800, 0}, false);
        translateTree.start();

        translateWood = new TranslationThread(new int[]{400, 400, 0}, new int[]{400, 2120, 0}, false);
        translateWood.start();

        translateFloor = new TranslationThread(new int[]{400, 620, 0}, new int[]{400, 1900, 0}, false);
        translateFloor.start();

        //--------------------------------- ROTACIONES ---------------------------------//
        rotateTrain = new RotationThread(1, true);
        rotateTrain.start();

        rotateStar = new RotationThread(1, true);
        rotateStar.start();

        rotateGifts = new RotationThread(1, true);
        rotateGifts.start();
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
        boolean filled = true;

        // === Inicio de la sección para dibujar los objetos iniciales ===
        // Casa
        house(translateFloor.getX(), translateFloor.getY(), translateFloor.getZ(), 1, 110, noPivot, Axis.X, filled, false);

        // Árbol de navidad
        christmasTree(translateTree.getX(), translateTree.getY(), translateTree.getZ(), 1, 110, noPivot, Axis.X, filled, false);

        // Regalos
        gifts(translateGift.getX(), translateGift.getY(), translateGift.getZ(), 1, 110, noPivot, Axis.X, filled, false);
        // === Fin de la sección para dibujar los objetos iniciales ===

        g.drawImage(buffer, 0, 0, this);
    }

    //--------------------------------- TIMER ---------------------------------//
    private void initializeTimers() {
        // Aquí se dibujan los objetos que en el transcurso del tiempo se van a ir agregando
        timerGeneral = new Timer(1000, e -> {
            tiempoTranscurrido++;

            if (tiempoTranscurrido == 10) {
                addStar();
            }
            if (tiempoTranscurrido == 20) {
                // Agregar luces navideñas
            }
            if (tiempoTranscurrido == 30) {
                // Agregar tren
            }
            if (tiempoTranscurrido == 40) {
                // Nieve cayendo por la ventana
            }
            if (tiempoTranscurrido == 50) {
                // Agregar estrella
            }
            if (tiempoTranscurrido == 55) {
                // Hacer rotar la estrella y hacer que brille
            }
        });
        timerGeneral.start();
    }

    //--------------------------------- MÉTODOS ---------------------------------//
    private void addStar() {
        // TODO: Tengo que hacer que la estrella aparezca en la cima del árbol y que se pinte bien
        //mgM.polygonOblique(shape.verticesStar3D(100, 100), shape.sidesStar3D(), 400, 400, 0, 1, 110, new int[]{0, 0, 0}, Axis.X, CustomColors.GOLD, CustomColors.GOLD, true, false);
    }

    private void setRotateStar() {
        // TODO: Hacer que rote y brille la estrella
    }

    private void addLights() {
        // TODO: Luces navideñas en todo el árbol
    }

    private void addSnow() {
        // TODO: Terminar de dibujar las ventanas en el cuarto junto a una chimenea y hacer que la nieve caiga
    }

    private void addTrain() {
        // TODO: Método de Jorge
    }

    public void house(int x, int y, int z, double scale, double angle, int[] pivot, Axis axis, boolean isFill, boolean pivotIsCenter) {
        mgM.polygonOblique(shape.verticesRectangularPrism(2000, 2000, 4000), shape.sidesRectangularPrism(),translateFloor.getX(), translateFloor.getY() - 100, translateFloor.getZ(), 1, 110, pivot, Axis.X, CustomColors.DARK_BROWN, CustomColors.DARK_BROWN, isFill, false);
        mgM.polygonOblique(shape.verticesRectangularPrism(2000, 1900, 100), shape.sidesRectangularPrism(), translateFloor.getX(), translateFloor.getY(), translateFloor.getZ(), 1, 110, pivot, Axis.X, CustomColors.BROWN, CustomColors.WOOD, isFill, false);
    }

    public void christmasTree(int x, int y, int z, double scale, double angle, int[] pivot, Axis axis, boolean isFill, boolean pivotIsCenter) {
        mgM.polygonOblique(shape.verticesHexagonalPrism(150, 400), shape.sidesHexagonalPrism(), translateWood.getX(), translateWood.getY(), translateWood.getZ(), 1, 110, pivot, Axis.X, CustomColors.DARK_BROWN, CustomColors.BROWN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(400, 1500), shape.sidesHexagonalPyramid(), x, y, z, scale, 110, pivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(400, 1400), shape.sidesHexagonalPyramid(), x, y - 100, z, scale, 110, pivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(350, 1200), shape.sidesHexagonalPyramid(), x, y - 300, z, scale, 110, pivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(300, 1000), shape.sidesHexagonalPyramid(), x, y - 500, z, scale, 110, pivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(200, 700), shape.sidesHexagonalPyramid(), x, y - 800, z, scale, 110, pivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(130, 500), shape.sidesHexagonalPyramid(), x, y - 1000, z, scale, 110, pivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
        mgM.polygonOblique(shape.verticesHexagonalPyramid(60, 300), shape.sidesHexagonalPyramid(), x, y - 1200, z, scale, 110, pivot, Axis.X, CustomColors.DARK_GREEN, CustomColors.GREEN, true, false);
    }

    public void gifts(int x, int y, int z, double scale, double angle, int[] pivot, Axis axis, boolean isFill, boolean pivotIsCenter) {
        // TODO: Tengo que hacerles decoración y que estén mas dispersos
        mgM.polygonOblique(shape.verticesRectangularPrism(50, 20, 80), shape.sidesRectangularPrism(), x - 300, y, z, scaleCube.currentS, 20, pivot, Axis.X, Color.BLACK, CustomColors.GOLD, true, false);
//        mgM.polygonOblique(shape.verticesRectangularPrism(50, 50, 50), shape.sidesRectangularPrism(), x + 250, y, z - 10, scaleCube.currentS, 70, pivot, Axis.X, Color.BLACK, CustomColors.PURPLE, true, false);
//        mgM.polygonOblique(shape.verticesRectangularPrism(50, 50, 50), shape.sidesRectangularPrism(), x - 200, y, z + 10, scaleCube.currentS, 70, pivot, Axis.X, Color.BLACK, CustomColors.DARK_RED, true, false);
//        mgM.polygonOblique(shape.verticesRectangularPrism(50, 50, 50), shape.sidesRectangularPrism(), x - 200, y, z - 10, scaleCube.currentS, 70, pivot, Axis.X, Color.BLACK, CustomColors.DARK_RED, true, false);
//        mgM.polygonOblique(shape.verticesRectangularPrism(50, 50, 100), shape.sidesRectangularPrism(), x + 250, y, z, scaleCube.currentS, 10, pivot, Axis.Y, Color.BLACK, CustomColors.GOLD, true, false);
//        mgM.polygonOblique(shape.verticesRectangularPrism(50, 50, 50), shape.sidesRectangularPrism(), x, y, z, scaleCube.currentS, 10, pivot, Axis.Y, Color.BLACK, CustomColors.DARK_BLUE, true, false);
//        mgM.polygonOblique(shape.verticesRectangularPrism(50, 50, 50), shape.sidesRectangularPrism(), x - 200, y, z + 50, scaleCube.currentS, 10, pivot, Axis.Y, Color.BLACK, CustomColors.PURPLE, true, false);
//        mgM.polygonOblique(shape.verticesRectangularPrism(50, 50, 50), shape.sidesRectangularPrism(), x + 250, y, z + 50, scaleCube.currentS, 10, pivot, Axis.Y, Color.BLACK, CustomColors.DARK_RED, true, false);
    }

    // === Inicio de la sección de métodos para los movimientos de mouse ===
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
    // === Fin de la sección de métodos para los movimientos de mouse ===

    //--------------------------------- MAIN ---------------------------------//
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
}
