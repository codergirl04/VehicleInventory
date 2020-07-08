// car dealer class
// create one for each car dealer

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CarDealer {
    private Vehicle vehicleList[];
    private String location;
    private int vehicleCount;
    private static String dealershipBrand;
    private static int maxVehicleCount = 1024;
    private Scanner scanner;

    //default constructor
    public CarDealer() {
        this.vehicleList = new Vehicle[1024];
        Arrays.fill(vehicleList, null);
        this.vehicleCount = 0;
        this.location = "The Lost City";
    }

    //non-default constructor
    public CarDealer(String name, String location) {
        this.vehicleList = new Vehicle[1024];
        Arrays.fill(vehicleList, null);
        this.vehicleCount = 0;
        this.location = location;
        this.dealershipBrand = name;
    }

    //accessor methods
    public String getLocation() {
        return location;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public static String getDealershipBrand() {
        return dealershipBrand;
    }

    public static int getMaxVehicleCount() {
        return maxVehicleCount;
    }

    //mutator methods
    public void setLocation(String location) {
        this.location = location;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    // instantiates vehicle objects and assigns them to vehicleList array
    public void init() {
        String[] makes = new String[] {"Honda", "Tesla", "Toyota", "Nissan", "BMW", "Mercedes Benz",
                "Porsche", "Volkswagen", "Chevrolet", "Honda"};
        String[] models = new String[] {"Accord", "Model Y", "Camry", "Maxima", "330i", "M100",
                "911", "Beatle", "Camaro", "Accord"};
        int[] years = new int[] {2020, 2019, 2018, 2019, 2020, 2016, 2017, 2017, 2020, 2020};
        double[] prices = new double[] {20000.0, 50000.0, 25000.0, 23000.0, 50000.0, 40000.0,
                120000.0, 20000.0, 26000.0, 21000.0};
        for (int i=0; i<makes.length; i++) {
            if (i%2 == 0) {
                Vehicle newVehicle = new Vehicle();
                newVehicle.setMake(makes[i]);
                newVehicle.setModel(models[i]);
                newVehicle.setYear(years[i]);
                newVehicle.setPrice(prices[i]);
                addVehicle(newVehicle);
            }
            else {
                Vehicle newVehicle = new Vehicle(makes[i], models[i], years[i], prices[i]);
                addVehicle(newVehicle);
            }
        }
        // sorting the vehicle list by descending order of year
        // extra credit
        sort(this.vehicleList, this.vehicleCount);
    }

    // sorts vehicle array by descending order of year
    // descending because the assignment said to stop searching when the cars
    // in the list are older than the one being searched for
    private void sort(Vehicle[] vehicleList, int vehicleCount) {
        Vehicle[] sortedVehicleList = null;
        sortedVehicleList = new Vehicle[vehicleCount];
        int sortedCount = 0;
        for (int i=0; i<vehicleCount; i++) {
            binaryInsert(sortedVehicleList, vehicleList[i], sortedCount++);
        }
        int i = 0;
        for (Vehicle vehicle : sortedVehicleList) {
            vehicleList[i++] = vehicle;
        }
    }

    //shows the menu, asks for the user's choice, and executes it
    public void run() {
        this.scanner = new Scanner(System.in);
        while (true) {
            menu();
            System.out.println("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1 -> viewInventory();
                    case 2 -> searchMakeModel();
                    case 3 -> searchPriceRange();
                    case 4 -> searchSimilarVehicles();
                    case 5 -> quit();
                    default -> System.out.println("Invalid input.");
                }
            }
            else {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }
        }
    }

    //adds a vehicle to the inventory and increments vehicleCount
    private void addVehicle(Vehicle vehicle) {
        this.vehicleList[vehicleCount++] = vehicle;
    }

    //displays the menu
    private void menu() {
        System.out.println("MENU\n" +
                "1. View vehicle inventory\n" +
                "2. Search by make and model\n" +
                "3. Search by price\n" +
                "4. Search similar vehicles\n" +
                "5. Quit");
    }

    //displays the vehicle inventory
    private void viewInventory() {
        System.out.println("Vehicle Inventory \nMake & Model, Year, Price");
        for (int i=0; i<vehicleCount; i++) {
            System.out.println(vehicleList[i]);
        }
    }

    //allows the user to search for vehicles by make and model
    private void searchMakeModel() {
        System.out.print("Vehicle make: ");
        String make = scanner.nextLine();
        System.out.print("Vehicle model: ");
        String model = scanner.nextLine();

        int vehicles = 0;
        // Take care of the possibility that the user was searching for all models of a make
        // or the case that he gave only a model name. Find all matches even if only one input
        // is given
        for (int i=0; i<this.vehicleCount; i++) {
            boolean makeMatched = false;
            boolean modelMatched = false;
            if (make.equals("") || vehicleList[i].getMake().equals(make)) {
                makeMatched = true;
            }
            if (model.equals("") ||  vehicleList[i].getModel().equals(model)) {
                modelMatched = true;
            }
            if (makeMatched && modelMatched) {
                System.out.println(vehicleList[i]);
                vehicles++;
            }
        }
        if (vehicles == 0) {
            System.out.println("No such vehicle is found.");
        }
    }

    //allows the user to search for vehicles within a certain price range
    private void searchPriceRange() {
        try {
            System.out.print("Enter minimum price: $");
            double minimumPrice = scanner.nextDouble();
            System.out.print("Enter maximum price: $");
            double maximumPrice = scanner.nextDouble();
            int vehicles = 0;
            for (int i=0; i<this.vehicleCount; i++) {
                if (minimumPrice <= vehicleList[i].getPrice() && vehicleList[i].getPrice() <= maximumPrice) {
                    System.out.println(vehicleList[i]);
                    vehicles++;
                }
            }
            if (vehicles == 0) {
                System.out.println("No vehicles found within that price range.");
            }
        } catch (NoSuchElementException ex) {
            System.out.println("Invalid input.");
        }
    }

    // searches the vehicle inventory for vehicles with the same make, model, and year
    // extra credit
    private void searchSimilarVehicles() {
        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();
        try {
            System.out.print("Enter vehicle year: ");
            int year = scanner.nextInt();
            Vehicle vehicle = new Vehicle(make, model, year, 0);
            int vehiclesFound = 0;
            for (int i=0; i<this.vehicleCount; i++) {
                if (vehicleList[i].getYear() < vehicle.getYear()) {
                    break;
                }
                if (vehicleList[i].equals(vehicle)) {
                    System.out.println(vehicleList[i]);
                    vehiclesFound++;
                }
            }
            if (vehiclesFound == 0) {
                System.out.println("No similar vehicles.");
            }
        }
        catch (NoSuchElementException ex) {
            System.out.println("Invalid input.");
        }


    }

    //terminates the program
    private void quit() {
        System.out.print("Thanks for using " + dealershipBrand + " at " + this.location);
        System.exit(0);
    }

    // inserts new vehicles into the array of sorted vehicles based on year
    // uses binary search for efficiency
    private void binaryInsert(Vehicle[] vList, Vehicle v, int numValidElements) {
        int first = 0;
        int last = numValidElements-1;
        if (numValidElements == 0) {
            insert(vList, numValidElements, first, v);
        }
        while (first <= last) {
            if (v.getYear() >= vList[first].getYear()) {
                insert(vList, numValidElements, first, v);
                return;
            }
            if (v.getYear() <= vList[last].getYear()) {
                insert(vList, numValidElements, last+1, v);
                return;
            }
            int middle = (int)((last+first)/2);
            if (v.getYear() == vList[middle].getYear()) {
                insert(vList, numValidElements, middle, v);
                return;
            }
            if (v.getYear() < vList[middle].getYear()) {
                first = (int)(middle)+1;
            }
            else {
                last = (int)((last+first)/2)-1;
            }
        }
    }

    // helper method which inserts vehicle at a specific index
    private void insert(Vehicle[]l, int numValidElements, int index, Vehicle v) {
        if (numValidElements == l.length) {
            return;
        }
        for (int i=numValidElements; i> index; i--) {
            l[i] = l[i-1];
        }
        l[index] = v;
    }
}
