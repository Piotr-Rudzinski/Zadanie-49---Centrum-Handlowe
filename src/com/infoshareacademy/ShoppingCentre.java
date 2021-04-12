package com.infoshareacademy;

import com.infoshareacademy.vehicles.Boat;
import com.infoshareacademy.vehicles.Car;
import com.infoshareacademy.vehicles.Vehicle;
import com.sun.source.tree.CaseTree;

import java.io.StringBufferInputStream;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class ShoppingCentre {



    public static void main( String[] args )
    {
        Set<String> set = new HashSet<>();
        set.add("Car");
        set.add("Boat");
        set.add("Plane");


        Shop shop = new Shop(15, set);


      //  shop.set();
        Car car = new Car("car1");
        Boat boat = new Boat("boat1");



        shop.createStartingVehicles();

        //Integer choice;

        Boolean menuContinue = true;
        while (menuContinue) {

            shop.displayMenuBeginning();
            shop.displayVehicles();
            shop.displayMenu();

            switch (shop.getIntegerFromkeyboard(5)) {
                case 1:
                    Integer choiceAdd = shop.displayAddToSaleSubmenu();
                    Vehicle vehicle = shop.createNewVehicle(choiceAdd);
                    shop.addNewVehicleForSale(vehicle);
                    shop.clearScreen();
                    break;
                case 2:
                    Integer choiceRemove = shop.displayRemoveFromSaleSubmenu(shop.forSaleDB.size());
                    shop.removeVehicleFromSale(choiceRemove - 1);
                    shop.clearScreen();
                    break;
                case 3:
                    Integer choiceSell = shop.displaySaleSubmenu(shop.forSaleDB.size());
                    shop.saleVehicle(choiceSell - 1);
                    shop.clearScreen();
                    break;
                case 4:
                    Integer choiceOut = shop.displayVehicleOutSubmenu(shop.forSaleDB.size());
                    shop.removeVehicleFromSold(choiceOut - 1);
                    shop.clearScreen();
                    break;
                case 5:
                    menuContinue = false;
                    break;
                default:
                    break;
            }

        }

        System.out.println("Koniec");



    }








}
