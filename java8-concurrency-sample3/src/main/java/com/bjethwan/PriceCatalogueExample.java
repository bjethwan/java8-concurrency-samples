package com.bjethwan;

import static com.bjethwan.Currency.USD;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PriceCatalogueExample
{
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder();
    private final ExchangeService exchangeService = new ExchangeService();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        new PriceCatalogueExample().findLocalDiscountedPrice(Currency.CHF, "IphoneX");
    }

    /*
     * This is good but one of the threads is kept waiting for product to come back from catalog.
     * Waste of resource, you can use Google Guvava ListenableFuture which has callbacks functionality,
     * or much better you can use Java 8 CompletableFuture, which will let you even compose in fluent API
     */
    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) throws InterruptedException, ExecutionException
    {
        long time = System.currentTimeMillis();
        
        Future<Product> productFuture = executorService.submit(()-> catalogue.productByName(productName));
        Future<Price> priceFuture = executorService.submit(() -> priceFinder.findBestPrice(productFuture.get()));
        Future<Double> exchangeRateFuture = executorService.submit(() -> exchangeService.lookupExchangeRate(USD, localCurrency));

        double localPrice = exchange(priceFuture.get(), exchangeRateFuture.get());

        System.out.printf("A %s will cost us %f %s\n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
        
        executorService.shutdownNow();
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }

}
