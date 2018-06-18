package com.bjethwan;

import static com.bjethwan.Currency.USD;

public class PriceCatalogueExample
{
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder();
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(String[] args) throws InterruptedException
    {
        new PriceCatalogueExample().findLocalDiscountedPrice(Currency.CHF, "IphoneX");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) throws InterruptedException
    {
        long time = System.currentTimeMillis();
        
        PriceRunnable priceRunnable = new PriceRunnable(productName);
        ExchangeRateRunnable exchangeRateRunnable = new ExchangeRateRunnable(localCurrency);
        
        // issue - creating threads on the fly is time consuming, 
        // and unbound thread creation can hit memory for thread stack and CPU for context switching.
        Thread priceThread = new Thread(priceRunnable);
        Thread exchangeRateThread = new Thread(exchangeRateRunnable);
        
        priceThread.start();
        exchangeRateThread.start();
                
        priceThread.join();
        exchangeRateThread.join();

        double localPrice = exchange(priceRunnable.getProductPrice(), exchangeRateRunnable.getExchangeRate());

        System.out.printf("A %s will cost us %f %s\n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }
    
    private class PriceRunnable implements Runnable{
    	private final String productName;
    	private Price productPrice;
    	
		public PriceRunnable(String productName){
    		this.productName=productName;
    	}
		@Override
		public void run() {
	        Product product = catalogue.productByName(productName);
	        productPrice = priceFinder.findBestPrice(product);
		}
		public Price getProductPrice() {
			return productPrice;
		}
    }
    private class ExchangeRateRunnable implements Runnable{
    	private final Currency localCurrency;
    	private double exchangeRate;
 
		public ExchangeRateRunnable(Currency localCurrency){
    		this.localCurrency=localCurrency;
    	}
		@Override
		public void run() {
			exchangeRate = exchangeService.lookupExchangeRate(USD, localCurrency);
		}
    	public double getExchangeRate() {
			return exchangeRate;
		}
    }
}
