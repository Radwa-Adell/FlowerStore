package flowerstore;

import java.io.File;
import java.sql.Statement;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dashbordController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button availableflowers_addbtn;

    @FXML
    private Button availableflowers_clearbtn;

    @FXML
    private TableColumn<FlowersData, Integer> availableflowers_col_flowerid;

    @FXML
    private TableColumn<FlowersData, String> availableflowers_col_flowername;

    @FXML
    private TableColumn<FlowersData, String> availableflowers_col_price;

    @FXML
    private TableColumn<FlowersData, String> availableflowers_col_status;

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
    private TableView<FlowersData> availableflowers_tableview;

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
    private TableColumn<customerData,String> purchase_col_flowerid;

    @FXML
    private TableColumn<customerData, String> purchase_col_flowername;

    @FXML
    private TableColumn<customerData, String> purchase_col_price;


    @FXML
    private ComboBox<?> purchase_flowername;

    @FXML
    private ComboBox<?> purchase_flowerid;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Spinner<Integer> purchase_quantity;
    
       @FXML
    private Button purchase_addcart;

    @FXML
    private Label purchase_total;

    @FXML
    private Button purchasebtn;

    @FXML
    private Button signoutbtn;

    @FXML
    private Label username;
       @FXML
    private TableView<customerData> purchase_tableview;
       
         @FXML
    private TableColumn<customerData, String> purchase_col_quantity;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;
    
    
    public void homeAvailableFlowers(){
        
        String sql = "SELECT COUNT(id) FROM flowers WHERE status = 'Available'";
        
        connect = database.con();
        
        try{
            int countAF = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                countAF = result.getInt("COUNT(id)");
            }
            home_availableflowers.setText(String.valueOf(countAF));
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void homeTotalIncome(){
        
        String sql = "SELECT SUM(total) FROM customer_info";
        
        connect = database.con();
        
        try{
            double countTI = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                countTI = result.getInt("SUM(total)");
            }
            
            home_totalincome.setText("$" + String.valueOf(countTI));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
       
    public void homeTotalCustomer(){
        
        String sql = "SELECT COUNT(id) FROM customer_info";
        
        connect = database.con();
        
        try{
            int countTC = 0;
            
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                countTC = result.getInt("COUNT(id)");
            }
            home_totalcustomers.setText(String.valueOf(countTC));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    

    public void availableFlowersAdd() {
        String sql = "INSERT INTO flowers (flower_id, name, status, price, image, date) "
                + "VALUES(?,?,?,?,?,?)";

        connect = database.con();
        try {
            Alert alert;

        if (availableflowers_flowerid.getText().isEmpty()
                    || availableflowers_flowername.getText().isEmpty()
                    || availableflowers_status.getSelectionModel().getSelectedItem() == null
                    || availableflowers_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                // CHECK IF THE FLOWER ID IS ALREADY EXIST
                String checkData = "SELECT flower_id FROM flowers WHERE flower_id = '"
                        + availableflowers_flowerid.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Flower ID: " + availableflowers_flowerid.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, availableflowers_flowerid.getText());
                    prepare.setString(2, availableflowers_flowername.getText());
                    prepare.setString(3, (String) availableflowers_status.getSelectionModel().getSelectedItem());
                    prepare.setString(4, availableflowers_price.getText());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(5, uri);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // SHOW UPDATED TABLEVIEW
                    availableFlowersShowListData();

                    // CLEAR ALL FIELDS
                    availableFlowersClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void availableFlowersUpdate()
    {
       String uri = getData.path;
        
       if(!(uri == null || uri == "") )
       {
            uri = uri.replace("\\", "\\\\");
       }
        
        
              String sql = "UPDATE flowers SET name = '"
                + availableflowers_flowername.getText() + "', status = '"
                + availableflowers_status.getSelectionModel().getSelectedItem() + "', price = '"
                + availableflowers_price.getText() + "', image = '"
                + uri + "' WHERE flower_id = '" + availableflowers_flowerid.getText() + "'";

        connect = database.con();
        try
        {
              Alert alert;

        if (availableflowers_flowerid.getText().isEmpty()
                    || availableflowers_flowername.getText().isEmpty()
                    || availableflowers_status.getSelectionModel().getSelectedItem() == null
                    || availableflowers_price.getText().isEmpty()
                    || uri == null || uri == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } 
        else {
             alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Flower ID: " + availableflowers_flowerid.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    
                    // SHOW UPDATED TABLEVIEW
                    availableFlowersShowListData();

                    // CLEAR ALL FIELDS
                    availableFlowersClear();
        }
        }
        
        }
         catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void availableFlowersDelete() throws SQLException {

        String sql = "DELETE FROM flowers WHERE flower_id = '"
                + availableflowers_flowerid.getText() + "'";

        connect = database.con();
        try
        {
                Alert alert;

            if (availableflowers_flowerid.getText().isEmpty()
                    || availableflowers_flowername.getText().isEmpty()
                    || availableflowers_status.getSelectionModel().getSelectedItem() == null
                    || availableflowers_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else
            {
             alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Delete Flower ID: " + availableflowers_flowerid.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deletted!");
                    alert.showAndWait();
                    
                    // SHOW UPDATED TABLEVIEW
                    availableFlowersShowListData();

                    // CLEAR ALL FIELDS
                    availableFlowersClear();
            }
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void availableFlowersClear() {

        availableflowers_flowerid.setText("");
        availableflowers_flowername.setText("");
        availableflowers_status.getSelectionModel().clearSelection();
        availableflowers_price.setText("");
        getData.path = "";

        availableflowers_imageview.setImage(null);

    }
      
    public void availableFlowersSearch() {
    FilteredList<FlowersData> filter = new FilteredList<>(availableFlowersList, e -> true);
    
    availableflowers_search.textProperty().addListener((observable, oldValue, newValue) -> {

        filter.setPredicate(flowerData -> {

            if (newValue == null || newValue.isEmpty()) {
                return true; // Display all items if the search text is empty
            }

            String searchKey = newValue.toLowerCase();

            if (flowerData.getFlowerId().toString().contains(searchKey)) {
                return true;
            } else if (flowerData.getName() != null && flowerData.getName().toLowerCase().contains(searchKey)) {
                return true;
            } else if (flowerData.getStatus() != null && flowerData.getStatus().toLowerCase().contains(searchKey)) {
                return true;
            } else if (flowerData.getPrice() != null && flowerData.getPrice().toString().contains(searchKey)) {
                return true;
            } else {
                return false;
            }
        });
    });

    SortedList<FlowersData> sortList = new SortedList<>(filter);

    sortList.comparatorProperty().bind(availableflowers_tableview.comparatorProperty());

    availableflowers_tableview.setItems(sortList);
}

     
     String listStatus[] = {"Available", "Not Available"};
    public void availableFlowersStatus() {

        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        availableflowers_status.setItems(listData);

    }

    public void availableFlowersInsertImage() {

      FileChooser open = new FileChooser();
      open.setTitle("Open Image File");
      open.getExtensionFilters().add(new ExtensionFilter("File Image", "*.jpg", "*.png", ".jfif"));

      File file = open.showOpenDialog(main_form.getScene().getWindow());

      if(file != null){
          getData.path = file.getAbsolutePath();

          image = new Image(file.toURI().toString(), 123, 142, false, true);
         availableflowers_imageview.setImage(image);
      }
   
        }


    public ObservableList<FlowersData> availableFlowersListData() {
        ObservableList<FlowersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM flowers";

        Connection connect = database.con();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            FlowersData flower;
            while (result.next()) {
                flower = new FlowersData(result.getInt("flower_id"),
                        result.getString("name"), result.getString("status"),
                        result.getDouble("price"), result.getString("image"),
                        result.getDate("date"));

                listData.add(flower);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<FlowersData> availableFlowersList;

    public void availableFlowersShowListData() {
        availableFlowersList = availableFlowersListData();
        availableflowers_col_flowerid.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
        availableflowers_col_flowername.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableflowers_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        availableflowers_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        availableflowers_tableview.setItems(availableFlowersList);

    }
    
    public void availableFlowersSelect()
    {
         FlowersData flower = availableflowers_tableview.getSelectionModel().getSelectedItem();
        int num = availableflowers_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableflowers_flowerid.setText(String.valueOf(flower.getFlowerId()));
        availableflowers_flowername.setText(flower.getName());
        availableflowers_price.setText(String.valueOf(flower.getPrice()));

        getData.path = flower.getImage();

        String uri = "file:" + flower.getImage();

        image = new Image(uri, 128, 142, false, true);
        availableflowers_imageview.setImage(image);
    }
    
    public void purchaseAddToCart()
    {
         purchaseCustomerId();

        String sql = "INSERT INTO customer (customer_id, flower_id, name, quantity, price, date) "
                + "VALUES(?,?,?,?,?,?)";

        connect = database.con();

        try {
            Alert alert;

            if (purchase_flowerid.getSelectionModel().getSelectedItem() == null
                    || purchase_flowername.getSelectionModel().getSelectedItem() == null
                    || qty == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose the product first");
                alert.showAndWait();
            } else {
                double priceData = 0;
                double totalPrice;
                String checkPrice = "SELECT name, price FROM flowers WHERE name = '"
                        + purchase_flowername.getSelectionModel().getSelectedItem() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkPrice);

                if (result.next()) {
                    priceData = result.getDouble("price");
                }

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setInt(2, (Integer) purchase_flowerid.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) purchase_flowername.getSelectionModel().getSelectedItem());
                prepare.setString(4, String.valueOf(qty));
                
                totalPrice = (priceData * qty);

                prepare.setString(5, String.valueOf(totalPrice));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(6, String.valueOf(sqlDate));
                
                prepare.executeUpdate();
                
                purchaseShowListData();
                purchaseDisplayTotal();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void purchasePay(){
        
        String sql = "INSERT INTO customer_info (customer_id, total, date) VALUES(?,?,?)";
        
        connect = database.con();
        
        try{
            Alert alert;
            
            if(totalP == 0){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something wrong :3");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful !! Thanks for purchase.");
                    alert.showAndWait();
                    
                    totalP = 0;
                }
            }
            
        }catch(Exception e){e.printStackTrace();}
        
    }
       private double totalP = 0;
    public void purchaseDisplayTotal(){
        purchaseCustomerId();
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '"+customerId+"'";
        
        connect = database.con();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if(result.next()){
                totalP = result.getDouble("SUM(price)");
            }
            
            purchase_total.setText("$" + String.valueOf(totalP));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    public void purchaseFlowerId()
    {
            String sql = "SELECT status, flower_id FROM flowers WHERE status = 'Available'";

        connect = database.con();

        try {
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getInt("flower_id"));
            }
            purchase_flowerid.setItems(listData);

            purchaseFlowerName();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void purchaseFlowerName()
    {
             String sql = "SELECT flower_id, name FROM flowers WHERE flower_id = '"
                + purchase_flowerid.getSelectionModel().getSelectedItem() + "'";

        connect = database.con();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("name"));
            }
           purchase_flowername.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
       private SpinnerValueFactory<Integer> spinner;
    public void purchaseSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchase_quantity.setValueFactory(spinner);
    }

    private int qty;
    public void purchaseQuantity() {
        qty = purchase_quantity.getValue();
    }
    
    
     public ObservableList<customerData> purchaseListData() {
        purchaseCustomerId();

        ObservableList<customerData> listData = FXCollections.observableArrayList();
         String sql = "SELECT * FROM customer WHERE customer_id = '" + customerId + "'";
        connect = database.con();
        try
        { 
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData customer;
            while(result.next())
            {
                  customer = new customerData(result.getInt("customer_id"),
                         result.getInt("flower_id"), result.getString("name"),
                         result.getInt("quantity"), result.getDouble("price"),
                         result.getDate("date"));

                listData.add(customer);
            }
        }

         catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
     }
     
      private ObservableList<customerData> purchaseListD;

     public void purchaseShowListData()
     {
              purchaseListD = purchaseListData();

        purchase_col_flowerid.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
        purchase_col_flowername.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

         purchase_tableview.setItems(purchaseListD);
     }
     
     private int customerId;
    public void purchaseCustomerId()
    {
        String sql ="SELECT MAX(customer_id) from customer";
        
        connect=database.con();
        
       
              try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                customerId = result.getInt("MAX(customer_id)");
            }
            int countData =0; 
              String checkInfo = "SELECT MAX(customer_id) FROM customer_info";

            prepare = connect.prepareStatement(checkInfo);
            result = prepare.executeQuery();

            if (result.next()) {
                countData = result.getInt("MAX(customer_id)");
            }
              if (customerId == 0) {
                customerId += 1;
            } else if (customerId == countData) {
                customerId = countData + 1;
            }
              }
              

        
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void displayUsername() {
        String user = getData.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            availableflowers_form.setVisible(false);
            purchase_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:#F4C2C2");
            availableflowersbtn.setStyle("-fx-background-color: transparent");
            purchasebtn.setStyle("-fx-background-color: transparent");
            
            
          homeAvailableFlowers();
         homeTotalIncome();
        homeTotalCustomer();

        } else if (event.getSource() == availableflowersbtn) {
            home_form.setVisible(false);
            availableflowers_form.setVisible(true);
            purchase_form.setVisible(false);

            availableflowersbtn.setStyle("-fx-background-color:#F4C2C2");
            home_btn.setStyle("-fx-background-color: transparent");
            purchasebtn.setStyle("-fx-background-color: transparent");

            // TO SHOW THE UPDATED TABLEVIEW ONCE YOU CLICKED THE AVAILABLE FLOWERS BUTTON
            availableFlowersShowListData();
             availableFlowersStatus();
            availableFlowersSearch();

        } else if (event.getSource() == purchasebtn) {
            home_form.setVisible(false);
            availableflowers_form.setVisible(false);
            purchase_form.setVisible(true);

            purchasebtn.setStyle("-fx-background-color:#F4C2C2");
            availableflowersbtn.setStyle("-fx-background-color: transparent");
            home_btn.setStyle("-fx-background-color: transparent");

             purchaseShowListData();
            purchaseFlowerId();
            purchaseFlowerName();
            purchaseSpinner();
            purchaseDisplayTotal();
        }

    }

    private double x = 0, y = 0;

    public void logout() {

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // HIDE YOUR DASHBOARD FORM
                signoutbtn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        
       homeAvailableFlowers();
         homeTotalIncome();
        homeTotalCustomer();
       
        availableFlowersShowListData();
        availableFlowersStatus();
        
        purchaseShowListData();
        purchaseFlowerId();
        purchaseFlowerName();
        purchaseSpinner();
        purchaseDisplayTotal();
    }

}
