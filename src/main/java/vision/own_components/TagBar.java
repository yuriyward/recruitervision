package vision.own_components;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Yuriy on 29.07.2017.
 */
public class TagBar extends HBox {

    private ObservableList<String> tags;
    private JFXTextField inputTextField;

    public ObservableList<String> getTags() {
        return tags;
    }

    public TagBar() {
        createTagBar();
    }

    public TagBar(boolean withoutRemove) {
        createTagBar(withoutRemove);
    }

    private void createTagBar(boolean... removeX) {
        getStyleClass().setAll("tag-bar");
        getStylesheets().add(getClass().getResource("/css/tag-style.css").toExternalForm());
        tags = FXCollections.observableArrayList();
        inputTextField = new JFXTextField();
        if (removeX.length == 0)
            inputTextField.setOnAction(evt -> {
                String text = inputTextField.getText();
                if (!text.isEmpty() && !tags.contains(text)) {
                    tags.add(text);
                    inputTextField.clear();
                }
            });
        inputTextField.prefHeightProperty().bind(this.heightProperty());
        HBox.setHgrow(inputTextField, Priority.ALWAYS);
        inputTextField.setBackground(null);

        if (removeX.length == 0)
            inputTextField.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
                    if (tags.size() > 0) {
                        String text = inputTextField.getText();
                        if (text.isEmpty())
                            tags.remove(tags.size() - 1);
                    }
                }
            });

        tags.addListener((ListChangeListener.Change<? extends String> change) -> {
            while (change.next()) {
                if (change.wasPermutated()) {
                    ArrayList<Node> newSublist = new ArrayList<>(change.getTo() - change.getFrom());
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.add(null);
                    }
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.set(change.getPermutation(i), getChildren().get(i));
                    }
                    getChildren().subList(change.getFrom(), change.getTo()).clear();
                    getChildren().addAll(change.getFrom(), newSublist);
                } else {
                    if (change.wasRemoved()) {
                        getChildren().subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
                    }
                    if (change.wasAdded()) {
                        if (removeX.length == 0) {
                            getChildren().addAll(change.getFrom(), change.getAddedSubList().stream().map(Tag::new).collect(Collectors.toList()));
                        } else {
                            getChildren().addAll(change.getFrom(), change.getAddedSubList().stream().map(TagWithoutRemove::new).collect(Collectors.toList()));
                        }
                    }
                }
            }
        });
        getChildren().add(inputTextField);
    }

    public void addStringToBar(String text) {
        tags.add(text);
    }

    public void addStringsToBar(Iterable stringArray) {
        if (stringArray != null && stringArray.iterator().hasNext()) {
            for (Object s : stringArray) {
                tags.add((String) s);
            }
        }
    }

    public void clearBar() {
        tags.clear();
    }

    private class Tag extends HBox {
        Tag(String tag) {
            getStyleClass().setAll("tag");
            Button removeButton = new Button("X");
            removeButton.setOnAction((evt) -> tags.remove(tag));
            Text text = new Text(tag);
            HBox.setMargin(text, new Insets(0, 0, 0, 5));
            getChildren().addAll(text, removeButton);
        }
    }

    private class TagWithoutRemove extends HBox {
        TagWithoutRemove(String tag) {
            getStyleClass().setAll("tag");
            Text text = new Text(tag);
            HBox.setMargin(text, new Insets(0, 5, 0, 5));
            getChildren().addAll(text);
        }
    }
}
