// car dealer app class
// makes a car dealer and runs it

public class CarDealerApp {
    public static void main(String[] args) {
        CarDealer carDealer = null;
        carDealer = new CarDealer("Foothill Car Dealers", "Los Altos");
        carDealer.init();
        carDealer.run();
    }
}

