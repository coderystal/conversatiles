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
		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();
		
		//check undo 10x *********************************************************************************UNDO
		int totFlipped = 0;
		int backable = 0;
		for (int undoRounds = 0; undoRounds < 20; undoRounds++) {
			System.out.println("\nundo round " + undoRounds);
			//flip random amount of cards
	    	int toFlip = (new Random()).nextInt(100);
			System.out.println("to flip " + toFlip);
			for (int flipped = 0; flipped < toFlip && totFlipped < conversatileSteps.getTotQs(); flipped++) {
				//click card or draw button
				conversatileSteps.clickCardOrDraw();
				totFlipped++;

				backable = totFlipped == 1 ? 0 : backable+1;
				
				//check m/n button counts
				conversatileSteps.validateNumViewedCards(totFlipped);
				
				conversatileSteps.updateHistorySet();
			}
			

	    	int toUndo = (new Random()).nextInt(totFlipped + 1);
	    	conversatileSteps.validateNumViewedCards(totFlipped);
			System.out.println("to undo " + toUndo);
			System.out.println(backable);
			for (int undone = 0; undone < toUndo; undone++) {
				//click undo
				conversatileSteps.clickBack();
				backable--;

				if (backable < 1) {
					backable = 0;
					if (undone > 20) {
						System.out.println("backable" + backable + " undone " + undone);
						conversatileSteps.validateBackButtonEnabledStatus(false);
						break;
					}
				} else {
					conversatileSteps.validateBackButtonEnabledStatus(true);
				}

				
				
				//check history
				conversatileSteps.clickNumViewedAndValidateHistory();
				
				//else - more complicated check, will people actually notice? not worth. ie not checking more prev undos, or disabled
	
				//after undo - card is added to qHistorySet, qHistorySet should be unchanged (duplicate), size = numviewed = totFlipped
				conversatileSteps.validateHistorySetSize(totFlipped);
				conversatileSteps.updateHistorySet();
				conversatileSteps.validateHistorySetSize(totFlipped);
				conversatileSteps.validateNumViewedCards(totFlipped);
				
				//tot check
				conversatileSteps.validateNumTotalCards();
			}
			System.out.println(backable);	
		}

		conversatileSteps.close();
	}
}
