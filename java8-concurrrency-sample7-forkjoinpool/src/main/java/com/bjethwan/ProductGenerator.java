package com.bjethwan;

import java.util.ArrayList;
import java.util.List;

public class ProductGenerator {	
	public static List<Product> generateListOfProducts(final int num){
		List<Product> productList = new ArrayList<>();
		for(int i=0;i<num;i++){
			Product product = new Product();
			product.setName("Product."+i);
			product.setPrice(10);
			productList.add(product);
		}
		return productList;
	}
}
