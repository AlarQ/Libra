package model.elements;

import lombok.Data;
import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "add_date")
    private Date addDate;
    @Column(name = "admin")
    private Byte admin;
    @OneToMany(
            mappedBy = "user",
            targetEntity = BookUser.class,
            fetch = FetchType.EAGER
    )
    private List<BookUser> books = new ArrayList<>();

    public List<Book> lastAddedBooks() {
        Map<Book, Date> result;
        result = books.stream().collect(
                Collectors.toMap(BookUser::getBook, BookUser::getDate));

        Comparator<Map.Entry<Book, Date>> valueComparator = (e1, e2) -> {
            Date v1 = e1.getValue();
            Date v2 = e2.getValue();
            return v2.compareTo(v1);
        };

        Set<Map.Entry<Book, Date>> entries = result.entrySet();
        List<Map.Entry<Book, Date>> listOfEntries = new ArrayList<Map.Entry<Book, Date>>(entries);
        Collections.sort(listOfEntries, valueComparator);

        LinkedHashMap<Book, Date> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        List<Book> lastAdded = new ArrayList<>();

        for (Map.Entry<Book, Date> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
            lastAdded.add(entry.getKey());
        }
        return lastAdded;
    }

    public boolean deleteUser(String login) {
        List result;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query quer = session.createQuery("FROM User u WHERE u.login=:login");
        quer.setParameter("login", login);
        result = quer.getResultList();
        session.getTransaction().commit();
        session.close();
        if (result.size() > 0) {
            Session session1 = HibernateUtil.getSession();
            session1.beginTransaction();
            Query query = session1.createQuery("delete FROM User u " +
                    "WHERE u.login='" + login + "'");
            int res = query.executeUpdate();
            session1.getTransaction().commit();
            session1.close();
            return res > 0;
        }
        return false;
    }

    public List<Book> topRatedBooks() {
        Map<Book, Float> bookToRatingMap;
        bookToRatingMap = books.stream().collect(
                Collectors.toMap(BookUser::getBook, BookUser::getRating));

        Set<Map.Entry<Book, Float>> entries = bookToRatingMap.entrySet();
        List<Map.Entry<Book, Float>> listOfEntries = new ArrayList<>(entries);
        List<Map.Entry<Book, Float>> sortedListOfEntries = listOfEntries.stream()
                .sorted((e1,e2)->e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());

        LinkedHashMap<Book, Float> sortedBookToRatingMap = new LinkedHashMap<>(sortedListOfEntries.size());
        List<Book> topRated = new ArrayList<>();

        for (Map.Entry<Book, Float> entry : sortedListOfEntries) {
            sortedBookToRatingMap.put(entry.getKey(), entry.getValue());
            topRated.add(entry.getKey());
        }
        Set<Map.Entry<Book, Float>> entrySetSortedByValue = sortedBookToRatingMap.entrySet();
        for (Map.Entry<Book, Float> mapping : entrySetSortedByValue) {
            System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
        }
        return topRated;
    }

    public void addUser() {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(this);
        tx.commit();
        session.close();
    }

    public static User getUserByEmail(String email) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User u " +
                "WHERE u.email=:email");
        query.setParameter("email", email);
        List result = query.getResultList();

        session.getTransaction().commit();
        if (!result.isEmpty())
            return (User) result.get(0);
        else return null;
    }

    public boolean isOwned(Book book) {
        books = this.getBooks();
        for (BookUser b : books) {
            //change it using equals
            if (b.getBook().getISBN().equals(book.getISBN()))
                return true;
        }
        return false;
    }

    public void addBook(Book book) {
        if (books == null)
            books = new ArrayList<>();
        {
            BookUser bookUser = new BookUser(book, this);
            books.add(bookUser);
            bookUser.saveBookUser();
        }
    }


    public List<BookUser> getBooks() {
        return books;
    }

    //skoro nie pozwala na brak pól dla ktorych sa gettersy
    //to coś jest nie tak z budową klasy
    public int retNumberOfBooks() {
        return books.size();
    }

    public String retFavoriteAuthor() {
        if (!books.isEmpty()) {
            List<String> authors = books.stream().map(b -> b.getBook().getAuthor()).collect(Collectors.toList());

            List<String> favorites = new ArrayList<>();
            Map<String, Long> occurrences =
                    authors.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

            System.out.println(occurrences);
            int c = 0;
            long maxValueInMap = (Collections.max(occurrences.values()));
            for (Map.Entry<String, Long> entry : occurrences.entrySet()) {
                if (entry.getValue() == maxValueInMap & c < 2) {
                    favorites.add(entry.getKey());
                    c++;
                }
            }
            if (!favorites.isEmpty()) {
                StringBuilder sB = new StringBuilder();
                for (String s : favorites) {
                    sB.append(s);
                    sB.append(" | ");
                }
                String result = sB.toString();
                return result;
            } else
                return "You don't have books on your shelf";
        }
        return "";
    }

    public User() {
        this.admin = 0;
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.addDate = new Date();
        this.admin = 0;
    }
}

