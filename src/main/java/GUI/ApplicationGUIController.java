package GUI;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lightfilesystem.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ApplicationGUIController implements Initializable {

    @FXML
    private Label currentFileAuthorLabel;

    @FXML
    private Label currentFileUpdatedLabel;

    @FXML
    private Label currentFileCreatedLabel;

    @FXML
    private ChoiceBox<Access> currentFileGroupAccessChoiceBox;

    @FXML
    private ChoiceBox<Access> currentFileOtherAccessChoiceBox;

    @FXML
    private ListView<String> myListView;

    @FXML
    private TextArea fileContentArea;

    @FXML
    private ChoiceBox<User> currentUserSelector;

    @FXML
    private ChoiceBox<Group> currentUserGroupSelector;

    @FXML
    private Button setCurrentFilePasswordButton;

    @FXML
    private PasswordField setCurrentFilePasswordFiled;

    @FXML
    private Button addNewUserButton;

    @FXML
    private Button addNewFileButton;

    @FXML
    private Button addNewGroupButton;

    private LightFileSystem lfs;

    TextInputDialog textInputDialog = new TextInputDialog();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.lfs = LightFileSystem.getInstance();

        var nameOfFiles = lfs
                .getFilesystem()
                .getFiles()
                .stream()
                .map(File::getTitle)
                .collect(Collectors.toList());

        this.addNewUserButton.setOnAction(e -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("What is the name of the new user?");
            textInputDialog.showAndWait();

            var username = textInputDialog.getEditor().getText();

            if(!username.equals("") && lfs.getFilesystem().canCreateUser(username)){
                var createdUser = lfs.getFilesystem().createUser(username);
                lfs.getStateManager().setCurrentUser(Optional.of(createdUser));
                this.currentUserSelector.setValue(createdUser);
                this.currentUserSelector.getItems().add(createdUser);
                this.update();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No name provided or user already exists");
                alert.setHeaderText("Could not create user!");
                alert.show();
            }
        });

        this.addNewFileButton.setOnAction(e -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("What is the name of the new file?");
            textInputDialog.showAndWait();

            var fileName = textInputDialog.getEditor().getText();
            var currentUser = lfs.getStateManager().getCurrentUser();

            if(!fileName.equals("") && lfs.getFilesystem().canCreateFile(fileName) && currentUser.isPresent()){
                var createdFile = lfs.getFilesystem().createFile(fileName, currentUser.get());
                this.myListView.getItems().add(createdFile.getTitle());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No name provided or file already exists");
                alert.setHeaderText("Could not create file!");
                alert.show();
            }
        });

        this.addNewGroupButton.setOnAction(e -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("What is the name of the new group?");
            textInputDialog.showAndWait();

            var groupName = textInputDialog.getEditor().getText();

            if(!groupName.equals("") && lfs.getFilesystem().canCreateGroup(groupName)){
                var createdGroup = lfs.getFilesystem().createGroup(groupName);
                this.currentUserGroupSelector.getItems().add(createdGroup);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No name provided or group already exists");
                alert.setHeaderText("Could not create group!");
                alert.show();
            }
        });

        this.setCurrentFilePasswordButton.setOnAction(e -> {
            var currentFile = lfs.getStateManager().getCurrentFile();
            var currentUser = lfs.getStateManager().getCurrentUser();
            if(currentFile.isPresent() && currentUser.isPresent()){
                var access = AccessChecker.getUserAccess(currentFile.get(), currentUser.get());
                if(List.of(Access.WRITE, Access.ALL).contains(access)){
                    currentFile.get().setPassword(this.setCurrentFilePasswordFiled.getText());
                }
            }
        });

        currentFileOtherAccessChoiceBox.getItems().addAll(
                Access.NONE,
                Access.WRITE,
                Access.READ,
                Access.ALL
        );

        currentFileGroupAccessChoiceBox.getItems().addAll(
                Access.NONE,
                Access.WRITE,
                Access.READ,
                Access.ALL
        );

        currentFileOtherAccessChoiceBox.setOnAction(this::onOtherAccessSelect);
        currentFileGroupAccessChoiceBox.setOnAction(this::onGroupAccessSelect);


        currentUserGroupSelector.setConverter(new GroupStringConverter());
        currentUserGroupSelector.getItems().addAll(lfs.getFilesystem().getGroups());
        currentUserGroupSelector.setValue(lfs.getStateManager().getCurrentUser().get().getGroup());
        currentUserGroupSelector.setOnAction(this::onSetCurrentUserGroup);

        currentUserSelector.setConverter(new UserStringConverter());
        currentUserSelector.getItems().addAll(lfs.getFilesystem().getUsers());
        currentUserSelector.setValue(lfs.getStateManager().getCurrentUser().get());
        currentUserSelector.setOnAction(this::onUserSelect);

        myListView.getItems().addAll(nameOfFiles);

        myListView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            var selectedFile = lfs.getFilesystem().getFile(myListView.getSelectionModel().getSelectedItem());
            lfs.getStateManager().setCurrentFile(selectedFile);
            this.update();
        });

        fileContentArea.setWrapText(true);
        fileContentArea.textProperty().addListener((observable, oldValue, newValue) -> {
            var currentFIle = lfs.getStateManager().getCurrentFile();
            if(!this.isTextAreaDisabled() && currentFIle.isPresent()){
                currentFIle.get().setContent(fileContentArea.getText());
            }
        });

        this.textInputDialog.setHeaderText("Password is required!");

        this.update();
}

    private void onSetCurrentUserGroup(ActionEvent actionEvent) {
        var selectedGroup = this.currentUserGroupSelector.getValue();
        var currentUser = lfs.getStateManager().getCurrentUser();
        currentUser.ifPresent(user -> user.setGroup(selectedGroup));
        this.update();
    }

    private void onGroupAccessSelect(ActionEvent actionEvent) {
        Access access = currentFileGroupAccessChoiceBox.getValue();
        var currentFile = lfs.getStateManager().getCurrentFile();
        currentFile.ifPresent(file -> file.setGroupsAccess(access));
        this.updateFileRelatedGUI();
        this.updateCurrentFileSetPasswordSection();
    }

    private void onOtherAccessSelect(ActionEvent actionEvent) {
        Access access = currentFileOtherAccessChoiceBox.getValue();
        var currentFile = lfs.getStateManager().getCurrentFile();
        currentFile.ifPresent(file -> file.setOtherAccess(access));
        this.updateFileRelatedGUI();
        this.updateCurrentFileSetPasswordSection();
    }

    public void onUserSelect(ActionEvent event){
        User selectedUser = currentUserSelector.getValue();
        this.lfs.getStateManager().setCurrentUser(Optional.of(selectedUser));
        this.currentUserGroupSelector.setValue(selectedUser.getGroup());
        this.update();
    }

    public void updateFileRelatedGUI(){
        fileContentArea.setText(this.getTextAreaContent());
        fileContentArea.setDisable(this.isTextAreaDisabled());
        this.setCurrentFileInformation();
    }

    public void update(){
        this.updateFileRelatedGUI();
        this.updateCurrentFileSetPasswordSection();
        this.currentFileOtherAccessChoiceBox.setDisable(this.isAccessSelectorDisabled());
        this.currentFileGroupAccessChoiceBox.setDisable(this.isAccessSelectorDisabled());
        this.setCurrentFilePasswordFiled.setDisable(this.isAccessSelectorDisabled());
        this.setCurrentFilePasswordButton.setDisable(this.isAccessSelectorDisabled());
        this.getFilePassword();
    }

    private void updateCurrentFileSetPasswordSection() {
        var currentFile = lfs.getStateManager().getCurrentFile();
        var currentUser = lfs.getStateManager().getCurrentUser();
        if(currentFile.isPresent() && currentUser.isPresent()){
            var access = AccessChecker.getUserAccess(currentFile.get(), currentUser.get());
            if(List.of(Access.WRITE, Access.ALL).contains(access)){
                this.setCurrentFilePasswordButton.setDisable(false);
                this.setCurrentFilePasswordFiled.setDisable(false);
            } else {
                this.setCurrentFilePasswordButton.setDisable(true);
                this.setCurrentFilePasswordFiled.setDisable(true);
            }
        }

    }

    public Boolean isTextAreaDisabled(){
        var currentUser = lfs.getStateManager().getCurrentUser();
        var currentFile = lfs.getStateManager().getCurrentFile();

        if(currentFile.isEmpty() || currentUser.isEmpty()){
            return true;
        }

        if(currentFile.get().getIsProtected()){
            return true;
        }

        var access = AccessChecker.getUserAccess(currentFile.get(), currentUser.get());
        return List.of(Access.NONE, Access.WRITE, Access.READ).contains(access);
    }

    public String getTextAreaContent() {
        var currentUser = lfs.getStateManager().getCurrentUser();
        var currentFile = lfs.getStateManager().getCurrentFile();

        if(currentFile.isEmpty() || currentUser.isEmpty()){
            return "Choose a file to display!";
        }

        if(currentFile.get().getIsProtected()){
            return "This file requires the password to access!";
        }

        var access = AccessChecker.getUserAccess(currentFile.get(), currentUser.get());

        if(List.of(Access.NONE, Access.WRITE).contains(access)){
            return "You do not have permission to access this file!";
        }
        return currentFile.get().getContent();
    }

    public void getFilePassword(){
        var currentFile = lfs.getStateManager().getCurrentFile();
        if(currentFile.isPresent() && currentFile.get().getIsProtected()){
            this.textInputDialog.showAndWait();
            //this.textInputDialog.getOnCloseRequest();
            /*
            .ifPresent(response -> {
                System.out.println(response);
                if(response == "Dialog.ok.button"){
                    if(AccessChecker.isPasswordValid(currentFile.get(), this.textInputDialog.getEditor().getText())){
                        this.fileContentArea.setText(currentFile.get().getContent());
                        this.fileContentArea.setDisable(false);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.show();
                    }
                }
            });
             */


            if(AccessChecker.isPasswordValid(currentFile.get(), this.textInputDialog.getEditor().getText())){
                this.fileContentArea.setText(currentFile.get().getContent());
                this.fileContentArea.setDisable(false);
                this.currentFileOtherAccessChoiceBox.setDisable(false);
                this.currentFileGroupAccessChoiceBox.setDisable(false);
                this.setCurrentFilePasswordFiled.setDisable(false);
                this.setCurrentFilePasswordButton.setDisable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            }


        }
    }

    public void setCurrentFileInformation(){
        var currentFile = lfs.getStateManager().getCurrentFile();
        if(currentFile.isPresent()){
            this.currentFileAuthorLabel.setText(currentFile.get().getAuthor().getName());
            this.currentFileCreatedLabel.setText(currentFile.get().getCreated().toString());
            this.currentFileUpdatedLabel.setText(currentFile.get().getUpdated().toString());
            this.currentFileGroupAccessChoiceBox.setValue(currentFile.get().getGroupsAccess());
            this.currentFileOtherAccessChoiceBox.setValue(currentFile.get().getOtherAccess());
            this.setCurrentFilePasswordFiled.setText(currentFile.get().getPassword());
        }
    }

    public boolean isAccessSelectorDisabled() {
        var currentUser = lfs.getStateManager().getCurrentUser();
        var currentFile = lfs.getStateManager().getCurrentFile();

        if(currentFile.isPresent() && currentUser.isPresent()){
            if(currentFile.get().getIsProtected()){
                return true;
            }
            var access = AccessChecker.getUserAccess(currentFile.get(), currentUser.get());

            if(List.of(Access.READ, Access.NONE).contains(access)){
                return true;
            }
        }
        return false;
    }
}
