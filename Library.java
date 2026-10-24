import java.sql.SQLOutput;
import java.util.*;

public class Library {
    private HashMap<String, Book> books;
    private HashMap<String, Member> members;
    public Library() {
        books = new HashMap<>();
        members = new HashMap<>();
    }

    public boolean addBook(Book book) {
        if (book == null) {
            System.out.println("Book is null");
            return false;
        }
        if (books.containsKey(book.getId())) {
            System.out.println("Book already exists");
            return false;
        }
        books.put(book.getId(), book);
        System.out.println("book added");
        return true;
    }

    public boolean addMember(Member member) {
        if (member == null) {
            System.out.println("Member is null");
            return false;
        }
        if (members.containsKey(member.getId())) {
            System.out.println("Member already exists");
            return false;
        }
        if (!isValidNationalCode(member.getNationalId())) {
            throw new IllegalArgumentException("National code is invalid");
        }
        for (Member existingMember : members.values()) {
            if (existingMember.getNationalId().equals(member.getNationalId())) {
                System.out.println("NationalId is already in use");
                return false;
            }
        }
        members.put(member.getId(), member);
        System.out.println("Member added");
        return true;
    }

    public boolean removeBook(String id) {
        Book book = books.get(id);
        if (book == null) {
            System.out.println("Book not found");
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("Book already borrowed");
            return false;

        }
        books.remove(id);
        System.out.println("Book removed");
        return true;
    }

    public boolean removeMember(String id) {
        Member member = members.get(id);
        if (member == null) {
            System.out.println("Member not found");
            return false;
        }
        if (!member.getBorrowedBooks().isEmpty()) {
            System.out.println("Could not remove member");
            return false;
        }
        members.remove(id);
        System.out.println("Member removed");
        return true;
    }

    public boolean borrowBook(String memberId, String bookId) {
        if(memberId == null || bookId == null || memberId.isEmpty() || bookId.isEmpty()) {
            throw new IllegalArgumentException("Member or Book Id is invalid");
        }

        Member member = members.get(memberId);
        Book book = books.get(bookId);

        if (member == null) {
            System.out.println("Member not found");
            return false;
        }

        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available");
        }

