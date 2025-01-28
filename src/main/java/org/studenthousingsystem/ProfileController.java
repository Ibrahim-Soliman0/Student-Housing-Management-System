package org.studenthousingsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileController {
    @FXML
    private Rectangle warningButton1, warningButton2, warningButton3;
    @FXML
    private ImageView studentQRCode;
    @FXML
    private Label profileCity;
    @FXML
    private Label profileEmail;
    @FXML
    private Label profileId;
    @FXML
    private Label profileName;
    @FXML
    private Label profileNameLabel;
    @FXML
    private Label warningsLabel;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    public void initialize() {

        profileId.setText(StudentHousingSystem.student.getId());
        profileCity.setText(StudentHousingSystem.student.getCity());
        profileName.setText(StudentHousingSystem.student.getName());
        profileEmail.setText(StudentHousingSystem.student.getEmail());

        issueWarning(StudentHousingSystem.student.getWarnings());
        String firstName = profileName.getText().toUpperCase().trim().split(" ")[0];
        profileNameLabel.setText(firstName.trim() + "'s PROFILE");

        generateQRCode();
    }

    @FXML
    protected void onpaymentButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load((getClass().getResource("PaymentPage.fxml")));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onBackButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load((getClass().getResource("SearchForDorm.fxml")));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }
    protected void issueWarning(int warnings) {

        if (warnings == 1)
        {
            warningButton1.setFill(Color.RED);
        }
        else if (warnings >= 1 && warnings <= 2)
        {
            warningButton1.setFill(Color.RED);
            warningButton2.setFill(Color.RED);
        }
        else if (warnings == 3)
        {
            warningButton1.setFill(Color.RED);
            warningButton2.setFill(Color.RED);
            warningButton3.setFill(Color.RED);
        }

        warningsLabel.setText(warnings + " Warnings " + (warnings == 0 ? '\u263A' : '\u2639'));
    }
    private void generateQRCode() {

        ByteArrayOutputStream out = QRCode.from(profileId.getText()).to(ImageType.PNG).withSize(130, 130).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

        Image qrCodeImage = new Image(in);
        studentQRCode.setImage(qrCodeImage);
    }
}