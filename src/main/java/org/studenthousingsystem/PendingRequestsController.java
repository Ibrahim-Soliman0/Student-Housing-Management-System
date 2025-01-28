package org.studenthousingsystem;

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

public class PendingRequestsController {
    @FXML
    private TableView<PendingRequest> pendingTable;

    @FXML
    private TableColumn<PendingRequest, String> roomColumn;

    @FXML
    private TableColumn<PendingRequest, String> studentIDColumn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void backToAdminPage(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load((getClass().getResource("AdminPage.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }


    public void initialize() {
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));


        TableColumn<PendingRequest, Button> actionCol = new TableColumn<>("Accept");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("acceptBtn"));
        actionCol.setCellFactory(column -> new TableCell<>() {
            final Button acceptButton = new Button("Accept");

            {
                acceptButton.setOnAction(event -> {
                    PendingRequest pendingRequest = getTableView().getItems().get(getIndex());
                    System.out.println("Accepting request for room: " + pendingRequest.getRoomID());

                    getTableView().getItems().remove(pendingRequest);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(acceptButton);
                }
            }
        });

        TableColumn<PendingRequest, Button> declineCol = new TableColumn<>("Decline");
        declineCol.setCellValueFactory(new PropertyValueFactory<>("declineBtn"));
        declineCol.setCellFactory(column -> new TableCell<>() {
            final Button declineButton = new Button("Decline");

            {

                declineButton.setOnAction(event -> {
                    PendingRequest pendingRequest = getTableView().getItems().get(getIndex());
                    System.out.println("Declining request for room: " + pendingRequest.getRoomID());

                    getTableView().getItems().remove(pendingRequest);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(declineButton);
                }
            }
        });


        pendingTable.getColumns().addAll(actionCol, declineCol);


        pendingTable.getItems().addAll(
                new PendingRequest("Room 1", "20221469429"),
                new PendingRequest("Room 2", "20221444444"),
                new PendingRequest("Room 3", "20201458965")
        );
    }
}
