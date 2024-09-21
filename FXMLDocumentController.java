/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package flowerstore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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

import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author DELL
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
     private Button loginbtn;

    @FXML
    private AnchorPane main_frame;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    
    // Database tools
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
 
    public void login() throws SQLException, IOException {
        String sql = "SELECT * FROM admin WHERE username=? AND password=?";
        connect = database.con(); // Assuming database connection is correct
       

        try {
            prepare = connect.prepareStatement(sql); 
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            result = prepare.executeQuery();
            
            Alert alert;
            
      if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                if(result.next()){
                    // IF CORRECT USERNAME AND PASSWORD THEN PROCEED TO DASHBOARD 
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    
                   // getData.username = username.getText();
                    
                    // TO HIDE LOGIN FORM
                   // loginBtn.getScene().getWindow().hide();
                    
                    // LINK YOUR DASHBOARD FORM
                   
          Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
          Stage stage = new Stage();
          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();

                }else{
                    // IF NOT THEN ERROR MESSAGE WILL APPEAR
       
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                    
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic, if needed
    }
}
