package pl.umk.mat.danielsz.texteditor.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	@FXML private TextArea usersText;
	@FXML private TextField regexWord;
	@FXML private TextField changeWord;
	
	private TreeMap<String, Integer> wordsFrequency = new TreeMap<String, Integer>();
	private List<String> stopWords = new ArrayList<String>();
	
	public void initialize()
	{
		stopWords.addAll(Arrays.asList("a", "ale", "ach", "aj", "albo", "bardzo", "bez", "bo", "by", "być", 
				"ci", "cię", "ciebie", "co", "czy", "daleko", "dla", "dlaczego", "dlatego", "do", 
				"dobrze", "dokąd", "dość", "dużo", "dwa", "dwaj", "dwie", "dwoje", "dziś", "dzisiaj", 
				"gdyby", "gdzie", "go", "i", "ich", "ile", "im", "inny", "ja", "ją", "jak", "jakby", "jaki", 
				"je", "jeden", "jedna", "jedno", "jego", "jej", "jemu", "jeśli", "jest", "jestem", "jeżeli", 
				"już", "każdy", "kiedy", "kierunku", "kto", "ku", "lecz", "lub", "ma", "mają", "mam", "mi", "mną", 
				"mnie", "moi", "mój", "moja", "moje", "może", "mu", "my", "na", "nam", "nami", "nas", 
				"nasi", "nasz", "nasza", "nasze", "natychmiast", "nią", "nic", "nich", "nie", "niego", 
				"niej", "niemu", "nigdy", "nim", "nimi", "niż", "o", "obok", "od", "około", "on", "ona", 
				"one", "oni", "ono", "owszem", "po", "pod", "ponieważ", "przed", "przedtem", "są", 
				"sam", "sama", "się", "skąd", "tak", "taki", "tam", "ten", "to", "tobą", "tobie", "tu", 
				"tutaj", "twoi", "twój", "twoja", "twoje", "ty", "u", "w", "wam", "wami", "was", "wasi", 
				"wasz", "wasza", "wasze", "we", "więc", "wszystko", "wtedy", "wy", "z", "za", "żaden", "zawsze", "że"));
	}
		
	private String removeCharAt(String str, int p) {
		if(str.isEmpty())
			return " ";
		
		return str.substring(0, p) + str.substring(p + 1); 
	}
	
	private String deleteAnnotation(String word)
	{
		int i = word.length() - 1;
		while(word.charAt(i) != '[' && i > 0){
			word = removeCharAt(word, i);
			i--;
		}
		
		word = removeCharAt(word, i);
		return word;
	}
	
	private String removeSpecialChars(String word)
	{
		if(word.matches(".*\\[.*\\]"))
			word = deleteAnnotation(word);
		
		if(word.isEmpty())
			return word;
		
		char firstChar = word.charAt(0);
		char lastChar = word.charAt(word.length() - 1);
		
		while(lastChar == '.' || lastChar == ','
			|| lastChar == ';' || lastChar == ')'
			|| lastChar == ']' || lastChar == '}'
			|| lastChar == '-' || lastChar == '?' 
			|| lastChar == '\"' || lastChar == '!'
			|| lastChar == ',' || lastChar == ':'
			|| lastChar == '|' || lastChar == '['
			|| lastChar == '”' || lastChar == '…'
			|| lastChar == '+' || lastChar == '/') {
				word = removeCharAt(word, word.length() - 1);		

				if(!word.isEmpty())
					lastChar = word.charAt(word.length() - 1);
		}

		if(firstChar == '{' || firstChar == '[' || firstChar == '(' || firstChar  == '\"' || firstChar == '„' || firstChar == '…' || lastChar == '+'
				|| lastChar == '/') {
				word = removeCharAt(word, 0);
		}	
		
		if(word.matches(".*\\[.*\\]"))
			word = deleteAnnotation(word);
		
		return word;
	}
	
	private String deleteAllStopWords(String txt)
	{
		String ret = " ";
		for(String word : txt.split("\\s")) {
			if(word.isEmpty())
				continue;
			
			word = removeSpecialChars(word);
			ret += word + " ";
		}

		for(String word : stopWords) {
			ret = ret.replaceAll("\\b" + word + "\\b", "");
			ret = ret.replaceAll("\\b"+Character.toUpperCase(word.charAt(0)) + removeCharAt(word, 0) +"\\b", "");
		}
		
		return ret;
	}
	
	public void modifyText() 
	{
		if(usersText.getText() == null || usersText.getText().isEmpty() 
				|| regexWord.getText()  == null || regexWord.getText().isEmpty() 
				|| changeWord.getText() == null)
			return;
		
		String textToModify = usersText.getText();
		
		String newText = textToModify.replaceAll(regexWord.getText(), changeWord.getText());
		usersText.setText(newText);
	}
	
	public void showStopListView()
	{
		try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/StopListView.fxml"));
            Parent root = fxmlLoader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Stop List");
            stage.setScene(new Scene(root, 610, 430));
            
            StopListController otherController = fxmlLoader.getController();
            otherController.setStopList(stopWords);
            otherController.showStopWords();
            
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void countWords()
	{
		wordsFrequency.clear();
		if(usersText.getText() == null || usersText.getText().isEmpty())
			return;
		
		String txt = usersText.getText();
		String txtAfterMod = " ";
		
		if(!txt.isEmpty()) {
			for(String word: txt.split("\\s")){
				word = removeSpecialChars(word);
				
				txtAfterMod = txtAfterMod + word + " ";
			}
			
			txtAfterMod = deleteAllStopWords(txtAfterMod);
			
			for(String word: txtAfterMod.split("\\s")){
				word = removeSpecialChars(word);
				
				if(wordsFrequency.get(word.toLowerCase()) == null)
					wordsFrequency.put(word.toLowerCase(), 1);
				else
					wordsFrequency.put(word.toLowerCase(), wordsFrequency.get(word.toLowerCase()) + 1);
			}
		}
		
		try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/WordsFrequencyView.fxml"));
            Parent root = fxmlLoader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Words Frequency");
            stage.setScene(new Scene(root, 610, 430));
            
            WordsFrequencyController otherController = fxmlLoader.getController();
            otherController.setWordsFrequency(wordsFrequency);
            otherController.showWords();
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
		wordsFrequency.clear();
	}
		
}
