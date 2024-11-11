package com.erikat.examenprimeraevaad_erikat.Scenes;

import com.erikat.examenprimeraevaad_erikat.DAO.EquipoDAOHibernate;
import com.erikat.examenprimeraevaad_erikat.DAO.JugadorDAO;
import com.erikat.examenprimeraevaad_erikat.Model.Equipo;
import com.erikat.examenprimeraevaad_erikat.Model.Jugador;
import com.erikat.examenprimeraevaad_erikat.Utils.JavaFXUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.List;

public class JugadorController extends Controller {

    List<Jugador> jugadores;

    EquipoDAOHibernate equipoDAO;
    JugadorDAO jugadorDAO;
    Jugador jugadorSeleccionado;

    @FXML
    private TextField aliasTField;

    @FXML
    private TextField idTField;

    @FXML
    private ListView<Jugador> jugadoresLView;

    @FXML
    private TextField nacimientoTField;

    @FXML
    void onCambiarEquipoClick(ActionEvent event) { //Función de cambio de equipo
        /*
        Factores a tener en cuenta:
            1. Jugador seleccionado
            2. ID introducido
            3. Existe un equipo con ese ID
         */
        try {
            if (jugadorSeleccionado == null) { //No se ha seleccionado a nadie
                JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Debes seleccionar algún jugador primero", "Error de selección");
            } else if (idTField.getText().isEmpty()) { //No ha puesto nada en el id
                JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Introduce un id de equipo", "Error de campos");
            } else {
                Equipo equipo = equipoDAO.buscar(Integer.parseInt(idTField.getText()));
                if (equipo == null) { //No se encuentra el equipo
                    JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "No se ha encontrado el equipo", "Error de equipo");
                } else {
                    jugadorSeleccionado.setEquipo(equipo);
                    if (jugadorDAO.editar(jugadorSeleccionado)) {
                        JavaFXUtils.makeAlert(Alert.AlertType.INFORMATION, "Cambiado correctamente", "Cambio de equipo");
                        listRefresh();
                    } else { //Error de BD
                        JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Error en la base de datos", "Error de BBDD");
                    }
                }
            }
        }catch (NumberFormatException e){ //ID no numérico
            JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "El ID debe ser numérico", "Error de formato");
        }
    }

    @FXML
    void onCumpleClick(ActionEvent event) { //Función de listado de cumpleaños
        String res = "Lista de jugadores que cumplen años hoy:\n"; //String principal
        int length = res.length(); //Guardo la longitud para hacer una comprobación más tarde
        for (Jugador j : jugadores){ //Recorro toda la lista de jugadores
            LocalDate cumple = j.getFechaNacimiento(); //Guardo su nacimiento en un localDate para manipularlo mejor
            if (cumple.getDayOfMonth() == LocalDate.now().getDayOfMonth() && cumple.getMonth() == LocalDate.now().getMonth()){
                //Añado a los jugadores que su dia de nacimiento sea hoy y su mes de nacimiento sea el actual
                res += (j.getAliasJugador() + " cumple hoy " + (LocalDate.now().getYear() - cumple.getYear()) + " años.\n");
                //Resto el año actual menos el año de su nacimiento para sacar su edad
            }
        }
        if (res.length() == length){ //Si se da el caso de que nadie cumple años, el string no habrá variado
            res = "Hoy nadie cumple años"; //Por lo que dice que nadie cumple años, mera estética pero queda mejor
        }
        JavaFXUtils.makeAlert(Alert.AlertType.INFORMATION, res, "Cumpleaños"); //Saca un Alert con la información
    }

    @FXML
    void onContentClick(MouseEvent event) {
        jugadorSeleccionado = jugadoresLView.getSelectionModel().getSelectedItem();
        if (jugadorSeleccionado!=null){
            idTField.setText(String.valueOf(jugadorSeleccionado.getEquipo().getIdEquipo()));
            aliasTField.setText(String.valueOf(jugadorSeleccionado.getAliasJugador()));
            nacimientoTField.setText(jugadorSeleccionado.getFechaNacimiento().toString());

        }
    }

    void listRefresh(){
        jugadores = jugadorDAO.listar();
        jugadoresLView.setItems(FXCollections.observableArrayList(jugadores));
    }

    public void iniciarDatos(EquipoDAOHibernate equipoDAO){
        jugadorDAO = new JugadorDAO();
        this.equipoDAO = equipoDAO;

        listRefresh();
    }
}
