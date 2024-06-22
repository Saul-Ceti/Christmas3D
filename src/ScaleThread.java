public class ScaleThread extends Thread{

    double currentS = 1;
    double step;
    double scale;
    boolean repeat;
    public ScaleThread(double scale, boolean repeat){
        this.scale = scale;
        this.step = Math.abs((this.scale - 1) / 100);
        this.repeat = repeat;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10);
                updateScale();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateScale(){
        if (repeat) {
            if (scale > 1){
                if (currentS > scale || currentS < 1) {
                    step *= -1; // Invertir el paso para cambiar la dirección
                }
                currentS += step;
            } else {
                if (currentS < scale || currentS > 1) {
                    step *= -1;
                }
                currentS -= step;
            }
        }else {
            if (Math.abs(currentS - scale) > step) {

                if (scale > 1) {
                    currentS += step;
                } else {
                    currentS -= step;
                }
            }
        }
    }

    public double getCurrentS(){
        return currentS;
    }

}
