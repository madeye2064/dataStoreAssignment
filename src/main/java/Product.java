/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 16/12/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Product {
    private String productId;
    private String productName;
    private String genre;
    private String artist;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
