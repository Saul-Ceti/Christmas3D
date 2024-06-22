import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Queue;
import java.util.*;

import static java.lang.Math.*;

public class MyGraphics {
    View view;
    BufferedImage buffer;
    int pixelWidth = 1;
    int[] director;

    public MyGraphics(View view, BufferedImage buffer){
        this.view = view;
        this.buffer = buffer;
        this.director = new int[]{1, 1, 1};
    }

    public void setPixelWidth(int pixelWidth){
        this.pixelWidth = pixelWidth;
    }

    public void putPixel(int x, int y, Color a) {
        if (x < view.getWidth() && y < view.getHeight() && x >= 0 && y >= 0) {
            if (pixelWidth <= 1) {
                buffer.setRGB(x, y, a.getRGB());
            } else {
                int halfWidth = pixelWidth / 2;
                for (int i = x - halfWidth; i <= x + halfWidth; i++) {
                    for (int j = y - halfWidth; j <= y + halfWidth; j++) {
                        if (i >= 0 && i < view.getWidth() && j >= 0 && j < view.getHeight()) {
                            buffer.setRGB(i, j, a.getRGB());
                        }
                    }
                }
            }
        }
    }

    //--------------------------LINE-------------------------//
    public void lineBresenham(int x1, int y1, int x2, int y2, Color a) {
        int dy = y2 - y1;
        int dx = x2 - x1;
        int x = x1, y = y1, p, incX = 1, incY = 1;
        double m = (double) dy / dx;

        if (dy < 0) {
            dy = -dy;
            incY = -1;
        }
        if (dx < 0) {
            dx = -dx;
            incX = -1;
        }

        if (dx > dy) {
            p = 2 * dy - dx;
            for (int i = 0; i <= dx; i++) {
                if (p >= 0) {
                    y += incY;
                    p += 2 * (dy - dx);
                } else {
                    p += 2 * dy;
                }
                x += incX;
                putPixel(x, y, a);
            }
        } else {
            p = 2 * dx - dy;
            for (int i = 0; i <= dy; i++) {
                if (p >= 0) {
                    x += incX;
                    p += 2 * (dx - dy);
                } else {
                    p += 2 * dx;
                }
                y += incY;
                putPixel(x, y, a);
            }
        }
    }

    //-------------------------CIRCLE---------------------------//
    public void circleBasic(int xc, int yc, int r, Color a) {
        int x1 = xc - r;
        int x2 = xc + r;
        for (int x = x1; x <= x2; x++) {
            double ya = yc + Math.sqrt(Math.pow(r, 2) - Math.pow((x - xc), 2));
            double yb = yc - Math.sqrt(Math.pow(r, 2) - Math.pow((x - xc), 2));
            putPixel(x, (int) ya, a);
            putPixel(x, (int) yb, a);
        }
        int y1 = yc - r;
        int y2 = yc + r;
        for (int y = y1; y <= y2; y++) {
            double xa = xc + Math.sqrt(Math.pow(r, 2) - Math.pow((y - yc), 2));
            double xb = xc - Math.sqrt(Math.pow(r, 2) - Math.pow((y - yc), 2));
            putPixel((int) xa, y, a);
            putPixel((int) xb, y, a);
        }
    }

    //------------------------------POLYGON-----------------------------//
    public void drawPolygon(int[] x, int[] y, Color a) {
        for (int i = 0; i < x.length - 1; i++) {
            lineBresenham(x[i], y[i], x[i + 1], y[i + 1], a);
        }
        lineBresenham(x[0], y[0], x[x.length - 1], y[x.length - 1], a);
    }

