package com.bjethwan;

import static com.bjethwan.Currency.USD;

import java.util.concurrent.CompletableFuture;

public class PriceCatalogueExample
{
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder();
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(String[] args)
    {
        new PriceCatalogueExample().findLocalDiscountedPrice(Currency.CHF, "IphoneX");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName)
    {
        long time = System.currentTimeMillis();

        CompletableFuture<Product> productCompletableFuture = 
        		CompletableFuture.supplyAsync(()->catalogue.productByName(productName));
       
        CompletableFuture<Price> priceCompletableFuture = 
        		CompletableFuture.supplyAsync(()->priceFinder.findBestPrice(productCompletableFuture.join()));

        CompletableFuture<Double> exchangeRateCompletableFuture = 
        		CompletableFuture.supplyAsync(()-> exchangeService.lookupExchangeRate(USD, localCurrency));

        double localPrice = exchange(priceCompletableFuture.join(), exchangeRateCompletableFuture.join());

        System.out.printf("A %s will cost us %f %s\n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }

}
