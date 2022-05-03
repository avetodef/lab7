package commands;

public class AutoUpdate implements Runnable {
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("autoupdate: " + e.getMessage());
            }
            //TODO сделать апдейт
            System.out.println("collection updated");
        }
    }
    public static void main(String[] args) {
        new Thread(new AutoUpdate()).start();
    }

}
