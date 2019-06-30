package fileChooser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.FileService;
import service.TextFile;
import sun.security.util.Length;

/**
 *
 * @author Dana
 */
public class FileChooserWindow extends Application {
    
 private Desktop desktop = Desktop.getDesktop();
 private TextFile choosedFile;
    @Override
    public void start(Stage primaryStage) throws Exception {
 
        final FileChooser fileChooser = new FileChooser();
 
        TextArea textArea = new TextArea();
        textArea.setMinHeight(70);
 
        Button SelectFileButton = new Button("Select File"); 
        Button checConcatenatedWordsButton = new Button("Check concatenated words");
        checConcatenatedWordsButton.setDisable(true);
        SelectFileButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                textArea.clear();
                File file = fileChooser.showOpenDialog(primaryStage);
                if(file!=null){
                    choosedFile = new TextFile(file);//open file chooser for choose file
                    printResults(textArea, "File Path = "+file.getPath());
                    checConcatenatedWordsButton.setDisable(false);
                }
                
            }
        });
        
        checConcatenatedWordsButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                FileService fileService = new FileService();
                try {
                    fileService.readtextFile(choosedFile);
                    fileService.countConcatenatedWords(choosedFile);
                } catch (IOException ex) {
                    Logger.getLogger(FileChooserWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                List<String> concatWord = choosedFile.getAllConcatenatedWords();
                if(concatWord!=null){
                    printResults(textArea, "Total concat word count="+concatWord.size());
                    if(concatWord.size()>0){
                      printResults(textArea, "The longest words:");
                      printResults(textArea, fileService.getLongestConcatenatedWordList(concatWord));
                            
                      if(concatWord.size()>1){
                          printResults(textArea, "The 2nd longest words:");
                           printResults(textArea, fileService.getSecondLongestConcatenatedWordList(concatWord, concatWord.get(0).length())); 
                      }
                      
                    }
                    
                }
            }
        });
 
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(5);
 
        vbox.getChildren().addAll(textArea, SelectFileButton, checConcatenatedWordsButton);
 
        Scene scene = new Scene(vbox, 500, 300);
 
        primaryStage.setTitle("Concatenated words searcher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    private void printResults(TextArea textArea, String text) {
       textArea.appendText(text + "\n");
    }
  private void printResults(TextArea textArea, List<String> text) {
      for(String word:text){
          textArea.appendText(word+ "\n");
      }
       
    }
 
    public static void main(String[] args) {
        Application.launch(args);
    }
   
}
