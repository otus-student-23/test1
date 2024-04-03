import java.util.ArrayList;
import java.util.List;

public class ProductCatalog {

    public List<String> getProducts(int limit) {
        List<String> products = new ArrayList<>();
        for (int i = 0; i< limit; i++){
            products.add(new String(new char[1024*1000])); // 1 MB string
        }
        return products;
    }
}
