import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Rent a car
// Get Confirmation slip

class Car { // Encapsulation
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDay) {
        return basePricePerDay * rentalDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer { 
    private String name;
    private int id;

    public Customer(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCustomerId() {
        return id;
    }
}

class Rent {
    private Car car;
    private Customer customer;
    private int days;

    public Rent(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rent> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rent(car, customer, days));
            System.out.println("Car rented successfully.");
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rent rentalToRemove = null;
        for (Rent rent : rentals) {
            if (rent.getCar() == car) {
                rentalToRemove = rent;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully.");
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner input = new Scanner(System.in);

        while (true) { 
            System.out.println("=== Car Rental System ===");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==");
                System.out.print("Enter your name: ");
                String customerName = input.nextLine();

                System.out.println("\nAvailable Cars: ");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }

                System.out.print("\nEnter car ID you want to rent: ");
                String carId = input.nextLine();

                System.out.print("Enter the number of days for rent: ");
                int rentalDay = input.nextInt();
                input.nextLine();

                Customer newCustomer = new Customer(customerName, customers.size() + 1);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDay);
                    System.out.println("\n=== Rental Information ===");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " - " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDay);
                    System.out.println("Total Price: " + totalPrice);

                    System.out.print("\nConfirm Rental (y/n): ");
                    String confirm = input.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDay);
                    } else {
                        System.out.println("\nRental Canceled...");
                    }
                } else {
                    System.out.println("Invalid car selection or car not available.");
                }
            } else if (choice == 2) {
                System.out.println("\n=== Return a Car ===");
                System.out.print("Enter the car ID you want to return: ");
                String carId = input.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rent rent : rentals) {
                        if (rent.getCar() == carToReturn) {
                            customer = rent.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car not returned.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not available.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        System.out.println("Thanks for using the car rental system.");
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem obj = new CarRentalSystem();

        // Adding some cars to the system
        obj.addCar(new Car("CAR001", "Toyota", "Camry", 50.0));
        obj.addCar(new Car("CAR002", "Honda", "Civic", 45.0));
        obj.addCar(new Car("CAR003", "Ford", "Focus", 40.0));

        obj.menu();
    }
}
