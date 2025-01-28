package org.studenthousingsystem;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PaymentController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private TextField cardNumber, cardName, cardDate, cvv;

    @FXML
    protected void onpayButtonClick() {
        if (validateCard())
        {
            StudentHousingSystem.student.setPaymentSuccessful(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Payment Successful");
            alert.show();

//            Email fromEmail = new Email("Ibrahimsoliman269@gmail.com", "ALEX SCI University");
//            Email toEmail = new Email("Ibrahimsoliman269@gmail.com");
//            String subject = "Payment Successful";
//            Content body = new Content("text/plain", "Your payment from card: " + cardNumber.getText() + "is completed");
//            Mail mail = new Mail(fromEmail,subject, toEmail,body);
//
//            SendGrid sendGrid = new SendGrid();
//
//            Request request = new Request();
//            Response response = new Response();
//
//            try {
//                request.setMethod(Method.POST);
//                request.setEndpoint("v3/mail/send");
//                request.setBody(mail.build());
//                response = sendGrid.api(request);
//            }
//            catch(IOException e) {}
//
//
//
//            if (response.getStatusCode() == 202)
//                System.out.println("Email Sent Successfully");
//            else
//                System.out.println("Error sending email: " + response.getStatusCode() + response.getBody() );
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (!cardNumberCheck())
                alert.setHeaderText("Invalid Card Number");
            else if (!validName())
                alert.setHeaderText("Card Holder Name Must not contain special characters or be left empty");
            else if (!cvvCheck())
                alert.setHeaderText("Invalid CVV Number");
            else if (!dateCheck())
                alert.setHeaderText("Card Expired");

            alert.show();
        }
    }
    @FXML
    protected void onBackButtonClick(ActionEvent actionEvent) throws IOException
    {
        root = FXMLLoader.load((getClass().getResource("StudentProfile.fxml")));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }

    private boolean validateCard()
    {
        return (cardNumberCheck() && cvvCheck() && dateCheck() && validName());
    }
    private boolean cardNumberCheck() {
        long number = Long.parseLong(cardNumber.getText().trim());
        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    private boolean dateCheck()
    {
        LocalDate currentDate = LocalDate.now();
        String[] currDate = currentDate.toString().split("-");
        String[] date = (cardDate.getText()).trim().split("/");
        int currYear, currMonth, cardYear,cardMonth;
        try
        {
            currYear = Integer.parseInt(currDate[0]);
            currMonth = Integer.parseInt(currDate[1]);
            cardYear = Integer.parseInt(date[1]) + 2000;
            cardMonth = Integer.parseInt(date[0]);
        }
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Date Format");
            alert.show();
            return false;
        }

        if (currYear < cardYear)
            return true;
        else if (currYear == cardYear && currMonth <= cardMonth)
            return true;

        return false;
    }

    private boolean cvvCheck()
    {
        long cvv;
        try
        {
           cvv = Long.parseLong(this.cvv.getText().trim());
        }
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid CVV Format");
            alert.show();
            return false;
        }

        return cvv >= 100 && cvv <= 9999;
    }

    private boolean validName()
    {
        String name = cardName.getText().trim();
        String isAlphabitic = "^[a-zA-Z]*$";
        return !name.isEmpty() && name.matches(isAlphabitic);
    }

    private int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    private int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    private int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    private boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    private int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }

    private long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}