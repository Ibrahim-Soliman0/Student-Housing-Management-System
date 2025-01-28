//package org.studenthousingsystem;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//public class SearchForDormController {
//    @FXML
//    private TableView<RoomRequest> roomTable;
//
//    @FXML
//    private TableColumn<RoomRequest, String> RoomNo;
//
//    @FXML
//    private ImageView profilePic;
//    @FXML
//    private TextField searchField;
//
//    @FXML
//    private Stage stage;
//    @FXML
//    private Scene scene;
//    @FXML
//    private Parent root;
//
//    @FXML
//    public void onLogoutBtnClicked(javafx.event.ActionEvent actionEvent) throws IOException {
//        root = FXMLLoader.load((getClass().getResource("Login.fxml")));
//        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//        scene = new Scene(root, 450, 450);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//
//    @FXML
//    public void Search() {
//
//        ObservableList<RoomRequest> data = FXCollections.observableArrayList();
//
//        data.addAll(new RoomRequest("Room 1"),
//                new RoomRequest("Room 2"),
//                new RoomRequest("Room 3")
//        );
//
//        roomTable.setItems(data);
//
//        FilteredList<RoomRequest> filter = new FilteredList<>(data, e-> true);
//
//        filter.setPredicate(room ->{
//            if(searchField.getText() == null || searchField.getText().isEmpty())
//                return true;
//            String s = searchField.getText().toLowerCase().trim();
//            if(room.getRoom().contains(searchField.getText()))
//                return true;
//            else return room.getRoom().toLowerCase().contains(s);
//        });
//
//        SortedList <RoomRequest> sort = new SortedList<>(filter);
//        sort.comparatorProperty().bind(roomTable.comparatorProperty());
//        roomTable.setItems(sort);
//    }
//
//    public void initialize() {
//        profilePic.setImage(new Image("C:\\Users\\ibrah\\IdeaProjects\\StudentHousingSystem\\src\\main\\resources\\org\\studenthousingsystem\\ProfilePic.png"));
//        RoomNo.setCellValueFactory(new PropertyValueFactory<>("room"));
//
//        TableColumn<RoomRequest, Button> actionCol = new TableColumn<>("Action");
//        actionCol.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
//
//        roomTable.getColumns().addAll(actionCol);
//
//        roomTable.getItems().addAll(
//                new RoomRequest("Room 1"),
//                new RoomRequest("Room 2"),
//                new RoomRequest("Room 3")
//        );
//
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.scheduleAtFixedRate(this::Search, 0, 400, TimeUnit.MILLISECONDS);
//    }
//
//    @FXML
//    private void onProfilePicClicked(javafx.scene.input.MouseEvent actionEvent) throws SQLException, IOException
//    {
//        root = FXMLLoader.load((getClass().getResource("StudentProfile.fxml")));
//        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//        scene = new Scene(root, 450, 450);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//}
