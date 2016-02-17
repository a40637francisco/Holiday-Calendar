package Calendar.MainDayPage;


import Calendar.DataBase.Queries;
import Calendar.MainCallendarPage.Main;
import Calendar.MainDayPage.AddUser.AddUserDisplay;
import Calendar.MainDayPage.JavaFxControls.AutoFillTextFieldForNames;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;

import static java.lang.Integer.parseInt;

public class DayPopUp {
    private static final double WINDOW_HEIGHT = 450;
    private static final double WINDOW_WIDTH = 495;
    private final ArrayList<String> nameList;
    private int MAX_DAY;
    private final String year;
    private final String month;
    private final String day;
    private Stage activeStage;
    private Scene activeScene;
    private BorderPane mainLayout;
    private String WINDOW_TITLE;
    private VBox topVBoxLayout;

    private final String[] monthStrings = {"Janeiro","Fevereiro","Marco","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
    private HBox centerHorizontalLayout;
    private VBox vBoxAtLeftCenter;
    private AutoFillTextFieldForNames textField;
    private HBox hBoxAtLeftCenter;
    private ScrollPane scrollPane;
    private Button buttonAddUser;
    private VBox vBoxScrollPane;
    private boolean isAddingUser;

    public DayPopUp(String id, String year) {
        this.year = year;
        month = getMonth(id);
        day = getDay(id);
        this.MAX_DAY = getMaxDay(month, year);
        WINDOW_TITLE = day+month+"/"+year;
        nameList = getNameListForDate();
        createWindow();
    }

    private ArrayList getNameListForDate() {
       return ToArrayList(Main.sqlDataBaseLink.callQuery(Queries.GetUsersFromDate,setParameters()));
    }

    private ArrayList ToArrayList(String[][] result) {
        ArrayList<String> ret = new ArrayList<>();
        for (int i = 0; i < result[0].length ; i++) {
            ret.add(i,result[0][i]);
        }
        return ret;
    }

    private ArrayList setParameters() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add(0,day);ret.add(1,month);ret.add(2,year);
        return ret;
    }

    private int getMaxDay(String month, String year) {
        int intYear = Integer.parseInt(year);
        int intMonth = getIntMonth(month);
        GregorianCalendar calendar = new GregorianCalendar(intYear, intMonth-1, 2);
        int maxDay = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return maxDay;
    }

    private int getIntMonth(String month) {
        for (int i = 0; i < monthStrings.length; i++) {
            if(month.equals(monthStrings[i]))
                return i+1;
        }
        throw new InvalidMonthException();
    }

    private String getDay(String id) {
        String ret="";
        for (int i = 0; i < id.length() && id.charAt(i) != '/'; i++) {
            ret+=id.charAt(i);
        }
        return ret;
    }

    private String getMonth(String id) {
        String ret;
        int i;
        for (i = 0; i < id.length() && id.charAt(i) != '/'; i++) {}
        ret = id.substring(++i);
        int monthidx = parseInt(ret);
        return monthStrings[monthidx];
    }

    private void createWindow() {
        activeStage = new Stage();
        createMainLayout();
        addToMainLayout();
        activeScene = new Scene(mainLayout,WINDOW_WIDTH,WINDOW_HEIGHT);
        stageProprieties();
        activeStage.showAndWait();
    }

    private void stageProprieties() {
        activeStage.setTitle(WINDOW_TITLE);
        activeStage.setScene(activeScene);
        activeStage.initModality(Modality.APPLICATION_MODAL);
    }

    private void createMainLayout() {
        mainLayout = new BorderPane();
        mainLayoutProprieties();
    }

    private void mainLayoutProprieties() {

    }

    private void addToMainLayout() {
        addToTop();
        addToCenter();
    }


    private void addToCenter() {
        divideCenter();
        addToLeftSideOfCenter();
        mainLayout.setCenter(centerHorizontalLayout);
    }

    private void divideCenter() {
        centerHorizontalLayout = new HBox();
        centerHorizontalLayout.setSpacing(50);
    }

    private void addToLeftSideOfCenter() {
        vBoxAtLeftCenter = new VBox();
        hBoxAtLeftCenter = new HBox();
        addFirstRow();
        createScrollPane();
        centerHorizontalLayout.getChildren().add(vBoxAtLeftCenter);

    }

    private void createScrollPane() {
        scrollPane = new ScrollPane();
        scrollPaneProprieties();
        vBoxScrollPane = new VBox();
        addToScrollPane();
        vBoxAtLeftCenter.getChildren().add(scrollPane);
    }

    private void addToScrollPane() {
        Iterator<String> it = nameList.iterator();
        while(it.hasNext()) {
            Text txt = new Text(it.next());
            handleNameClicked(txt);
            txt.setFont(new Font("Arial", 16));
            vBoxScrollPane.getChildren().add(txt);
        }
        scrollPane.setContent(vBoxScrollPane);
    }

    private void handleNameClicked(Text txt) {
        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("deu");
            }
        });
    }

    private void scrollPaneProprieties() {
        scrollPane.setPrefWidth(WINDOW_WIDTH / 3);
    }

    private void addFirstRow() {
        addImageToLeftCenter();
        addTextField();
        vBoxAtLeftCenter.getChildren().add(hBoxAtLeftCenter);
    }

    private void addImageToLeftCenter() {
//        todo
    }

    private void addTextField() {
        textField = new AutoFillTextFieldForNames("name",nameList);
        hBoxAtLeftCenter.getChildren().add(textField);
    }




    private void addToTop() {
        topVBoxLayout = new VBox();
        vBoxLayoutProprieties();
        addTitleToTopLayout();
        addButtonToTopLayout();
        mainLayout.setTop(topVBoxLayout);
    }

    private void addTitleToTopLayout() {
        Text txt = new Text(day+"  "+month+"  " + year);
        txt.setFill(Color.WHITE);
        txt.setFont(new Font("Arial", 32));
        topVBoxLayout.getChildren().add(txt);

    }

    private void vBoxLayoutProprieties() {
        topVBoxLayout.setStyle("-fx-background-color: #488F8F;");
        topVBoxLayout.setAlignment(Pos.CENTER);
        topVBoxLayout.setPrefHeight(100);
    }

    private void addButtonToTopLayout() {
        buttonAddUser = new Button("add");
        buttonAddUserProprieties();
        buttonAddUserHandler();
        topVBoxLayout.getChildren().add(buttonAddUser);
    }

    private void buttonAddUserProprieties() {
        buttonAddUser.setFont(new Font("Arial", 17));
        buttonAddUser.setTextFill(Color.WHITE);
        buttonAddUser.setStyle("-fx-background-color: #FF6400;");
    }

    private AddUserDisplay addUserDisplay;

    private void buttonAddUserHandler() {
        buttonAddUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!isAddingUser) {
                    isAddingUser = true;
                    addUserDisplay = new AddUserDisplay((Main.sqlDataBaseLink.callQuery(Queries.GetAllUsers, null)));
                    centerHorizontalLayout.getChildren().add(addUserDisplay);
                } else {
                    isAddingUser = false;
                    centerHorizontalLayout.getChildren().remove(addUserDisplay);
                }

            }
        });
    }


    public class InvalidMonthException extends RuntimeException{
    }
}
