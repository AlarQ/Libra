package model.elements;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;
import model.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Data
public class LibraryTable
{
    private String author;
    private String title;
    private String year;
    private String cover;
    private Button addBook;

    public LibraryTable(String author, String title, String year, String cover, Button addBook) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.cover = cover;
        this.addBook = addBook;
    }

    //przenieśc to
    public static ImageView createCover(String cover) {
        Image image = null;
        String path = Main.properties.getProperty("path.image");
        if (cover.equals("Cover"))
            path = path + "Cover.png";
        else
            path = path + cover;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        return imageView;
    }

}
