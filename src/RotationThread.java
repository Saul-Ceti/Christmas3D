public class RotationThread extends Thread {

    double angle, currentAngle = 0;
    boolean repeat;

    public RotationThread(double angle, boolean repeat) {
        this.angle = angle;
        this.repeat = repeat;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
                updateRotation();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateRotation() {
        currentAngle += angle;
        if (currentAngle >= 360) {
            currentAngle -= 360;
        } else if (currentAngle <= -360) {
            currentAngle += 360;
        }

        if (!repeat && Math.abs(currentAngle - angle) < 0.01) {
            Thread.currentThread().interrupt();
        }
    }

    public double getCurrentAngle() {
        return currentAngle;
    }
}