    //--------------------------------CUBE------------------------------//
    public void cube(int xo, int yo, int zo, int width, int height, int depth, double scale, Color color){

        int[] x = {xo, xo, xo+width, xo+width, xo, xo, xo+width, xo+width};
        int[] y = {yo, yo, yo, yo, yo+height, yo+height, yo+height, yo+height};
        int[] z = {zo, zo+depth, zo+depth, zo, zo, zo+depth, zo+depth, zo};

        double[] uList = new double[z.length];
        for (int i = 0; i < uList.length; i++){
            uList[i] = (double) -z[i] / director[2];
        }

        int[] xResult1 = new int[4];
        int[] yResult1 = new int[4];
        for (int i = 0; i < 4; i++){
            xResult1[i] = (int) Math.round(((x[i] + (director[0] * uList[i])) * scale) + x[0]);
            yResult1[i] = (int) Math.round(((y[i] + (director[1] * uList[i])) * scale) + y[0]);
        }

        int[] xResult2 = new int[4];
        int[] yResult2 = new int[4];
        for (int i = 0; i < 4; i++){
            xResult2[i] = (int) Math.round(((x[i+4] + (director[0] * uList[i+4])) * scale) + x[0]);
            yResult2[i] = (int) Math.round(((y[i+4] + (director[1] * uList[i+4])) * scale) + y[0]);
        }

        drawPolygon(xResult1, yResult1, color);
        drawPolygon(xResult2, yResult2, color);

        for (int i = 0; i < 4; i++){
            lineBresenham(xResult1[i], yResult1[i], xResult2[i], yResult2[i], color);
        }

    }

    //------------------------------POLYGON 3D------------------------------//
    public void polygonOblique(int[][] vertices, int[][] caras, int x, int y, int z, double scale, double angle, int[] pivot, View.Axis axis, Color lineColor, Color fillColor, boolean isFill, boolean pivotIsCenter) {
        int numVertices = vertices.length;

        int[] xProjected = new int[numVertices];
        int[] yProjected = new int[numVertices];

        // Obtener el ángulo de rotación actual
        double angleRadians = Math.toRadians(angle);

        int pivotX;
        int pivotY;
        int pivotZ;
        int[][] rotatedVertices = new int[numVertices][3];

        // Rotar los vértices en torno al pivote
        if (pivotIsCenter) {
            int[] centerPoints = calculateCentroid(vertices);
            pivotX = centerPoints[0];
            pivotY = centerPoints[1];
            pivotZ = centerPoints[2];
        } else {
            pivotX = pivot[0];
            pivotY = pivot[1];
            pivotZ = pivot[2];
        }

        for (int i = 0; i < numVertices; i++) {
            int[] v = vertices[i];
            int[] rv = rotateAroundPivot(v, angleRadians, pivotX, pivotY, pivotZ, axis);
            rotatedVertices[i] = rv;
        }

        // Proyección de los vértices usando el vector director
        for (int i = 0; i < numVertices; i++) {
            double u = (double) -rotatedVertices[i][2] / director[2];
            double projectedX = rotatedVertices[i][0] + director[0] * u;
            double projectedY = rotatedVertices[i][1] + director[1] * u;

            // Aplicar la proyección oblicua para obtener las coordenadas 2D
            double obliqueX = projectedX + (z * Math.cos(Math.toRadians(45)));
            double obliqueY = projectedY + (z * Math.sin(Math.toRadians(45)));

            xProjected[i] = (int) Math.round(obliqueX * scale) + x;
            yProjected[i] = (int) Math.round(obliqueY * scale) + y;
        }

        // Dibujar los polígonos y llenar las caras
        for (int[] cara : caras) {
            int numVerticesCara = cara.length;
            int[] xCara = new int[numVerticesCara];
            int[] yCara = new int[numVerticesCara];

            for (int j = 0; j < numVerticesCara; j++) {
                int verticeIndex = cara[j];
                xCara[j] = xProjected[verticeIndex];
                yCara[j] = yProjected[verticeIndex];
            }

            if (isFill) {
                fillPolygonSL(xCara, yCara, fillColor);
            }
            drawPolygon(xCara, yCara, lineColor);
        }
    }

    private int[] rotateAroundPivot(int[] v, double angle, int pivotX, int pivotY, int pivotZ, View.Axis axis) {
        if (axis == View.Axis.noRotation) {
            // Si el eje es noRotation, devolver los vértices sin modificar
            return new int[]{v[0], v[1], v[2]};
        }

        // Trasladar el punto al origen
        int x = v[0] - pivotX;
        int y = v[1] - pivotY;
        int z = v[2] - pivotZ;

        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);

