import java.util.List;

public class HeapDumpTest {
    public static void main(String[] args) throws InterruptedException {
        //Run configuration:
        //  VM options: -Xmx200m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/ramdisk/heapdump.hprof
        //  Program arguments: 180 300

        //jmap -dump:live,format=b,file=/tmp/heapdump.bin 14616

        int count = Integer.parseInt(args[0]);
        int waitTime = Integer.parseInt(args[1]);
        System.out.println("START");

        List<String> products = new ProductCatalog().getProducts(count);

        System.out.println("sleep...");
        Thread.sleep(waitTime * 1000L);
        System.out.println("DONE");
    }
}