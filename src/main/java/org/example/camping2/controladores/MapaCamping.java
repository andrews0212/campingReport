package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapaCamping {

    @FXML private Button EL4;
    @FXML private Button EL3;
    @FXML private Button EL2;
    @FXML private Button EL1;
    @FXML private Button DII;
    @FXML private Button DI;
    @FXML private Button B29;
    @FXML private Button B28;
    @FXML private Button B27;
    @FXML private Button B26;
    @FXML private Button B25;
    @FXML private Button B24;
    @FXML private Button B23;
    @FXML private Button B22;
    @FXML private Button B21;
    @FXML private Button B20;
    @FXML private Button B19;
    @FXML private Button B18;
    @FXML private Button B17;
    @FXML private Button B16;
    @FXML private Button B15;
    @FXML private Button B14;
    @FXML private Button B13;
    @FXML private Button B12;
    @FXML private Button B11;
    @FXML private Button B10;

    @FXML
    public void initialize() {
        // Añade a cada botón un handler que imprime en consola
        addButtonListener(EL4);
        addButtonListener(EL3);
        addButtonListener(EL2);
        addButtonListener(EL1);
        addButtonListener(DII);
        addButtonListener(DI);
        addButtonListener(B29);
        addButtonListener(B28);
        addButtonListener(B27);
        addButtonListener(B26);
        addButtonListener(B25);
        addButtonListener(B24);
        addButtonListener(B23);
        addButtonListener(B22);
        addButtonListener(B21);
        addButtonListener(B20);
        addButtonListener(B19);
        addButtonListener(B18);
        addButtonListener(B17);
        addButtonListener(B16);
        addButtonListener(B15);
        addButtonListener(B14);
        addButtonListener(B13);
        addButtonListener(B12);
        addButtonListener(B11);
        addButtonListener(B10);
    }

    private void addButtonListener(Button button) {
        if (button != null) {
            button.setOnAction(event -> System.out.println("boton pulsado"));
        }
    }
}
