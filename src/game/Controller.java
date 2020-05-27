package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * this class for buttons and menu event handler
 */
public class Controller {

    //view of the block application
    private View view;

    //constructor of the block game controller
    public Controller(View view) {
        this.view = view;

        this.view.addMenuHandler(new MenuHandler());
        this.view.addButtonHandler(new ButtonHandler());

    }

    private class MenuHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            MenuItem pressedMenuItem = (MenuItem) event.getSource();

            if(pressedMenuItem.getText().equals("Load Game World")) {
                view.loadFile();
            }

            if(pressedMenuItem.getText().equals("Save World Map")) {
                view.saveMap();
            }
        }
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            //downcast the clicked button into Button type
            Button pressedButton = (Button) event.getSource();

            //get the dropIndex(String) from the textInput
            String dropIndexString = view.getDropIndex();

            //get the choiceBox action value "Move Builder" OR "Move Block"
            String moveAction = view.getChoiceBoxItem();

            String buttonContent = pressedButton.getText();

            List<String> directions = new ArrayList<>();
            directions.add("north");
            directions.add("east");
            directions.add("south");
            directions.add("west");


            if(buttonContent.equals("Dig")) {
                view.dig();
            }

            if(buttonContent.equals("Drop")) {
                int dropIndex;

                try {
                    dropIndex = Integer.parseInt(dropIndexString);
                    view.drop(dropIndex);

                } catch (NumberFormatException ex) {
                    //if the input is invalid show error dialog
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WorldMap");
                    alert.setContentText("the input is invalid");
                    alert.showAndWait();
                }

            }

            /*if the button clicked is one of the direction buttons
            * check choiceBox options*/
            if(directions.contains(buttonContent)) {

                //if the choiceBox value is "Move Builder", move builder
                if(moveAction.equals("Move Builder")) {
                    view.moveBuilder(buttonContent);
                }

                //if the choiceBox value is "Move Block", move block
                if(moveAction.equals("Move Block")) {
                    view.moveBlock(buttonContent);
                }

            }

        }
    }


}
