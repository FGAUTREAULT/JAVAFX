package com.perso.gautreault.adress.view.dialog;

import com.perso.gautreault.adress.model.Person;
import com.perso.gautreault.adress.util.DateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonEditDialogController {

    @FXML
    private TextField firstNameLabel;
    @FXML
    private TextField lastNameLabel;
    @FXML
    private TextField streetLabel;
    @FXML
    private TextField postalCodeLabel;
    @FXML
    private TextField cityLabel;
    @FXML
    private TextField birthdayLabel;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameLabel.setText(person.getFirstName());
        lastNameLabel.setText(person.getLastName());
        streetLabel.setText(person.getStreet());
        postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
        cityLabel.setText(person.getCity());
        birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        birthdayLabel.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameLabel.getText());
            person.setLastName(lastNameLabel.getText());
            person.setStreet(streetLabel.getText());
            person.setPostalCode(Integer.parseInt(postalCodeLabel.getText()));
            person.setCity(cityLabel.getText());
            person.setBirthday(DateUtil.parse(birthdayLabel.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameLabel.getText() == null || firstNameLabel.getText().isEmpty()) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameLabel.getText() == null || lastNameLabel.getText().isEmpty()) {
            errorMessage += "No valid last name!\n";
        }
        if (streetLabel.getText() == null || streetLabel.getText().isEmpty()) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeLabel.getText() == null || postalCodeLabel.getText().isEmpty()) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeLabel.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityLabel.getText() == null || cityLabel.getText().isEmpty()) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayLabel.getText() == null || birthdayLabel.getText().isEmpty()) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayLabel.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
