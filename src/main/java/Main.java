import java.io.*;
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
    private static  ArrayList<Product> productList = new ArrayList<Product>() ;   //products Table
    private static ArrayList<QueryData> queryDataList = new ArrayList<QueryData>();  //QueryTable
    private static HashMap<String,ArrayList<Integer>> productIdToProductListIndex = new HashMap<String, ArrayList<Integer>>();
    private static HashMap<String, ArrayList<Integer>> artistToProductListIndex =  new HashMap<String, ArrayList<Integer>>();
    private static HashMap<String, ArrayList<Integer>> productIdToQueryDataIndex = new HashMap<String, ArrayList<Integer>>();
    private static HashMap<String, ArrayList<Integer>> queryToQueryDataIndex = new HashMap<String, ArrayList<Integer>>();
    public static void main(String[] args) throws IOException {
        String currentDirectory = new File("").getAbsolutePath();
        new Main().readProductData("productData.csv");
        new Main().readQueryData("QueryData.csv");

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

    private  void readQueryData(String filePath) throws IOException {
        FileInputStream fstream = new FileInputStream(String.valueOf(getClass().getResource("queryData.csv").getPath()));
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null){
            if(strLine == null) continue;
            String[] params = strLine.split(",");
            if(params.length!=3) {
                System.out.println("Invalid Input: "+ strLine);
                continue;
            }
            addQueryData(params[0], params[1], params[2]);
        }
        in.close();

    }

    private void readProductData(String filePath) throws IOException {
        FileInputStream fstream = new FileInputStream(String.valueOf(getClass().getResource(filePath).getPath()));
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null){
           if(strLine == null) continue;
           String[] params = strLine.split(",");
           if(params.length!=4) {
               System.out.println("Invalid Input");
               continue;
           }
           addProduct(params[0], params[1], params[2], params[3]);
        }
        in.close();
    }

    private static void updateProductIDIndexOnQueryData(int i) {
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
        updateProductIDIndexOnQueryData(i);
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