        if (!member.addBook(book)) {
            System.out.println("Could not add book to member");
            return false;
        }
        book.borrow();
        System.out.println("Book borrowed");
        return true;
    }

    public boolean returnBook(String memberId, String bookId) {
        if(memberId == null || bookId == null || memberId.isEmpty() || bookId.isEmpty()) {
            throw new IllegalArgumentException("Member or Book Id is invalid");
        }
        Member member = members.get(memberId);
        Book book = books.get(bookId);
        if (member == null) {
            throw new IllegalArgumentException("member not found");
        }
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        if (!member.getBorrowedBooks().contains(book)) {
            throw new IllegalStateException("Book is not borrowed");
        }
        if (!member.removeBook(book)) {
            throw new IllegalStateException("Could not remove book from member");
        }
        book.returnBook();
        System.out.println("Book returned");
        return true;
    }

    public void printBooks() {
        for (Map.Entry<String, Book> entry : books.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

    public void printMembers() {
        for (Map.Entry<String, Member> entry : members.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

    public ArrayList<Book> findBookByTitle(String title) {
        ArrayList<Book> searchBooks = new ArrayList<>();
        for (Map.Entry<String, Book> entry : books.entrySet()) {
            if (entry.getValue().getTitle().equals(title)) {
                searchBooks.add(entry.getValue());
            }
        }
        return searchBooks;
    }

    public Book findBookById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID is null");
        }
        Book book = books.get(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        return book;
    }

    public ArrayList<Member> findMemberByName(String firstname, String lastname) {
        ArrayList<Member> searchMembers = new ArrayList<>();
        for (Map.Entry<String, Member> entry : members.entrySet()) {
            if (entry.getValue().getFirstName().equals(firstname) && entry.getValue().getLastName().equals(lastname)) {
                searchMembers.add(entry.getValue());
            }
        }
        return searchMembers;
    }

    public Member findMemberById(String id) {
        Member member = members.get(id);
        if (member == null) {
            System.out.println("Member not found");
        }
        return member;
    }

    public Member findMemberByNationalId(String nationalId) {
        for (Member member : members.values()) {
            if(member.getNationalId().equals(nationalId)) {
                return member;
            }
        }
        System.out.println("Member not found");
        return null;
    }
    public ArrayList<Book> getBorrowedBooks(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("List is null");
            return null;
        }
        return member.getBorrowedBooks();
    }

    public boolean editBook(String bookId, String title, String author) {
        Book book = books.get(bookId);
        if (book == null) {
        System.out.println("Book not found");
            return false;
        }
        book.setTitle(title);
        book.setAuthor(author);
        return true;
    }

    public boolean editMember(String memberId, String firstName, String lastName) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("Member not found");
            return false;
        }
        member.setFirstName(firstName);
        member.setLastName(lastName);
        return true;
    }

    //manus
    public static void mainMenu() {
        System.out.println("""
                ===== LIBRARY MENU =====
                1. Add
                2. Remove
                3. Edit
                4. Print
                5. Manage Books
                6. Search
                7. Exit
                ========================
                """);
    }

    public static void addMenu() {
        System.out.println("""
                        ===== Add MENU =====
                        1. Add Book
                        2. Add Member
                        3. Back
                """);
    }

    public static void removeMenu() {
        System.out.println("""
                        ===== Remove MENU =====
                        1. Remove Book
                        2. Remove Member
                        3. Back
                """);
    }

    public static void editMenu() {
        System.out.println("""
                        ===== Edit MENU =====
                        1. Edit Book
                        2. Edit Member
                        3. Back
                """);
    }

    public static void printMenu() {
        System.out.println("""
                        ===== Print MENU =====
                        1. Print Books
                        2. Print Members
                        3. Back
                """);
    }

    public static void manageBooks() {
        System.out.println("""
                        ===== Manage Books =====
                        1. Borrow Book
                        2. Return Book
                        3. Show Borrowed Books
                        4. Back
                """);
    }

    public static void searchMenu() {
        System.out.println("""
                        ===== Search MENU ======
                        1. Search Books BY ID
                        2. Search Books BY Title
                        3. Search Members BY ID
                        4. Search Members BY Name
                        5. Search Members BY NationalId
                        6. Back
                """);
    }
    public static boolean isValidNationalCode(String nationalCode) {
        // باید دقیقاً 10 رقم باشد
        if (nationalCode == null || !nationalCode.matches("\\d{10}")) {
            return false;
        }
        int sum = 0;
        // 9 رقم اول
        for (int i = 0; i < 9; i++) {
            int digit = nationalCode.charAt(i) - '0';
            int weight = 10 - i;
            sum += digit * weight;
        }
        int remainder = sum % 11;
        int controlDigit = nationalCode.charAt(9) - '0';
        if (remainder < 2) {
            return controlDigit == remainder;
        } else {
            return controlDigit == 11 - remainder;
        }
    }
    public static void handleAddMenu(Library lib, Scanner input) {
        int addchoice = 0;
        while (addchoice != 3) {
            addMenu();
            try {
                addchoice = input.nextInt();
                input.nextLine();
                switch (addchoice) {
                    case 1:
                        System.out.println("Enter the book title: ");
                        String title = input.nextLine();
                        System.out.println("Enter the book author: ");
                        String author = input.nextLine();
                        Book b = new Book(title, author);
                        lib.addBook(b);
                        break;
                    case 2:
                        System.out.println("Enter the Member firstname: ");
                        String firstname = input.nextLine();
                        System.out.println("Enter the Member lastname: ");
                        String lastname = input.nextLine();
                        System.out.println("Enter the nationalId: ");
                        String nationalCode = input.nextLine();
                        Member m = new Member(firstname, lastname, nationalCode);
                        try {
                            lib.addMember(m);
                        }
                        catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Back to main menu");
                        break;
                }
            }
            catch (InputMismatchException e){
                System.out.println("Please enter a number.");
                input.nextLine();
            }
        }
    }
    public static void handleRemoveMenu(Library lib, Scanner input) {
        int removechoice = 0;
        while (removechoice != 3) {
            removeMenu();
            try{
            removechoice = input.nextInt();
            input.nextLine();
            switch (removechoice) {
                case 1:
                    System.out.println("Enter the idBook: ");
                    String idBook = input.nextLine();
                    lib.removeBook(idBook);
                    break;
                case 2:
                    System.out.println("Enter the idMember: ");
                    String idMember = input.nextLine();
                    lib.removeMember(idMember);
                    break;
                case 3:
                    System.out.println("Back to main menu");
                    break;
            }
        }
           catch(InputMismatchException e){
            System.out.println("Please enter a number.");
            input.nextLine();
        }
        }
    }
    public static void handleEditMenu(Library lib, Scanner input) {
        int editchoice = 0;
        while (editchoice != 3) {
            editMenu();
            try {
                editchoice = input.nextInt();
                input.nextLine();
                switch (editchoice) {
                    case 1:
                        System.out.println("Enter the idBook: ");
                        String idBook = input.nextLine();
                        Book B = lib.findBookById(idBook);
                        if (B != null) {
                            System.out.println("Enter the title: ");
                            String title = input.nextLine();
                            System.out.println("Enter the author: ");
                            String author = input.nextLine();
                            lib.editBook(idBook, title, author);
                        }
                        break;
                    case 2:
                        System.out.println("Enter the idMember: ");
                        String idMember = input.nextLine();
                        Member M = lib.findMemberById(idMember);
                        if (M != null) {
                            System.out.println("Enter FirstName: ");
                            String firstname = input.nextLine();
                            System.out.println("Enter LastName: ");
                            String lastname = input.nextLine();
                            lib.editMember(idMember, firstname, lastname);
                        }
                        break;
                    case 3:
                        System.out.println("Back to main menu");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                input.nextLine();
            }
        }
    }
    public static void handlePrintMenu(Library lib, Scanner input) {
        int printchoice = 0;
        while (printchoice != 3) {
            printMenu();
            try {
                printchoice = input.nextInt();
                input.nextLine();
                switch (printchoice) {
                    case 1:
                        lib.printBooks();
                        break;
                    case 2:
                        lib.printMembers();
                        break;
                    case 3:
                        System.out.println("Back to main menu");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                input.nextLine();
            }
        }
    }
    public static void handleManageMenu(Library lib, Scanner input) {
        int managebookschoice = 0;
        while (managebookschoice != 4) {
            manageBooks();
            try {
            managebookschoice = input.nextInt();
            input.nextLine();
            switch (managebookschoice) {
                case 1:
                    System.out.println("Enter the idMember: ");
                    String idMember = input.nextLine();
                    System.out.println("Enter the idBook: ");
                    String idBook = input.nextLine();
                    try {
                        lib.borrowBook(idMember, idBook);
                    }
                    catch (IllegalArgumentException | IllegalStateException e){
                        System.out.println(e.getMessage());
                }
                    break;
                case 2:
                    System.out.println("Enter the idMember: ");
                    idMember = input.nextLine();
                    System.out.println("Enter the idBook: ");
                    idBook = input.nextLine();
                    try {

                        lib.returnBook(idMember, idBook);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Enter the idMember: ");
                    idMember = input.nextLine();
                    System.out.println(lib.getBorrowedBooks(idMember));
                    break;
                case 4:
                    System.out.println("Back to main menu");
                    break;
            }
        }
           catch (InputMismatchException e){
            System.out.println("Please enter a number.");
            input.nextLine();
        }
            }
    }
    public static void handleSearchMenu(Library lib, Scanner input) {
        int searchchoice = 0;
        while (searchchoice != 6) {
            searchMenu();
            try {
                searchchoice = input.nextInt();
                input.nextLine();
                switch (searchchoice) {
                    case 1:
                        System.out.println("Enter the idBook: ");
                        String idBook = input.nextLine();
                        try {
                            System.out.println(lib.findBookById(idBook));
                        }
                        catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Enter the title: ");
                        String title = input.nextLine();
                        ArrayList<Book> results = lib.findBookByTitle(title);
                        for (Book book : results) {
                            System.out.println(book);
                        }
                        if (results.isEmpty()) {
                            System.out.println("No books found");
                        }
                        break;
                    case 3:
                        System.out.println("Enter the idMember: ");
                        String idMember = input.nextLine();
                        System.out.println(lib.findMemberById(idMember));
                        break;
                    case 4:
                        System.out.println("Enter the Member firstname: ");
                        String firstname = input.nextLine();
                        System.out.println("Enter the Member lastname: ");
                        String lastname = input.nextLine();
                        ArrayList<Member> result = lib.findMemberByName(firstname, lastname);
                        for (Member member : result) {
                            System.out.println(member);
                        }

                        break;
                    case 5:
                        System.out.println("Enter the NationalId: ");
                        String nationalId = input.nextLine();
                        System.out.println(lib.findMemberByNationalId(nationalId));
                        break;
                    case 6:
                        System.out.println("Back to main menu");
                        break;
                }
            }
                       catch (InputMismatchException e){
                    System.out.println("Please enter a number.");
                    input.nextLine();
                }
        }
    }
    public static void main(String[] args) {
        Library lib = new Library();
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (choice != 7) {
            mainMenu();
            try{
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    handleAddMenu(lib, input);
                    break;
                case 2:
                    handleRemoveMenu(lib, input);
                    break;
                case 3:
                    handleEditMenu(lib, input);
                    break;
                case 4:
                    handlePrintMenu(lib, input);
                    break;
                case 5:
                    handleManageMenu(lib, input);
                    break;
                case 6:
                    handleSearchMenu(lib, input);
                    break;
                case 7:
                    break;
            }
            }
            catch (InputMismatchException e){
                System.out.println("Please enter a number.");
                input.nextLine();
            }
        }
    }
}

