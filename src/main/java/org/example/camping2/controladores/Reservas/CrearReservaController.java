package org.example.camping2.controladores.Reservas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.awt.event.ActionEvent;

public class CrearReservaController {
    Memoria<Reserva, Integer> memoriaReserva;
    Memoria<Recurso, Integer> memoriaRecurso;
    Memoria<Cliente, Integer> memoriaCliente;
    @FXML
    private ComboBox tipoComboBox;
    @FXML
    private TextField dniText;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private TextField precioText;
    ObservableList<String> tipos = FXCollections.observableArrayList("BUNGALOW", "BARBACOA", "PARCELA");
    @FXML
    private TableView<Recurso> tablaRecurso;
    @FXML
    private TableColumn<Recurso, Integer> IDColumn;
    @FXML
    private TableColumn<Recurso, String> nombreColumn;
    @FXML
    private TableColumn<Recurso, String> tipoColumn;
    @FXML
    private TableColumn<Recurso, Integer> capacidadColumn;
    @FXML
    private TableColumn<Recurso, Integer> precioColumn;
    @FXML
    private TableColumn<Recurso, Integer> minimoPersonasColumn;

    private ObservableList<Recurso> recursos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    tipoComboBox.setItems(tipos);
    tipoComboBox.getSelectionModel().selectFirst();
    nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getNombre()));
    tipoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTipo()));
    capacidadColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacidad()));
    precioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecio()));
    minimoPersonasColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getMinimoPersonas()));
    IDColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
    recursos.clear();
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
    }


    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
    }

    public void setMemoriaCliente(Memoria<Cliente, Integer> memoriaCliente) {
        this.memoriaCliente = memoriaCliente;
    }

    public void cargar(javafx.event.ActionEvent actionEvent) {
        try {
            // Simulate database search logic
            recursos.clear();
            ObservableList<Recurso> recursos1 = FXCollections.observableArrayList();
            String selection = tipoComboBox.getSelectionModel().getSelectedItem().toString();
            recursos1.addAll(memoriaRecurso.findAll().stream().filter(p -> p.getTipo().equals(selection)).toList());
            recursos.addAll(recursos1);
            tablaRecurso.setItems(recursos);


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void hacerReserva(){
       Recurso recurso = tablaRecurso.getSelectionModel().getSelectedItem();
       Cliente cliente = memoriaCliente.findAll().stream().filter(p -> p.getDni().toUpperCase().equals(dniText.getText().toUpperCase())).findFirst().get();
       if (cliente != null && recurso != null) {
           memoriaReserva.add(new Reserva(cliente, recurso, fechaInicio.getValue(), fechaFin.getValue(), "ACTIVA", Integer.parseInt(precioText.getText()), 0));
       }
    }
}
