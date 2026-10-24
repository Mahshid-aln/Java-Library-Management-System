public class Book {
    private String id,title,author, type;
    private boolean available;
     private static int   counter=1;
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        id="B-"+counter;
        counter++;
        available=true;
    }
    enum bookStatus {
        Avaliable,borrowed;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        if(title!=null && !title.isBlank()) {
            this.title = title;
        }
        else {
            System.out.println("Title is invalid");
        }
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        if(author!=null && !author.isBlank()) {
            this.author = author;
        }
        else {
            System.out.println("Author is invalid");
        }
    }
    public boolean isAvailable() {
        return available;
    }
    public String getId() {
        return id;
    }
    public void borrow() {
       available=false;
        bookStatus status=bookStatus.borrowed;
    }
    public void returnBook() {
        available=true;
        bookStatus status=bookStatus.Avaliable;
    }
  public String toString() {
      return  ("ID: "+ id + "\t Title: " +title + "\t Author: " + author  + "\t Available:  "+available);
  }
}