package game;
import csse2002.block.world.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.*;
import java.util.*;

/**
 * This view class is to create GUI view and contains methods
 * that updates the view
 */
public class View {

    //this is the root box to put all the elements
    private VBox rootBox;

    //the gridPane that draw the worldMap
    private GridPane grid;

    //this member is to represent the block world
    private WorldMap worldMap;

    //the private member that represent the primaryStage
    private Stage stage;

    //the private member that store all the buttons
    private Button[] buttons;

    //the private member that store all the choice box buttons
    private ChoiceBox<String> moveButtons;

    //private member that store all the file menu items
    private MenuItem[] menuItems;

    //private member that represent the Inventory
    private Label labelInventory;

    //private member that represent the inventory list
    private Label labelInventoryList;

    private Builder builder;

    //the intial grid Position of the startingTile x coordinate
    private final int builderGridPositionX = 4;

    //the initial grid position of the startingTile y coordinate
    private final int builderGridPositionY = 4;

    //builder position coordinate x
    private int currentPositionX;

    //builder position coordinate y
    private int currentPositionY;

    //the text input for user to enter drop block index
    private TextField dropIndexInput;
    /**
     * View constructor
     */
    public View(Stage primaryStage) {
        rootBox = new VBox();
        stage = primaryStage;
        addElements();
    }

    /**
     * add the GUI elements to the root box
     */
    private void addElements() {
        //create left and right box
        VBox leftBox = new VBox();
        VBox rightBox = new VBox();



        //add all the components into the left and right box
        addLeftSideElements(leftBox);
        addRightSideElements(rightBox);

        //create the container for the left and rightBox
        HBox leftRightContainer = new HBox();
        leftRightContainer.getChildren().addAll(leftBox,rightBox);

        //create the menuBar and add the menu items
        MenuBar menuBar = new MenuBar();
        addMenuItems(menuBar);

        VBox bottomContainer = new VBox();
        addBottomInventoryList(bottomContainer);
        //add everything into the rootBox
        rootBox.getChildren().addAll(menuBar,leftRightContainer,
                bottomContainer);

    }

    /**
     * return the scene that store the view to the main application
     * @return scene with the current view
     */
    public Scene getScene() {
        return new Scene(rootBox);
    }

    /**
     *add the menu items into the menuBar
     * @param menuBar the menuBar that contains load and save options
     */
    private void addMenuItems(MenuBar menuBar) {

        menuItems = new MenuItem[2];
        //add the menu into the MenuBar
        Menu menu = new Menu("File");

        //add menu items
        MenuItem menuItemLoad = new MenuItem("Load Game World");
        MenuItem menuItemSave = new MenuItem("Save World Map");

        //store the menuItems into the private member menuItems
        menuItems[0] = menuItemLoad;
        menuItems[1] = menuItemSave;

        //add the menu items into the menu, and then add to menuBar
        menu.getItems().add(menuItemLoad);
        menu.getItems().add(menuItemSave);

        menuBar.getMenus().add(menu);

    }

    /**
     *add left side element which is the gridPane for drawing the world
     * Map
     * @param leftBox VBox that store the gridPane
     */
    private void addLeftSideElements(VBox leftBox) {
        leftBox.setPadding(new Insets(10,10,10,10));
        //create HBox for canvas to add
        HBox gridContainer = new HBox();

        //create canvas to draw the block map
        //Canvas canvas = new Canvas(450,450);

        int columnNumber = 9;
        int rowNumber = 9;
        //create gridPane for the block map
        grid = new GridPane();

        //grid.setGridLinesVisible(true);

        ColumnConstraints column = new ColumnConstraints(50);

        //set row constraint
        RowConstraints row = new RowConstraints(50);

        for(int i = 0; i < columnNumber; i++) {
            grid.getColumnConstraints().add(column);
        }

        for(int i =0; i < rowNumber; i++) {
            grid.getRowConstraints().add(row);
        }


        //add grid to the gridcontainer
        gridContainer.getChildren().add(grid);

        //set grid border line as black
        gridContainer.setStyle("-fx-border-color:black");

        leftBox.getChildren().addAll(gridContainer);
    }

    /**
     * add the buttom element (builder inventory list label) in to the
     * VBox container
     * @param bottomContainer VBox that store the builder's inventory
     *                        list
     */
    private void addBottomInventoryList(VBox bottomContainer) {
        bottomContainer.setPadding(new Insets(0,10,
                20,10));
        List<Block> inventoryList = new ArrayList<>();

        //create the labels of builder's inventory
        labelInventory = new Label("Inventory:");
        labelInventoryList = new Label(inventoryList.toString());

        //set the font for the labels
        labelInventory.setFont(Font.font("Verdana",
                FontWeight.BOLD,12));

        labelInventoryList.setFont(Font.font("Verdana",
                FontWeight.BOLD,12));

        bottomContainer.getChildren().addAll(labelInventory,
                labelInventoryList);
    }


