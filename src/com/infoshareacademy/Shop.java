package com.infoshareacademy;

import com.infoshareacademy.vehicles.*;

import java.util.*;

public class Shop {
    public List<Vehicle> forSaleDB = new ArrayList<>();
    public List<Vehicle> soldDB = new ArrayList<>();
    private Integer maxParkingCapacity;
    private Set<String> acceptedVehicleTypes = new HashSet<>();
    private Integer maxChoiceNumber;
    private Integer shopNumber;

    public Shop(Integer maxParkingCapacity, Set<String> acceptedVehicleTypes, Integer shopNumber) {
        this.maxParkingCapacity = maxParkingCapacity;
        this.acceptedVehicleTypes = acceptedVehicleTypes;
        this.shopNumber = shopNumber;
    }

    public void shopRun() {
        boolean menuContinue = true;
        while (menuContinue) {
            displayMenuBeginning();
            displayVehicles();
            displayMenu();

            switch (getIntegerFromkeyboard(5)) {
                case 1:
                    Integer choiceAdd = displayAddToSaleSubmenu();
                    Vehicle vehicle = createNewVehicle(choiceAdd);
                    addNewVehicleForSale(vehicle);
                    clearScreen();
                    break;
                case 2:
                    Integer choiceRemove = displayRemoveFromSaleSubmenu(forSaleDB.size());
                    removeVehicleFromSale(choiceRemove - 1);
                    clearScreen();
                    break;
                case 3:
                    Integer choiceSell = displaySaleSubmenu(forSaleDB.size());
                    saleVehicle(choiceSell - 1);
                    clearScreen();
                    break;
                case 4:
                    Integer choiceOut = displayVehicleOutSubmenu(forSaleDB.size());
                    removeVehicleFromSold(choiceOut - 1);
                    clearScreen();
                    break;
                case 5:
                    menuContinue = false;
                    clearScreen();
                    break;
                default:
                    break;
            }
        }
    }

    private Vehicle createNewVehicle(Integer choice) {
        if (choice == 1) {
            return new Car("Car");
        } else if (choice == 2) {
            return new Boat("Boat");
        } else if (choice == 3) {
            return new Plane("Plane");
        } else if (choice == 4) {
            return new Tank("Tank");
        } else {
            return new Bike("Bike");
        }
    }

    private void addNewVehicleForSale(Vehicle vehicle) {
        if (isVehicleTypeValid(vehicle)) {
            if (isPlaceOnSaleParking()) {
                addVehicleForSale(vehicle);
                System.out.print("Pojazd został dodany do listy pojazdów przeznaczonych do sprzedaży. Naciśnij dowolny klawisz.");
            } else {
                System.out.print("Typ pojazdu jest właściwy, ale nie ma miejsca na parkingu sprzedażowym. Nacisnij dowolny klawisz.");
            }
        } else {
            System.out.print("Nie można dodać tego typu pojazdu do sprzedaży!!!. Nacisnij dowolny klawisz.");
        }
        getCharFromkeyboard();
    }

    private void removeVehicleFromSale(Integer i) {
        if (!forSaleDB.isEmpty()) {
            removeVehicleForSale(i);
        } else {
            System.out.print("Ten pojazd nie jest w sprzedaży. Naciśnij dowolny klawisz.");
        }
        getCharFromkeyboard();
    }

    private void saleVehicle(Integer i) {
        if (isPlaceOnSoldParking()) {
            Vehicle vehicle = forSaleDB.get((int) i);
            removeVehicleForSale((int) i);
            addSoldVehicle(vehicle);
            System.out.print("Pojazd został przesunięty z listy pojazdów przeznaczonych do sprzedazy do listy pojazdów sprzedanych. Naciśnij dowolny klawisz.");
        } else {
            System.out.print("Sprzedaż nie jest możliwa. Nie ma miejsca na parkingu sprzedanych pojazdów. Naciśnij dowolny klawisz.");
        }
        getCharFromkeyboard();
    }

    private void removeVehicleFromSold(Integer i) {
        if (!soldDB.isEmpty()) {
            removeSoldVehicle(i);
            System.out.print("Pojazd został wydany klientowi. Naciśnij dowolny klawisz.");
        } else {
            System.out.print("Nie ma samochodów do wydania. Naciśnij dowolny klawisz.");
        }
        getCharFromkeyboard();
    }

    private void getCharFromkeyboard() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private Boolean isVehicleTypeValid(Vehicle vehicle) {
        String vehicleType = getVehicleObjectSimpleName(vehicle);
        return acceptedVehicleTypes.contains(vehicleType);
    }

    private Boolean isPlaceOnSaleParking() {
        if (forSaleDB.size() < maxParkingCapacity) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isPlaceOnSoldParking() {
        if (soldDB.size() < maxParkingCapacity) {
            return true;
        } else {
            return false;
        }
    }

    private void addVehicleForSale(Vehicle vehicle) {
        forSaleDB.add(vehicle);
    }

    private void addSoldVehicle(Vehicle vehicle) {
        soldDB.add(vehicle);
    }

    private void removeVehicleForSale(Integer position) {
        forSaleDB.remove((int) position);
        System.out.print("Pojazd został usunięty. Naciśnij dowolny klawisz.");
    }

    private void removeSoldVehicle(Integer position) {
        soldDB.remove((int) position);
    }

    private void displayVehicles() {
        Integer tableLength = getTableLength();

        System.out.println("----------------------------------------------------------");
        System.out.println("| Lp. | Pojazdy do sprzedaży   | Pojazdy sprzedane       |");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < getTableLength(); i++) {
            System.out.println(getVehicleClassName(i));
        }
        System.out.println("----------------------------------------------------------");
        System.out.println();
    }

