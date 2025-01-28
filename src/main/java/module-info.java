module org.example.studenthousingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires qrgen;
    requires webcam.capture;
    requires java.logging;
    requires java.sql;
    requires com.google.zxing;
    requires sendgrid.java;
    requires com.oracle.database.jdbc;


    opens org.studenthousingsystem to javafx.fxml;
    exports org.studenthousingsystem;
}