package org.studenthousingsystem;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScannerController {

    private Webcam webcam;
    private boolean entrance = false, exit = false;
    private MultiFormatReader multiFormatReader;
    @FXML
    private ImageView cameraFeed;
    @FXML
    private Label studentID;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    public void initialize() {

        multiFormatReader = new MultiFormatReader();
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
    }

    @FXML
    protected void onScanEntranceClick(javafx.event.ActionEvent actionEvent) throws IOException {

        entrance = true;
        exit = false;

        root = FXMLLoader.load((getClass().getResource("WebcamPage.fxml")));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onScanExitClick(javafx.event.ActionEvent actionEvent) throws IOException {

        entrance = false;
        exit = true;

        root = FXMLLoader.load((getClass().getResource("WebcamPage.fxml")));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onScanClick(javafx.event.ActionEvent actionEvent) {

        studentID.setText("");
        openWebcam();
    }

    @FXML
    protected void onBackClick(javafx.event.ActionEvent actionEvent) throws IOException {

        closeWebcam();
        root = FXMLLoader.load((getClass().getResource("ScannerPage.fxml")));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }

    private void openWebcam() {

        if (webcam == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No webcam found");
            alert.show();
        }
        else
        {
            webcam.open();
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(this::updateFrame, 0, 33, TimeUnit.MILLISECONDS);
        }
    }

    private void closeWebcam() {
        if (webcam != null && webcam.isOpen())
        {
            webcam.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Webcam Closed");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The webcam is already closed");
            alert.show();
        }
    }

    private void updateFrame() {

        BufferedImage image = webcam.getImage();
        if (image != null)
        {
            Platform.runLater(() -> {
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
                    ImageIO.write(image, "PNG", imageOutputStream);
                    InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    Image studentQrScan = new Image(inputStream);

                    cameraFeed.setImage(studentQrScan);

                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = multiFormatReader.decode(binaryBitmap);
                    if (result != null)
                    {
                        String numeric = "^[0-9]*$";
                        if (result.getText().matches(numeric))
                            studentID.setText("Student ID: " + result.getText());
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Invalid Qrcode");
                            alert.show();
                        }
                        closeWebcam();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {}
            });
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Image found");
        }
    }
}

