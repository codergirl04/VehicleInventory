import java.util.Objects;

public class Vehicle {
    private String make;
    private String model;
    private int year;
    private double price;

    //default constructor
    public Vehicle() {
        this.make = "MFG";
        this.model = "MOD";
        this.year = 1970;
        this.price = 0.0;
    }

    //non-default constructor
    public Vehicle(String make, String model, int year, double price) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    //accessor methods
    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    //mutator methods
    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //toString method for printing
    @Override
    public String toString() {
        return make + " " + model + ';' + year + ";$" + price;
    }

    //equals method for comparing two vehicles
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vehicle vehicle = (Vehicle) other;
        return year == vehicle.year &&
                Objects.equals(make, vehicle.make) &&
                Objects.equals(model, vehicle.model);
    }
}
