
package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Collections;

/**
 *
 * @author Dana
 */
public class FileService {
    private FileChooserTrie root;
    public void readtextFile(TextFile choosenFile) throws IOException {
      //gets text file content 
        File file = choosenFile.getFile();
       if(file.exists() && !file.isDirectory()){
        List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
            for(String line: lines){
               choosenFile.getFileContent().add(line);
            }
       }
    }
    
    public void countConcatenatedWords(TextFile choosenFile){
      choosenFile.setAllConcatenatedWords(findAllConcatenatedWords(choosenFile.getFileContent())); //get all concat words
      List<String> concatWords = choosenFile.getAllConcatenatedWords();
    if(concatWords!=null && !concatWords.isEmpty()){
        Collections.sort(concatWords, new LengthComparator()); //sort from lagest to smallest
        
    }
    }
      public List<String> getSecondLongestConcatenatedWordList(List<String> contentList, int longestLen){
          int currlen = 0;
          int middleLen= 0;
          List<String> secondLongestList = new ArrayList<>();
          for(String word:contentList){
              if(word.length()<longestLen){
                  currlen = word.length();
                  if(middleLen==0){
                    middleLen =  currlen; 
                  }
                  if(middleLen>currlen){
                      break;
                  }
                  else{
                     secondLongestList.add(word);
                  }
              }
          }
          return secondLongestList;
      }
      public List<String> getLongestConcatenatedWordList(List<String> contentList){
          List<String> longestList = new ArrayList<>();
          int len = contentList.get(0).length();
          for(String word:contentList){
              if(len> word.length()){
                  break;
              }
              longestList.add(word);
          }
          return longestList;
      }
    
    private void addToTrie(String s) {
        FileChooserTrie trieNode = root;
        for (char c : s.toCharArray()) {
            if (trieNode.next[c - 'a'] == null) {
                trieNode.next[c - 'a'] = new FileChooserTrie();
            }
            trieNode = trieNode.next[c - 'a'];
        }
        trieNode.isWord = true;
    }

    private boolean searchWordInTrie(char[] charWordArray, int startIndex) {
        FileChooserTrie fileChooserTrieNode = root;
        for (int i = startIndex; i < charWordArray.length; i++) {
            char c = charWordArray[i];
            if (fileChooserTrieNode.next[c - 'a'] == null) return false;
            fileChooserTrieNode = fileChooserTrieNode.next[c - 'a'];
            if (fileChooserTrieNode.isWord && searchWordInTrie(charWordArray, i + 1)) {
                return true;
            }
        }
        return startIndex == 0 ? false : fileChooserTrieNode.isWord;
    }
   
    public List<String> findAllConcatenatedWords(List<String> words) {
        // create trie
        root = new FileChooserTrie();
        for (String str : words) {
            if (!str.equals("")){ 
            addToTrie(str);
            }
        }
        // search concatenated words
        List<String> list = new ArrayList<>();
        for (String str : words) {
             if (searchWordInTrie(str.toCharArray(), 0)){
                list.add(str); 
             }
                 
        }
        return list;
    }
}
