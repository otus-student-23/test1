public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    synchronized (Main.class) {
                        System.out.println(Thread.currentThread().getName());
                        Main.class.notifyAll();
                        try {
                            Main.class.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();
        }
    }
}