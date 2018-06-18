package com.bjethwan;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class Task extends RecursiveAction{
	
	private static final long serialVersionUID = 1L;
	private List<Product> products;
	private int first;
	private int last;
	private double increment;

	public Task(List<Product> productList, int first, int last, double increment){
		this.products = productList;
		this.first=first;
		this.last=last;
		this.increment=increment;
	}

	@Override
	protected void compute() {
		if(last-first<10){
			updatePrices();
		}else{
			int middle = (first+last)/2;
			System.out.printf("Task: Task Count: %d\n", getQueuedTaskCount());
			Task t1 = new Task(products, first, middle+1, increment);
			Task t2 = new Task(products, middle+1, last, increment);
			invokeAll(t1, t2);
		}
	}

	private void updatePrices() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=first;i<last;i++){
			Product p = products.get(i);
			p.setPrice(p.getPrice()+p.getPrice()*increment);
		}
	}
}
