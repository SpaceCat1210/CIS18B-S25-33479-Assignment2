//imports useful stuff
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
interface IBorrowable
{
    void borrowItem(String borrower);    
    void retunrItem();
    boolean isBorrowed();
}

class Item 
{
    String title;

    public Item(String title, int publicationYear)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
}
//creates book class that inherits from the item class using extends
class Book extends Item implements IBorrowable //implements IBorrowbale helps follow the stuff defind in the Iborrowable interface
{
    String borrower;

    public Book(String title, int publicationYear, String author, String ISBN)
    {
        //A super pulls from the Item class
        super(title, publicationYear);
    }
    
    
    public void borrowItem(String borrower)
    {     
      if (this.borrower == null)
      {
        this.borrower = borrower;
        System.out.println(title + " borrowed by " + borrower);
      } else {
        System.out.println(title + " is already borrowed.");
      }
    }
    
    
    
    public void retunrItem()
    {
       
        if (borrower != null) 
        {
            System.out.println(title + " returned by " + borrower);
            borrower = null;
        }
    }

    
    
    public boolean isBorrowed()
    {
        return borrower != null;
    }

}
//creates the magazine 
class Magazine extends Item
{

    public Magazine(String title, int publicationYear, int issueNumber)
    {
        super(title, publicationYear);
    }

}
//Creates the array where the books and magazine are going to be listed 
class Library
{
    static Library instance;
    List<Item> items;

    Library()
    {
        items = new ArrayList<>();
    }

    public static Library getInstance()
    {
        if (instance == null)
        {
            instance = new Library();
        }
        return instance;
    }
    public void addItem(Item item){
        items.add(item);
    }
    public void listAvailableItems()
    {
        for (Item item : items)
        {
            if (item instanceof Book book)
            {
                if (!book.isBorrowed())
                {
                    System.out.println("Book: " + book.getTitle());
                }
            }   else{
                System.out.println("Magazine: " + item.getTitle());
            }
        }
    }
    //looks for item by title
    public Item findItemByTitle(String title)
    {
        for (Item item : items)
        {
            if (item.getTitle().equalsIgnoreCase(title))
            {
                return item;
            }
        }
        return null;
    }

}
//This helps so I can modify the existing code
class LibraryItemFactory
{
    public static Item createItem(String type, String title, int publicationYear, String extraData)
    {
        if (type.equalsIgnoreCase("book"))
        {   //The UUID is used in this code to help identify each book by giving it a unique identifier. So if two books have the same name and autor, itll each book a number.
            return new Book(title, publicationYear, extraData, UUID.randomUUID().toString());
        } else if (type.equalsIgnoreCase("magazine")){
            return new Magazine(title, publicationYear, Integer.parseInt(extraData));
        }
        return null;
    }
}
//Code to test the script above
public class LibraryTest
{
    public static void main(String[] args)
    {
        Library library = Library.getInstance();

        Item book1 = LibraryItemFactory.createItem("book", "Coding for Dummies", 2024, "Kai Johnson");
        Item book2 = LibraryItemFactory.createItem("book", "Why I hate Coding", 2020, "Dylan Noriega");
        Item magazine = LibraryItemFactory.createItem("magazine", "Top 10 Fortnite Skins", 2025, "20");

        library.addItem(book1);
        library.addItem(book2);
        library.addItem(magazine);

        System.out.println("Available items before borrowing:");
        library.listAvailableItems();

        ((Book) book1).borrowItem("Dakota");

        System.out.println("\nAvailable items after borrowing:");
        library.listAvailableItems();

        ((Book) book1).retunrItem();

        System.out.println("\nAvailable items after returning:");
        library.listAvailableItems();

        Item foundItem = library.findItemByTitle("Why I hate Coding");
        System.out.println("\nSearching for 'Why I hate Coding': " + (foundItem != null ? foundItem.getTitle() : "Not Found"));
    }
}