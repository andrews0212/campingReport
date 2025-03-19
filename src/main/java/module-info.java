module org.example.camping2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires net.sf.jasperreports.core;
    requires org.slf4j;
    opens org.example.camping2.modelo.dto to org.hibernate.orm.core;

    opens org.example.camping2 to javafx.fxml;
    exports org.example.camping2.controladores;
    opens org.example.camping2.controladores to javafx.fxml;
}