package Calendar.MainDayPage.AddUser;

import Calendar.MainDayPage.JavaFxControls.AutoFillTextFieldForNames;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Collection;

public class AddUserDisplay extends VBox{ // mudar para extender um compoente para retornar para tras para remover

    private AutoFillTextFieldForNames nameInput;
    private Button addButton;
    private DayTypeInput dayTypeInput;
    private Collection list;

    public AddUserDisplay(String[][] nameList) {

        list = ArrayToList(nameList);
        addVerticalLayout();
    }

    private Collection ArrayToList(String[][] nameList) {
        ArrayList<String> aList = new ArrayList<>();
        if(nameList != null) {
            int j = 0;
            for (int i = 0; i < nameList[j].length; i++) {
                if(nameList[j][i] != null)
                    aList.add(nameList[j][i]);
            }
        }
        return aList;
    }

    private void addVerticalLayout() {
        if(this == null) // talvez para remover?
            createVBoxParentLayout();
        addFirstRow();
        addDayTypeReceivers();

    }

    private void addFirstRow() {
        HBox firstRow = new HBox();
        firstRow.getChildren().add(createTextLabel());
        firstRow.getChildren().add(createTextField());
        firstRow.getChildren().add(createAddButton());
        addButtonHandler();
        this.getChildren().add(firstRow);
    }

    private Text createTextLabel() {
        Text nameLabel = new Text("Name:");
        nameLabel.setFont(new Font("Arial", 16));
        return nameLabel;
    }

    private TextField createTextField() {
        return new AutoFillTextFieldForNames("Name",list);
    }

    private Button createAddButton() {
        addButton = new Button("Add");
        return addButton;
    }

    private void addButtonHandler() {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (hasCorrectFieldsToAdd())
                    addToCalendar();
            }
        });
    }

    private boolean hasCorrectFieldsToAdd() {
        if(!nameInput.hasText())
            highLightErrorInTextField();
        String name = nameInput.getText();
        return false;
    }

    private void highLightErrorInTextField() {

    }

    private void addToCalendar() {

    }

    private void addDayTypeReceivers() {
        dayTypeInput = new DayTypeInput("RadioButton", this);
    }

    private void createVBoxParentLayout() {
        setVBoxParentProperties();
    }

    private void setVBoxParentProperties() {

    }

}
