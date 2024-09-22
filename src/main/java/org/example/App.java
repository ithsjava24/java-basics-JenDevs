package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int [] prices = new int[24];

        String choice;
        do {
            showMenu();
            choice = sc.nextLine().toLowerCase();
            handleChoice(choice, sc, prices);
        } while (!choice.equals("e"));

    }

    public static void showMenu () {
        String menu = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta
                """;


        System.out.print(menu);
    }

    public static void handleChoice(String choice, Scanner sc, int [] prices){
        switch (choice) {
            case "1" -> inputElectricityPrices(sc, prices);
            case "2" -> minMaxAverage(prices);
            case "3" -> sortPrices(prices);
            case "4" -> bestChargingTime(prices);
            case "e" -> System.out.print("Programmet avlusas");
            default ->
                    System.out.print("Felaktigt choice, gör ett nytt choice\n\n");
        }
    }

    public static void inputElectricityPrices(Scanner sc, int[]prices) {
        System.out.print("Gör din inmatning av elpriser, timme för timme. Ange priset i öre/kWh.\n");
        for(int i = 0; i < prices.length; i++) {
            System.out.printf("%02d-%02d " ,i, (i+1)%24);
            prices[i] = sc.nextInt();
            sc.nextLine();
        }
    }

    public static void minMaxAverage(int[] prices){
        int min = prices[0];
        int max = prices[0];
        int minIndex = 0;
        int maxIndex = 0;
        int sum = 0;

        for(int i = 0; i < prices.length; i++){
            if(prices[i] < min){
                min = prices[i];
                minIndex = i;
            }
            if(prices[i] > max){
                max = prices[i];
                maxIndex = i;
            }
            sum += prices[i];
        }

        double average =  (double) sum / prices.length;

        double[] results = {min, minIndex, max, maxIndex, average};
        System.out.printf(Locale.forLanguageTag(("sv-SE")),"Lägsta pris: %02d-%02d, %d öre/kWh\n", (int) results[1], ((int) results[1] + 1) % 24, (int) results[0]);
        System.out.printf(Locale.forLanguageTag(("sv-SE")),"Högsta pris: %02d-%02d, %d öre/kWh\n", (int) results[3], ((int) results[3] + 1) % 24, (int) results[2]);
        System.out.printf(Locale.forLanguageTag(("sv-SE")),"Medelpris: %.2f öre/kWh\n", results[4]);

    }

    public static void sortPrices(int [] prices) {
        Integer [] index = new Integer[prices.length];

        for(int i = 0; i < prices.length; i++){
            index[i] = i;
        }

        Arrays.sort(index, Comparator.comparingInt(i -> prices[(int) i]).reversed());
        for(int i : index){
            System.out.printf(Locale.forLanguageTag(("sv-SE")), "%02d-%02d %d öre\n", i, (i + 1), prices[i]);
        }
    }

    public static void bestChargingTime(int [] prices){
        int minPrice = Integer.MAX_VALUE;
        int startHour = 0;

        for(int i = 0; i <= prices.length-4; i++){
            int nuvarandePris = prices[i] + prices[i+1] + prices[i+2] + prices[i+3];

            if(nuvarandePris < minPrice){
                minPrice = nuvarandePris;
                startHour = i;
            }
        }

        double averagePrice = minPrice / 4.f;

        System.out.printf("Påbörja laddning klockan %d\n", startHour);
        System.out.printf(Locale.forLanguageTag("sv-Se"),"Medelpris 4h: %.1f öre/kWh\n", averagePrice);
    }
}
