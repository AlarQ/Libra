package Model.Elements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "genre")
    private String genre;

    @Column(name = "cover")
    private String cover;

    @Column(name = "year_of_publication")
    private int yearOfPublication;

    @Column(name = "ISBN")
    private String ISBN;

    @Column(name = "rating")
    private float rating;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "book_user",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public void addUser(User user)
    {
        if (users == null)
            users = new ArrayList<>();
        users.add(user);
    }

    public Book()
    {
    }

    public Book(String author, String title)
    {
        this.author = author;
        this.title = title;
    }

    public Book(String author, String title, String publisher, int yearOfPublication, String genre, String ISBN, String cover)
    {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.cover = cover;
        this.genre = genre;
        this.ISBN = ISBN;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", genre='" + genre + '\'' +
                ", cover='" + cover + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", ISBN='" + ISBN + '\'' +
                ", users=" + users +
                '}';
    }

    public String getTitle()
    {
        return title;
    }

    public List<User> getUsers()
    {
        return users;
    }
}
