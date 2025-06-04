package questiondecktesting;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class QuestionDeckUndo {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(10));
		
		
		driver.get("https://htmlpreview.github.io/?https://github.com/coderystal/questiondeck/blob/main/v1/index.html");
		System.out.println("got");
		driver.manage().window().maximize();
		System.out.println("maximized");
		wait.until(ExpectedConditions.presenceOfElementLocated(new ById("question")));

		Set<Question> qHistorySet = new TreeSet<Question>();
		
		//check undo 10x *********************************************************************************UNDO
		Question qTrail = null;
		Question qCur = null;
		int totFlipped = 0;
		for (int undoRounds = 0; undoRounds < 10; undoRounds++) {		     
			//flip random amount of cards
	    	int toFlip = (new Random()).nextInt(50);
			for (int flipped = 0; flipped < toFlip; flipped++) {
				//click card or draw button
				driver.findElement(new ById((new Random()).nextInt(2) == 0 ? "question" : "drawbutton")).click();
				totFlipped++;
	
				//check m/n button counts
				assertEquals("total cards", totFlipped+"", driver.findElement(new ById("numviewed")).getText());
				
				//update qTrail from card
				qTrail = qCur;
				qCur = new Question(driver.findElement(new ById("question")));
				qHistorySet.add(qCur);
			}
			

	    	int toUndo = (new Random()).nextInt(totFlipped + 1);
			for (int undone = 0; undone < toUndo; undone++) {
				//click undo
				driver.findElement(new ById("undobtn")).click();
				Question curQ = new Question(driver.findElement(new ById("question")));
				
				//check history
				driver.findElement(new ById("numviewed")).click();
				List<String> deckHist = Arrays.asList(driver.findElement(new ByClassName("modal-content")).getText().split("Deck history\n")[1].split("\n")).stream().toList();
				assertEquals("viewed", deckHist.toString(), qHistorySet.stream().toList().toString());
				driver.findElement(new ById("numviewed")).sendKeys(Keys.ESCAPE);
				
				if (undone == 0) {
					assertEquals("first undo", qTrail, curQ);
				}
				//else - more complicated check, will people actually notice? not worth. ie not checking more prev undos, or disabled
	
				//after undo - card is added to qHistorySet, qHistorySet should be unchanged (duplicate), size = numviewed = totFlipped
				assertEquals(totFlipped, qHistorySet.size());
				qHistorySet.add(curQ);
				assertEquals(totFlipped, qHistorySet.size());
				assertEquals("total cards", totFlipped+"", driver.findElement(new ById("numviewed")).getText());
				
				//tot check
				assertEquals("total cards", "652", driver.findElement(new ById("numcards")).getText());
			}			
		}
		
		driver.close();
		System.out.println("done");
	}
}
