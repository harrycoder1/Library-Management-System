import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

class Book {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }
}

class Library {
    private List<Book> books;

    public Library() {
        this.books = new java.util.ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getAllBooks() {
        return books;
    }
}

public class LibraryManagementSystemGUI extends JFrame {

    private Library library;

    private JTextField titleField, authorField, isbnField;
    private JTextArea displayArea;

    private JButton addButton, displayButton, removeButton, clearButton;

    public LibraryManagementSystemGUI() {
        library = new Library();

        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        layoutComponents();

        setVisible(true);
    }

    private void initComponents() {
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        isbnField = new JTextField(20);

        addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        displayButton = new JButton("Display Books");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBooks();
            }
        });

        removeButton = new JButton("Remove Book");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });

        clearButton = new JButton("Clear Display");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearDisplay();
            }
        });

        displayArea = new JTextArea(15, 40);
        displayArea.setEditable(false);
    }

    private void layoutComponents() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);

        panel.add(addButton);
        panel.add(displayButton);
        panel.add(removeButton);
        panel.add(clearButton);

        panel.add(new JScrollPane(displayArea));

        add(panel);
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
            Book book = new Book(title, author, isbn);
            library.addBook(book);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
        }
    }

    private void displayBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            displayArea.setText("No books in the library.");
        } else {
            String bookDetails = books.stream()
                    .map(book -> "Title: " + book.getTitle() + "\nAuthor: " + book.getAuthor() + "\nISBN: " + book.getIsbn() + "\n\n")
                    .collect(Collectors.joining());
            displayArea.setText(bookDetails);
        }
    }

    private void removeBook() {
        String titleToRemove = JOptionPane.showInputDialog(this, "Enter the title to remove:");
        if (titleToRemove != null && !titleToRemove.isEmpty()) {
            List<Book> books = library.getAllBooks();
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(titleToRemove)) {
                    library.removeBook(book);
                    JOptionPane.showMessageDialog(this, "Book removed successfully!");
                    displayBooks();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Book with title '" + titleToRemove + "' not found.");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the title to remove a book.");
        }
    }

    private void clearDisplay() {
        displayArea.setText("");
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagementSystemGUI();
            }
        });
    }
}
