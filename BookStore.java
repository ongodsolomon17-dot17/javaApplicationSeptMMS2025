import java.util.Scanner;
import java.util.ArrayList;

// Book class to hold book data
class Book {
    String title;
    String author;
    int yearPublished;
    String about;

    //constructor 
    public Book(String title, String author, int yearPublished, String about){
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.about = about;
    }

    //display brief details of the book
    public void displayBook(){
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year: " + yearPublished);
        System.out.println("About: " + about);
        System.out.println("---------------------------");
    }
}

public class BookStore{

    //method to search for a book
    public static void searchBook(Scanner input, ArrayList<Book> books) {
        System.out.print("Enter the title of the book you want to search: ");
        String searchTitle = input.nextLine();
        boolean found = false;

        for (Book book : books){ // loop through the list
            if (book.title.equalsIgnoreCase(searchTitle)){
                System.out.println("Book found! \nThese are the details:");
                book.displayBook();
                found = true;
                break;
            }
        }

        if (!found) { // fixed missing )
            System.out.println("Book title not found. Check for existing Book titles and try again");
        }
    }

    //method to view all books
    public static void viewAllBooks(ArrayList<Book> books){
        System.out.println("\n=== ALL BOOKS IN STORE ===");
        for (Book book : books){
            book.displayBook();
        }
    }

    //method for employee pay
    public static void calculateEmployeePay(Scanner input){
        System.out.print("\nEnter Employee Name: ");
        String name = input.nextLine();

        System.out.print("Enter Hours Worked: ");
        double hours = input.nextDouble();

        System.out.print("Enter Hourly Rate: ");
        double rate = input.nextDouble();
        input.nextLine(); // clear buffer

        double pay = hours * rate;
        System.out.println("\nEmployee: " + name);
        System.out.println("Total Pay: $" + pay);
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<Book>(); // fixed: was BookStore

        // adding 10 books
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "A classic American novel about the Jazz Age."));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, "A novel about racial inequality in the Deep South."));
        books.add(new Book("1984", "George Orwell", 1949, "A dystopian novel about totalitarianism and surveillance."));
        books.add(new Book("Pride and Prejudice", "Jane Austen", 1813, "A romantic novel set in the British Regency era."));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "A novel about teenage rebellion and angst."));
        books.add(new Book("The Hobbit", "J.R. Tolkien", 1937, "A fantasy novel about Bilbo Baggins' adventure."));
        books.add(new Book("Fahrenheit 451", "Ray Bradbury", 1953, "A dystopian novel where books are banned and burned."));
        books.add(new Book("Moby-Dick", "Herman Melville", 1851, "A novel about Captain Ahab's obsession with a white whale."));
        books.add(new Book("War and Peace", "Leo Tolstoy", 1869, "A historical novel about the Napoleonic Wars."));
        books.add(new Book("The Odyssey", "Homer", -800, "An epic poem about Odysseus' journey home after the Trojan War."));

        // Simple menu
        int choice;
        do {
            System.out.println("\n=== BOOKSTORE MENU ===");
            System.out.println("1. View All Books");
            System.out.println("2. Search Book");
            System.out.println("3. Calculate Employee Pay");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine(); // clear buffer

            switch(choice){
                case 1: viewAllBooks(books); break;
                case 2: searchBook(input, books); break;
                case 3: calculateEmployeePay(input); break;
                case 4: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice");
            }
        } while(choice != 4);

        input.close();
    }
}