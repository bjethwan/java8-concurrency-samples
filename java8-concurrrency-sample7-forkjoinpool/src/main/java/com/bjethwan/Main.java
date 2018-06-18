package com.bjethwan;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		
		List<Product> products = ProductGenerator.generateListOfProducts(100);
		
		Task task = new Task(products, 0, products.size(), .2);
		
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(task);
		
		do{
			System.out.println("************************************");
			System.out.printf("Main: Parallelism: %d\n",pool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
			System.out.println("************************************");
			
			try {
				TimeUnit.SECONDS.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!task.isDone());
		
		if(task.isCompletedNormally()){
			System.out.println("Main: The proceesss completed normally.");
		}
		
		for(Product product: products){
			if(product.getPrice()!=12)
				System.out.printf("Product %s:%f\n", product.getName(), product.getPrice());
		}
		
		pool.shutdown();
	}
}