        int rotatedX = 0, rotatedY = 0, rotatedZ = 0;

        // Rotar según el eje especificado
        switch (axis) {
            case X:
                rotatedX = x;
                rotatedY = (int) (y * cosA - z * sinA);
                rotatedZ = (int) (y * sinA + z * cosA);
                break;
            case Y:
                rotatedX = (int) (x * cosA + z * sinA);
                rotatedY = y;
                rotatedZ = (int) (-x * sinA + z * cosA);
                break;
            case Z:
                rotatedX = (int) (x * cosA - y * sinA);
                rotatedY = (int) (x * sinA + y * cosA);
                rotatedZ = z;
                break;
            default:
                throw new IllegalArgumentException("Eje de rotación no válido. Usa 'X', 'Y' o 'Z'.");
        }

        // Trasladar el punto de regreso al pivote
        rotatedX += pivotX;
        rotatedY += pivotY;
        rotatedZ += pivotZ;

        return new int[]{rotatedX, rotatedY, rotatedZ};
    }

    public int[] calculateCentroid(int[][] vertices) {
        int sumX = 0, sumY = 0, sumZ = 0;
        int n = vertices.length;

        for (int[] vertex : vertices) {
            sumX += vertex[0];
            sumY += vertex[1];
            sumZ += vertex[2];
        }

        return new int[]{sumX / n, sumY / n, sumZ / n};
    }

    //------------------------------VARIOS------------------------------//
    public int[][] randomPoints(int n){
        int[][] points = new int[n][2];
        for (int i = 0; i < n ; i++){
            int x = (int) (Math.random() * (view.getWidth()-1)) + 1;
            int y = (int) (Math.random() * (view.getHeight()-1)) + 1;
            points[i][0] = x;
            points[i][1] = y;
        }
        return points;
    }

    public int[][] movePoints(int[][] points, Color color){
        int[] x = new int[points.length];
        int[] y = new int[points.length];
        for (int i = 0; i < points.length ; i++){
            x[i] = points[i][0];
            y[i] = points[i][1] + 1;
            if (y[i] < view.getHeight()){
                putPixel(x[i], y[i], color);
            }else{
                y[i] = 0;
            }
            points[i][0] = x[i];
            points[i][1] = y[i];
        }
        return points;
    }

    //------------------------------FILL POLYGON------------------------------//
    public void fillPolygonSL(int[] xPoints, int[] yPoints, Color fillColor) {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Encontrar el minY y maxY del polígono
        for (int y : yPoints) {
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        // Iterar por cada línea horizontal (scanline)
        for (int y = minY; y <= maxY; y++) {
            List<Integer> xIntersections = new ArrayList<>();

            // Encontrar intersecciones con la línea horizontal y
            for (int i = 0; i < xPoints.length; i++) {
                int startX = xPoints[i];
                int startY = yPoints[i];
                int endX = xPoints[(i + 1) % xPoints.length];
                int endY = yPoints[(i + 1) % yPoints.length];

                if ((startY <= y && endY >= y) || (endY <= y && startY >= y)) {
                    // Calcular la intersección x con la línea horizontal y
                    int dx = endX - startX;
                    int dy = endY - startY;

                    if (dy != 0) {
                        int x = startX + (y - startY) * dx / dy;
                        xIntersections.add(x);
                    } else if (startY == y) {
                        // Agregar el punto de inicio si está en la línea horizontal
                        xIntersections.add(startX);
                    }
                }
            }

            // Ordenar las intersecciones
            Collections.sort(xIntersections);

            // Dibujar las líneas entre pares de intersecciones
            for (int i = 0; i < xIntersections.size() - 1; i += 2) {
                int startX = xIntersections.get(i);
                int endX = xIntersections.get(i + 1);
                lineBresenham(startX, y, endX, y, fillColor);
            }
        }
    }
}

