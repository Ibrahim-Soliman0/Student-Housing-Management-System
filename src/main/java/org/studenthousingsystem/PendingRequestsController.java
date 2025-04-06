package org.studenthousingsystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PendingRequestsController {

    @FXML
    private TableView<RoomRequest> pendingRequestsTable;

    private ObservableList<RoomRequest> requests;

    @FXML
    private TableColumn<RoomRequest, String> roomColumn, floorColumn, studentColumn;
    @FXML
    private TableColumn<RoomRequest, Button>approveColumn, rejectColumn;

    @FXML
    private TextField searchField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize()
    {
        roomColumn.setCellValueFactory(cellData -> {
            Room room = cellData.getValue().getRoom();
            return new SimpleStringProperty(room != null ? room.getRoomNumber() : "");
        });

        floorColumn.setCellValueFactory(cellData -> {
            Room room = cellData.getValue().getRoom();
            return new SimpleStringProperty(room != null ? room.getFloor() : "");
        });

        studentColumn.setCellValueFactory(cellData -> {
            Student student = cellData.getValue().getStudent();
            return new SimpleStringProperty(student != null ? student.getName() : "");
        });
        approveColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    RoomRequest roomRequest = getTableRow().getItem();
                    Button approveButton = roomRequest.getApproveButton();
                    approveButton.setOnAction(e -> {
                        roomRequest.setStaff(StudentHousingSystem.staff);
                        Database.approveRoomRequest(roomRequest);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The student has been Approved");
                        alert.setTitle("Applied Successfully");
                        alert.show();
                        requests.remove(roomRequest);
                    });
                    setGraphic(roomRequest.getApproveButton());
                }
            }
        });
        rejectColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    RoomRequest roomRequest = getTableRow().getItem();
                    Button rejectButton = roomRequest.getRejectButton();
                    rejectButton.setOnAction(e -> {
                        roomRequest.setStaff(StudentHousingSystem.staff);
                        Database.rejectRoomRequest(roomRequest);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The student has been Rejected");
                        alert.setTitle("Applied Successfully");
                        alert.show();
                        requests.remove(roomRequest);
                    });
                    setGraphic(rejectButton);
                }
            }
        });

        requests = FXCollections.observableArrayList();

        requests.addAll(Database.getAllRoomRequests());

        pendingRequestsTable.setItems(requests);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::Search, 0, 400, TimeUnit.MILLISECONDS);
    }

    @FXML
    public void Search()
    {
        FilteredList<RoomRequest> filter = new FilteredList<>(requests, e -> true);

        filter.setPredicate(roomRequest -> {
            if (searchField.getText() == null || searchField.getText().isEmpty())
                return true;

            String searchText = searchField.getText().toLowerCase().trim();

            return roomRequest.getRoom().getRoomNumber().toLowerCase().contains(searchText) ||
                    roomRequest.getRoom().getFloor().toLowerCase().contains(searchText) ||
                    roomRequest.getStudent().getName().toLowerCase().contains(searchText);
        });

        SortedList<RoomRequest> sort = new SortedList<>(filter);
        sort.comparatorProperty().bind(pendingRequestsTable.comparatorProperty());
        pendingRequestsTable.setItems(sort);
    }

    @FXML
    public void backToAdminPage(ActionEvent actionEvent) throws IOException
    {
        root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("StaffPage.fxml"))));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }
}
