import java.util.Scanner;
import java.util.ArrayList;

class MyAvailablePhoneMakes {
    String make;

    public MyAvailablePhoneMakes(String make) {
        this.make = make;
    }
}

class AvailablePhones {
    String make;
    String model;
    String features;
    double price;
    int quantity;

    public AvailablePhones(String make, String model, String features, double price, int quantity) {
        this.make = make;
        this.model = model;
        this.features = features;
        this.price = price;
        this.quantity = quantity;
    }
}

class PasswordForCheckingMyAvailablePhonesQuantity {
    String password = "blueyellow";
}

public class MainPhoneStore {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Available Phone Makes
        ArrayList<MyAvailablePhoneMakes> myAvailablePhoneMakes = new ArrayList<>();
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Apple/iPhone"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Samsung/Galaxy"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Google/Pixel"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("OnePlus"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Xiaomi"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Sony"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("LG"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Huawei"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Motorola"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Nokia"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Oppo"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Vivo"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Realme"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Techno"));
        myAvailablePhoneMakes.add(new MyAvailablePhoneMakes("Infinix"));

        // Available Phones
        ArrayList<AvailablePhones> availablePhones = new ArrayList<>();

        availablePhones.add(new AvailablePhones("Apple", "iPhone 14", "5G, Face ID, Dual Camera", 999, 10));
        availablePhones.add(new AvailablePhones("Samsung", "Galaxy S23", "5G, Fingerprint Sensor, Triple Camera", 899, 15));
        availablePhones.add(new AvailablePhones("Google", "Pixel 7", "5G, Face Unlock, Dual Camera", 799, 8));
        availablePhones.add(new AvailablePhones("OnePlus", "OnePlus 11", "5G, Fingerprint Sensor, Triple Camera", 699, 12));
        availablePhones.add(new AvailablePhones("Xiaomi", "Mi 13", "5G, Face Unlock, Quad Camera", 599, 20));
        availablePhones.add(new AvailablePhones("Sony", "Xperia 1 IV", "5G, Face Unlock, Triple Camera", 1099, 5));
        availablePhones.add(new AvailablePhones("LG", "LG Velvet", "5G, Fingerprint Sensor, Dual Camera", 499, 7));
        availablePhones.add(new AvailablePhones("Huawei", "P60 Pro", "5G, Face Unlock, Quad Camera", 899, 10));
        availablePhones.add(new AvailablePhones("Motorola", "Edge 40 Pro", "5G, Face Unlock, Triple Camera", 699, 12));
        availablePhones.add(new AvailablePhones("Nokia", "G20", "5G, Face Unlock, Dual Camera", 499, 15));
        availablePhones.add(new AvailablePhones("Oppo", "Find X5 Pro", "5G, Face Unlock, Triple Camera", 799, 8));
        availablePhones.add(new AvailablePhones("Vivo", "X80 Pro", "5G, Face Unlock, Quad Camera", 899, 10));
        availablePhones.add(new AvailablePhones("Realme", "GT Neo3", "5G, Face Unlock, Triple Camera", 699, 12));
        availablePhones.add(new AvailablePhones("Techno", "Camon 18", "5G, Face Unlock, Dual Camera", 499, 15));
        availablePhones.add(new AvailablePhones("Infinix", "Note 12", "5G, Face Unlock, Triple Camera", 599, 10));

        PasswordForCheckingMyAvailablePhonesQuantity admin =
                new PasswordForCheckingMyAvailablePhonesQuantity();

        while (true) {

            System.out.println("\n==============================");
            System.out.println("Welcome to S-Tech Mobile Stores");
            System.out.println("==============================");

            System.out.println("1. Check available phone makes");
            System.out.println("2. Check available phone models");
            System.out.println("3. Admin Only: Check phone quantity");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();

            if (choice == 1) {

                System.out.println("\nThese are the available phone makes we have:");

                for (MyAvailablePhoneMakes phoneMake : myAvailablePhoneMakes) {
                    System.out.println("- " + phoneMake.make);
                }

            } else if (choice == 2) {

                System.out.println("\nThese are the available phone models we have:");

                for (AvailablePhones phone : availablePhones) {
                    System.out.println("- " + phone.make + " " + phone.model);
                }

            } else if (choice == 3) {

                System.out.print("Please enter the admin password: ");
                String enteredPassword = input.next();

                if (enteredPassword.equals(admin.password)) {

                    System.out.println("\nAvailable Phones");

                    for (AvailablePhones phone : availablePhones) {

                        System.out.println("------------------------------------");
                        System.out.println("Make: " + phone.make);
                        System.out.println("Model: " + phone.model);
                        System.out.println("Features: " + phone.features);
                        System.out.println("Price: $" + phone.price);
                        System.out.println("Quantity: " + phone.quantity);

                    }

                } else {

                    System.out.println("Incorrect password. Access denied.");

                }

            } else if (choice == 4) {

                System.out.println("Thank you for visiting S-Tech Mobile Stores.");
                break;

            } else {

                System.out.println("Invalid choice.");

            }

        }

        input.close();

    }
}