package com.infoshareacademy;

import com.infoshareacademy.vehicles.*;

import java.util.*;

public class ShoppingCentre {
    private final Random random = new Random();
    private final Integer maxCarAmount = 15;
    private Integer shopsAmount;
    private Integer maxVehicleAmount;
    private List<Shop> shopsDB = new ArrayList<>();


    public static void main( String[] args ) {
        ShoppingCentre shoppingCentre = new ShoppingCentre();



        for (int i = 0; i < shoppingCentre.shopsAmount; i++) {
            shoppingCentre.shopsDB.add(shoppingCentre.createRandomShop());
        }




        Shop shop = shoppingCentre.shopsDB.get(0);
        shop.shopRun();

       // shoppingCentre.clearScreen();


    }


    private Shop createRandomShop() {
        Integer carLimit = generateMaxParkingCapacity();
        Set<String> set = new HashSet<>();
        set = generateAcceptedVehicleTypes();
        Shop shop = new Shop(carLimit, set);
        for (int i = 0; i < carLimit; i++) {

            Boolean result = true;
            while (result) {
                Object o = generateVehicle();
                Class c = o.getClass();
                String s = c.getSimpleName();

                if (set.contains(s)) {
                    result = true;
                } else {
                    shop.forSaleDB.add(generateVehicle());
                    result = false;
                }
            }
        }

        Integer soldLimit = random.nextInt(carLimit);
        for (int i = 0; i < soldLimit; i++) {
            Boolean result = true;
            while (result) {
                Object o = generateVehicle();
                Class c = o.getClass();
                String s = c.getSimpleName();

                if (set.contains(s)) {
                    result = true;
                } else {
                    shop.soldDB.add(generateVehicle());
                    result = false;
                }
            }
        }
        return shop;
    }

    private List<Shop> createRandomShops(Integer shopsAmount) {
        List<Shop> list = new ArrayList<>();

        for (int i =0; i < shopsAmount; i++) {
            list.add(createRandomShop());
        }
        return list;
    }

    private Set<String> generateAcceptedVehicleTypes() {
       // Random random = new Random();

        Set<String> list = new HashSet<>();
        String[] types = {"Car", "Boat", "Plain", "Tank", "Bike"};
        String vehicletype;

        while (list.size() < 3) {
            vehicletype = types[random.nextInt(types.length)];
            list.add(vehicletype);
        }

        System.out.println(list);
        return list;
    }

    private Integer generateMaxParkingCapacity() {
        Integer maxParkingCapacity = 0;

        while (maxParkingCapacity < 5) {
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

    private void displayConfigurationMenu() {
        System.out.println("*******************************************************************************************");
        System.out.println("Konfiguracja centrum handlowego");
        System.out.print("* Podaj liczbę sklepów: ");

        shopsAmount = getKeyboardNumber();

        System.out.print("* Podaj maksymalną liczbę pojazdów w sklepie: ");

        Integer maxVehicleAmount = getKeyboardNumber();

        System.out.print("* Naciśnij dowolny klawisz aby wygenerowac centrum handlowe.");
    }

    public static Integer getKeyboardNumber() {
        Boolean notValidChoice = true;
        Integer choice = 0;

        while (notValidChoice) {
            try {
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę.");
                notValidChoice = true;
            }
        }
        return  choice;
    }

    private void clearScreen() {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            System.out.println("Clear / CLS error");
        }
    }



}
