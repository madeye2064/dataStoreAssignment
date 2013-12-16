import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 16/12/13
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static  ArrayList<Product> productList = new ArrayList<Product>() ;
    private static ArrayList<QueryData> queryDataList = new ArrayList<QueryData>();
    private static HashMap<String,ArrayList<Integer>> productIdToProductListIndex = new HashMap<String, ArrayList<Integer>>();
    private static HashMap<String, ArrayList<Integer>> artistToProductListIndex =  new HashMap<String, ArrayList<Integer>>();
    private static HashMap<String, ArrayList<Integer>> productIdToQueryDataIndex = new HashMap<String, ArrayList<Integer>>();
    private static HashMap<String, ArrayList<Integer>> queryToQueryDataIndex = new HashMap<String, ArrayList<Integer>>();
    public static void main(String[] args) {
        addProduct("1","Shirt","formals","swati");
        addProduct("2","t-shirt","casuals","vishakha");
        addProduct("3","skirt","formals","swati");
        addProduct("4","jeans","casuals","vishakha");
        addProduct("5","bedSheets","home decor","vishal");
        addProduct("5","coats","formals","vishal");
        addQueryData("1", "formal clothes", "010101");
        addQueryData("3","formal clothes","020202");
        addQueryData("2","casuals","0101010");
        addQueryData("4","casuals","020202");
        addQueryData("1","all clothes","010101");
        addQueryData("2","all clothes","010101");
        addQueryData("3","all clothes","010101");
        addQueryData("4","all clothes","010101");
        addQueryData("6","coats","9127412");
        System.out.println("all clothes products:");
        getQuerywiseProductDetails("all clothes");
        System.out.println("\nsavadv products: ");
        getQuerywiseProductDetails("savadv");
        System.out.println("\n casuals products:");
        getQuerywiseProductDetails("casuals");
        System.out.println("\n artist swati:");
        getArtistWiseUniqueQueries("swati");
        System.out.println("\n artist vishakha");
        getArtistWiseUniqueQueries("vishakha");
        System.out.println("\n random artist");
        getArtistWiseUniqueQueries("fsfdsd");
        System.out.println("\n artist Vishal");
        getArtistWiseUniqueQueries("vishal");

    }

    private static void updateProducIDIndexOnQueryData(int i) {
        QueryData  queryData  = queryDataList.get(i);
        String productId = queryData.getProductId();
        if(!productIdToQueryDataIndex.containsKey(productId)) {
            productIdToQueryDataIndex.put(productId, new ArrayList<Integer>());
        }
        productIdToQueryDataIndex.get(productId).add(i);
    }

    private static void updateQueryIndex(int i) {
       QueryData  queryData  = queryDataList.get(i);
       String query = queryData.getQuery();
       if(!queryToQueryDataIndex.containsKey(query)) {
           queryToQueryDataIndex.put(query, new ArrayList<Integer>());
       }
       queryToQueryDataIndex.get(query).add(i);

    }

    private static void updateArtistIndexOnProductList(int i) {
        Product product = productList.get(i);
        String artist = product.getArtist();
        if(!artistToProductListIndex.containsKey(artist)){
            artistToProductListIndex.put(artist, new ArrayList<Integer>());
        }
        artistToProductListIndex.get(artist).add(i);
    }

    private static void updateProductIDIndexOnProductList(int i) {
        Product product = productList.get(i);
        String productId = product.getProductId();
        if(!productIdToProductListIndex.containsKey(productId)) {
            productIdToProductListIndex.put(productId, new ArrayList<Integer>());
        }
        productIdToProductListIndex.get(productId).add(i);
    }

    public static void addProduct(String productId, String productName, String genre, String artist){
        Product p = new Product();
        p.setProductId(productId);
        p.setProductName(productName);
        p.setArtist(artist);
        p.setGenre(genre);
        if(productIdToProductListIndex.containsKey(productId)){
            System.out.println("Error: productId already exists: productId" + productId);
        }
        else {
            int i = productList.size();
            productList.add(p);
            updateProductIDIndexOnProductList(i);
            updateArtistIndexOnProductList(i);
        }

    }
    public static void addQueryData (String productId, String query, String timestamp){
        if(!productIdToProductListIndex.containsKey(productId)){
            System.out.println("Error:No Such ProductId Exists: productId " + productId);
            return;
        }
        QueryData q = new QueryData();
        q.setProductId(productId);
        q.setQuery(query);
        q.setTimestamp(timestamp);
        int i = queryDataList.size();
        queryDataList.add(q);
        updateProducIDIndexOnQueryData(i);
        updateQueryIndex(i);
    }

    public static void getQuerywiseProductDetails(String query){
       ArrayList<Integer> indexList ;
       if(queryToQueryDataIndex.containsKey(query)){
          indexList = queryToQueryDataIndex.get(query);
          for(int i=0;i<indexList.size();i++){
              int index = indexList.get(i);
              QueryData q = queryDataList.get(index);
              String productId = q.getProductId();
              if(!productIdToProductListIndex.containsKey(productId)) continue;
              int productIdIndex = productIdToProductListIndex.get(productId).get(0);
              Product p = productList.get(productIdIndex);
              System.out.println(p.getProductId()+","+ p.getProductName()+","+p.getArtist()+","+p.getGenre());
          }
       }
      else{
           System.out.println(" No such Query");
       }
    }

    public static void getArtistWiseUniqueQueries(String artist){
        ArrayList<Integer> productIndexList;
        if(!artistToProductListIndex.containsKey(artist)){
            System.out.println("Error: No such artist");
            return;
        }
        else{
            productIndexList = artistToProductListIndex.get(artist);
            HashMap<String, Boolean> uniqueQueries =  new HashMap<String, Boolean>();
            for(int productIndex:productIndexList){
                String productId = productList.get(productIndex).getProductId();
                if(!productIdToQueryDataIndex.containsKey(productId)) continue;
                ArrayList<Integer> QueryListIndex = productIdToQueryDataIndex.get(productId);
                for(int queryIndex: QueryListIndex){
                    QueryData q = queryDataList.get(queryIndex);
                    if(uniqueQueries.containsKey(q.getQuery())) continue;
                    System.out.println( " Query: "+ q.getQuery());
                    uniqueQueries.put(q.getQuery(), true);
                }
            }
        }
    }


}
