import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;


public class FloorBuilderApp extends Application  {
    private FloorBuilderView   view;
    private Building           model;
    private int                currentFloor;    // Index of the floor being displayed
    private int                currentColor;    // Index of the current room color

    public void start(Stage primaryStage) {
        model = Building.example();
        currentFloor = 0;
        currentColor = 0;

        VBox aPane = new VBox();
        view = new FloorBuilderView(model);
        view.setPrefWidth(Integer.MAX_VALUE);
        view.setPrefHeight(Integer.MAX_VALUE+20);

        //Menu Bar
        String floor1Name = FloorPlan.floor1().getName();
        String floor2Name = FloorPlan.floor2().getName();
        String floor3Name = FloorPlan.floor3().getName();
        String floor4Name = FloorPlan.floor4().getName();
        String floor5Name = FloorPlan.floor5().getName();


        Menu selectFloorMenu = new Menu("Select Floor");
        MenuItem menuItem1 = new MenuItem(floor4Name);
        MenuItem menuItem2 = new MenuItem(floor3Name);
        MenuItem menuItem3 = new MenuItem(floor2Name);
        MenuItem menuItem4 = new MenuItem(floor1Name);
        MenuItem menuItem5 = new MenuItem(floor5Name);


        selectFloorMenu.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, new SeparatorMenuItem(), menuItem5);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(selectFloorMenu);
        aPane.getChildren().add(menuBar);


        aPane.getChildren().add(view);
        primaryStage.setTitle("Floor Plan Builder");
        primaryStage.setScene(new Scene(aPane, 370,320));
        primaryStage.show();

        //Menu bar event handlers
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                view.update(3, currentColor);}});
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                view.update(2, currentColor);}});
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                view.update(1, currentColor);}});
        menuItem4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                view.update(0, currentColor);}});
        menuItem5.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                view.update(4, currentColor);}});

        // Plug in the floor panel event handlers:
        for (int r=0; r<model.getFloorPlan(0).size(); r++) {
            for (int c=0; c<model.getFloorPlan(0).size(); c++) {
                view.getFloorTileButton(r, c).setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        handleTileSelection(event);
                    }});
            }
        }

        // Plug in the radioButton event handlers
        view.getEditWallsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getSelectExitsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getEditRoomsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getDefineRoomsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});

        // Plug in the office color button
        view.getRoomColorButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                currentColor = (currentColor + 1)%view.ROOM_COLORS.length;
                view.update(currentFloor, currentColor);
            }});

        view.update(currentFloor, currentColor);
    }

    // Handle a Floor Tile Selection
    private void handleTileSelection(ActionEvent e) {
        // Determine which row and column was selected
        int r=0, c=0;
        OUTER:
        for (r=0; r<model.getFloorPlan(0).size(); r++) {
            for (c=0; c<model.getFloorPlan(0).size(); c++) {
                if (e.getSource() == view.getFloorTileButton(r, c))
                    break OUTER;
            }
        }

        // Check if we are in edit wall mode, then toggle the wall
        if (view.getEditWallsButton().isSelected()) {
            model.getFloorPlan(currentFloor).setWallAt(r, c, !model.getFloorPlan(currentFloor).wallAt(r, c));
            // Remove this tile from the room if it is on one, because it is now a wall
            Room room = model.getFloorPlan(currentFloor).roomAt(r, c);
            if (room != null)
                room.removeTile(r, c);
        }

        // Otherwise check if we are in edit exits mode
        else if (view.getSelectExitsButton().isSelected()) {
            if (model.exitAt(r, c) != null)
                model.removeExit(r, c);
            else {
                model.addExit(r, c);
                // Remove this tile from the room if it is on one, because it is now an exit
                Room off = model.getFloorPlan(currentFloor).roomAt(r, c);
                if (off != null)
                    off.removeTile(r, c);
            }
        }

        // Otherwise check if we are selecting a room tile
        else if (view.getEditRoomsButton().isSelected()) {
            if (!model.getFloorPlan(currentFloor).wallAt(r, c) && !model.hasExitAt(r, c)) {
                Room room = model.getFloorPlan(currentFloor).roomAt(r, c);
                if (room != null) {
                    room.removeTile(r, c);
                    if (room.getNumberOfTiles() == 0)
                        model.getFloorPlan(currentFloor).removeRoom(room);
                }
                else {
                    room = model.getFloorPlan(currentFloor).roomWithColor(currentColor);
                    if (room == null) {
                        room = model.getFloorPlan(currentFloor).addRoomAt(r, c);
                        room.setColorIndex(currentColor);
                    }
                    else {
                        room.addTile(r, c);
                    }
                }
            }
        }
        else if (view.getDefineRoomsButton().isSelected()){
            if(model.getFloorPlan(currentFloor).roomAt(r,c) == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Selection");
                alert.setHeaderText("You must select a tile that belongs to a room.");
                alert.showAndWait();
            }
        }
        // Otherwise do nothing
        else {
        }

        view.update(currentFloor, currentColor);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
