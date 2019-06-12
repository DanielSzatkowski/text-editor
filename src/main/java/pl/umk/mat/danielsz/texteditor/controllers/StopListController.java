package pl.umk.mat.danielsz.texteditor.controllers;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class StopListController {
	List<String> stopWords;
	
	@FXML private TextArea textArea;
	@FXML private TextField wordField;
	
	public void setStopList(List<String> _stopWords)
	{
		stopWords = _stopWords;
	}
	
	public void showStopWords()
	{
		textArea.clear();
		
		String stopWordsString = "";
		for(String word : stopWords)
		{
			stopWordsString += word + "\n";
		}
		
		if(!stopWordsString.isEmpty())
			textArea.setText(stopWordsString);
	}
	
	public void add()
	{
		if(!wordField.getText().isEmpty() && wordField.getText() != null)
			stopWords.add(wordField.getText());
		
		showStopWords();
	}
	
	public void erase()
	{
		if(!wordField.getText().isEmpty() && wordField.getText() != null)
			stopWords.remove(wordField.getText());
		
		showStopWords();

		System.out.println("erase");
	}
}
