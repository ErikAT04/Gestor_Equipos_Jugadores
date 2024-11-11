package com.erikat.examenprimeraevaad_erikat.Scenes;

import com.erikat.examenprimeraevaad_erikat.DAO.EquipoDAOHibernate;
import com.erikat.examenprimeraevaad_erikat.DAO.EquipoDAOMongo;
import com.erikat.examenprimeraevaad_erikat.DAO.EquipoDAOSQL;
import com.erikat.examenprimeraevaad_erikat.Model.Equipo;
import com.erikat.examenprimeraevaad_erikat.Utils.JavaFXUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EquipoController extends Controller implements Initializable {
    EquipoDAOHibernate daoHibernate;
    EquipoDAOSQL daosql;
    EquipoDAOMongo daoMongo;
    @FXML
    private ComboBox<String> categoriaCBox;

    @FXML
    private TableColumn<Equipo, String> categoriaTCol;

    @FXML
    private TableView<Equipo> equipoTView;

    @FXML
    private TableColumn<Equipo, Integer> idTCol;

    @FXML
    private TextField idTField;

    @FXML
    private TableColumn<Equipo, String> nombreTCol;

    @FXML
    private TextField nombreTField;

    @FXML
    private TableColumn<Equipo, String> patrocinadorTCol;

    @FXML
    private TextField patrocinadorTField;

    @FXML
    private TableColumn<Equipo, String> sancionTCol;

    @FXML
    private ToggleGroup sancionTGroup;

    @FXML
    void onAltaClick(ActionEvent event) { //Función que da de alta al equipo
        /*
        Factores a tener en cuenta:
            1. Todos los campos están rellenos
            2. No se repite el ID ni el nombre (unique) en la tabla
         */
        try {
            if (idTField.getText().isEmpty() || patrocinadorTField.getText().isEmpty() || nombreTField.getText().isEmpty() || sancionTGroup.getSelectedToggle()==null || categoriaCBox.getValue()==null){
                JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Debe rellenar todos los campos", "Error de campos");
            } else {
                int id = Integer.parseInt(idTField.getText());
                Equipo equipo = daoHibernate.buscar(id);
                if (equipo==null && daoHibernate.buscarPorNombre(nombreTField.getText())==null){
                    equipo = new Equipo(id,
                            nombreTField.getText(),
                            patrocinadorTField.getText(),
                            categoriaCBox.getValue(),
                            ((RadioButton) sancionTGroup.getSelectedToggle()).getText().equals("Si"));
                    //Para el booleano, cojo el botón seleccionado, miro su texto y si pone "Si", es true
                    if (daoMongo.insertar(equipo) && daosql.insertar(equipo)){
                        JavaFXUtils.makeAlert(Alert.AlertType.INFORMATION, "Equipo dado de alta", "Inserción");
                        tableRefresh(); //Refresca la tabla
                    } else { //Ha saltado un error en alguna de las bases de datos
                        JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Ha habido un error en alguna de las bases de datos", "Error de base de datos");
                    }
                } else { //El ID o el nombre se ha encontrado en la BD
                    JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Ya existe ese id o ese nombre en la base", "Error de inserción");
                }
            }
        }catch (NumberFormatException e){ //Try-Catch: Se ha intentado meter un id no numérico
            JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "El id debe ser numérico", "Error de inserción");
        }
    }

    @FXML
    void onEliminarClick(ActionEvent event) { //Función que elimina al equipo
        /*
        Factores a tener en cuenta:
            1. Se ha puesto un ID
            2. Se encuentra el ID en la base de datos
         */
        try {
            if (idTField.getText().isEmpty()) {
                JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Debe el identificador", "Error de campos");
            } else {
                Equipo equipo = daoHibernate.buscar(Integer.parseInt(idTField.getText()));
                if (equipo == null) { //Si es nulo es porque no se ha encontrado
                    JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "No se ha encontrado ese equipo", "Error de eliminación");
                } else {
                    if (daoHibernate.eliminar(equipo) && daoMongo.eliminar(equipo)) {
                        JavaFXUtils.makeAlert(Alert.AlertType.INFORMATION, "Equipo eliminado", "Eliminación");
                        tableRefresh();
                    } else { //Error de base de datos
                        JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Ha habido un error en alguna de las bases de datos", "Error de base de datos");
                    }
                }
            }
        }catch (NumberFormatException e){
        JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "El id debe ser numérico", "Error de inserción");
    }
    }

    @FXML
    void onFormPlayersClick(ActionEvent event) { //Función que abre la ventana de jugadores
        JugadorController controller = (JugadorController) JavaFXUtils.openInNewStage("Jugadores", "JugadorView.fxml");
        controller.iniciarDatos(daoHibernate); //Para reciclar el DAO de Hibernate, se lo paso ocmo parámetro
    }

    @FXML
    void onLimpiarClick(ActionEvent event) { //Función que borra los contenidos
        //Pone todos los textos vacíos
        nombreTField.setText("");
        idTField.setText("");
        patrocinadorTField.setText("");
        //Coge el botón seleccionado y lo desactiva
        sancionTGroup.getSelectedToggle().setSelected(false);
        //Quita el valor del ComboBox
        categoriaCBox.setValue(null);
    }

    @FXML
    void onModificarClick(ActionEvent event) { //Función que modifica el estado de un equipo
        /*
        Factores a tener en cuenta:
            1. Se ha seleccionado un ID y un estado
            2. Existe el ID en la BBDD
         */
        try {
            if (idTField.getText().isEmpty() || sancionTGroup.getSelectedToggle() == null) {
                JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Debe rellenar, como minimo, el identificador y el grupo", "Error de campos");
            } else {
                int id = Integer.parseInt(idTField.getText());

                Equipo equipo = daoHibernate.buscar(id);
                if (equipo == null) { //Si el equipo es nulo es porque no hay uno con ese ID
                    JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "No existe ese id en la base", "Error de actualización");
                } else {
                    equipo.setSancionado(((RadioButton) sancionTGroup.getSelectedToggle()).getText().equals("Si"));
                    if (daoHibernate.editar(equipo)) {
                        JavaFXUtils.makeAlert(Alert.AlertType.INFORMATION, "Estado de sanción actualizado", "Actualización");
                        tableRefresh();
                    } else { //Error de BBDD
                        JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "Ha habido un error en alguna de las bases de datos", "Error de base de datos");
                    }
                }
            }
        }catch (NumberFormatException e){ //Valor no numérico en ID
        JavaFXUtils.makeAlert(Alert.AlertType.ERROR, "El id debe ser numérico", "Error de inserción");
    }
    }

    @FXML
    void onContentClick(MouseEvent event) { //Función que se activa cuando se hace click en la tabla
        Equipo equipo = equipoTView.getSelectionModel().getSelectedItem(); //Coge el item seleccionado
        if (equipo!=null){ //Si no es nulo (Se ha seleccionado una fila con contenido):
            for (Toggle toggle : sancionTGroup.getToggles()){
                if (((RadioButton) toggle).getText().equals((equipo.isSancionado())?"Si":"No")){
                    toggle.setSelected(true);
                }
                //Recorre el grupo entero y, si el texto de uno de los toggle coincide con Si(si es true) o No(si es false), se activa
            }
            idTField.setText(String.valueOf(equipo.getIdEquipo()));
            nombreTField.setText(equipo.getNombreEquipo());
            patrocinadorTField.setText(equipo.getPatrocinador());
            categoriaCBox.setValue(equipo.getCategoria());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { //Función que se ejecuta al iniciar la vista
        //Inicio de las DAO
        daoHibernate = new EquipoDAOHibernate();
        daosql = new EquipoDAOSQL();
        daoMongo = new EquipoDAOMongo();

        //Inicio de las tablas
        idTCol.setCellValueFactory(new PropertyValueFactory<>("idEquipo"));
        categoriaTCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        nombreTCol.setCellValueFactory(new PropertyValueFactory<>("nombreEquipo"));
        patrocinadorTCol.setCellValueFactory(new PropertyValueFactory<>("patrocinador"));
        sancionTCol.setCellValueFactory(data-> new ReadOnlyObjectWrapper<>((data.getValue().isSancionado())? "Si" : "No"));

        tableRefresh(); //Refresca la tabla

        ObjectMapper JSON_MAPPER = new ObjectMapper(); //Mapeo del JSON de Categorias
        try {
            /*
            AVISO: Como del JSON solo nos interesa el nombre y no se va a hacer nada más con los demás valores,
            veo un poco inútil el hacer una clase para ello. Por ende, guardo el JSON en una lista de mapas,
            recorro el mapa entero y me quedo con el nombre de cada mapa de pares.
             */
            List<Map<String, String>> mapaValores = JSON_MAPPER.readValue(Thread.currentThread().getContextClassLoader().getResource("db/Categorias.json"), JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, Map.class));
            for (Map map : mapaValores){
                categoriaCBox.getItems().add(map.get("nombre").toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableRefresh() {
        ObservableList<Equipo> listaEquipos = FXCollections.observableArrayList(daoHibernate.listar());
        equipoTView.setItems(listaEquipos);
    }
}
