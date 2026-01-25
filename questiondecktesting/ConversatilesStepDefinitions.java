package questiondecktesting;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ConversatilesStepDefinitions {
	ConversatilesPOM conversatilesPage;

	final int totQs = 731;
	public int getTotQs() {
		return totQs;
	}
	
	public ConversatilesStepDefinitions() {
		conversatilesPage = new ConversatilesPOM();
		
		conversatilesPage.getDriver().get("https://coderystal.github.io/conversatiles/");
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

	public void clickReset() {
    	System.out.println("reset");
    	conversatilesPage.getResetButton().click();		
	}
	
	
	
}
