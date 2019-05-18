package pl.umk.mat.danielsz.texteditor.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class WordsFrequencyController {
	@FXML private TextArea wordsArea;
	
	private TreeMap<String, Integer> wordsFrequency = new TreeMap<String, Integer>();
	
	private List<Map.Entry<String, Integer>> getSortedListOfWords(Map<String, Integer> map)
	{
		List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>();
		
		entries.addAll(map.entrySet());
		Collections.sort(entries, (Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) -> {
			return entry2.getValue() - entry1.getValue();
		});
		
		return entries;
	}
	
	public void setWordsFrequency(TreeMap<String, Integer> map)
	{
		wordsFrequency = map;
	}
	
	public void showWords()
	{
		String words = "";
		
		List<Map.Entry<String, Integer>> entries = getSortedListOfWords(wordsFrequency);
		
		for(Map.Entry<String, Integer> entry : entries) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			
			if(key.isEmpty())
				continue;
			
			words += key + ": " + value + "\n";
		}
		
		wordsArea.setText(words);
	}
}
