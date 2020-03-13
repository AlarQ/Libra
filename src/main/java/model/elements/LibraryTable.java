package model.elements;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LibraryTable
{
    private String author;
    private String title;
    private String year;
    private String cover;
    private Button addBook;
    public LibraryTable(String author, String title, String year, String cover, Button addBook)
    {
        this.author = author;
        this.title = title;
        this.year = year;
        this.cover = cover;
        this.addBook = addBook;
    }

    public ImageView createCover(String cover)
    {
        Image image = null;
        String path = Main.properties.getProperty("path.image") + cover;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getTitle()
    {
        return title;
    }

    public String getYear()
    {
        return year;
    }

    public String getCover()
    {
        return cover;
    }

    public Button getAddBook()
    {
        return addBook;
    }

    public void setAddBook(Button addBook)
    {
        this.addBook = addBook;
    }

    @Override
    public String toString()
    {
        return "LibraryTable{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", cover='" + cover + '\'' +
                ", addBook=" + addBook +
                '}';
    }
}
