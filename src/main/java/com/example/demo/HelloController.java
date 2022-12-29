package com.example.demo;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class HelloController {
    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    @FXML
    public Pane SignInPane;

    @FXML
    public Pane signUpPane;

    @FXML
    public Button signUpButton;

    @FXML
    public Button getStartedButton;

    @FXML
    public ImageView backImageView;

    @FXML
    public TextField registerNameField;

    @FXML
    public TextField registerPasswordField;

    @FXML
    public TextField registerEmailField;

    @FXML
    public TextField registerFirstNameField;

    @FXML
    public TextField registerPhoneNumberField;

    @FXML
    public RadioButton maleButton;

    @FXML
    public RadioButton femaleButton;

    @FXML
    public Label controlRegisterLabel;

    @FXML
    public Label succesLabel;

    @FXML
    public Label goBack;

    @FXML
    public TextField passwordField;

    @FXML
    public TextField usernameField;

    @FXML
    public Label loginNotifierLabel;

    @FXML
    public Label nameExistsLabel;

    @FXML
    public Label checkEmaiLabel;

    public static String username, password, gender;
    public static ArrayList<User> loggedInUser = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<User>();


    public void registration() {

        if ((!registerNameField.getText().equalsIgnoreCase(""))
                && (!registerPasswordField.getText().equalsIgnoreCase(""))
                && (!registerEmailField.getText().equalsIgnoreCase(""))
                && (!registerFirstNameField.getText().equalsIgnoreCase(""))
                && (!registerPhoneNumberField.getText().equalsIgnoreCase(""))
                && (maleButton.isSelected() || femaleButton.isSelected())) {
            if (checkUser(registerNameField.getText())) {
                if (checkEmailLabel(registerEmailField.getText())) {
                    User user = new User();
                    user.nameString = registerNameField.getText();
                    user.passwordString = registerPasswordField.getText();
                    user.emailString = registerEmailField.getText();
                    user.fullNameString = registerFirstNameField.getText();
                    user.phoneNumberString = registerPhoneNumberField.getText();
                    if (maleButton.isSelected()) {
                        user.genderString = "Male";
                    } else {
                        user.genderString = "FEMALE";
                    }
                    users.add(user);
                    // file save
                    // close file
                    goBack.setOpacity(1);
                    succesLabel.setOpacity(1);
                    makeDefault();
                    if (controlRegisterLabel.getOpacity() == 1) {
                        controlRegisterLabel.setOpacity(0);
                    }
                    if (nameExistsLabel.getOpacity() == 1) {
                        nameExistsLabel.setOpacity(0);
                    }
                } else {
                    checkEmaiLabel.setOpacity(1);
                    setOpacity(nameExistsLabel, goBack, controlRegisterLabel, succesLabel);
                }

            } else {
                nameExistsLabel.setOpacity(1);
                setOpacity(succesLabel, goBack, controlRegisterLabel, checkEmaiLabel);
            }
        } else {
            controlRegisterLabel.setOpacity(1);
            setOpacity(succesLabel, goBack, nameExistsLabel, checkEmaiLabel);
        }

    }

    private void setOpacity(Label aLabel, Label bLabel, Label cLabel, Label dLabel) {
        if (aLabel.getOpacity() == 1 || bLabel.getOpacity() == 1 || cLabel.getOpacity() == 1 || dLabel.getOpacity() == 1) {
            aLabel.setOpacity(0);
            bLabel.setOpacity(0);
            cLabel.setOpacity(0);
            dLabel.setOpacity(0);
        }
    }

    private void setOpacity(Label controlRegisterLabel, Label checkmail, Label nameExistLabel) {
        controlRegisterLabel.setOpacity(0);
        checkmail.setOpacity(0);
        nameExistLabel.setOpacity(0);

    }

    private boolean checkUser(String username) {
        for (User user : users) {
            if (user.nameString.equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkEmailLabel(String emailString) {
        for (User user : users) {
            if (user.emailString.equalsIgnoreCase(emailString)) {
                return false;
            }
        }
        return true;

    }

    private void makeDefault() {
        registerNameField.setText("");
        registerPasswordField.setText("");
        registerEmailField.setText("");
        registerFirstNameField.setText("");
        registerPhoneNumberField.setText("");
        maleButton.setSelected(true);
        setOpacity(controlRegisterLabel, checkEmaiLabel, nameExistsLabel);
    }


    public void login() {

        username = usernameField.getText();
        password = passwordField.getText();
        boolean login = false;
        for (User xUser : users) {
            if (xUser.nameString.equalsIgnoreCase(username) && xUser.passwordString.equalsIgnoreCase(password)) {
                login = true;
                loggedInUser.add(xUser);
                System.out.println(xUser.nameString);
                gender = xUser.genderString;
                break;
            }
        }
        if (login) {
            changeWindow();
        } else {
            loginNotifierLabel.setOpacity(1);
        }
    }

    public void changeWindow() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent rootParent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Room.fxml")));


            stage.setScene(new Scene(rootParent, 330, 560));
            stage.setTitle(username + "");
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.setResizable(false);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws RuntimeException, IOException {

        if (event.getSource().equals(signUpButton)) {

            new animatefx.animation.FadeIn(signUpPane).play();

            signUpPane.toFront();

        } else if (event.getSource().equals(getStartedButton)) {

            new FadeIn(SignInPane).play();
            SignInPane.toFront();

        }
        loginNotifierLabel.setOpacity(0);
        usernameField.setText("");
        passwordField.setText("");
    }


    @FXML
    private void handleMouseEvent(MouseEvent eventt) throws RuntimeException {

        if (eventt.getSource() == backImageView) {
            new FadeIn(SignInPane).play();
            SignInPane.toFront();
        }
        registerNameField.setText("");
        registerPasswordField.setText("");
        registerEmailField.setText("");
    }
}