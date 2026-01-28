package questiondecktesting;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ConversatilesStepDefinitions {
	private static ConversatilesPOM conversatilesPage;
	private static List<Question> deck;
	private static Set<Integer> seenNums;
	List<Question> qHistory = new ArrayList<Question>();
	Set<Question> qHistorySet = new TreeSet<Question>();

	final static int totQs = 731;
	public int getTotQs() {
		return totQs;
	}
	
	public ConversatilesStepDefinitions() {
		conversatilesPage = new ConversatilesPOM();
		
//		conversatilesPage.getDriver().get("https://coderystal.github.io/conversatiles/");
		conversatilesPage.getDriver().get("file:///C:/Users/cywen/OneDrive/Desktop/util/conversatiles/index.html");
		System.out.println("got");
		conversatilesPage.getDriver().manage().window().maximize();
		System.out.println("maximized");
		conversatilesPage.getDriver().findElement(new ById("screentoggle")).click();
		System.out.println("screentoggled");
		conversatilesPage.getWait().until(ExpectedConditions.presenceOfElementLocated(new ById("question")));
	}
	
	public void close() {
		conversatilesPage.getDriver().close();
    	System.out.println("done");
	}

	public void validateBackButtonEnabledStatus(boolean expectedEnabled) {
		assertEquals("back enabled", expectedEnabled, conversatilesPage.getBackButton().isEnabled());
	}
	
	public void validateButtonEnabledValuesAfterReset() {
		assertEquals("viewed enabled", true, conversatilesPage.getNumViewedButton().isEnabled());
		assertEquals("cards enabled", true, conversatilesPage.getNumCardsButton().isEnabled());
		assertEquals("reset enabled", false, conversatilesPage.getResetButton().isEnabled());
		assertEquals("back enabled", false, conversatilesPage.getBackButton().isEnabled());
		assertEquals("draw enabled", true, conversatilesPage.getDrawButton().isEnabled());
	}
	
	public void clickCardOrDraw() {
		//click card or draw button
		if ((new Random()).nextInt(2) == 0)
			conversatilesPage.getCard().click();
		else
			conversatilesPage.getDrawButton().click();
	}
	
	public void validateButtonEnabledValuesAfterFlip(int flipped) {
		assertEquals("viewed enabled", true, conversatilesPage.getNumViewedButton().isEnabled());
		assertEquals("cards enabled", true, conversatilesPage.getNumCardsButton().isEnabled());
		assertEquals("reset enabled", true, conversatilesPage.getResetButton().isEnabled());
		assertEquals("back enabled", flipped != 0, conversatilesPage.getBackButton().isEnabled());
		assertEquals("draw enabled", flipped != totQs-1, conversatilesPage.getDrawButton().isEnabled());
	}

	public void clickBack() {
    	conversatilesPage.getBackButton().click();		
	}
	public void clickReset() {
    	System.out.println("reset");
    	conversatilesPage.getResetButton().click();
    	qHistory = new ArrayList<Question>();
    	qHistorySet = new TreeSet<Question>();
	}
	
	public void clickNumCardsAndStoreDeck() {
		conversatilesPage.getNumCardsButton().click();
		deck = Arrays.asList(conversatilesPage.getModalContent().getText().split("Deck\n")[1].split("\n")).stream()
				.map(str -> str.split(" ", 2)).map(strarr -> new Question(Integer.parseInt(strarr[0]), strarr[1])).toList();
		conversatilesPage.getNumCardsButton().sendKeys(Keys.ESCAPE);
	}
	
	public void validateHistoryAfterViewedAll_nums_and_strings() {

		//check all qnums *********************************************************************************clicked through all - ie no repeats
		assertEquals("all qnums", IntStream.rangeClosed(1, totQs).boxed().collect(Collectors.toList()).toString(), qHistorySet.stream().map(qu -> qu.num).toList().toString());
		
		conversatilesPage.getNumCardsButton().click();
		List<String> deck = Arrays.asList(conversatilesPage.getModalContent().getText().split("Deck\n")[1].split("\n")).stream().toList();
		assertEquals("viewed", deck.toString(), qHistorySet.stream().toList().toString());
		conversatilesPage.getNumCardsButton().sendKeys(Keys.ESCAPE);
	}
	
	public void updateHistorySet() {
		Question q = new Question(conversatilesPage.getCard());
		qHistory.add(q);
		qHistorySet.add(q);
	}
	
	public void clickNumViewedAndValidateHistory() {
		conversatilesPage.getNumViewedButton().click();
		List<String> deckHist = Arrays.asList(conversatilesPage.getModalContent().getText().split("Deck history\n")[1].split("\n")).stream()
				.toList();
		assertEquals("viewed", deckHist.toString(), qHistorySet.stream().toList().toString());
		conversatilesPage.getNumViewedButton().sendKeys(Keys.ESCAPE);
	}
	
	public void testFind() {
		List<Integer> allQNums = IntStream.rangeClosed(1, totQs).boxed().collect(Collectors.toList());
        Collections.shuffle(allQNums);
        seenNums = new HashSet<Integer>();

		for (int i = 0; i < 50; i++)
			findCardNumber((new Random()).nextInt(totQs) + 1);
		System.out.println("found 50");
		
		for (int qnum: allQNums)
			findCardNumber(qnum);
		System.out.println("found " + totQs);

		for (int i = 0; i < 50; i++)
			findCardNumber((new Random()).nextInt(totQs) + 1);
		System.out.println("found 50");
	}
	

	private void findCardNumber(int qnum) {
		conversatilesPage.getEnteredCardNum().sendKeys((new Random()).nextInt(2) == 0 ? (Keys.chord(Keys.CONTROL, "a") + Keys.DELETE) : 
			("" + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE));
		
		conversatilesPage.getEnteredCardNum().sendKeys(qnum + "");
		conversatilesPage.getEnteredCardNum().sendKeys((new Random()).nextInt(2) == 0 ? Keys.ENTER : Keys.TAB);
		assertEquals("question", deck.get(qnum-1), new Question(conversatilesPage.getCard()));

		seenNums.add(qnum);
		assertEquals("total cards", seenNums.size()+" cards seen", conversatilesPage.getNumViewedButton().getText());
		validateNumTotalCards();
	}

	public void validateNumViewedCards(int n) {
		assertEquals("total cards", n + " cards seen", conversatilesPage.getNumViewedButton().getText());
	}
	
	public void validateNumTotalCards() {
		assertEquals("total cards", totQs+" cards", conversatilesPage.getNumCardsButton().getText());
	}
}