package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int [] priser = new int[24];

        String val;
        do {
            showMenu();
            val = sc.nextLine().toLowerCase();
            hanteraVal(val, sc, priser);
        } while (!val.equals("e"));

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

    public static void hanteraVal(String val, Scanner sc, int [] priser){
        switch (val) {
            case "1" -> inmatningElpriser(sc, priser);
            case "2" -> minMaxMedel(priser);
            case "3" -> sorteraPriser(priser); // rensa i kod, ta bort onödigt
            case "4" -> bestChargingTime(priser);
            case "e" -> System.out.print("Programmet avlusas");
            default ->
                    System.out.print("Felaktigt val, gör ett nytt val\n\n");
        }
    }

    public static void inmatningElpriser(Scanner sc, int[]priser) {
        for(int i = 0; i < priser.length; i++) {
            System.out.printf("%02d-%02d " ,i, (i+1)%24);
            priser[i] = sc.nextInt();
            sc.nextLine();
        }
    }

    public static void minMaxMedel(int[] priser){
        int min = priser[0];
        int max = priser[0];
        int minIndex = 0;
        int maxIndex = 0;
        int sum = 0;

        for(int i = 0; i < priser.length; i++){
            if(priser[i] < min){
                min = priser[i];
                minIndex = i;
            }
            if(priser[i] > max){
                max = priser[i];
                maxIndex = i;
            }
            sum += priser[i];
        }

        double average =  (double) sum / priser.length;

        double[] resultat = {min, minIndex, max, maxIndex, average};
        System.out.printf(Locale.forLanguageTag(("sv-SE")),"Lägsta pris: %02d-%02d, %d öre/kWh\n", (int) resultat[1], ((int) resultat[1] + 1) % 24, (int) resultat[0]);
        System.out.printf(Locale.forLanguageTag(("sv-SE")),"Högsta pris: %02d-%02d, %d öre/kWh\n", (int) resultat[3], ((int) resultat[3] + 1) % 24, (int) resultat[2]);
        System.out.printf(Locale.forLanguageTag(("sv-SE")),"Medelpris: %.2f öre/kWh\n", resultat[4]);

    }

    public static void sorteraPriser (int [] priser) {
        Integer [] index = new Integer[priser.length];

        for(int i = 0; i < priser.length; i++){
            index[i] = i;
        }

        Arrays.sort(index, Comparator.comparingInt(i -> priser[(int) i]).reversed());
        for(int i : index){
            System.out.printf(Locale.forLanguageTag(("sv-SE")), "%02d-%02d %d öre\n", i, (i + 1), priser[i]);
        }
    }

    public static void bestChargingTime(int [] priser){
        int minPrice = Integer.MAX_VALUE;
        int startHour = 0;

        for(int i = 0; i <= priser.length-4; i++){
            int nuvarandePris = priser[i] + priser[i+1] + priser[i+2] + priser[i+3];

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
