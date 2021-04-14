package com.infoshareacademy;

import com.infoshareacademy.vehicles.*;

import java.util.*;


public class Shop {
    private List<Vehicle> carsForSaleDB = new ArrayList<>();
    private List<Vehicle> soldCarsDB = new ArrayList<>();
    private Integer maxParkingCapacity;
    private Set<String> acceptedVehicleTypes = new HashSet<>();
    private Integer maxChoiceNumber;
    private Integer shopNumber;
    private final String os = System.getProperty("os.name");

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
                    Integer choiceRemove = displayRemoveFromSaleSubmenu(carsForSaleDB.size());
                    removeVehicleFromSale(choiceRemove - 1);
                    clearScreen();
                    break;
                case 3:
                    Integer choiceSell = displaySaleSubmenu(carsForSaleDB.size());
                    saleVehicle(choiceSell - 1);
                    clearScreen();
                    break;
                case 4:
                    Integer choiceOut = displayVehicleOutSubmenu(carsForSaleDB.size());
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
        if (!carsForSaleDB.isEmpty()) {
            removeVehicleForSale(i);
        } else {
            System.out.print("Ten pojazd nie jest w sprzedaży. Naciśnij dowolny klawisz.");
        }
        getCharFromkeyboard();
    }

    private void saleVehicle(Integer i) {
        if (isPlaceOnSoldParking()) {
            Vehicle vehicle = carsForSaleDB.get((int) i);
            removeVehicleForSale((int) i);
            addSoldVehicle(vehicle);
            System.out.print("Pojazd został przesunięty z listy pojazdów przeznaczonych do sprzedazy do listy pojazdów sprzedanych. Naciśnij dowolny klawisz.");
        } else {
            System.out.print("Sprzedaż nie jest możliwa. Nie ma miejsca na parkingu sprzedanych pojazdów. Naciśnij dowolny klawisz.");
        }
        getCharFromkeyboard();
    }

    private void removeVehicleFromSold(Integer i) {
        if (!soldCarsDB.isEmpty()) {
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
        return (carsForSaleDB.size() < maxParkingCapacity);
    }

    private Boolean isPlaceOnSoldParking() {
        return (soldCarsDB.size() < maxParkingCapacity);
    }

    private void addVehicleForSale(Vehicle vehicle) {
        carsForSaleDB.add(vehicle);
    }

    private void addSoldVehicle(Vehicle vehicle) {
        soldCarsDB.add(vehicle);
    }

    private void removeVehicleForSale(Integer position) {
        carsForSaleDB.remove((int) position);
        System.out.print("Pojazd został usunięty. Naciśnij dowolny klawisz.");
    }

    private void removeSoldVehicle(Integer position) {
        soldCarsDB.remove((int) position);
    }

    private void displayVehicles() {
        Integer tableLength = getTableLength();

        System.out.println("----------------------------------------------------------");
        System.out.println("| Lp. | Pojazdy do sprzedaży    | Pojazdy sprzedane      |");
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
            s = getVehicleObjectSimpleName(carsForSaleDB.get(i));
        } catch (IndexOutOfBoundsException e) {
            s = "noVehicleTypeClass";
        }

        stringBuilder.append(generateTableLine(s));

        try {
            s = getVehicleObjectSimpleName(soldCarsDB.get(i));
        } catch (IndexOutOfBoundsException e) {
            s = "noVehicleTypeClass";
        }

        stringBuilder.append(generateTableLine(s));
        return stringBuilder;
    }

    private String generateTableLine (String s) {
       String result;
        if (s.equals("Car")) {
            result = "Car                     |";
        } else if (s.equals("Boat")) {
            result = "Boat                    |";
        } else if (s.equals("Plane")) {
            result = "Plane                   |";
        } else if (s.equals("Tank")) {
            result = "Tank                    |";
        } else if (s.equals("Bike")) {
            result = "Bike                    |";
        } else if (s.equals("noVehicleTypeClass")) {
            result = " -                      |";
        } else {
            result = " -                      |";
        }
        return result;
    }

    private Integer getTableLength() {
        Integer length1 = carsForSaleDB.size();
        Integer length2 = soldCarsDB.size();

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
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            System.out.println("Clear / CLS error");
        }
    }

    public List<Vehicle> getCarsForSaleDB() {
        return carsForSaleDB;
    }

    public List<Vehicle> getSoldCarsDB() {
        return soldCarsDB;
    }
}
