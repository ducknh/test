package Libary;

import Product.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Magazines extends Product {
    private static final String url = "jdbc:mysql://localhost:3306/library";
    private static final String username = "root";
    private static final String password = "";

    private static Connection conn = null;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    private static Scanner s;

    public Magazines() {
        s = new Scanner(System.in);
    }

    public Magazines(int id, String title, String genre, String date, String publisher) {
		super(id, title, genre, date, publisher);
	}

    public void addMagazine() {
        System.out.println("Enter Magazine's Title: ");
        title = s.nextLine();
        System.out.println("Enter Magazine's Genre: ");
        genre = s.nextLine();
        System.out.println("Enter Magazine's Date of Publish: ");
        date = s.nextLine();
        System.out.println("Enter Magazine's Publisher: ");
        publisher = s.nextLine();

        try {
            conn = DriverManager.getConnection(url, username, password);
            pst = conn.prepareStatement("INSERT INTO magazines(Title, Author, Genre, Date, Publisher) VALUES(?, ?, ?, ?, ?)");
            pst.setString(1, title);
            pst.setString(2, genre);
            pst.setString(3, date);
            pst.setString(4, publisher);
            pst.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void showMagazine() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            pst = conn.prepareStatement("SELECT * FROM magazines");
            rs = pst.executeQuery();

            while(rs.next()) {
                id = rs.getInt("ID");
                title = rs.getString("Title");
                genre = rs.getString("Genre");
                date = rs.getString("Date");
                publisher = rs.getString("Publisher");

                System.out.println(toString());
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMagazine() {
        try {
            System.out.println("Enter Magazine's ID: ");
            id = validation(id);
            conn = DriverManager.getConnection(url, username, password);
            pst = conn.prepareStatement("SELECT * FROM magazines WHERE ID = ?");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                System.out.println("Enter Magazine's New Title: ");
                title = s.nextLine();
                System.out.println("Enter Magazine's New Genre: ");
                genre = s.nextLine();
                System.out.println("Enter Magazine's New Date of Publish: ");
                date = s.nextLine();
                System.out.println("Enter Magazine's New Publisher: ");
                publisher = s.nextLine();

                pst = conn.prepareStatement("UPDATE magazines SET Title = ?, Author = ?, Genre = ?, Date = ?, Publisher = ?");
                pst.setString(1, title);
                pst.setString(2, genre);
                pst.setString(3, date);
                pst.setString(4, publisher);
                pst.executeUpdate();
                System.out.println("Update Magazine Successfully!");
            } else {
                System.out.println("No Magazine Found!");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMagazine() {
        try {
            System.out.println("Enter Magazine's ID: ");
            id = validation(id);
            conn = DriverManager.getConnection(url, username, password);
            pst = conn.prepareStatement("SELECT * FROM magazines WHERE ID = ?");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                pst = conn.prepareStatement("DELETE FROM magazines");
                pst.executeUpdate();
                System.out.println("Delete Magazine Successfully!");
            } else {
                System.out.println("No Magazine Found!");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

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
                "\nGenre: " + genre +
                "\nDate of Publish: " + date +
                "\nPublisher: " + publisher;
    }
}
