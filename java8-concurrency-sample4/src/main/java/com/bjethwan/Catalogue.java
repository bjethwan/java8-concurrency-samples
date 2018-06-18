package com.bjethwan;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Catalogue
{
    private static final Product harrypotter = new Product(Product.Category.BOOK, "Harry Potter");
    private static final Product davincicode = new Product(Product.Category.BOOK, "Da Vinci Code");
    
    private static final Product iphone6 = new Product(Product.Category.ELECTRONICS, "Iphone6");
    private static final Product iphone6Plus = new Product(Product.Category.ELECTRONICS, "Iphone6 Plus");
    private static final Product iphone7 = new Product(Product.Category.ELECTRONICS, "Iphone7");
    private static final Product iphone7Plus = new Product(Product.Category.ELECTRONICS, "Iphone7 Plus");
    private static final Product iphone8 = new Product(Product.Category.ELECTRONICS, "Iphone8");
    private static final Product iphoneX = new Product(Product.Category.ELECTRONICS, "IphoneX");
    private static final Product miA1 = new Product(Product.Category.ELECTRONICS, "Mi A1");
    
    private static final Product toothbrush = new Product(Product.Category.HEALTH, "Tooth brush");
    private static final Product perfume = new Product(Product.Category.HEALTH, "Perfume");

    public static final List<Product> products = Arrays.asList(
            harrypotter, davincicode, 
            iphone6,iphone6Plus,iphone7,iphone7Plus,iphone8,iphoneX,miA1, 
            perfume, toothbrush
    );

    private final Map<String, Product> productsByName = 
    		products.stream().collect(toMap(Product::getName, p -> p));

    public Product productByName(final String name)
    {
        return productsByName.get(name);
    }

}
