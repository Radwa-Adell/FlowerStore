package flowerstore;

import flowerstore.database;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    @FXML
    public Hyperlink Create_account;

    @FXML
    private TextField emailup;

    @FXML
    public Hyperlink have_account;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button loginbtn;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordup;

    @FXML
    private AnchorPane sign_up_form;

    @FXML
    private Button signupbtn;

    @FXML
    private TextField username;

    @FXML
    private TextField usernameup;

    // Database tools
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private double x, y;

    public void login() throws SQLException {

        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        connect = database.con();

        if (connect == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Failed to connect to the database.");
            alert.showAndWait();
            return;
        }

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            result = prepare.executeQuery();
            Alert alert;
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    // إخفاء نموذج تسجيل الدخول
                    loginbtn.getScene().getWindow().hide();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Logged in!");
                    alert.showAndWait();

                    
                    getData.username=username.getText();
                    // ربط نموذج لوحة التحكم
                    Parent root = FXMLLoader.load(getClass().getResource("dashbord.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    // جعل النافذة قابلة للسحب
                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password");
                    alert.showAndWait();
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void signup() {
        String sql = "insert into admin (username,email,password) values (?,?,?)";
        connect = database.con();

        if (connect == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Failed to connect to the database.");
            alert.showAndWait();
            return;
        }

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, usernameup.getText());
            prepare.setString(2, emailup.getText());
            prepare.setString(3, passwordup.getText());

            Alert alert;
            if (emailup.getText().isEmpty() || passwordup.getText().isEmpty() || usernameup.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                signupbtn.getScene().getWindow().hide();
                prepare.execute();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully created a new account!");
                alert.showAndWait();

                // تفريغ الحقول
                emailup.setText("");
                usernameup.setText("");
                passwordup.setText("");

                Parent root = FXMLLoader.load(getClass().getResource("dashbord.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
    }

    public void switchForm(ActionEvent ev) {
        if (ev.getSource() == Create_account) {
            login_form.setVisible(false);
            sign_up_form.setVisible(true);
        } else if (ev.getSource() == have_account) {
            login_form.setVisible(true);
            sign_up_form.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic
    }
}
