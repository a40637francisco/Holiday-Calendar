package Calendar.MainDayPage.JavaFxControls;

import Calendar.CalendarRules.DayType;
import Calendar.MainDayPage.AddUser.DayTypeInput;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class RadioButtonDayGroup extends RadioButton{
    private static  ToggleGroup RADIOBUTTONS_GROUP;
    private static int Id = 0;
    private final DayTypeInput dayInputType;

    public RadioButtonDayGroup(DayTypeInput dayTypeInput) {
        RADIOBUTTONS_GROUP = new ToggleGroup();
        dayInputType = dayTypeInput;
        RADIOBUTTONS_GROUP.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                int selected = -1;
                if (RADIOBUTTONS_GROUP.getSelectedToggle() != null) {
                    selected = (int) RADIOBUTTONS_GROUP.getSelectedToggle().getUserData();
                }
                if (DayTypeInput.typeWithParameters(selected)) {
                    dayInputType.createDateInputSlider();
                } else {
                    dayInputType.removeDateInputSlider();
                }
            }
        });
    }

    public RadioButtonDay addRadioButton(DayType dayType) {
        RadioButtonDay r = new RadioButtonDay(dayType.toString());
        return r;
    }

    public class RadioButtonDay extends RadioButton{
        public RadioButtonDay(String text) {
            super(text);
            this.setUserData(Id++);
            this.setToggleGroup(RADIOBUTTONS_GROUP);
        }
    }

}
