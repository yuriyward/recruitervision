package vision.javafx_own_components;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Yuriy on 29.07.2017.
 */
public class OwnTagBar extends HBox {

    private final ObservableList<String> tags;
    private final JFXTextField inputTextField;

    public ObservableList<String> getTags() {
        return tags;
    }

    public OwnTagBar() {
        getStyleClass().setAll("tag-bar");
        getStylesheets().add(getClass().getResource("/css/tag-style.css").toExternalForm());
        tags = FXCollections.observableArrayList();
        inputTextField = new JFXTextField();
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
                        getChildren().addAll(change.getFrom(), change.getAddedSubList().stream().map(Tag::new).collect(Collectors.toList()));
                    }
                }
            }
        });
        getChildren().add(inputTextField);
    }

    private class Tag extends HBox {
        public Tag(String tag) {
            getStyleClass().setAll("tag");
            Button removeButton = new Button("X");
            removeButton.setOnAction((evt) -> tags.remove(tag));
            Text text = new Text(tag);
            HBox.setMargin(text, new Insets(0, 0, 0, 5));
            getChildren().addAll(text, removeButton);
        }
    }
}
