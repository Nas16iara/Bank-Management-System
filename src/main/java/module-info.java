module com.ranasia.banking {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires java.sql;
    requires mysql.connector.j;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires commons.logging;
    requires org.bouncycastle.provider;
    requires spring.security.crypto;


    opens com.ranasia.banking to javafx.fxml;
    exports com.ranasia.banking;
}