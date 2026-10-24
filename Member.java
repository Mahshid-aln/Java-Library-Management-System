import java.util.ArrayList;

public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private String nationalId;
    private static int counter=1;
    private ArrayList<Book> borrowedBooks;
    public Member( String firstName, String lastName, String nationalId) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.nationalId=nationalId;
            id = "M-" + counter++;
            borrowedBooks = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public void setFirstName(String firstName) {
        if(firstName!=null && !firstName.isBlank()) {
            this.firstName = firstName;
        }
        else {
            System.out.println("First name is invalid");
        }
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lastName) {
        if(lastName!=null && !lastName.isBlank()) {
            this.lastName = lastName;
        }
        else {
            System.out.println("Last name is invalid");
        }
    }
    public String getLastName() {
        return lastName;
    }
    public String getNationalId() {
        return nationalId;
    }
    public boolean  addBook(Book book) {
        if( book!= null && !borrowedBooks.contains(book) ) {
            borrowedBooks.add(book);
            return true;

        }
        return false;
    }
    public boolean removeBook(Book book) {
        if( book!= null && borrowedBooks.contains(book) ) {
            borrowedBooks.remove(book);
            return true;
        }
        return false;
    }
    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public String borrowBook() {
        String result = "";
        for (Book book : borrowedBooks) {
            result += book.getId()+"-"+book.getTitle()+"\n";
        }
        return result;
    }
    public String toString(){
        return ("id: "+id+"\tFullName: "+firstName+" "+lastName+ "\tnationalId: "+nationalId+"\tborrowed books: "+borrowBook());
    }
}