    private StringBuilder getVehicleClassName(Integer i) {
        String s;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|  ");
        if ((i + 1) < 10) {
            stringBuilder.append(" " + (i + 1));
        } else {
            stringBuilder.append(i + 1);
        }
        stringBuilder.append(" | ");

        try {
            s = getVehicleObjectSimpleName(forSaleDB.get(i));
        } catch (IndexOutOfBoundsException e) {
            s = "noVehicleTypeClass";
        }

        if (s.equals("Car")) {
            stringBuilder.append("Car                    | ");
        } else if (s.equals("Boat")) {
            stringBuilder.append("Boat                   | ");
        } else if (s.equals("Plane")) {
            stringBuilder.append("Plane                  | ");
        } else if (s.equals("Tank")) {
            stringBuilder.append("Tank                   | ");
        } else if (s.equals("Bike")) {
            stringBuilder.append("Bike                   | ");
        } else if (s.equals("noVehicleTypeClass")) {
            stringBuilder.append(" -                     | ");
        }

        try {
            s = getVehicleObjectSimpleName(soldDB.get(i));
        } catch (IndexOutOfBoundsException e) {
            s = "noVehicleTypeClass";
        }

        if (s.equals("Car")) {
            stringBuilder.append("Car                     |");
        } else if (s.equals("Boat")) {
            stringBuilder.append("Boat                    |");
        } else if (s.equals("Plane")) {
            stringBuilder.append("Plane                   |");
        } else if (s.equals("Tank")) {
            stringBuilder.append("Tank                    |");
        } else if (s.equals("Bike")) {
            stringBuilder.append("Bike                    |");
        } else if (s.equals("noVehicleTypeClass")) {
            stringBuilder.append(" -                      |");
        } else {
            stringBuilder.append(" -                      |");
        }
        return stringBuilder;
    }

    private Integer getTableLength() {
        Integer length1 = forSaleDB.size();
        Integer length2 = soldDB.size();

        if (length1 > length2) {
            return length1;
        } else {
            return length2;
        }
    }

    private void displayMenuBeginning() {
        Object[] acceptedtypes = acceptedVehicleTypes.toArray();

        System.out.println("Sklep z pojazdami nr " + (shopNumber + 1));
        System.out.println("W tym sklepie można kupić następujące rodzaje pojazadów: " + acceptedtypes[0] + ", " + acceptedtypes[1] + ", " + acceptedtypes[2] + ".");
        System.out.println();
        System.out.println("Lista pojazdów przeznaczonych do sprzedaży i pojazdów już sprzedanych:");
    }

    private void displayMenu() {
        System.out.println("Menu");
        System.out.println("1. Dodaj pojazd do sprzedaży");
        System.out.println("2. Usuń pojazd ze sprzedaży");
        System.out.println("3. Sprzedaj pojazd");
        System.out.println("4. Wydaj pojazd klientowi");
        System.out.println("5. Wyjście ze sklepu");
        System.out.println();
        System.out.print("Podaj numer polecenia: ");
    }

    private Integer displayAddToSaleSubmenu() {
        System.out.println("1. Dodaj pojazd do sprzedaży - podaj typ pojazdu wskazując cyfrę od 1 do 5");
        System.out.println("   1. Car");
        System.out.println("   2. Boat");
        System.out.println("   3. Plane");
        System.out.println("   4. Tank");
        System.out.println("   5. Bike");
        System.out.print("Typ pojazdu: ");

        return getIntegerFromkeyboard(5);
    }

    private Integer displayRemoveFromSaleSubmenu(Integer maxChoiceNumber) {
        System.out.print("2. Usuń pojazd ze sprzedaży - podaj numer pojazdu: ");
        return getIntegerFromkeyboard(maxChoiceNumber);
    }

    private Integer displaySaleSubmenu(Integer maxChoiceNumber) {
        System.out.print("3. Sprzedaj pojazd - podaj numer pojazdu: ");
        return getIntegerFromkeyboard(maxChoiceNumber);
    }

    private Integer displayVehicleOutSubmenu(Integer maxChoiceNumber) {
        System.out.print("4. Wydaj pojazd klientowi - podaj numer pojazdu: ");
        return getIntegerFromkeyboard(maxChoiceNumber);
    }

    private Integer getIntegerFromkeyboard(Integer maxChoiceNumber) {
        Boolean notValidChoice = true;
        Integer choice = 0;

        while (notValidChoice) {
            try {
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextInt();

                if ((choice > 0) && (choice < (maxChoiceNumber + 1))) {
                    notValidChoice = false;
                } else {
                    System.out.print("Podaj liczbę z zakresu 1-5: ");
                    notValidChoice = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę.");
                notValidChoice = true;
            }
        }
        return choice;
    }

    private String getVehicleObjectSimpleName(Vehicle vehicle) {
        Object obj = vehicle;
        Class c = obj.getClass();
        return c.getSimpleName();
    }

    private void clearScreen() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            System.out.println("Clear / CLS error");
        }
    }
}
