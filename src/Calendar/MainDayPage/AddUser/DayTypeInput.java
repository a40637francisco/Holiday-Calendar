package Calendar.MainDayPage.AddUser;

import Calendar.CalendarRules.DayType;
import Calendar.MainDayPage.JavaFxControls.IntegerTextField;
import Calendar.MainDayPage.JavaFxControls.RadioButtonDayGroup;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DayTypeInput {

    private static final int MAX_DAY = 31;
    private static final int MAX_MONTH = 12;
    private static final int MAX_YEAR = 3000;
    private final String[] DAY_TYPE_INPUT_METHOD= {"RadioButton","ChoiceBox","ComboBox","MenuButton"};
    private final VBox layoutParent;
    private boolean isSelectedDayTypeParameterized;
    private VBox dateInputLayout;

    public DayTypeInput(String type, VBox vBoxParent) {
        layoutParent = vBoxParent;
        switch (type){
            case "RadioButton":
                startRadioButtonsGroup(this);
                break;

        }
    }

    private void startRadioButtonsGroup(DayTypeInput dayTypeInput) {
        RadioButtonDayGroup g= new RadioButtonDayGroup(dayTypeInput);
        RadioButtonDayGroup.RadioButtonDay r;
        for (int i = 0; i < DayType.values().length; i++) {
            r = g.addRadioButton(DayType.values()[i]);
            layoutParent.getChildren().add(r);
        }
    }

    public VBox getLayout() {
        return  layoutParent;
    }

    public static boolean typeWithParameters(int selected) {
        return DayType.values()[selected].hasParameters();
    }

    public  void createDateInputSlider() {
        if(isSelectedDayTypeParameterized) return;
        isSelectedDayTypeParameterized = true;
        dateInputLayout = new VBox();
        dateInputLayoutStart();
        dateInputLayoutEnd();
        layoutParent.getChildren().add(dateInputLayout);
    }

    public void removeDateInputSlider() {
        if( dateInputLayout != null) {
            layoutParent.getChildren().remove(dateInputLayout);
            isSelectedDayTypeParameterized = false;
        }
    }

    private void dateInputLayoutEnd() {
        HBox feriasLayoutEnd = new HBox();
        Text startLabel = new Text("Termina:");
        TextField endDayTF = new IntegerTextField(2,30,MAX_DAY,"dia");
        TextField endMonthTF = new IntegerTextField(2,37,MAX_MONTH,"mes");
        TextField endYearTF = new IntegerTextField(4,45,MAX_YEAR,"ano");
        feriasLayoutEnd.getChildren().addAll(startLabel, endDayTF, endMonthTF, endYearTF);
        dateInputLayout.getChildren().add(feriasLayoutEnd);
    }

    private void dateInputLayoutStart() {
        HBox feriasLayoutStart = new HBox();
        Text startLabel = new Text("Comeca:");
        TextField startDayTF = new IntegerTextField(2,30,MAX_DAY,"dia");
        TextField startMonthTF = new IntegerTextField(2,37,MAX_MONTH,"mes");
        TextField startYearTF= new IntegerTextField(4,45,MAX_YEAR,"ano");
        feriasLayoutStart.getChildren().addAll(startLabel, startDayTF, startMonthTF, startYearTF);
        dateInputLayout.getChildren().add(feriasLayoutStart);
    }




}
