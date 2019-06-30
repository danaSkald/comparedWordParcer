
package service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dana
 */
public class TextFile {
    private File file;
    private List<String> fileContent = new ArrayList<>();
    private List<String> allConcatenatedWords;
   
    
    public TextFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<String> getFileContent() {
        return fileContent;
    }

    public void setFileContent(List<String> fileContent) {
        this.fileContent = fileContent;
    }

    public List<String> getAllConcatenatedWords() {
        return allConcatenatedWords;
    }

    public void setAllConcatenatedWords(List<String> allConcatenatedWords) {
        this.allConcatenatedWords = allConcatenatedWords;
    }
    
    
}
