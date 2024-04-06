public class Main {

    private static final Class<Main> monitor = Main.class;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                synchronized (monitor) {
                    System.out.println("2: " + Thread.currentThread().getState());
                    monitor.notifyAll();
                    monitor.wait();
                    monitor.notifyAll();
                    monitor.wait(3000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("1: " + thread.getState());

        synchronized (monitor) {
            thread.start();
            monitor.wait();
            System.out.println("3: " + thread.getState());
            monitor.notifyAll();
            monitor.wait();
            System.out.println("4: " + thread.getState());
            monitor.notifyAll();
            System.out.println("5: " + thread.getState());
        }

        thread.join();
        System.out.println("6: " + thread.getState());
    }
}