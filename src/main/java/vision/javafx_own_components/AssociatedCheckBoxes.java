package vision.javafx_own_components;

import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;

import java.util.List;

/**
 * @author Yuriy on 30.07.2017.
 */
public class AssociatedCheckBoxes {
    private final JFXCheckBox selectAll;
    private final List<JFXCheckBox> boxes;
    private Runnable selectAllStateChangeProcessor;

    public AssociatedCheckBoxes(JFXCheckBox selectAll, List<JFXCheckBox> boxes) {
        this.boxes = boxes;
        this.selectAll = selectAll;
        this.boxes.forEach(box -> box.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
            if (selectAllStateChangeProcessor == null) {
                boolean allSelected = this.boxes.stream()
                        .map(JFXCheckBox::isSelected)
                        .reduce(
                                true,
                                (a, b) -> a && b
                        );

                boolean anySelected = this.boxes.stream()
                        .map(JFXCheckBox::isSelected)
                        .reduce(
                                false,
                                (a, b) -> a || b
                        );

                if (allSelected) {
                    this.selectAll.setSelected(true);
                }

                if (!anySelected) {
                    this.selectAll.setSelected(false);
                    this.selectAll.setIndeterminate(false);
                }

                if (anySelected && !allSelected) {
                    this.selectAll.setSelected(false);
                    this.selectAll.setIndeterminate(true);
                }
            }
        }));

        this.selectAll.selectedProperty().addListener((observable, wasSelected, isSelected) ->
                scheduleSelectAllStateChangeProcessing()
        );
        this.selectAll.indeterminateProperty().addListener((observable, wasIndeterminate, isIndeterminate) ->
                scheduleSelectAllStateChangeProcessing()
        );
    }

    private void scheduleSelectAllStateChangeProcessing() {
        if (selectAllStateChangeProcessor == null) {
            selectAllStateChangeProcessor = this::processSelectAllStateChange;
            Platform.runLater(selectAllStateChangeProcessor);
        }
    }

    private void processSelectAllStateChange() {
        if (!selectAll.isIndeterminate()) {
            boxes.forEach(box -> box.setSelected(selectAll.isSelected()));
        }
        selectAllStateChangeProcessor = null;
    }
}
