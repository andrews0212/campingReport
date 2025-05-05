package org.example.camping2.controladores.Reservas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.Date;
import java.util.List;

public class BuscaReservaController {

    Memoria<Reserva, Integer> memoriaReserva;

    @FXML
    private TableView<Reserva> clientesTableView;
    @FXML
    private TableColumn<Reserva, Integer> IDReserva;
    @FXML
    private TableColumn<Reserva, Integer> IDClientecolumn;
    @FXML
    private TableColumn<Reserva, String> dniColumn;
    @FXML
    private TableColumn<Reserva, String> FechaInicioColumn;
    @FXML
    private TableColumn<Reserva, String> FechaFinColumn;
    @FXML
    private TableColumn<Reserva, String> EstadoColumn;
    @FXML
    private TableColumn<Reserva, Integer> PersonasColumn;
    @FXML
    private TableColumn<Reserva, Integer> PrecioTotalColumn;

    private ObservableList<Reserva> Reservas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        IDReserva.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        IDClientecolumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdcliente().getId()));
        dniColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdcliente().getDni()));
        FechaInicioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFechaInicio().toString()));
        FechaFinColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFechaFin().toString()));
        EstadoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEstado()));
        PersonasColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getNumeroPersonas()));
        PrecioTotalColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecioTotal()));
        clientesTableView.setItems(Reservas);


    }

    public Memoria<Reserva, Integer> getMemoriaReserva() {
        return memoriaReserva;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
    }

    @FXML
    public void buscaTodos(ActionEvent event) {
        Reservas.clear();
        try {
            // Simulate database search logic
            ObservableList<Reserva> resultados = FXCollections.observableArrayList();;
            resultados.addAll(memoriaReserva.findAll());
            Reservas.addAll(resultados);


        }catch (Exception e){

        }
    }
}
