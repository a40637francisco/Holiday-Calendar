package Calendar.MainCallendarPage;

import Calendar.DataBase.DataBaseType;
import Calendar.DataBase.Queries;
import Calendar.DataBase.SqlServer.SqlServer;
import Calendar.Files.ReadFile;
import Calendar.MainDayPage.DayPopUp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;


import static java.lang.Integer.parseInt;

public class Main extends Application {
    private static final String YEAR_FILE_NAME = "Years.txt";

    public static final boolean IS_WINDOW_REZISABLE = false;
    public static final int NUMBER_OF_WEEKS = 6;
    public static final String MainTile = "Calendário";
    public static final int MainTile_SIZE = 29;
    public static final String MainTile_Font = "Arial";
    public static String year;
    public static final String[] diasSemana ={"Domingo","Segunda","Terça","Quarta","Quinta","Sexta","Sabado"};
    public static final String[] meses ={"Janeiro","Fevereiro","Marco","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

    public static DataBaseType sqlDataBaseLink;


    Stage activeStage;
    Scene activeScene;
    BorderPane borderPaneLayout;
    HBox hBoxLayoutTop;
    VBox vBoxLayoutLeft,vBoxLayoutCenter;
    Text mainText;
    ComboBox <String> comboBoxYear;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        createLinkWithDataBase();
        CreateYearComboBox();
        comboBoxYearHandler();
        activeStage = primaryStage;
        activeStage.setTitle("Calendar");

        borderPaneLayout = new BorderPane();

        addToTop();
        addToLeft();
        addToCenter();
        addToBottom();

        activeScene = new Scene(borderPaneLayout,1783,675);
        activeStage.setScene(activeScene);
        activeStage.setResizable(IS_WINDOW_REZISABLE);
        activeStage.show();
        activeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.printf("fechar");
            }
        });
    }

    private void createLinkWithDataBase() {
        sqlDataBaseLink = new SqlServer();
    }

    private void CreateYearComboBox() {
        comboBoxYear = new ComboBox<String>();
        fillComboBox();
        comboBoxYear.setPromptText(comboBoxYear.getItems().get(0));
        year = comboBoxYear.getPromptText();
        System.out.println(year);
    }

    private void fillComboBox() {
        fillComboBoxFromDataBase();
   //     fillComboBoxFromTxtFile();
    }

    private void comboBoxYearHandler() {
        comboBoxYear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                year = comboBoxYear.getValue();
                addToCenter();
            }
        });
    }

    private void fillComboBoxFromDataBase() {
        String[][] years = sqlDataBaseLink.callQuery(Queries.GetYears, null);
        for (int i = 0; i <years.length ; i++) {
            for (int j = 0; j < years[i].length ; j++) {
                if(years[i][j] != null)
                    comboBoxYear.getItems().add(years[i][j]);
            }
        }
    }

    private void fillComboBoxFromTxtFile() {
        String y;
        while((y = readYearFile()) != null) {
            comboBoxYear.getItems().add(y);
        }
    }

    private String readYearFile() {
        try {
            return ReadFile.readLine(YEAR_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addToBottom() {
    }

    private void addToLeft() {
        vBoxLayoutLeft = new VBox();
        vBoxLayoutLeft.setPadding(new Insets(5, 0, 0, 8));
        vBoxLayoutLeft.setSpacing(20);
        vBoxLayoutLeft.setPrefWidth(meses[8].length() * 11);
        Text padding = new Text();
        vBoxLayoutLeft.getChildren().add(padding);
        for (int i = 0; i < meses.length ; i++) {
            Text txt = new Text(meses[i]);
            txt.setFont(Font.font("Arial",16));
            txt.setFill(Color.WHITE);
            vBoxLayoutLeft.getChildren().add(txt);
        }
        vBoxLayoutLeft.setStyle("-fx-background-color: #488F8F;");
        borderPaneLayout.setLeft(vBoxLayoutLeft);

    }

    private void addToCenter() {
        vBoxLayoutCenter = new VBox();
        createDiasDaSemanaLabel();
        fillCenter();
        borderPaneLayout.setCenter(vBoxLayoutCenter);
    }

        private void checkWhatButtonClicked(ActionEvent event) {
            Button b = (Button) event.getSource();
            DayPopUp popUp = new DayPopUp(b.getId(),year);
            System.out.println(b.getId());
        }

        private void createButtons(HBox activeLayout,int month) {
        int auxIdx = 1;
        for (int day = 1; auxIdx <= diasSemana.length * NUMBER_OF_WEEKS; day++, ++auxIdx) {
            String strDay = getDayByMonthAndByYear(month, auxIdx, day);
            Button button = new Button(strDay);
            button.setPrefWidth(50);
            button.setId(strDay + "/" + month);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    checkWhatButtonClicked(event);
                }
            });
            if(strDay.equals("-")) {
                button.setDisable(true);
                day--;
            }

            activeLayout.getChildren().add(button);
        }
    }

        private String getDayByMonthAndByYear(int month, int dayOfMonth, int day) {
        int y = parseInt(year);
        GregorianCalendar cal  = new GregorianCalendar(y, month,day);
        String ret = MyCalendar.checkDayOfWeekMatches(dayOfMonth, day, cal);

        return ret;
    }

    private void fillCenter() {
        HBox hBoxAux;
        for (int month = 0; month < meses.length; month++) {
            hBoxAux = createHorizontalLayoutForButtons();
            createButtons(hBoxAux,month);
            vBoxLayoutCenter.getChildren().add(hBoxAux);
        }
    }

        private HBox createHorizontalLayoutForButtons() {
        HBox hLayout;
        hLayout = new HBox();
        hLayout.setSpacing(0);
        hLayout.setPrefHeight(38);
        return hLayout;
    }

        private void createDiasDaSemanaLabel() {
        HBox hBoxAux = new HBox();
        hBoxAux.setSpacing(2);
        hBoxAux.setPrefHeight(40);
        hBoxAux.setStyle("-fx-background-color: #EA3F00;");
        Text txt;
        for (int i = 0; i < NUMBER_OF_WEEKS; i++) {
            createOneWeek(hBoxAux);
        }
        vBoxLayoutCenter.setAlignment(Pos.BASELINE_CENTER);
        vBoxLayoutCenter.getChildren().add(hBoxAux);

    }

        private void createOneWeek(HBox hBoxAux) {
        Text txt;
        for (int i = 0; i < diasSemana.length; i++) {
            txt = new Text();
            txt.setFill(Color.WHITE);
            txt.setText(diasSemana[i]);
            hBoxAux.getChildren().add(txt);
        }
    }

    private void addToTop() {
        hBoxLayoutTop = new HBox();
        createMainTitle();
        sethBoxLayoutTopProprieties();
        addYearComboBox();
        borderPaneLayout.setTop(hBoxLayoutTop);
    }

        private void sethBoxLayoutTopProprieties() {
        hBoxLayoutTop.setPrefHeight(50);
        hBoxLayoutTop.setAlignment(Pos.TOP_CENTER);
        hBoxLayoutTop.setSpacing(10);
        hBoxLayoutTop.getChildren().add(mainText);
        hBoxLayoutTop.setStyle("-fx-background-color: #FF6400;");
    }

        private void createMainTitle() {
        mainText = new Text();
        mainText.setText(MainTile);
        mainText.setFont(new Font(MainTile_Font, MainTile_SIZE));
        mainText.setFill(Color.WHITE);
    }

        private void addYearComboBox() {
        comboBoxYear.setPadding(new Insets(10,0,0,0));
        hBoxLayoutTop.getChildren().add(comboBoxYear);

    }

}
