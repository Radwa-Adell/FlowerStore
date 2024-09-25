/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flowerstore;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class dashbordController  implements Initializable{
        @FXML
    private AnchorPane main_form;

     @FXML
    private Button availableflowers_addbtn;

    @FXML
    private Button availableflowers_clearbtn;

    @FXML
    private TableColumn<?, ?> availableflowers_col_flowerid;

    @FXML
    private TableColumn<?, ?> availableflowers_col_flowername;

    @FXML
    private TableColumn<?, ?> availableflowers_col_price;

    @FXML
    private TableColumn<?, ?> availableflowers_col_status;

    @FXML
    private Button availableflowers_deletebtn;

    @FXML
    private TextField availableflowers_flowerid;

    @FXML
    private TextField availableflowers_flowername;

    @FXML
    private AnchorPane availableflowers_form;

    @FXML
    private Button availableflowers_imagebtn;

    @FXML
    private ImageView availableflowers_imageview;

    @FXML
    private TextField availableflowers_price;

    @FXML
    private TextField availableflowers_search;

    @FXML
    private ComboBox<?> availableflowers_status;

    @FXML
    private TableView<?> availableflowers_tableview;

    @FXML
    private Button availableflowers_updatebtn;

    @FXML
    private Button availableflowersbtn;

    @FXML
    private Button closebtn;

    @FXML
    private Label home_availableflowers;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalcustomers;

    @FXML
    private Label home_totalincome;

    @FXML
    private Button minimizebtn;

    @FXML
    private Button paynowbtn;

    @FXML
    private TableColumn<?, ?> purchase_col_flowerid;

    @FXML
    private TableColumn<?, ?> purchase_col_flowername;

    @FXML
    private TableColumn<?, ?> purchase_col_price;

    @FXML
    private TableColumn<?, ?> purchase_col_status;

    @FXML
    private ComboBox<?> purchase_flowename;

    @FXML
    private ComboBox<?> purchase_flowerid;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Spinner<?> purchase_quantity;

    @FXML
    private Label purchase_total;

    @FXML
    private Button purchasebtn;

      @FXML
    private Button signoutbtn;

    @FXML
    private Label username;
    
       public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
