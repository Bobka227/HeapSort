package GUI;

import AbstrTable.eTypProhl;
import AgendaHeap.AgendaHeap;
import Heap.AbstrHeap;
import Obec.Obec;
import Obec.enumKraje;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgAgenda extends Application {

    private AgendaHeap agendaKraj = new AgendaHeap();
    private TextArea outputArea;
    private TextField searchField, fileField, nameField, pscField, pocetMuzuField, pocetZenField;
    private ComboBox<enumKraje> krajComboBox;
    private BorderPane bp;
    private ListView<Obec> obciLV;
    private ComboBox<eTypProhl> typItrCB;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Agenda Kraj");

        createList();

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(300);

        searchField = new TextField();
        searchField.setPromptText("Název obce...");
        fileField = new TextField();
        fileField.setPromptText("Název souboru...");
        nameField = new TextField();
        nameField.setPromptText("Název obce...");
        pscField = new TextField();
        pscField.setPromptText("PSC...");
        pocetMuzuField = new TextField();
        pocetMuzuField.setPromptText("Počet mužů...");
        pocetZenField = new TextField();
        pocetZenField.setPromptText("Počet žen...");

        krajComboBox = new ComboBox<>();
        krajComboBox.getItems().addAll(enumKraje.values());

        ObservableList<eTypProhl> list = FXCollections.observableArrayList(eTypProhl.values());
        typItrCB = new ComboBox<>(list);
        typItrCB.setPromptText("Vyberte typ iteratora");

        Button importButton = new Button("Importovat Data");
        importButton.setOnAction(e -> importData());

        Button findMaxButton = new Button("Zobrazit Max");
        findMaxButton.setOnAction(e -> displayMaxObec());

        Button addButton = new Button("Přidat Obec");
        addButton.setOnAction(e -> addObec());

        Button removeMaxButton = new Button("Odebrat Max");
        removeMaxButton.setOnAction(e -> removeMaxObec());

        Button buildTreeButton = new Button("Vybudovat Haldu");
        buildTreeButton.setOnAction(e -> buildHeap());

        Button generateButton = new Button("Generovat Obec");
        generateButton.setOnAction(e -> generateObec());

        Button reorganizeButton = new Button("Reorganizovat");
        reorganizeButton.setOnAction(e -> reorganizeHeap());

        Button displayAllButton = new Button("Zobrazit Všechny Obce");
        displayAllButton.setOnAction(e -> displayAllObce());

        Button zrusButton = new Button("Zrušit");
        zrusButton.setOnAction(e -> clearData());

        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        controls.getChildren().addAll(
                importButton, new Label("Vyhledat Max"), findMaxButton,
                new Label("Přidat obec:"), nameField, krajComboBox, pscField, pocetMuzuField, pocetZenField, addButton,
                new Label("Odebrat Max"), removeMaxButton,
                new Label("Vybudovat haldu"), buildTreeButton,
                new Label("Generovat obec"), generateButton,
                new Label("Reorganizovat"), reorganizeButton,
                new Label("Zobrazit všechny obce"), typItrCB, displayAllButton,
                zrusButton
        );

        bp = new BorderPane();
        bp.setPadding(new Insets(10));
        bp.setRight(controls);
        bp.setCenter(obciLV);
        bp.setBottom(outputArea);

        primaryStage.setScene(new Scene(bp, 800, 600));
        primaryStage.show();
    }

    private void createList() {
        ObservableList<Obec> listData = FXCollections.observableArrayList();
        obciLV = new ListView<>(listData);
        obciLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        obciLV.setPrefSize(400, 600);
    }

    private void importData() {
        try {
            String fileName = "kraje.csv";
            int count = agendaKraj.importData(fileName);
            outputArea.setText("Načteno " + count + " obcí z " + fileName);
        } catch (IOException e) {
            outputArea.setText("Chyba při načítání souboru: " + e.getMessage());
        }
    }

    private void displayMaxObec() {
        Obec maxObec = agendaKraj.zpristupniMax();
        if (maxObec != null) {
            outputArea.setText("Maximální obec: " + maxObec.toString());
        } else {
            outputArea.setText("Halda je prázdná.");
        }
    }

    private void addObec() {
        try {
            String name = nameField.getText().trim();
            enumKraje kraj = krajComboBox.getValue();
            int psc = Integer.parseInt(pscField.getText().trim());
            int pocetMuzu = Integer.parseInt(pocetMuzuField.getText().trim());
            int pocetZen = Integer.parseInt(pocetZenField.getText().trim());

            Obec novaObec = new Obec(name, kraj, psc, pocetMuzu, pocetZen);
            agendaKraj.vloz(novaObec);
            outputArea.setText("Obec přidána: " + novaObec.toString());
        } catch (NumberFormatException e) {
            outputArea.setText("Chyba při převodu čísel: " + e.getMessage());
        } catch (Exception e) {
            outputArea.setText("Chyba při přidávání obce: " + e.getMessage());
        }
    }

    private void removeMaxObec() {
        Obec removed = agendaKraj.odeberMax();
        if (removed != null) {
            outputArea.setText("Odebrána obec: " + removed.toString());
        } else {
            outputArea.setText("Halda je prázdná.");
        }
    }

    private void buildHeap() {
        try {
            agendaKraj.VybudujZeStavajicichDat();
            outputArea.setText("Halda byla vybudována.");
            displayAllObce();
        } catch (Exception e) {
            outputArea.setText("Chyba při budování haldy: " + e.getMessage());
        }
    }

    private void generateObec() {
        Obec novaObec = agendaKraj.Generuj();
        agendaKraj.vloz(novaObec);
        outputArea.setText("Vygenerována obec: " + novaObec.toString());
    }

    private void reorganizeHeap() {
        javafx.scene.control.Dialog<String> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Změnit prioritu");
        dialog.setHeaderText("Vyberte kritérium priority pro přestavbu haldy:");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Podle počtu obyvatel", "Podle názvu");
        choiceBox.setValue("Podle počtu obyvatel");

        VBox vbox = new VBox(choiceBox);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return choiceBox.getValue();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String kriterium = result.get();

            try {
                if ("Podle počtu obyvatel".equals(kriterium)) {
                    agendaKraj.zmenKriteriumNaObyvatel();
                } else if ("Podle názvu".equals(kriterium)) {
                    agendaKraj.zmenKriteriumNaNazev();
                }

                outputArea.setText("Halda byla přestavěna s novou prioritou: " + kriterium);
                displayAllObce();
            } catch (Exception e) {
                outputArea.setText("Chyba při přestavbě haldy: " + e.getMessage());
            }
        } else {
        }
    }

    private void displayAllObce() {
        eTypProhl typProhl = (eTypProhl) typItrCB.getValue();
        Iterator<Obec> itr = agendaKraj.VytvorIterator(eTypProhl.HLOUBKA);
        obciLV.getItems().clear();
        if (typProhl == eTypProhl.SIRKA) {
            itr = agendaKraj.VytvorIterator(eTypProhl.SIRKA);
        }
        while (itr.hasNext()) {
            obciLV.getItems().add(itr.next());
        }
    }

    private void clearData() {
        agendaKraj.zrus();
        outputArea.setText("Všechna data byla zrušena.");
        obciLV.getItems().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
