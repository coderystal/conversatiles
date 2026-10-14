package questiondecktesting;

import static org.junit.Assert.assertEquals;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ConversatilesStepDefinitions {
	private static ConversatilesPOM conversatilesPage;
	private static List<Question> deck;
	private static Set<Integer> seenNums;
	List<Question> qHistory = new ArrayList<Question>();
	Set<Question> qHistorySet = new TreeSet<Question>();
	LinkedList<Question> qHistoryLL = new LinkedList<Question>();

	final static int totQs = 850;
	final static int totQsComplete = 909;
	final static List<String> categories = new ArrayList<String>(List.of("likes", "dislikes", "experience", "goals", "fantasy", 
							"identity", "people", "tendencies", "worldview"));
	boolean complete = false;
	
	public int getTotQs() {
		return totQs;
	}
	public int getTotQsComplete() {
		return totQsComplete;
	}
	public int getTotDeckQs() {
		return Integer.parseInt(conversatilesPage.getNumCardsButton().getText().split(" ")[0]);
	}
	
	public ConversatilesStepDefinitions() {
		conversatilesPage = new ConversatilesPOM();
		
		launchConversatiles(getLocalSiteUrl());
	}
	
	public String getPublishedSiteUrl() {
		return "https://coderystal.github.io/conversatiles/";
	}
	public String getLocalSiteUrl() {
		return "file:///C:/Users/cywen/OneDrive/Desktop/coderystal/conversatiles/index.html";
	}
	
//	eg launchConversatiles(getUrlToQuestion(getPublishedSiteUrl(), "likes", 10))
	public String getUrlToQuestion(String url, String deck, int num) {
		return url + "?deck=" + deck + "&question=" + num + "&complete=true";
	}
	
	public void launchConversatiles(String url) {
		conversatilesPage.getDriver().get(url);
//		System.out.println("got " + url);
		conversatilesPage.getDriver().manage().window().maximize();
//		System.out.println("maximized");
		conversatilesPage.getScreenToggle().click();
//		System.out.println("screentoggled");
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
		qHistoryLL.add(getCurrentQuestion());
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
    	
    	if (qHistoryLL.size() > 1)
    		qHistoryLL.removeLast();
    	
		assertEquals("question displayed after back, from ll", qHistoryLL.get(qHistoryLL.size()-1), getCurrentQuestion());
	}
	public void clickReset() {
    	System.out.println("reset");
    	conversatilesPage.getResetButton().click();
    	qHistory = new ArrayList<Question>();
    	qHistorySet = new TreeSet<Question>();
	}
	
	public void clickNumCardsAndStoreDeck() {
		conversatilesPage.getNumCardsButton().click();
		deck = getModalQuestionStrings("Deck").stream()
				.map(str -> str.split(" ", 2)).map(strarr -> new Question(Integer.parseInt(strarr[0]), strarr[1])).toList();
		
		conversatilesPage.getNumCardsButton().sendKeys(Keys.ESCAPE);
	}
	
	public void validateHistoryAfterViewedAll_nums_and_strings() {

		//check all qnums *********************************************************************************clicked through all - ie no repeats
		assertEquals("all qnums", IntStream.rangeClosed(1, (complete ? totQsComplete : totQs)).boxed().collect(Collectors.toList()).toString(), qHistorySet.stream().map(qu -> qu.num).toList().toString());
		
		conversatilesPage.getNumCardsButton().click();
		List<String> deck = getModalQuestionStrings("Deck");
		assertEquals("viewed", deck.toString(), qHistorySet.stream().toList().toString());
		conversatilesPage.getNumCardsButton().sendKeys(Keys.ESCAPE);
	}
	
	public Question getCurrentQuestion() {
		return new Question(conversatilesPage.getCard());
	}
	
	public void updateHistorySet() {
		Question q = new Question(conversatilesPage.getCard());
		qHistory.add(q);
		qHistorySet.add(q);
	}
	
	public void customizeDeckByPreset(String preset, boolean inclIntense) {
		conversatilesPage.getCustomizeDeckButton().click();
		if (inclIntense)
			includeIntensByPreset();
		else
			excludeIntensByPreset();
		conversatilesPage.getButtonByText(preset).click();
		if (preset.equals("complete"))
			complete = true;
	}
	
	public void includeIntensByPreset() {
		if (!conversatilesPage.getIncludeHighIntensCheckbox().isSelected())
			conversatilesPage.getIncludeHighIntensCheckbox().click();
	}
	public void excludeIntensByPreset() {
		if (conversatilesPage.getIncludeHighIntensCheckbox().isSelected()) {
			conversatilesPage.getIncludeHighIntensCheckbox().click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void openAndResetContextualizeDeckForm() {
		conversatilesPage.getCustomizeDeckButton().click();
		conversatilesPage.getButtonByText("advanced").click();
		conversatilesPage.getSetToCompleteButton().click();
	}
	
	public void customizeDeckByAdvanced(String category) {
		openAndResetContextualizeDeckForm();
		conversatilesPage.getCategorySelectedValLink().click();
		conversatilesPage.getClearCategoriesButton().click();
		conversatilesPage.getAdvancedCheckbox(category).click();
		conversatilesPage.getCompleteCustomButton().click();
	}
	
	public void selectRandomAdvancedDeck() {
		conversatilesPage.getCustomizeDeckButton().click();
		conversatilesPage.getButtonByText("advanced").click();
		clickSomeCustomOptions(
				(new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0,  
				(new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, 
				(new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, 
				(new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0, (new Random()).nextInt(2) == 0);
		
		conversatilesPage.getCompleteCustomButton().click();
	}
	
	private void clickSomeCustomOptions(boolean... bs) {
		conversatilesPage.getCategorySelectedValLink().click();
		if (bs[0])
			conversatilesPage.getAdvancedCheckbox("dislikes").click();
		if (bs[1])
			conversatilesPage.getAdvancedCheckbox("experience").click();
		if (bs[2])
			conversatilesPage.getAdvancedCheckbox("fantasy").click();
		if (bs[3])
			conversatilesPage.getAdvancedCheckbox("tendencies").click();
		if (bs[4])
			conversatilesPage.getAdvancedCheckbox("worldview").click();
		if (Integer.parseInt(conversatilesPage.getNumMatch().getText()) == 0)
			conversatilesPage.getAdvancedCheckbox("worldview").click();
		
		
		conversatilesPage.getIntensitySelectedValLink().click();
		if (bs[5])
			conversatilesPage.getAdvancedCheckbox("comment - simple pleasantry").click();
		if (bs[6])
			conversatilesPage.getAdvancedCheckbox("factor - recurring or otherwise relevant context").click();
		if (bs[7])
			conversatilesPage.getAdvancedCheckbox("theme - foundational, impactful, or otherwise superlative thought or feeling").click();
		if (Integer.parseInt(conversatilesPage.getNumMatch().getText()) == 0)
			conversatilesPage.getAdvancedCheckbox("factor - recurring or otherwise relevant context").click();
		
		
		conversatilesPage.getSpecificitySelectedValLink().click();
		if (bs[8])
			conversatilesPage.getAdvancedCheckbox("generally unambiguous").click();
		if (Integer.parseInt(conversatilesPage.getNumMatch().getText()) == 0)
			conversatilesPage.getAdvancedCheckbox("generally unambiguous").click();
		
		conversatilesPage.getKeywordSelectedValLink().click();
		if (bs[9])
			conversatilesPage.getKeywordInput().sendKeys("a");
		if (Integer.parseInt(conversatilesPage.getNumMatch().getText()) == 0)
			conversatilesPage.getKeywordInput().clear();

		
		conversatilesPage.getDetailsSelectedValLink().click();
		if (bs[10])
			conversatilesPage.getAdvancedCheckbox("includes comments/suggestions").click();
		if (Integer.parseInt(conversatilesPage.getNumMatch().getText()) == 0)
			conversatilesPage.getAdvancedCheckbox("includes comments/suggestions").click();
		

		conversatilesPage.getSourceSelectedValLink().click();
		if (bs[11])
			conversatilesPage.getAdvancedCheckbox("toastmasters").click();
		if (bs[12])
			conversatilesPage.getAdvancedCheckbox("GracieAbrams").click();
		if (bs[13])
			conversatilesPage.getAdvancedCheckbox("simon.pause").click();
		if (Integer.parseInt(conversatilesPage.getNumMatch().getText()) == 0)
			conversatilesPage.getAdvancedCheckbox("toastmasters").click();
		

		conversatilesPage.getConversatilitySelectedValLink().click();
		if (bs[14])
			conversatilesPage.getAdvancedCheckbox("edited by coderystal").click();
		if (Integer.parseInt(conversatilesPage.getNumMatch().getText()) == 0)
			conversatilesPage.getAdvancedCheckbox("edited by coderystal").click();
		
		System.out.println("num match = " + conversatilesPage.getNumMatch().getText());
	}
	
	
	public void compareDeckLimit() {
		boolean[] clicks = new boolean[15];
		for (int i = 0; i < 15; i++)
			clicks[i] = (new Random()).nextInt(2) == 0;

		conversatilesPage.getCustomizeDeckButton().click();
		conversatilesPage.getButtonByText("advanced").click();
		conversatilesPage.getSetToCompleteButton().click();
		clickSomeCustomOptions(clicks);
		
		int numMatch = Integer.parseInt(conversatilesPage.getNumMatch().getText());
		System.out.println("num match = " + numMatch);
		
		if (5 < numMatch)
			setDeckLimit("all " + numMatch);
		assertEquals("all " + numMatch, conversatilesPage.getCardCountDropdown().getFirstSelectedOption().getText());
		conversatilesPage.getCompleteCustomButton().click();
		conversatilesPage.getNumCardsButton().click();
		List<String> deckall = getModalQuestionStrings("Deck");
		conversatilesPage.getNumCardsButton().sendKeys(Keys.ESCAPE);
		System.out.println("deckall");
		System.out.println(deckall);
		
		compareDeckLimit(5, numMatch, clicks, deckall);
		compareDeckLimit(10, numMatch, clicks, deckall);
		compareDeckLimit(15, numMatch, clicks, deckall);
		compareDeckLimit(20, numMatch, clicks, deckall);
		compareDeckLimit(25, numMatch, clicks, deckall);
	}
	public void setDeckLimit(String s) {
		System.out.println(s);
		conversatilesPage.getCardCountDropdown().selectByVisibleText(s);
		
	}
	private void compareDeckLimit(int limit, int numMatch, boolean[] clicks, List<String> deck) {
		if (limit < numMatch) {
			conversatilesPage.getCustomizeDeckButton().click();
			conversatilesPage.getButtonByText("advanced").click();
			conversatilesPage.getSetToCompleteButton().click();
			clickSomeCustomOptions(clicks);
			setDeckLimit("" + limit);
			conversatilesPage.getCompleteCustomButton().click();
			conversatilesPage.getNumCardsButton().click();
			List<String> deckLimited = getModalQuestionStrings("Deck");
			conversatilesPage.getNumCardsButton().sendKeys(Keys.ESCAPE);
			
			int deckIt = 0;
			int deckLimIt = 0;
			while (deckLimIt < deckLimited.size()) {
				if(deckIt >= deck.size()) {
					System.out.println("FAILED - COULD NOT FIND NEXT ITEM IN LIMITED DECK IN DECK IN ORDER");
					System.out.println("deck limit="+limit);
					System.out.println(deckLimited);
					System.out.println("deck all");
					System.out.println(deck);
					assertEquals("not found in order starting from limited deck position " + deckLimIt +
							"- see comparison between last item in deck all and item we're looking for from deck limited",
							deck.get(deck.size()-1),
							deckLimited.get(deckLimIt));
				}
				if (deck.get(deckIt).equals(deckLimited.get(deckLimIt))) {
					deckLimIt++;
				}
				deckIt++;
			}
			System.out.println("deck limited, deck limit="+limit + ", is an ordered subset of deck all");
			System.out.println(deckLimited);
		}
	}
	
	public void comparePresetVsAdvanced() {		
		//presets with intense ***************************************************************
		List<Integer> presetsWithIntenseCounts = categories.stream().map((cat)-> {
			customizeDeckByPreset(cat, true);			
			return getTotDeckQs();
		}).toList();
		System.out.println("presets with intense counts");
		System.out.println(presetsWithIntenseCounts);
		
		//presets without intense *************************************************************
		customizeDeckByPreset("complete'", false);
		List<Integer> presetsWithoutIntenseCounts = categories.stream().map((cat)-> {
			conversatilesPage.getCustomizeDeckButton().click();
			conversatilesPage.getButtonByText(cat+"'").click();
			return getTotDeckQs();
		}).toList();
		System.out.println("presets without intense counts");
		System.out.println(presetsWithoutIntenseCounts);
		
		//advanced *****************************************************************************
		openAndResetContextualizeDeckForm();
		conversatilesPage.getCategorySelectedValLink().click();

		//advanced with intense *************************************************************
		List<Integer> advancedWithIntenseCounts = categories.stream().map((cat)-> {
			conversatilesPage.getClearCategoriesButton().click();
			conversatilesPage.getAdvancedCheckbox(cat).click();
			return getCustomDeckSize();
		}).toList();
		System.out.println("advanced with intense counts");
		System.out.println(advancedWithIntenseCounts);
		
		//advanced without intense *************************************************************
		conversatilesPage.getIntensitySelectedValLink().click();
		conversatilesPage.getAdvancedHighIntensityCheckbox().click();
		conversatilesPage.getCategorySelectedValLink().click();
		List<Integer> advancedWithoutIntenseCounts = categories.stream().map((cat)-> {
			conversatilesPage.getClearCategoriesButton().click();
			conversatilesPage.getAdvancedCheckbox(cat).click();
			return getCustomDeckSize();
		}).toList();
		System.out.println("advanced without intense counts");
		System.out.println(advancedWithoutIntenseCounts);
		
		//asserts********************************************************************************************************
		assertEquals("preset v advanced with intense counts", presetsWithIntenseCounts, advancedWithIntenseCounts);
		assertEquals("preset v advanced without intense counts", presetsWithoutIntenseCounts, advancedWithoutIntenseCounts);
		
		try {
			conversatilesPage.getCompleteCustomButton().click();
		} catch (Exception e) {
			conversatilesPage.getButtonByText("Use this custom deck!").click();
		}
		
		//complete checks
		for (String cat: categories) {
			comparePresetVsAdvanced(cat, true);
			comparePresetVsAdvanced(cat, false);
		}
	}
	
	private int getCustomDeckSize() {
		try {
			return Integer.parseInt(conversatilesPage.getCompleteCustomButton().getText().split(" ")[1]);
		} catch (Exception e) {
			return Integer.parseInt(conversatilesPage.getCompleteCustomButton().getText().split("contextualized ")[1].split("-")[0]);
		}
	}
	
	public void comparePresetVsAdvanced(String cat, boolean incl) {
		System.out.println(cat + " " + incl);
		//select via preset
		customizeDeckByPreset(cat + (incl ? "" : "'"), incl);
		//get preset count
		int presetCount = Integer.parseInt(conversatilesPage.getNumCardsButton().getText().split(" ")[0]);
		System.out.println("preset count " + presetCount);
		//get preset deck
		conversatilesPage.getNumCardsButton().click();
		List<String> presetDeck = getModalQuestionStrings("Deck");
		System.out.println("preset deck\n" + presetDeck);
		conversatilesPage.getNumCardsButton().sendKeys(Keys.ESCAPE);
		
		//select via advanced
		openAndResetContextualizeDeckForm();
		conversatilesPage.getIntensitySelectedValLink().click();
		if (!incl)
			conversatilesPage.getAdvancedHighIntensityCheckbox().click();
		conversatilesPage.getCategorySelectedValLink().click();
		conversatilesPage.getClearCategoriesButton().click();
		conversatilesPage.getAdvancedCheckbox(cat).click();
		//get advanced count
		int advancedCount = getCustomDeckSize();
		System.out.println("advanced count " + advancedCount);
		//get advanced deck
		try {
			conversatilesPage.getCompleteCustomButton().click();
		} catch (Exception e) {
			conversatilesPage.getButtonByText("Use this custom deck!").click();
		}
		conversatilesPage.getNumCardsButton().click();
		List<String> advancedDeck = getModalQuestionStrings("Deck");
		System.out.println("advanced deck\n" + advancedDeck);
		conversatilesPage.getNumCardsButton().sendKeys(Keys.ESCAPE);
		
		//asserts
		assertEquals("preset vs advanced count", presetCount, advancedCount);
		assertEquals("preset vs advanced deck", presetDeck, advancedDeck);
	}
	
	public void printHistorySet() {
		System.out.println(qHistorySet);
	}
	
	public void validateHistorySetSize(int expsize) {
		assertEquals(expsize, qHistorySet.size());
	}
	
	private List<String> getModalQuestionStrings(String tabletitle) {
		List<String> textLines = Arrays.asList(conversatilesPage.getModalContent().getText().split(tabletitle + "\n")[1].split("\n"));
		
		return IntStream.range(0, textLines.size())
				.mapToObj(lineindex -> getIndAndTextFromTableString(textLines.get(lineindex), lineindex == 0 ? "" : textLines.get(lineindex-1)))
				.filter(str -> !str.isEmpty())
				.toList();
	}
	
	public void clickNumViewedAndValidateHistory() {
		conversatilesPage.getNumViewedButton().click();
		List<String> deckHist = getModalQuestionStrings("Deck history");
		assertEquals("viewed", deckHist.toString(), qHistorySet.stream().toList().toString());
		conversatilesPage.getNumViewedButton().sendKeys(Keys.ESCAPE);
	}
	
	private String getIndAndTextFromTableString(String tablestr, String prevstr) {
		int tablestrlen = tablestr.length();
		if (tablestrlen < 4)
			return "";
		if (!Character.isDigit(tablestr.charAt(0)))
			tablestr = prevstr + " " + tablestr;
		return tablestr;
	}

	public void clickNumViewedAndValidateHistoryWithoutNumbers() {
		conversatilesPage.getNumViewedButton().click();
		List<String> deckHist = getModalQuestionStrings("Deck history").stream()
				.map(str -> str.split(" ", 2)[1])
				.toList();
		assertEquals("viewed", deckHist.toString(), qHistorySet.stream().map((Question q) -> q.toString().split(" ", 2)[1]).toList().toString());
		conversatilesPage.getNumViewedButton().sendKeys(Keys.ESCAPE);
	}
	
	public void testFind() {
		List<Integer> allQNums = IntStream.rangeClosed(1, (complete ? totQsComplete : totQs)).boxed().collect(Collectors.toList());
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
		if (qnum % 25 == 0)
			System.out.println(qnum);
		conversatilesPage.getEnteredCardNum().sendKeys((new Random()).nextInt(2) == 0 ? (Keys.chord(Keys.CONTROL, "a") + Keys.DELETE) : 
			("" + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE));
		
		conversatilesPage.getEnteredCardNum().sendKeys(qnum + "");
		conversatilesPage.getEnteredCardNum().sendKeys((new Random()).nextInt(2) == 0 ? Keys.ENTER : Keys.TAB);
		assertEquals("question", deck.get(qnum-1), new Question(conversatilesPage.getCard()));

		seenNums.add(qnum);
		validateNumViewedCards(seenNums.size());
		validateNumTotalCards();
	}

	public void validateNumViewedCards(int n) {
		if (n == 1)
			assertEquals("total cards", n + " card seen", conversatilesPage.getNumViewedButton().getText());
		else
			assertEquals("total cards", n + " cards seen", conversatilesPage.getNumViewedButton().getText());
	}
	
	public void validateNumTotalCards() {
		assertEquals("total cards", (complete ? totQsComplete : totQs)+" cards", conversatilesPage.getNumCardsButton().getText());
	}
	
	public void clickInfoCompareQuestionTextAndResume() {
		String cardq = conversatilesPage.getCardQuestion().getText();
		conversatilesPage.getInfoButton().click();
		assertEquals("card question and info question", cardq, conversatilesPage.getInfoQuestion().getText());
		conversatilesPage.getResumeButton().click();
		assertEquals("card question and info question", cardq, conversatilesPage.getCardQuestion().getText());
		conversatilesPage.getScreenToggle().click();
	}
}
