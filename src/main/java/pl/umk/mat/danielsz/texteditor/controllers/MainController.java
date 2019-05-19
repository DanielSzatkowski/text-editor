package pl.umk.mat.danielsz.texteditor.controllers;

import java.io.IOException;
import java.util.ArrayList;
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
	
	private String removeCharAt(String str, int p) {
		if(str.isEmpty())
			return " ";
		
		return str.substring(0, p) + str.substring(p + 1); 
	}
	
	private String removeSpecialChars(String word)
	{
		if(word.isEmpty())
			return word;
		
		char firstChar = word.charAt(0);
		char lastChar = word.charAt(word.length() - 1);
		
		while(lastChar == '.' || lastChar == ','
			|| lastChar == ';' || lastChar == ')'
			|| lastChar == ']' || lastChar == '}'
			|| lastChar == '-') {
				word = removeCharAt(word, word.length() - 1);		

				lastChar = word.charAt(word.length() - 1);
		}

		if(firstChar == '{' || firstChar == '[' || firstChar == '(') {
				word = removeCharAt(word, 0);
		}	
		
		return word;
	}
	
	private String deleteAllStopWords(String txt)
	{
		for(String word : txt.split("\\s")) {
			if(word.isEmpty())
				continue;
			
			word = removeSpecialChars(word);
		}
		
		for(String word : stopWords) {
			txt = " " + txt + " ";
			txt = txt.replaceAll("(?i) " + word + " ", " ");
		}
		
		return txt;
	}
	
	public void modifyText() 
	{
		if(usersText.getText() == null || usersText.getText().isEmpty() 
				|| regexWord.getText()  == null || regexWord.getText().isEmpty() 
				|| changeWord.getText() == null || changeWord.getText().isEmpty())
			return;
		
		String textToModify = usersText.getText();
		
		String newText = textToModify.replaceAll(regexWord.getText(), changeWord.getText());
		usersText.setText(newText);
	}
	
	public void countWords()
	{
		if(usersText.getText() == null || usersText.getText().isEmpty())
			return;
		
		String txt = usersText.getText();
		txt = deleteAllStopWords(txt);
		
		if(!txt.isEmpty()) {
			for(String word: txt.split("\\s")){
				word = removeSpecialChars(word);
				
				if(wordsFrequency.get(word) == null)
					wordsFrequency.put(word, 1);
				else
					wordsFrequency.put(word, wordsFrequency.get(word) + 1);
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
	
	public void initialize()
	{
		stopWords.add("a");
		stopWords.add("aby");
		stopWords.add("ach");
		stopWords.add("acz");
		stopWords.add("aczkolwiek");
		stopWords.add("aj");
		stopWords.add("albo");
		stopWords.add("ale");
		stopWords.add("alez");
		stopWords.add("ależ");
		stopWords.add("ani");
		stopWords.add("az");
		stopWords.add("aż");
		stopWords.add("bardziej");
		stopWords.add("bardzo");
		stopWords.add("beda");
		stopWords.add("bedzie");
		stopWords.add("bez");
		stopWords.add("deda");
		stopWords.add("będą");
		stopWords.add("bede");
		stopWords.add("będę");
		stopWords.add("będzie");
		stopWords.add("bo");
		stopWords.add("bowiem");
		stopWords.add("by");
		stopWords.add("byc");
		stopWords.add("być");
		stopWords.add("byl");
		stopWords.add("byla");
		stopWords.add("byli");
		stopWords.add("bylo");
		stopWords.add("byly");
		stopWords.add("był");
		stopWords.add("była");
		stopWords.add("było");
		stopWords.add("były");
		stopWords.add("bynajmniej");
		stopWords.add("cala");
		stopWords.add("cali");
		stopWords.add("caly");
		stopWords.add("cała");
		stopWords.add("cały");
		stopWords.add("ci");
		stopWords.add("cie");
		stopWords.add("ciebie");
		stopWords.add("cię");
		stopWords.add("co");
		stopWords.add("cokolwiek");
		stopWords.add("cos");
		stopWords.add("coś");
		stopWords.add("czasami");
		stopWords.add("czasem");
		stopWords.add("czemu");
		stopWords.add("czy");
		stopWords.add("czyli");
		stopWords.add("daleko");
		stopWords.add("dla");
		stopWords.add("dlaczego");
		stopWords.add("dlatego");
		stopWords.add("do");
		stopWords.add("dobrze");
		stopWords.add("dokad");
		stopWords.add("dokąd");
		stopWords.add("dosc");
		stopWords.add("dość");
		stopWords.add("duzo");
		stopWords.add("dużo");
		stopWords.add("dwa");
		stopWords.add("dwaj");
		stopWords.add("dwie");
		stopWords.add("dwoje");
		stopWords.add("dzis");
		stopWords.add("dzisiaj");
		stopWords.add("dziś");
		stopWords.add("gdy");
		stopWords.add("gdyby");
		stopWords.add("gdyz");
		stopWords.add("gdyż");
		stopWords.add("gdzie");
		stopWords.add("gdziekolwiek");
		stopWords.add("gdzies");
		stopWords.add("gdzieś");
		stopWords.add("go");
		stopWords.add("i");
		stopWords.add("ich");
		stopWords.add("ile");
		stopWords.add("im");
		stopWords.add("inna");
		stopWords.add("inne");
		stopWords.add("inny");
		stopWords.add("innych");
		stopWords.add("iz");
		stopWords.add("iż");
		stopWords.add("ja");
		stopWords.add("jak");
		stopWords.add("jakas");
		stopWords.add("jakaś");
		stopWords.add("jakby");
		stopWords.add("jaki");
		stopWords.add("jakichs");
		stopWords.add("jakichś");
		stopWords.add("jakie");
		stopWords.add("jakis");
		stopWords.add("jakiś");
		stopWords.add("jakiz");
		stopWords.add("jakiż");
		stopWords.add("jakkolwiek");
		stopWords.add("jako");
		stopWords.add("jakos");
		stopWords.add("jakoś");
		stopWords.add("ją");
		stopWords.add("je");
		stopWords.add("jeden");
		stopWords.add("jedna");
		stopWords.add("jednak");
		stopWords.add("jednakze");
		stopWords.add("jednakże");
		stopWords.add("jedno");
		stopWords.add("jego");
		stopWords.add("jej");
		stopWords.add("jemu");
		stopWords.add("jesli");
		stopWords.add("jest");
		stopWords.add("jestem");
		stopWords.add("jeszcze");
		stopWords.add("jeśli");
		stopWords.add("jezeli");
		stopWords.add("jeżeli");
		stopWords.add("juz");
		stopWords.add("już");
		stopWords.add("kazdy");
		stopWords.add("każdy");
		stopWords.add("kiedy");
		stopWords.add("kilka");
		stopWords.add("kims");
		stopWords.add("kimś");
		stopWords.add("kto");
		stopWords.add("ktokolwiek");
		stopWords.add("ktora");
		stopWords.add("ktore");
		stopWords.add("ktorego");
		stopWords.add("ktorej");
		stopWords.add("ktory");
		stopWords.add("ktorych");
		stopWords.add("ktorym");
		stopWords.add("ktorzy");
		stopWords.add("ktos");
		stopWords.add("ktoś");
		stopWords.add("która");
		stopWords.add("które");
		stopWords.add("którego");
		stopWords.add("której");
		stopWords.add("który");
		stopWords.add("których");
		stopWords.add("którym");
		stopWords.add("którzy");
		stopWords.add("ku");
		stopWords.add("lat");
		stopWords.add("lecz");
		stopWords.add("lub");
		stopWords.add("ma");
		stopWords.add("mają");
		stopWords.add("mało");
		stopWords.add("mam");
		stopWords.add("mi");
		stopWords.add("miedzy");
		stopWords.add("między");
		stopWords.add("mimo");
		stopWords.add("mna");
		stopWords.add("mną");
		stopWords.add("mnie");
		stopWords.add("moga");
		stopWords.add("mogą");
		stopWords.add("moi");
		stopWords.add("moim");
		stopWords.add("moj");
		stopWords.add("moja");
		stopWords.add("moje");
		stopWords.add("moze");
		stopWords.add("mozliwe");
		stopWords.add("mozna");
		stopWords.add("może");
		stopWords.add("możliwe");
		stopWords.add("można");
		stopWords.add("mój");
		stopWords.add("mu");
		stopWords.add("musi");
		stopWords.add("my");
		stopWords.add("na");
		stopWords.add("nad");
		stopWords.add("nam");
		stopWords.add("nami");
		stopWords.add("nas");
		stopWords.add("nasi");
		stopWords.add("nasz");
		stopWords.add("nasza");
		stopWords.add("nasze");
		stopWords.add("naszego");
		stopWords.add("naszych");
		stopWords.add("natomiast");
		stopWords.add("natychmiast");
		stopWords.add("nawet");
		stopWords.add("nia");
		stopWords.add("nią");
		stopWords.add("nic");
		stopWords.add("nich");
		stopWords.add("nie");
		stopWords.add("niech");
		stopWords.add("niego");
		stopWords.add("niej");
		stopWords.add("niemu");
		stopWords.add("nigdy");
		stopWords.add("nim");
		stopWords.add("nimi");
		stopWords.add("niz");
		stopWords.add("niż");
		stopWords.add("no");
		stopWords.add("o");
		stopWords.add("obok");
		stopWords.add("od");
		stopWords.add("około");
		stopWords.add("on");
		stopWords.add("ona");
		stopWords.add("one");
		stopWords.add("oni");
		stopWords.add("ono");
		stopWords.add("oraz");
		stopWords.add("oto");
		stopWords.add("owszem");
		stopWords.add("pan");
		stopWords.add("pana");
		stopWords.add("pani");
		stopWords.add("po");
		stopWords.add("pod");
		stopWords.add("podczas");
		stopWords.add("pomimo");
		stopWords.add("ponad");
		stopWords.add("poniewaz");
		stopWords.add("ponieważ");
		stopWords.add("powinien");
		stopWords.add("powinna");
		stopWords.add("powinni");
		stopWords.add("powinno");
		stopWords.add("poza");
		stopWords.add("prawie");
		stopWords.add("przeciez");
		stopWords.add("przecież");
		stopWords.add("przed");
		stopWords.add("przede");
		stopWords.add("przedtem");
		stopWords.add("przez");
		stopWords.add("przy");
		stopWords.add("roku");
		stopWords.add("rowniez");
		stopWords.add("również");
		stopWords.add("sam");
		stopWords.add("sama");
		stopWords.add("są");
		stopWords.add("sie");
		stopWords.add("się");
		stopWords.add("skad");
		stopWords.add("skąd");
		stopWords.add("soba");
		stopWords.add("sobą");
		stopWords.add("sobie");
		stopWords.add("sposob");
		stopWords.add("sposób");
		stopWords.add("swoje");
		stopWords.add("ta");
		stopWords.add("tak");
		stopWords.add("taka");
		stopWords.add("taki");
		stopWords.add("takie");
		stopWords.add("takze");
		stopWords.add("także");
		stopWords.add("tam");
		stopWords.add("te");
		stopWords.add("tego");
		stopWords.add("tej");
		stopWords.add("ten");
		stopWords.add("teraz");
		stopWords.add("też");
		stopWords.add("to");
		stopWords.add("toba");
		stopWords.add("tobą");
		stopWords.add("tobie");
		stopWords.add("totez");
		stopWords.add("toteż");
		stopWords.add("totobą");
		stopWords.add("trzeba");
		stopWords.add("tu");
		stopWords.add("tutaj");
		stopWords.add("twoi");
		stopWords.add("twoim");
		stopWords.add("twoj");
		stopWords.add("twoja");
		stopWords.add("twoje");
		stopWords.add("twój");
		stopWords.add("twym");
		stopWords.add("ty");
		stopWords.add("tych");
		stopWords.add("tylko");
		stopWords.add("tym");
		stopWords.add("u");
		stopWords.add("w");
		stopWords.add("wam");
		stopWords.add("wami");
		stopWords.add("was");
		stopWords.add("wasz");
		stopWords.add("wasza");
		stopWords.add("wasze");
		stopWords.add("we");
		stopWords.add("według");
		stopWords.add("wiele");
		stopWords.add("wielu");
		stopWords.add("więc");
		stopWords.add("więcej");
		stopWords.add("wlasnie");
		stopWords.add("właśnie");
		stopWords.add("wszyscy");
		stopWords.add("wszystkich");
		stopWords.add("wszystkie");
		stopWords.add("wszystkim");
		stopWords.add("wszystko");
		stopWords.add("wtedy");
		stopWords.add("wy");
		stopWords.add("z");
		stopWords.add("za");
		stopWords.add("zaden");
		stopWords.add("zadna");
		stopWords.add("zadne");
		stopWords.add("zadnych");
		stopWords.add("zapewne");
		stopWords.add("zawsze");
		stopWords.add("ze");
		stopWords.add("zeby");
		stopWords.add("zeznowu");
		stopWords.add("zł");
		stopWords.add("znow");
		stopWords.add("znowu");
		stopWords.add("znów");
		stopWords.add("zostal");
		stopWords.add("został");
		stopWords.add("żaden");
		stopWords.add("żadna");
		stopWords.add("żadne");
		stopWords.add("żadnych");
		stopWords.add("że");
		stopWords.add("żeby");
	}
}
