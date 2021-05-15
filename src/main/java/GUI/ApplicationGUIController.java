package GUI;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Label myLabel;


    @FXML
    private ListView<String> myListView;

    @FXML
    private TextArea fileContentArea;

    @FXML
    private ChoiceBox<User> currentUserSelector;

    @FXML
    private CheckBox filterCheckbox;

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

        var users = lfs
                .getFilesystem()
                .getUsers();

        currentUserSelector.setConverter(new UserStringConverter());
        currentUserSelector.getItems().addAll(users);
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

    public void onUserSelect(ActionEvent event){
        User selectedUser = currentUserSelector.getValue();
        this.lfs.getStateManager().setCurrentUser(Optional.of(selectedUser));
        this.update();
    }

    public void update(){
        fileContentArea.setText(this.getTextAreaContent());
        fileContentArea.setDisable(this.isTextAreaDisabled());

        this.getFilePassword();
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
        return List.of(Access.NONE, Access.WRITE).contains(access);
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

            if(AccessChecker.isPasswordValid(currentFile.get(), this.textInputDialog.getEditor().getText())){
                this.fileContentArea.setText(currentFile.get().getContent());
                this.fileContentArea.setDisable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            }
        }
    }
}
