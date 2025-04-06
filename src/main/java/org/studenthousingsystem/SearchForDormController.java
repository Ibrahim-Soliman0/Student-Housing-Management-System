package org.studenthousingsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SearchForDormController {
    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> buildingColumn, floorColumn, roomColumn;
    @FXML
    private TableColumn<Room, Button> applyColumn;

    private ObservableList<Room> rooms;

    @FXML
    private ImageView profilePic;
    @FXML
    private TextField searchField;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    public void initialize()
    {
        profilePic.setImage(new Image("D:\\Personal\\Github Projects\\student housing system\\Student-Housing-Management-System\\src\\main\\resources\\org\\studenthousingsystem\\ProfilePic.png"));

        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        buildingColumn.setCellValueFactory(new PropertyValueFactory<>("building"));
        floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
        applyColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Room room = getTableRow().getItem();
                    setGraphic(room.getApplyButton());
                }
            }
        });

        rooms = FXCollections.observableArrayList();

        rooms.addAll(Database.getAllNonOccupiedRooms());

        roomTable.setItems(rooms);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::Search, 0, 400, TimeUnit.MILLISECONDS);
    }

    @FXML
    public void onLogoutBtnClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load((getClass().getResource("Login.fxml")));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void Search()
    {
        FilteredList<Room> filter = new FilteredList<>(rooms, e -> true);

        filter.setPredicate(room -> {
            if (searchField.getText() == null || searchField.getText().isEmpty())
                return true;

            String searchText = searchField.getText().toLowerCase().trim();

            return room.getRoomNumber().toLowerCase().contains(searchText) ||
                    room.getBuilding().toLowerCase().contains(searchText) ||
                    room.getFloor().toLowerCase().contains(searchText);
        });

        SortedList<Room> sort = new SortedList<>(filter);
        sort.comparatorProperty().bind(roomTable.comparatorProperty());
        roomTable.setItems(sort);
    }

    @FXML
    private void onProfilePicClicked(javafx.scene.input.MouseEvent actionEvent) throws  IOException
    {
        root = FXMLLoader.load((getClass().getResource("StudentProfile.fxml")));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }

}
