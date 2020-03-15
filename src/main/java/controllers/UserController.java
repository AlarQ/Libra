package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import model.HibernateUtil;
import model.Main;
import model.elements.Book;
import model.elements.BookUser;
import model.elements.LibraryTable;
import model.elements.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import windows_launchers.AppViewHandler;
import windows_launchers.ViewHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserController extends AbstractController
{
    @FXML private AnchorPane apYourBooks;
    @FXML private AnchorPane apLibrary;
    @FXML private AnchorPane apSettings;
    @FXML private Button btnYourBooks;
    @FXML private Button btnLibrary;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtTitle;
    @FXML private TextField txtPublisher;
    @FXML private TextField txtYearOfPublication;
    @FXML private TextField txtGenre;
    @FXML private TextField txtISBN;
    @FXML private TextField txtRating;
    @FXML private Button btnAddCover;
    @FXML private Button btnCancel;
    @FXML private Button btnConfirm;
    @FXML private Label lblAuthor;
    @FXML private Label lblTitle;
    @FXML private Label lblPublisher;
    @FXML private Label lblyearOfPublication;
    @FXML private Label lblGenre;
    @FXML private Label lblISBN;
    @FXML private Label lblCover;
    @FXML private Label lblRating;
    @FXML private Label lblNumberOfBooks;
    @FXML private Label lblFavAuthor;
    @FXML private GridPane gpLastAdded;
    @FXML private GridPane gpTopRatedByYou;
    @FXML private TableColumn<String, LibraryTable> colAuthor;
    @FXML private TableColumn<String, LibraryTable> colTitle;
    @FXML private TableColumn<String, LibraryTable> colYear;
    @FXML private TableColumn<String, LibraryTable> colCover;
    @FXML private TableColumn<LibraryTable,Button> colAction;
    @FXML private Button btnSearch;
    @FXML private TextField tfSearch;
    @FXML private TableView<LibraryTable> tvLibrary = new TableView<LibraryTable>();
    @FXML private Button btnGoBackToLogin;
    @FXML private Button btnDeleteUser;
    @FXML private TextField txtDeleteUser;
    @FXML private Button btnSettings;
    private static User actualUser;
    private ObservableList<LibraryTable> obsList = FXCollections.observableArrayList();

    public UserController(ViewHandler viewHandler)
    {
        super(viewHandler);
    }
    public UserController(){};
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        setBtnOnAction();
        apYourBooks.toFront();
        initColumns();
        lblNumberOfBooks.setText(Integer.toString(actualUser.retNumberOfBooks()));
        lblFavAuthor.setText(actualUser.retFavoriteAuthor());
        actualUser.lastAddedBooks();
        showLastAddedBooks();
        actualUser.topRatedBooks();
      showTopRatedByYou();

    }

    public void setBtnOnAction()
    {
        btnGoBackToLogin.setOnAction(this::goBackToLogin);
        btnSearch.setOnAction(this::showSearchResults);
        btnYourBooks.setOnAction(this::showYourBooks);
        btnLibrary.setOnAction(this::showLibrary);
        btnAddCover.setOnAction(this::addCover);
        btnConfirm.setOnAction(this::confirm);
        btnCancel.setOnAction(this::cancel);
        btnSettings.setOnAction(this::showSettings);
        btnDeleteUser.setOnAction(this::deleteUser);
    }

    private void deleteUser(ActionEvent event)
    {
        if(actualUser.getAdmin()==1)
        {
            String login = txtDeleteUser.getText();
            boolean res = actualUser.deleteUser(login);
            if(res)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User "+login+" deleted");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User "+login+" doesn't exist");
                alert.showAndWait();
            }
        }else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You're not admin user. You can't delete other users!");
            alert.showAndWait();
        }
    }

    public void showSearchResults(ActionEvent event)
    {
        tvLibrary.getItems().clear();
        String find = tfSearch.getText();
        List<Book> result;

        Session session = HibernateUtil.getSession();
        session.beginTransaction();


        Query query = session.createQuery("from Book where title Like :title");
        query.setParameter("title", "%" + find + "%");
        result = query.getResultList();

        for (int i = 0; i < result.size(); i++) {
            Book book = result.get(i);
            Button button = new Button("Add book");
            button.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e)
                {
                    System.out.println("adding book.....");
                    if (!actualUser.isOwned(book))
                    {
                        BookUser bookUser = new BookUser(book, actualUser, 5);
                        try
                        {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex)
                        {
                            ex.printStackTrace();
                        }
                        bookUser.saveBookUser();
                    }
                }
            });
            LibraryTable libTab = new LibraryTable(book.getAuthor(),
                    book.getTitle(), Integer.toString(book.getYearOfPublication()),
                    book.getCover(),button);
            obsList.add(libTab);
        }

        if (result.size() == 0)
            System.out.println("result is empty!");

        session.getTransaction().commit();
        session.close();

        System.out.println("******");
        for (LibraryTable lt : obsList)
            System.out.println(lt);
        tvLibrary.setEditable(true);
        tvLibrary.setItems(obsList);

    }
    public void showYourBooks(ActionEvent event)
    {
        apYourBooks.toFront();
    }

    public void showLibrary(ActionEvent e)
    {
        apLibrary.toFront();
    }

    public void showSettings(ActionEvent event) { apSettings.toFront(); }

    public void showLastAddedBooks()
    {
        List<Book> lastAdded = actualUser.lastAddedBooks();
        if(lastAdded.size()>0)
            prepareGridPane(gpLastAdded,lastAdded);
    }

    public void showTopRatedByYou()
    {
        List<Book> topRated = actualUser.topRatedBooks();
        if(topRated.size()>0)
        prepareGridPane(gpTopRatedByYou,topRated);
    }

    public void prepareGridPane(GridPane pane, List<Book> books)
    {
        List<String> covers = new ArrayList<>();

        String path;
        ImageView[] imageViews = new ImageView[5];
        System.out.println("!!!!!!!!!!!!!!!");
        System.out.println(books);
        for (int i = 0; i <= 4 && i<books.size(); i++) {
            path = Main.properties.getProperty("path.image");
            covers.add(books.get(i).getCover());
            Image image = null;
            if(covers.get(i).equals("Cover"))
                path = path+"Cover.png";
            else
                path =  path+covers.get(i);

            try {
                image = new Image(new FileInputStream(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageViews[i] = new ImageView(image);
            imageViews[i].setFitHeight(100);
            imageViews[i].setFitWidth(100);
            imageViews[i].setPreserveRatio(false);
            GridPane.setConstraints(imageViews[i], i, 0);
            pane.getChildren().add(imageViews[i]);
            if(covers.get(i).equals("Cover"))
            {
                Label label = prepareDefaultBookLabel(books.get(i).getTitle());
                label.setTextFill(Color.web("black"));
                GridPane.setConstraints(label, i, 0);
                pane.getChildren().add(label);
            }
        }
    }

    public void initColumns()
    {
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colCover.setCellValueFactory(new PropertyValueFactory<>("cover"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("addBook"));
    }
    //GDZIE TO UMIESCIC
    public Label prepareDefaultBookLabel(String text)
    {
        Label label = new Label(text);
        label.setTextFill(Color.web("#FFFFFF"));
        label.setWrapText(true);
        label.setMaxWidth(80);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    public void cancel(ActionEvent event)
    {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    //returns number of not-empty fields
    public boolean checkTextFields()
    {
        if (txtTitle.getText().trim().equals("")) {
            lblTitle.setText("This field can't be empty!");
            return false;
        }
        return true;
    }

    public void confirm(ActionEvent event)
    {
        int year;
        if (txtYearOfPublication.getText().equals(""))
            year = -1;
        else
            year = Integer.parseInt(txtYearOfPublication.getText());

        float rating;
        if (txtRating.getText().equals(""))
            rating = -1;
        else
            rating = Float.parseFloat(txtRating.getText());

        //!!!funkcja zmieniająca rating dla ksiązki!!!!!!!!
        Book book = new Book(txtAuthor.getText(), txtTitle.getText(), txtPublisher.getText(),
                year, txtGenre.getText(), txtISBN.getText(), lblCover.getText(),
                rating);

        boolean flag = true;
        if (Book.isBookExistByTitle(book.getTitle())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Book exist in db! You can find it in library :)");
            alert.showAndWait();
            flag = false;
        }
        if (checkTextFields() && flag) {
            //dodać sprawdzanie czy ksiazka juz jest w bazie
            BookUser bookUser = new BookUser(book, actualUser, rating);
            bookUser.saveBookUser();
            System.out.println("book added: " + book
                    + "\nto user: " + actualUser);
            lblNumberOfBooks.setText(Integer.toString(actualUser.retNumberOfBooks()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Book added!");
            alert.showAndWait();
        }
        lblNumberOfBooks.setText(Integer.toString(actualUser.retNumberOfBooks()));
    }

    public void addCover(ActionEvent e)
    {
        FileChooser fc = new FileChooser();
        File selectedfile = fc.showOpenDialog(null);

        if (selectedfile != null)
            lblCover.setText(selectedfile.getName());
        else
            lblCover.setText("Not valid file!");
    }

    public static void setActualUser(User acUser)
    {
        actualUser = acUser;
    }

    public void goBackToLogin(ActionEvent event)
    {
        try {
            new AppViewHandler().launchLoginWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