    /**
     * this cass add the right side elements into the right side
     * container
     * @param rightBox the VBox that add all the right side elements such
     *                 as buttons and input fields
     */
    private void addRightSideElements(VBox rightBox) {
        //set padding and space for the right box
        rightBox.setPadding(new Insets(10,10,10,10));
        rightBox.setSpacing(10);

        //create a gridPane for direction buttons
        GridPane buttonGrid = new GridPane();


        //assign the private member buttons to button array
        buttons = new Button[6];

        //create north direction button and disable it first
        Button northButton = new Button("north");

        //create east direction button and disable it first
        Button eastButton = new Button("east");

        //create south direction button and disable it first
        Button southButton = new Button("south");

        //create west direction button and disable it first
        Button westButton = new Button("west");

        //create a dig button and disabled first
        Button digButton = new Button("Dig");

        //create the drop button and disable it first
        Button dropButton = new Button("Drop");
        //dropButton.setDisable(true);

        //add those buttons into the button array
        buttons[0] = northButton;
        buttons[1] = eastButton;
        buttons[2] = southButton;
        buttons[3] = westButton;
        buttons[4] = digButton;
        buttons[5] = dropButton;

        //set all the buttons size and font size
        for(int i = 0; i < 6; i++) {
            buttons[i].setMinWidth(70);
            buttons[i].setStyle("-fx-font-weight: BOLD;-fx-font-size: 12;" +
                    "-fx-background-color: BLUE; -fx-text-fill:WHITE");
            buttons[i].setDisable(true);

        }

        for(int i = 0; i < 4; i++) {
            buttons[i].setMinHeight(40);
        }

        //add the direction buttons to the gridPane
        buttonGrid.add(northButton,1,0);
        buttonGrid.add(eastButton,2,1);
        buttonGrid.add(southButton,1,2);
        buttonGrid.add(westButton,0,1);


        //move the gridPane to the center of the rightBox
        buttonGrid.setAlignment(Pos.CENTER);

        //add choiceBox of MoveBuilder and moveBlock
        moveButtons = new ChoiceBox<>();

        moveButtons.setStyle("-fx-font-size: 12 ");

        //create two menuItems "Move Builder" and "Move Block"
        moveButtons.getItems().add("Move Builder");
        moveButtons.getItems().add("Move Block");

        //set move builder as default option
        moveButtons.getSelectionModel().select(0);

        //set the choiceBox disable first
        moveButtons.setDisable(true);

        //create a container for the choiceBox
        HBox choiceBoxContainer = new HBox();

        choiceBoxContainer.setMargin(moveButtons,new Insets
                (20,5,20,5));

        choiceBoxContainer.getChildren().add(moveButtons);


        //move the choiceBox to the center of the rightBox
        choiceBoxContainer.setAlignment(Pos.CENTER);

        //create a dropButton and textField container
        HBox dropContainer = new HBox();
        //set dropContainer padding and space
        dropContainer.setSpacing(5);

        //create the drop Index textField
        dropIndexInput = new TextField();

        dropIndexInput.setDisable(true);

        //add the dropButton and textField into the HBox
        dropContainer.getChildren().addAll(dropButton,
                dropIndexInput);

        //add all the rightBox elements together
        rightBox.getChildren().addAll(buttonGrid,choiceBoxContainer,
                digButton,dropContainer);

    }

