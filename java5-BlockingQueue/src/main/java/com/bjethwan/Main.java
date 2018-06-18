package com.bjethwan;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Producer implements Runnable {
	private final BlockingQueue<Integer> queue;
	private int counter;
	Producer(BlockingQueue<Integer> q) { queue = q; counter=0;}
	public void run() {
		try {
			while (true) { queue.put(produce()); }
		} catch (InterruptedException ex) {ex.printStackTrace();}
	}
	Integer produce() { return ++counter;}
}

class Consumer implements Runnable {
	private final BlockingQueue<Integer> queue;
	private final char id;
	Consumer(char id, BlockingQueue<Integer> q) { this.id=id; queue = q; }
	public void run() {
		try {
			while (true) { consume(queue.take()); }
		} catch (InterruptedException ex) {ex.printStackTrace();}
	}
	void consume(Integer x) throws InterruptedException { TimeUnit.MILLISECONDS.sleep((int)(Math.random()*1000));System.out.println("Consumer."+id+": "+x); }
}

public class Main {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> q = new LinkedBlockingQueue<>(10);
		Producer p = new Producer(q);
		Consumer c1 = new Consumer('A', q);
		Consumer c2 = new Consumer('B', q);
		new Thread(p).start();
		new Thread(c1).start();
		new Thread(c2).start();
		for(;;){
			System.out.println(q);
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
