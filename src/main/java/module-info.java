module com.example.covidmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens covidmanagement to javafx.fxml;
    opens covidmanagement.model to javafx.base;
    exports covidmanagement;
    exports covidmanagement.controller;
    opens covidmanagement.controller to javafx.fxml;
    exports covidmanagement.controller.xetnghiemcontroller;
    opens covidmanagement.controller.xetnghiemcontroller to javafx.fxml;
    exports covidmanagement.controller.nhankhaucontroller;
    opens covidmanagement.controller.nhankhaucontroller to javafx.fxml;
    exports covidmanagement.controller.hokhaucontroller;
    opens covidmanagement.controller.hokhaucontroller to javafx.fxml;
    exports covidmanagement.model;
    exports covidmanagement.controller.khaibaocontroller;
    opens covidmanagement.controller.khaibaocontroller to javafx.fxml;
    exports covidmanagement.controller.cachlycontroller;
    opens covidmanagement.controller.cachlycontroller to javafx.fxml;
}