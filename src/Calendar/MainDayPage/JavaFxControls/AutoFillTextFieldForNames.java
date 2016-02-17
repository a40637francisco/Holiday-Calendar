package Calendar.MainDayPage.JavaFxControls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collection;


public class AutoFillTextFieldForNames extends TextField {
    private final AutoFillTextFieldForNames txtField;
    private final ContextMenu contextMenu;
    private PrefixTree root;
    private String textFieldValue;
    private Collection<String> list;
    private int maxLength = 255;

    public AutoFillTextFieldForNames(String promptText, Collection<String> list) {
        super();
        txtField = this;
        this.setPromptText(promptText);
        this.list = list;
        root = new PrefixTree(list);
        contextMenu = new ContextMenu();

        this.getContent().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (isNewValueEmpty(newValue)) {
                    clearContextMenu();
                    return;
                }
                if (isNewValueAccepted(newValue)) {
                    clearContextMenu();
                    updateContextMenu(newValue);
                    return;
                }
                if (newValue.length() < oldValue.length()) {
                    clearContextMenu();
                    updateContextMenu(oldValue);
                    txtField.setCursor(Cursor.TEXT);
                }
            }

            private boolean isNewValueEmpty(String newValue) {
                return newValue.equals("");
            }

            private boolean isNewValueAccepted(String newValue) {
                String lastChar = (newValue.charAt(newValue.length() - 1) + "");
                if (lastChar.equals(""))
                    return false;
                if (lastChar.matches("[a-zA-Z]") && (newValue.length() <= maxLength))
                    return true;
                if (lastChar.equals(" "))
                    return true;
                return false;
            }
        });
    }

    private void clearContextMenu() {
        contextMenu.getItems().removeAll();
        contextMenu.getItems().clear();
        contextMenu.hide();
    }

    private void updateContextMenu(String newValue) {
        createMenuItems(root.base, newValue);
        contextMenu.show(txtField, Side.BOTTOM, 0, 0);
    }

    private void createMenuItems(ArrayList<PrefixTree.Node> base, String newValue) {
        for(PrefixTree.Node tmp : base) {
            if(tmp.hasNewValue(newValue)){
                addFromNode(tmp, tmp.value);
            }
        }
        this.setContextMenu(contextMenu);
    }

    private void addFromNode(PrefixTree.Node tmp, String value) {
        value+= " ";
        for(PrefixTree.Node a  : tmp.adj) {
            if (a.adj.isEmpty())
                createItemToadd(value + a.value);
            else {
                addFromNode(a, value + a.value);
            }
        }
        if(tmp.adj.isEmpty())
            createItemToadd(value);
    }

//
//    private boolean branchHasNewValue(String current, String wantedValue) {
//        if(current.length() > wantedValue.length()){
//            String aux = current.substring(0,wantedValue.length()).toLowerCase();
//            wantedValue = wantedValue.toLowerCase();
//            return wantedValue.equals(aux);
//        } else {
//            return false;
//        }
//    }

    private void createItemToadd(String s) {
        MenuItem item = new MenuItem(s);
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                txtField.setText(mItem.getText());
                setMyCursor();
                System.out.println(mItem.getText());
                clearContextMenu();
            }
        });
        contextMenu.getItems().add(item);
    }

    private void setMyCursor() {
        txtField.positionCaret(txtField.getLength());
    }

    public void setList(Collection<String> list) {
        this.list = list;
        root = new PrefixTree(list);
    }

    public boolean hasText() {
        return !textFieldValue.equals("");
    }


    private class PrefixTree {
        public ArrayList<Node> base;

        public PrefixTree(Collection<String> list) {
            if(list == null) return;
            base = new ArrayList<>(10);
            addToBaseList(list);
        }

        private void addToBaseList(Collection<String> list) {
            for(String tmp : list) {
                if(tmp != null)
                    add(tmp);
            }
        }

        private void add(String name) {
            String[] s = name.split(" ");
            ArrayList<Node> aux = base;
            for (String value : s) {
                Node n = new Node(value);
                Node l = n;
                if ((n = isInList(n, aux)) == null) {
                    n = l;
                    aux.add(n);
                }
                aux = aux.get(aux.indexOf(n)).adj;

            }
        }

        private Node isInList(Node n, ArrayList<Node> aux) {
            String s = n.value;
            for (Node tmp : aux) {
                if(tmp.value.equals(s))
                    return tmp;
            }
            return null;
        }

        public class Node {
            public ArrayList<Node> adj;
            public String value;

            public Node(String s) {
                value = s;
                adj = new ArrayList<>();
            }

            public boolean hasNewValue(String newValue) {
                newValue = newValue.toLowerCase();
                if(newValue.length() > value.length()) {
                    String aux = newValue.substring(0, value.length());
                    aux = aux.toLowerCase();
                    String lowerValue = value.toLowerCase();
                    return aux.equals(lowerValue);
                }
                String aux = value.substring(0, newValue.length());
                aux = aux.toLowerCase();
                return aux.equals(newValue);
            }
        }


    }

}
