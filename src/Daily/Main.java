package Daily;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {

    private TableView<Task> table = new TableView<Task>();
    private final ObservableList<Task> data =
            FXCollections.observableArrayList(
            );
    final HBox hb = new HBox();
    private int width = 400;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Daily");
        stage.setWidth(width);
        stage.setHeight(720);
        stage.setX(880);
        stage.setY(0);
        String filePath = "src/Daily/DailyDatas";

        final Label label = new Label("-Daily-");
        label.setFont(new Font("Arial", 20));


        final Button clearButton = new Button("Clear");
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.remove(0,1);
            }
        });

        final Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                    writeCSV(filePath);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });

        final Button loadButton = new Button("Load");
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                    readCSV(filePath);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });


        final Button deleteRowButton = new Button("Delete");
        deleteRowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                    table.getItems().removeAll(
                            table.getSelectionModel().getSelectedItems()
                    );
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });






        HBox title = new HBox();
        HBox titleSub = new HBox();

        titleSub.getChildren().addAll(loadButton, saveButton, deleteRowButton, clearButton);
        title.getChildren().addAll(label, titleSub);
        title.setSpacing(width - 260);


        table.setEditable(true);


        TableColumn doneCol = new TableColumn("Done?");
        doneCol.setMinWidth(40);
        doneCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Done"));
        doneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        doneCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDone(t.getNewValue());
                    }
                }
        );


        TableColumn orderCol = new TableColumn("Order");
        orderCol.setMinWidth(10);
        orderCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Order"));
        orderCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder(t.getNewValue());
                    }
                }
        );

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(50);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );


        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setMinWidth(140);
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                    }
                }
        );


        table.setItems(data);
        table.getColumns().addAll(doneCol, orderCol, nameCol, descriptionCol);

        final TextField addDone = new TextField();
        addDone.setPromptText("done?");
        addDone.setMaxWidth(doneCol.getPrefWidth());
        final TextField addOrder = new TextField();
        addOrder.setMaxWidth(orderCol.getPrefWidth());
        addOrder.setPromptText("order");
        final TextField addName = new TextField();
        addName.setMaxWidth(nameCol.getPrefWidth());
        addName.setPromptText("name");
        final TextField addDesciption = new TextField();
        addDesciption.setMaxWidth(descriptionCol.getPrefWidth());
        addDesciption.setPromptText("description");

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Task(
                        addDone.getText(),
                        addOrder.getText(),
                        addName.getText(),
                        addDesciption.getText()));
                addDone.clear();
                addOrder.clear();
                addName.clear();
                addDesciption.clear();
            }
        });




        hb.getChildren().addAll(addDone, addOrder, addName, addDesciption, addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(title, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    // must have method to fill up datas on one thing
    public static class Task {

        private final SimpleStringProperty done;
        private final SimpleStringProperty order;
        private final SimpleStringProperty name;
        private final SimpleStringProperty description;

        private Task(String done, String order, String name, String description) {
            this.done = new SimpleStringProperty(done);
            this.order = new SimpleStringProperty(order);
            this.name = new SimpleStringProperty(name);
            this.description = new SimpleStringProperty(description);
        }

        public String getDone() {
            return done.get();
        }

        public SimpleStringProperty doneProperty() {
            return done;
        }

        public void setDone(String done) {
            this.done.set(done);
        }

        public String getOrder() {
            return order.get();
        }

        public SimpleStringProperty orderProperty() {
            return order;
        }

        public void setOrder(String order) {
            this.order.set(order);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getDescription() {
            return description.get();
        }

        public SimpleStringProperty descriptionProperty() {
            return description;
        }

        public void setDescription(String description) {
            this.description.set(description);
        }
    }

    // transfer our datas into a csv file
    public void writeCSV(String filePath) throws Exception{
        Writer writer = null;
        String done = "";
        String order = "";
        String name = "";
        String description = "";
        try{
            File file = new File(filePath);
            writer = new BufferedWriter(new FileWriter(file));
            for (Task task : data){

                if (task.getDone() == ""){
                    done = "-";
                }else done = task.getDone();

                if (task.getOrder() == ""){
                    order = "-";
                }else order = task.getOrder();

                if (task.getName() == ""){
                    name = "-";
                }else name = task.getName();

                if (task.getDescription() == ""){
                    description = "-";
                }else description = task.getDescription();

                writer.write(done + "," + order + "," + name + "," + description + "\n");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally{
            writer.flush();
            writer.close();
        }

    }

    private void readCSV(String filePath) {

        String CsvFile = filePath;
        String FieldDelimiter = ",";

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(CsvFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(FieldDelimiter, -1);

                Task task = new Task(fields[0], fields[1], fields[2], fields[3]);
                data.add(task);

            }

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }


}

