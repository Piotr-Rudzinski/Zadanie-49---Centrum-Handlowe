package com.infoshareacademy;

import com.infoshareacademy.vehicles.*;

import java.util.*;


public class ShoppingCentre {
    private final Random random = new Random();
    private final Integer maxCarAmount = 15;
    private Integer shopsAmount;
    private Integer shopNumber = 0;
    private List<Shop> shopsDB = new ArrayList<>();


    public static void main( String[] args ) {
        ShoppingCentre shoppingCentre = new ShoppingCentre();
        shoppingCentre.createShoppingCentre();
    }

    private void createShoppingCentre() {
        displayConfigurationMenuBeginning();
        shopsAmount = getKeyboardNumber();
        displayConfigurationMenuEnd();
        getAnyKey();

        for (int i = 0; i < shopsAmount; i++) {
            shopsDB.add(createRandomShop(i));
        }

        while (shopNumber != (shopsAmount + 1)) {
            shopSelectMenu();

            Boolean notProperShopNumber = true;
            while (notProperShopNumber) {
                System.out.print("Podaj numer sklepu (max. " + shopsAmount + "): ");
                shopNumber = getKeyboardNumber();

                if ((shopNumber < (shopsAmount + 2)) && (shopNumber > 0)) {
                    notProperShopNumber = false;
                }
            }

            if (shopNumber != (shopsAmount + 1)) {
                clearScreen();
                shopsDB.get(shopNumber - 1).shopRun();
           }

        }
        System.out.println("Wyszedłeś z cetrum handlowego.");
        System.out.println("Koniec działania programu.");
    }

    private void displayConfigurationMenuBeginning() {
        System.out.println("*******************************************************************************************");
        System.out.println("* Konfiguracja Centrum Handlowego");
        System.out.print("* Podaj liczbę sklepów: ");
    }

    private void displayConfigurationMenuEnd() {
        System.out.println("*");
        System.out.println("* Każdy sklep posiadać będzie parkingi umożliwiające umieszczenie od 5 do 15 pojazdów.");
        System.out.println("*");
        System.out.println("* Naciśnij dowolny klawisz aby wygenerować Centrum Handlowe.");
        System.out.print("*******************************************************************************************");
    }

    private void shopSelectMenu() {
        System.out.println("*******************************************************************************************");
        System.out.println("Liczba sklepów z różnego rodzaju pojazdami znajdujących się w Centrum Handlowym: " + shopsAmount);
        System.out.println("Podaj numer sklepu, do którego chcesz wejść. ");
        System.out.println("Wskazanie liczby " + (shopsAmount + 1) + " spowoduje wyjście z Centrum Handlowego.");
        System.out.println();
    }

    private void getAnyKey() {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();
    }

    public Integer getShopNumber () {
        return shopNumber;
    }

    private static Integer getKeyboardNumber() {
        Boolean notValidChoice = true;
        Integer choice = 0;

        while (notValidChoice) {
            try {
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextInt();

                if (choice > 0 ) {
                    notValidChoice = false;
                } else {
                    System.out.print("Podaj liczbę > 0: ");
                    notValidChoice = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę.");
                notValidChoice = true;
            }
        }
        return  choice;
    }

    private Shop createRandomShop(Integer number) {
        Integer carLimit = generateMaxParkingCapacity();
        Set<String> set = new HashSet<>();
        set = generateAcceptedVehicleTypes();
        Shop shop = new Shop(carLimit, set, number);

        filParkingWithWehicles(shop, shop.getCarsForSaleDB(), "carsForSaleDB", set, carLimit );

        Integer soldLimit = random.nextInt(carLimit);
        filParkingWithWehicles(shop, shop.getSoldCarsDB(), "soldCarsDB", set, soldLimit );

        return shop;
    }

    private void filParkingWithWehicles(Shop shop, List<Vehicle> parkingDB, String parkingName,Set<String> set, Integer limit) {
        for (int i = 0; i < limit; i++) {
            Boolean result = true;
            while (result) {
                Object o = generateVehicle();
                Class c = o.getClass();
                String s = c.getSimpleName();

                if (set.contains(s)) {
                    if (parkingName.equals("soldCarsDB")) {
                        shop.getCarsForSaleDB().add((Vehicle) o);
                    } else {
                        shop.getSoldCarsDB().add((Vehicle) o);
                    }
                    result = false;
                } else {
                    result = true;
                }
            }
        }
    }








    private Set<String> generateAcceptedVehicleTypes() {
       // Random random = new Random();

        Set<String> list = new HashSet<>();
        String[] types = {"Car", "Boat", "Plane", "Tank", "Bike"};
        String vehicletype;

        while (list.size() < 3) {
            vehicletype = types[random.nextInt(types.length)];
            list.add(vehicletype);
        }
        return list;
    }

    private Integer generateMaxParkingCapacity() {
        Integer maxParkingCapacity = 0;

        while (maxParkingCapacity < 5 || maxParkingCapacity > maxCarAmount) {
            maxParkingCapacity = random.nextInt(maxCarAmount);
        }
        return maxParkingCapacity;
    }

    private Vehicle generateVehicle() {
        Vehicle vehicle = new Vehicle();

        Integer type = random.nextInt(5);

        if (type == 1) {
            return new Car("car");
        } else if (type == 2) {
            return new Boat("boat");
        } else if (type == 3) {
            return new Plane("plane");
        } else if (type == 4) {
            return new Tank("tank");
        } else {
            return new Bike("bike");
        }
    }

    private void clearScreen() {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e) {
            System.out.println("Clear / CLS error");
        }
    }
}
