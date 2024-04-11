package Libary;

import Product.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Theses extends Product {
    private static final String url = "jdbc:mysql://localhost:3306/library";
    private static final String username = "root";
    private static final String password = "";

    private static Connection conn = null;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    private static Scanner s;

    private String author;

    public Theses() {
        s = new Scanner(System.in);
    }

    public Theses(int id, String title, String author, String genre, String date, String publisher) {
        super(id, title, genre, date, publisher);
        this.author = author;
    }

    public void addThesis() {
        System.out.println("Enter Thesis's Title: ");
        title = s.nextLine();
        System.out.println("Enter Thesis's Author: ");
        author = s.nextLine();
        System.out.println("Enter Thesis's Genre: ");
        genre = s.nextLine();
        System.out.println("Enter Thesis's Date of Publish: ");
        date = s.nextLine();
        System.out.println("Enter Thesis's Publisher: ");
        publisher = s.nextLine();

        try {
            conn = DriverManager.getConnection(url, username, password);
            pst = conn.prepareStatement("INSERT INTO theses(Title, Author, Genre, Date, Publisher) VALUES(?, ?, ?, ?, ?)");
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setString(3, genre);
            pst.setString(4, date);
            pst.setString(5, publisher);
            pst.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void showThesis() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            pst = conn.prepareStatement("SELECT * FROM theses");
            rs = pst.executeQuery();

            while(rs.next()) {
                id = rs.getInt("ID");
                title = rs.getString("Title");
                author = rs.getString("Author");
                genre = rs.getString("Genre");
                date = rs.getString("Date");
                publisher = rs.getString("Publisher");

                System.out.println(toString());
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateThesis() {
        try {
            System.out.println("Enter Thesis's ID: ");
            id = validation(id);
            conn = DriverManager.getConnection(url, username, password);
            pst = conn.prepareStatement("SELECT * FROM theses WHERE ID = ?");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                System.out.println("Enter Thesis's New Title: ");
                title = s.nextLine();
                System.out.println("Enter Thesis's New Author: ");
                author = s.nextLine();
                System.out.println("Enter Thesis's New Genre: ");
                genre = s.nextLine();
                System.out.println("Enter Thesis's New Date of Publish: ");
                date = s.nextLine();
                System.out.println("Enter Thesis's New Publisher: ");
                publisher = s.nextLine();

                pst = conn.prepareStatement("UPDATE theses SET Title = ?, Author = ?, Genre = ?, Date = ?, Publisher = ?");
                pst.setString(1, title);
                pst.setString(2, author);
                pst.setString(3, genre);
                pst.setString(4, date);
                pst.setString(5, publisher);
                pst.executeUpdate();
                System.out.println("Update Thesis Successfully!");
            } else {
                System.out.println("No Thesis Found!");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteThesis() {
        try {
            System.out.println("Enter Thesis's ID: ");
            id = validation(id);
            conn = DriverManager.getConnection(url, username, password);
            pst = conn.prepareStatement("SELECT * FROM theses WHERE ID = ?");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                pst = conn.prepareStatement("DELETE FROM theses");
                pst.executeUpdate();
                System.out.println("Delete Thesis Successfully!");
            } else {
                System.out.println("No Thesis Found!");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int validation(int validate) {
        while(true) {
            try {
                validate = Integer.parseInt(s.nextLine());
                return validate;
            } catch(NumberFormatException e) {
                System.out.println("Invalid Input! You must enter numbers only: ");
            }
        }
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nGenre: " + genre +
                "\nDate of Publish: " + date +
                "\nPublisher: " + publisher;
    }
}

