package questiondecktesting;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class QuestionDeckFlipAndReset {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(10));
		
		
		driver.get("https://htmlpreview.github.io/?https://github.com/coderystal/questiondeck/blob/main/v1/index.html");
		System.out.println("got");
		driver.manage().window().maximize();
		System.out.println("maximized");
		wait.until(ExpectedConditions.presenceOfElementLocated(new ById("question")));

		List<Question> qHistory = new ArrayList<Question>();
		Set<Question> qHistorySet = new TreeSet<Question>();
	    
		for (int i = 0; i < 652; i++) {
			//click card or draw button *************************************************************************CARD AND DRAW NEW CARD
			driver.findElement(new ById((new Random()).nextInt(2) == 0 ? "question" : "drawbutton")).click();

			//check m/n button counts
			assertEquals("total cards", i+1+"", driver.findElement(new ById("numviewed")).getText());
			assertEquals("total cards", "652", driver.findElement(new ById("numcards")).getText());
			
			//store q from card
			Question q = new Question(driver.findElement(new ById("question")));
			qHistory.add(q);
			qHistorySet.add(q);
			
			//check deck history *********************************************************************************DECK HISTORY
			driver.findElement(new ById("numviewed")).click();
			List<String> deckHist = Arrays.asList(driver.findElement(new ByClassName("modal-content")).getText().split("Deck history\n")[1].split("\n")).stream().toList();
			assertEquals("viewed", deckHist.toString(), qHistorySet.stream().toList().toString());
			driver.findElement(new ById("numviewed")).sendKeys(Keys.ESCAPE);
		}

		//check all qnums *********************************************************************************clicked through all - ie no repeats
		assertEquals("all qnums", IntStream.rangeClosed(1, 652).boxed().collect(Collectors.toList()).toString(), qHistorySet.stream().map(qu -> qu.num).toList().toString());
		
		//check complete deck *********************************************************************************COMPLETE DECK
		driver.findElement(new ById("numcards")).click();
		List<String> deck = Arrays.asList(driver.findElement(new ByClassName("modal-content")).getText().split("Deck\n")[1].split("\n")).stream().toList();
		assertEquals("viewed", deck.toString(), qHistorySet.stream().toList().toString());
		driver.findElement(new ById("numcards")).sendKeys(Keys.ESCAPE);
		
		//check reset 10x *********************************************************************************RESET
		for (int resetNum = 0; resetNum < 10; resetNum++) {
	    	System.out.println("reset");
	    	driver.findElement(new ById("resetbutton")).click();
	    	qHistory = new ArrayList<Question>();
	    	qHistorySet = new TreeSet<Question>();
		     
			//flip random amount of cards
	    	int toFlip = (new Random()).nextInt(50);
			for (int flipped = 0; flipped < toFlip; flipped++) {
				//click card or draw button
				driver.findElement(new ById((new Random()).nextInt(2) == 0 ? "question" : "drawbutton")).click();

				//check m/n button counts
				assertEquals("total cards", flipped+1+"", driver.findElement(new ById("numviewed")).getText());
				assertEquals("total cards", "652", driver.findElement(new ById("numcards")).getText());
				
				//store q from card
				Question q = new Question(driver.findElement(new ById("question")));
				qHistory.add(q);
				qHistorySet.add(q);
				
				//check deck history
				driver.findElement(new ById("numviewed")).click();
				List<String> deckHist = Arrays.asList(driver.findElement(new ByClassName("modal-content")).getText().split("Deck history\n")[1].split("\n")).stream().toList();
				assertEquals("viewed", deckHist.toString(), qHistorySet.stream().toList().toString());
				driver.findElement(new ById("numviewed")).sendKeys(Keys.ESCAPE);
			}
		}
		
		driver.close();
    	System.out.println("done");
	}
}
