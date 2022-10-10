package g56020.atlg4.stibride;

import g56020.atlg4.stibride.model.dto.FavoriteDto;
import g56020.atlg4.stibride.model.dto.StationDto;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

public class FXMLController {
    @FXML
    private Button btnAddFavorite;

    @FXML
    private Button btnRmFavorite;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<StationRow, List<Integer>> colLigne;

    @FXML
    private TableColumn<StationRow, StationDto> colStation;

    @FXML
    private ImageView imgMetro;

    @FXML
    private Label lFavorite;

    @FXML
    private Label lNbStations;

    @FXML
    private Label lStatus;

    @FXML
    private SearchableComboBox<StationDto> scbDestination;

    @FXML
    private SearchableComboBox<FavoriteDto> scbFavorite;

    @FXML
    private SearchableComboBox<StationDto> scbOrigine;

    @FXML
    private ScrollPane spMetro;

    @FXML
    private TableView<StationRow> tableStops;

    record StationRow(StationDto station, List<Integer> lines) {
    }


    @FXML
    public void initialize(List<StationDto> stations, List<FavoriteDto> favorites) {
        initSearchableComboBox(stations, favorites);
        initTableView();
    }

    public void setButtonHandler(Presenter presenter) {

        this.btnSearch.setOnAction(event -> {
            presenter.searchShortestPath(scbOrigine.getValue(), scbDestination.getValue());
        });

        this.btnAddFavorite.setOnAction(event -> {
            TextInputDialog textInput = new TextInputDialog("");
            textInput.setResizable(true);
            textInput.setHeaderText("Entrez le nom du trajet");
            textInput.setTitle("Trajet Favoris");
            textInput.setContentText("Nom : ");
            textInput.showAndWait();
            String fav_name = textInput.getResult();
            if (fav_name != null) {
                presenter.addFavorite(scbOrigine.getValue(), scbDestination.getValue(), fav_name);
            }
        });

        this.btnRmFavorite.setOnAction(event -> {
            presenter.removeFavorite(scbOrigine.getValue(), scbDestination.getValue());
        });
    }

    public void addStation(StationRow row) {
        this.tableStops.getItems().add(row);
    }

    public void clearStations() {

        tableStops.getItems().clear();
    }

    private void initSearchableComboBox(List<StationDto> stations, List<FavoriteDto> favorites) {
        for (var station : stations) {
            scbOrigine.getItems().add(station);
            scbDestination.getItems().add(station);
        }
        scbFavorite.setItems(FXCollections.observableArrayList(favorites));

        scbFavorite.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                scbOrigine.setValue(newValue.getSource());
                scbDestination.setValue(newValue.getDestination());
            }
        });

        scbOrigine.setValue(stations.get(0));
        scbDestination.setValue(stations.get(1));
    }

    public void refreshFavorites(List<FavoriteDto> favorites) {
        if (favorites == null) {
            scbFavorite.setItems(FXCollections.observableArrayList());
        } else {
            scbFavorite.setItems(FXCollections.observableArrayList(favorites));
        }
    }

    private void initTableView() {
        colLigne.setCellValueFactory(element -> new ReadOnlyObjectWrapper<>(element.getValue().lines));
        colStation.setCellValueFactory(element -> new ReadOnlyObjectWrapper<>(element.getValue().station));
    }
}
