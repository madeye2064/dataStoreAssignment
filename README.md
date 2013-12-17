dataStoreAssignment
===================

The code stores the data in the two arraylists (tables) and builds indexes on top of them with hashMaps.
The productsId field in product table serves as reference key for QueryData table. No queries are added to 
the datastore for which the productId doesnt exist in the products table.
The code exposes two methods for the queries:

 getQuerywiseProductDetails(String query);
 getArtistWiseUniqueQueries(String artist);
 
 (See usage examples in main)
 The Data files are specified in the resources folder in csv format.
 ProductData.csv format: ProductId,ProductName,genre,artist
 QueryData.csv format:ProductId,query,timestamp
 
 