    /**
     * load the selected file into the worldMap
     */
    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);

        //if the file is choose
        if(file != null) {
            //try to load the map
            try {
                String fileName = file.getPath();
                //clear previous drawings

                //try to load the file and assigning to the worldMap
                worldMap = new WorldMap(fileName);

                //assigning the worldMap builder to the class builder
                builder = worldMap.getBuilder();

                //get the starting position for the builder
                currentPositionX = worldMap.getStartPosition().getX();
                currentPositionY = worldMap.getStartPosition().getY();

                //enable all the buttons
                enableButtons();

                //enable the choiceBox move buttons
                moveButtons.setDisable(false);

                //enable the text input
                dropIndexInput.setDisable(false);

                //clear the previous drawling and redraw the map
                redrawMap();

                //show "load successfully" dialog
                informationBox("Load Map successfully", "the map " +
                        "is loaded");

            } catch (FileNotFoundException ex) {
                //if the file is not found show error message
                alertBox("Cannot load Map", "The file is " +
                        "not found");

            } catch (WorldMapInconsistentException ex) {
                //if the worldMap is not consistent show error message
                alertBox("Cannot load Map", "The map is " +
                        "inconsistent");

            } catch (WorldMapFormatException ex) {
                alertBox("Cannot load Map", "The map format" +
                        " is wrong");
            }
        }
    }

    /**
     * add the eventHandler to all the buttons
     * @param buttonHandler the eventHandler object for buttons
     */
    public void addButtonHandler(EventHandler<ActionEvent> buttonHandler)
    {
        for(Button button:buttons) {
            button.setOnAction(buttonHandler);
        }

    }

    /**
     * update the inventory list in the GUI
     */
    private void updateInventoryList()
    {
        //get the blocks from the builder's inventory
        List<Block> inventoryBlocks = builder.getInventory();

        //convert the block types into the list of block type
        List<String> inventoryBlockTypes = new ArrayList<>();

        //add the block type into the list of block type string
        for(Block block: inventoryBlocks) {
            inventoryBlockTypes.add(block.getBlockType());
        }

        //change the inventory label into the builder's inventory list
        labelInventoryList.setText(inventoryBlockTypes.toString());

        labelInventory.setText("Builder Inventory:");
    }

    /**
     * this method link all menu with the menuHandler
     * @param menuHandler - eventHandler object for menu in the GUI
     */
    public void addMenuHandler(EventHandler<ActionEvent> menuHandler) {
        for(MenuItem item: menuItems) {
            item.setOnAction(menuHandler);
        }
    }


    /**
     * draw all the tiles in the gridPane
     */
    private void drawTiles()
    {
        /*calculate the difference between actual map position
        and grid position */
        int[] differenceXY = calculateMapPositionDifference();

        //get the x coordinate difference
        int xDifference = differenceXY[0];

        //get the y coordinate difference
        int yDifference = differenceXY[1];

        /*add the coordinate difference to get the actual position coordinate
        by iterating through all the grid in the 9 x 9 gridPane
         */
        for(int gridX = 0; gridX < 9; gridX++) {
            for(int gridY = 0; gridY < 9; gridY++) {

                /*add the difference to the grid position get the
                actual map position for x and y coordinate
                 */
                int actualPositionX = gridX + xDifference;
                int actualPositionY = gridY + yDifference;

                //get the position using the actual x and y coordinates
                Position actualPosition = new Position(actualPositionX,
                        actualPositionY);

                //get the tile of the actualposition
                Tile tileToDraw = worldMap.getTile(actualPosition);

                //if there is tile on that position, draw tile on that grid
                if(tileToDraw != null) {
                    drawTile(tileToDraw, gridX, gridY);
                }

            }
        }

    }

    /**
     * draw specific tile in the gridPane
     * @param tileToDraw the tile to draw
     */
    private void drawTile(Tile tileToDraw, int x, int y)
    {

        //get the size of the blocks in the input tile
        //int numberOfBlock = tile.getBlocks().size();

        int numberOfBlocks = tileToDraw.getBlocks().size();
        String blockNumberString = Integer.toString(numberOfBlocks);

        Text blockNumberText = new Text(blockNumberString);
        blockNumberText.setFill(Color.WHITE);
        blockNumberText.setFont(Font.font("Verdana",20));

        //Get topBlock of the tile for the colour
        Block topBlock = null;
        try {
            topBlock = tileToDraw.getTopBlock();

        } catch (TooLowException ex) {

        }

        //create the stack that represent the tile
        StackPane stack = new StackPane();



        //if the tile contain blocks, draw colour that represent the topBlock
        if(numberOfBlocks > 0) {
            String blockType = topBlock.getBlockType();
            paintTopBlockColour(blockType,stack);
        }

        //when there is no block on the tile, paint the tile as black
        if(numberOfBlocks == 0) {
            stack.setStyle("-fx-background-color: BLUE");
        }

        //set the text on the top right corner
        stack.setAlignment(blockNumberText,Pos.TOP_RIGHT);

        stack.getChildren().add(blockNumberText);

        //draw exit on the stackPane
        drawExits(tileToDraw, stack);

        grid.add(stack,x,y);

    }

    /**
     * calculate the difference between the gridposition of startingTile
     * and sparseTileArray position of startingTile(actual position) based
     * on the difference we can convert the current grid position into
     * actual Map position
     */
    private int[] calculateMapPositionDifference ()
    {
        //declare a size two int array to store x and y differences
        int[] differenceXY = new int[2];

        Position actualPosition = worldMap.getStartPosition();
        int actualX = actualPosition.getX();
        int actualY = actualPosition.getY();
        int xDifference = currentPositionX - builderGridPositionX;
        int yDifference = currentPositionY - builderGridPositionY;

        differenceXY[0] = xDifference;
        differenceXY[1] = yDifference;

        return differenceXY;

    }


    /**
     * move builder action
     */
    public void moveBuilder(String direction) {

        //get the current tile first
        Tile currentTile = builder.getCurrentTile();

        //get the exit of that tile
        Tile goToTile = currentTile.getExits().get(direction);


        try{

            //move the builder to the corresponding tile
            builder.moveTo(goToTile);

            //change the builder position based on the direction
            changeBuilderPosition(direction);

        } catch (NoExitException ex) {
            if(goToTile == null) {
                alertBox("Cannot move builder", "there is "
                        + "no such exit");
            } else {
                alertBox("Cannot move builder", "The " +
                        "height difference is more than one");
            }

        }

        //redraw the map
        redrawMap();

    }

    /**
     * move the builder's current map position based on the direction
     * @param direction direction of the exit "north", "south", "east"
     *                  ,"west"
     */
    private void changeBuilderPosition (String direction) {
        //if the direction is north, y coordinate minus one
        if (direction.equals("north")) {
            currentPositionY = currentPositionY - 1;
        }

        //if the direction is east, x coordinate plus one
        else if (direction.equals("east")) {
            currentPositionX = currentPositionX + 1;
        }

        //if the direction is south, Y coordinate plus one
        else if (direction.equals("south")) {
            currentPositionY = currentPositionY + 1;
        }

        //if the direction is west, X coordinate minus one
        else if (direction.equals("west")) {
            currentPositionX = currentPositionX - 1;
        }
    }

    /**
     * this method clear the worldMap
     */
    private void clearWorldMap() {
        grid.getChildren().clear();
    }



    private void drawBuilder() {

        //create a circle that represent the builder
        Circle circle = new Circle(5,5f,5);

        //set the circle colour as black
        circle.setFill(Color.BLACK);

        //put the builder in the center of the tile
        grid.setHalignment(circle, HPos.CENTER);

        //put the border contain the builder in the center of the gridPane
        grid.add(circle,builderGridPositionX,builderGridPositionY);

    }

    private void drawExits(Tile tileToDraw, StackPane stack) {
        Map<String,Tile> exits = tileToDraw.getExits();

        //create gridPane inside the each grid of the map for exit drawling
        GridPane gridExit = new GridPane();

        ColumnConstraints column = new ColumnConstraints(10);

        //set row constraint
        RowConstraints row = new RowConstraints(10);

        //the gridPane seperated into 5 columns
        for(int i = 0; i < 5; i++) {
            gridExit.getColumnConstraints().add(column);
        }

        //the gridPane seperated into 5 rows
        for(int i =0; i < 5; i++) {
            gridExit.getRowConstraints().add(row);
        }

        /*if the tile contains north exits,draw triangle on the top
        and point to the north direction
         */
        if (exits.containsKey("north")) {

            //create a triangle that point to the "north"
            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                0.0,10.0,
                5.0,0.0,
                10.0,10.0
            );

            //set triangle colour as black
            triangle.setFill(Color.BLACK);

            //locate the triangle on the top of the grid
            gridExit.add(triangle,2,0);
        }

        /*if the tile contains east exits, draw triangle on the right
        and point to the east direction
         */
        if (exits.containsKey("east")) {

            //create a triangle point ot the east
            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                    0.0,0.0,
                    10.0,5.0,
                    0.0,10.0
            );

            //set triangle colour as black
            triangle.setFill(Color.BLACK);

            //locate the triangle on the right of the grid
            gridExit.add(triangle,4,2);
        }

        /*if the tile contain south exit draw triangle on the top and
        point to the north direction
         */
        if (exits.containsKey("south")) {

            //create a triangle point ot the south
            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                    0.0,0.0,
                    10.0,0.0,
                    5.0,10.0
            );

            //set triangle colour as black
            triangle.setFill(Color.BLACK);

            //locate the triangle on the bottom of the grid
            gridExit.add(triangle,2,4);
        }
        if (exits.containsKey("west")) {

            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                    10.0,0.0,
                    10.0,10.0,
                    0.0,5.0
            );

            triangle.setFill(Color.BLACK);
            gridExit.add(triangle,0,2);
        }

        stack.getChildren().add(gridExit);
    }


    /**
     * paint the stackPane based on the topBlock of the tile
     * "Brown" for stone block
     * "GREEN" for grass block
     * "ORANGE" for soil block
     * "RED" for wood block
     * @param blockType  the top block type(String) of the tile top block
     * @param stack stackPane that represent the tile
     */
    private void paintTopBlockColour(String blockType, StackPane stack) {

        // if the blockType is stone
        if (blockType.equals("stone")) {
            //paint the stackPane as BROWN
            stack.setStyle("-fx-border-color:BLACK;-fx-border-width:0.5;" +
                    "-fx-background-color:BROWN");
        } else if(blockType.equals("grass")) {

            //if the blockType is grass, paint as GREEN
            stack.setStyle("-fx-border-color:BLACK; -fx-border-width:0.5;" +
                    "-fx-background-color: GREEN");
        } else if (blockType.equals("soil")) {

            //if the blockType is Soil, paint as ORANGE
            stack.setStyle("-fx-border-color:BLACK;-fx-border-width:0.5;" +
                    "-fx-background-color: ORANGE");
        } else if (blockType.equals("wood")) {

            //if the blockType is Wood, paint as RED
            stack.setStyle("-fx-border-color:BLACK;-fx-border-width:0.5;"+
                    "-fx-background-color: RED");
        }
    }


    /**
     * the dig action on the worldMap
     */
    public void dig() {
        try {
            builder.digOnCurrentTile();

            //clear the worldMap and redraw the worldMap
            redrawMap();
        } catch (TooLowException ex) {

            //if the target tile is too low to dig, call the alert function
            alertBox("Cannot dig target Block", "the " +
                    "target tile is too low");

        } catch (InvalidBlockException ex ) {

            //if the top block is invalid to dig, call the alert function
            alertBox("Cannot dig target Block", "the target "
                    + "block is invalid");

        }
    }

    /**
     * this method is the drop action
     * @param blockIndex the index of inventory block
     */
    public void drop(int blockIndex) {
        try {
            //drop the given index block on the current tile
            builder.dropFromInventory(blockIndex);

            //if the drop action is done successfully redraw map
            redrawMap();
        } catch (InvalidBlockException ex) {

            //call the invalid block alert box
            alertBox("Cannot drop the block", "the target " +
                    "block is invalid");

        } catch(TooHighException ex) {

            //if the target tile is too high, call the alert box function
            alertBox("Cannot drop the block", "the target " +
                    "tile is too high");


        }

    }

    /**
     *get the drop block index input
     * @return drop index input from the index textfield
     */
    public String getDropIndex() {
        return dropIndexInput.getText();
    }

    /**
     *get the menuItem text from the choiceBox
     * @return the menuItem content
     */
    public String getChoiceBoxItem() {
        return moveButtons.getValue();
    }


    /**
     *enable all the buttons
     */
    private void enableButtons() {
        for(Button button: buttons) {
            button.setDisable(false);
        }
    }

    /**
     * move block action
     * @param direction the direction of the exit
     */
    public void moveBlock(String direction) {

        //get the current tile of the builder
        Tile currentTile = builder.getCurrentTile();
        try{
            //move block to the corresponding direction
            currentTile.moveBlock(direction);

            //clear the map and redraw it
            redrawMap();

        } catch (TooHighException ex) {

            //if the block is too high to move show alert box
            alertBox("Cannot move block","target tile is " +
                    "too high");

        } catch(InvalidBlockException ex) {

            //if the block is invalid to move call invalid alert box
            alertBox("Cannot move block","Block on the " +
                    "current tile cannot be moved");

        } catch(NoExitException ex) {

            //if there is no exit, show the error dialog
            alertBox("Cannot move block","there is no such" +
                    " exit");

        }
    }


    /**
     * redraw the map by clear the map first and draw each elements
     */
    private void redrawMap() {
        //clear the map first
        clearWorldMap();

        //then draw the tiles
        drawTiles();

        //draw the builder on the map
        drawBuilder();

        //update builder inventory list label
        updateInventoryList();

    }


    /**
     * save the map into the given path
     */
    public void saveMap() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Map");

        File file = fileChooser.showSaveDialog(stage);

        if(file != null) {
            String fileName = file.getPath();
            try{
                worldMap.saveMap(fileName);
            } catch (IOException ex) {
                alertBox("Cannot save file", "the file is" +
                        " incorrect");
            }
        }
    }


    /**
     * this method generate a dialog box shown the error message
     * @param title title of the error message
     * @param errorMessage content of the error message
     */
    private void alertBox(String title, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }


    /**
     * this method generate a dialog box shown user message
     * @param title title of the information box
     * @param text content of the information box
     */
    private void informationBox(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
