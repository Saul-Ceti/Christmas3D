public class TranslationThread extends Thread {

    int[] coordinates, initialPoints, finalPoints;
    boolean repeat;
    boolean movingToFinal;

    public TranslationThread(int[] initialPoints, int[] finalPoints, boolean repeat) {
        this.coordinates = initialPoints.clone();
        this.initialPoints = initialPoints.clone();
        this.finalPoints = finalPoints.clone();
        this.repeat = repeat;
        this.movingToFinal = true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(70);
                updatePosition();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updatePosition() {
        int[] targetPoints = movingToFinal ? finalPoints : initialPoints;
        int dx = targetPoints[0] - coordinates[0];
        int dy = targetPoints[1] - coordinates[1];
        int dz = targetPoints[2] - coordinates[2];

        int maxDxy = Math.max(Math.abs(dx), Math.abs(dy));
        int maxD = Math.max(Math.abs(maxDxy), Math.abs(dz));

        if (maxD != 0) {  // Prevent division by zero
            double stepX = (double) dx / maxD;
            double stepY = (double) dy / maxD;
            double stepZ = (double) dz / maxD;

            coordinates[0] += (int) Math.signum(stepX);
            coordinates[1] += (int) Math.signum(stepY);
            coordinates[2] += (int) Math.signum(stepZ);
        }

        // Check if the coordinates have reached the target points
        if (coordinates[0] == targetPoints[0] && coordinates[1] == targetPoints[1] && coordinates[2] == targetPoints[2]) {
            if (repeat) {
                movingToFinal = !movingToFinal; // Switch direction
            } else {
                // If repeat is false, stop the thread
                Thread.currentThread().interrupt();
            }
        }

    }

    public int[] getCoordinates(){
        return coordinates;
    }
    public int getX(){
        return coordinates[0];
    }
    public int getY(){
        return coordinates[1];
    }
    public int getZ(){
        return coordinates[2];
    }

}
