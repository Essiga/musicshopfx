package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sharedrmi.application.api.ProductService;
import sharedrmi.application.dto.AlbumDTO;


import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class MusicSearchController {

    @FXML
    private TextField musicSearchTextField;
    @FXML
    private TableView<AlbumDTO> musicView;
    @FXML
    private TableColumn<AlbumDTO, String> albumTitleCol;
    @FXML
    private TableColumn<AlbumDTO, String> releaseDateCol;
    @FXML
    private TableColumn<AlbumDTO, String> mediumTypeCol;
    @FXML
    private TableColumn<AlbumDTO, String> priceCol;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    protected void MusicSearchButtonClicked() {

        //TODO: only call Naming.lookup at startup and add error handling
        try {
            ProductService productService = (ProductService) Naming.lookup("rmi://localhost/ProductService");

            List<AlbumDTO> albums = productService.findAlbumsBySongTitle(musicSearchTextField.getText());

            ObservableList<AlbumDTO> albumDTO = FXCollections.observableArrayList(albums);

            albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            releaseDateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
            mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("mediumType"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            musicView.setItems(albumDTO);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void musicViewClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
            AlbumDTO albumDTO = musicView.getSelectionModel().getSelectedItem();

            switchSceneToProductOverview("productOverview-view.fxml", e, albumDTO);
        }
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            switchScene("musicSearch-view.fxml", e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            switchSceneToCartView("cart-view.fxml", e);
    }

    private void switchScene(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchSceneToCartView(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        CartController cartController = loader.getController();
        cartController.setData();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchSceneToProductOverview(String fxml, Event event, AlbumDTO albumDTO) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        MusicOverviewController musicOverviewController = loader.getController();
        musicOverviewController.setData(albumDTO);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}