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

    @Override
    public void initialize(URL location, ResourceBundle resources){
        /*
        myListView.setItems(stringList);
        //myListView.setCellFactory(stringListView -> new StringListViewCell());

         */

        var lfs = LightFileSystem.getInstance();
        this.lfs = lfs;
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

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                var selectedFile = lfs.getFilesystem().getFile(myListView.getSelectionModel().getSelectedItem());
                lfs.getStateManager().setCurrentFile(selectedFile);
                fileContentArea.setText(selectedFile.get().getContent());
            }
        });

        //ObservableValue<Optional<File>> obsCurrentFile = new ReadOnlyObjectWrapper<>(lfs.getStateManager().getCurrentFile());

        /*
        fileContentArea.disableProperty().bind(
                Bindings.createBooleanBinding(()->lfs.getStateManager().getCurrentFile().isEmpty()),
                obsCurrentFile
        );

         */
        fileContentArea.setWrapText(true);
        fileContentArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                var currentFIle = lfs.getStateManager().getCurrentFile();
                if(currentFIle.isPresent()){
                    currentFIle.get().setContent(fileContentArea.getText());
                }
            }
        });
}

    public void onUserSelect(ActionEvent event){
        User selectedUser = currentUserSelector.getValue();
        this.lfs.getStateManager().setCurrentUser(Optional.of(selectedUser));
        System.out.println(selectedUser.getName());
    }

    public void pressing(ActionEvent event) {
        var currentUser = LightFileSystem
                .getInstance()
                .getStateManager()
                .getCurrentUser()
                .get();

        System.out.println(currentUser.getName());
        myLabel.setText(currentUser.getName());
    }

    public Boolean isTextAreaDisabled(){
        var lfs = LightFileSystem.getInstance();
        var currentUser = lfs.getStateManager().getCurrentUser();
        var currentFile = lfs.getStateManager().getCurrentFile();

        if(currentFile.isEmpty() || currentUser.isEmpty()){
            return true;
        }
        var access = AccessChecker.getUserAccess(currentFile.get(), currentUser.get());
        return List.of(Access.NONE, Access.WRITE).contains(access);
    }

    public String getTextAreaContent() {
        var lfs = LightFileSystem.getInstance();
        var currentUser = lfs.getStateManager().getCurrentUser();
        var currentFile = lfs.getStateManager().getCurrentFile();

        if(currentFile.isEmpty() || currentUser.isEmpty()){
            return "Choose a a file to display!";
        }
        var access = AccessChecker.getUserAccess(currentFile.get(), currentUser.get());

        if(List.of(Access.NONE, Access.WRITE).contains(access)){
            return "You do not have permission to access this file!";
        }
        return currentFile.get().getContent();
    }

    public void changeCurrentUser(User user){
        this.lfs.getStateManager().setCurrentUser(Optional.of(user));
    }
}
