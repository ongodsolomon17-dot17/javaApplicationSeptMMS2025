// write a java program that stores in an array 10 books titles and allow users to view all books, search for a books
//write a java program to accept an employee name hours worked hour rate and use what u have collected to calculate the// 

import java.util.Scanner;
import java.util.ArrayList;
public class BookStore{

    String title;
    String author;
    int yearPublished;
    String about;

    //constructor 
    public BookStore(String title, String author, int yearPublished, String about){
        
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.about = about;

    }

    //display brief details of the books we have.
    public void displayBookStore(){
        System.out.println("Title: " + title);
        System.out.println("About: " + about);
    }
    //method to ask users for book details
    public void searchBook(Scanner input) {
        System.out.print("Enter the title of the book you want to read");
        String searchTitle = input.nextLine();
            boolean found = false;
            for (BookStore books : BookStore){
                if 
    (books.title.equalsIgnoreCase(searchTitle)){
        System.out.println("Book found \n These are the details of the book you searched for: ");
                                                    books.displayBookStore();
                                                    found = true;
                                                    break;
    }
        
            }

            if (!found {
                System.out.println("Book title not found, check for existing Book titles and try again");
            }
            
    }
    public static void main(String[] args){


        Scanner input = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<Book>();

        books.add(new Book("the Great Gatsby", "F. Scott Fitzgerald", 1925, "This is a book written by F. Scott Fitzgerald and is a very classic American novel very fantastic to read."));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, "This is a book written by Harper Lee and is a novel about the serious issues of rape and racial inequality."));
        books.add(new Book("1984", "George Orwell", 1949, "This is a book written by George Orwell and is a dystopian social science fiction novel and cautionary tale about the dangers of totalitarianism."));
        books.add(new Book("Pride and Prejudice", "Jane Austen", 1813, "This is a book written by Jane Austen and is a romantic novel of manners that depicts the British Regency era."));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "This is a book written by J.D. Salinger and is a novel about teenage rebellion and angst."));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", 1937, "This is a book written by J.R.R. Tolkien and is a fantasy novel and children's book by English author J. R. R. Tolkien."));
        books.add(new Book("Fahrenheit 451", "Ray Bradbury", 1953, "This is a book written by Ray Bradbury and is a dystopian novel about a future American society where books are outlawed and 'firemen' burn any that are found."));
        books.add(new Book("Moby-Dick", "Herman Melville", 1851, "This is a book written by Herman Melville and is a novel about the voyage of the whaling ship Pequod and its captain, Ahab, who is obsessed with hunting the giant white whale Moby Dick."));
        books.add(new Book("War and Peace", "Leo Tolstoy", 1869, "This is a book written by Leo Tolstoy and is a novel that chronicles the history of the French invasion of Russia and the impact of the Napoleonic era."));
        books.add(new Book("The Odyssey", "Homer", -800, "This is a book written by Homer and is an epic poem that follows the Greek hero Odysseus on his journey home after the fall of Troy."));

        // creatting an ArrayList of 10 book titles
    }
    
input.close();
}