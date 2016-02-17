package Calendar.MainDayPage.JavaFxControls;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class IntegerTextField extends TextField{
    private int maxLength;
    private int currentLength = 0;
    private String numericLastKey;
    private SimpleIntegerProperty value;
    private int maxValue;

    public IntegerTextField(int maxLength, int maxWidth, int maxValue, String promptTxt) {
        super();
        this.maxLength = maxLength;
        this.setPromptText(promptTxt);
        this.setPrefWidth(maxWidth);
        this.maxValue = maxValue;
        value = new SimpleIntegerProperty();
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(isNewValueEmpty(newValue)) return;
                if(isNewValueAccepted(newValue)) {
                    if(isInputEmptyToAlreadyEmpty(oldValue, newValue)) {
                        setValue(-1);
                        clearTextMine();
                        return;
                    }
                    setValue(Integer.parseInt(newValue));
                } else if (!isInputEmptyToAlreadyEmpty(oldValue, newValue)) {
                    setTextValue(oldValue);
                    setCursorBack();
                }
            }
            private boolean isNewValueEmpty(String newValue) {
                return newValue.equals("");
            }
            private boolean isInputEmptyToAlreadyEmpty(String oldValue, String newValue) {
                return oldValue.equals("") && newValue.equals("");
            }
            private boolean isNewValueAccepted(String newValue) {
                if(newValue.equals(""))
                    return false;
                if (newValue.matches("\\d*") && (newValue.length() <= maxLength) && (Integer.parseInt(newValue) <= maxValue ))
                    return true;
                return false;
            }
        });

    }

    public int getValue() {
        return value.get();
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    private void clearTextMine() {
        this.clear();
    }

    private void setCursorBack() {
        this.positionCaret(this.getLength());
    }

    public String getValueString() {
        return value+"";
    }

    private void setTextValue(String s) {
        this.setText(s);
    }

    private void setTextValue(int value) {
        this.setText(value+"");
    }
}
