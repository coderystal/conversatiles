package questiondecktesting;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class QuestionDeckFindCardNumber {
	private static WebDriver driver;
	private static List<Question> deck;
	private static Set<Integer> seenNums;
	
	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(10));
		
		
		driver.get("https://htmlpreview.github.io/?https://github.com/coderystal/questiondeck/blob/main/v1/index.html");
		System.out.println("got");
		driver.manage().window().maximize();
		System.out.println("maximized");
		wait.until(ExpectedConditions.presenceOfElementLocated(new ById("question")));
		
		//store complete deck *********************************************************************************DECK COMPLETE
		driver.findElement(new ById("numcards")).click();
		deck = Arrays.asList(driver.findElement(new ByClassName("modal-content")).getText().split("Deck\n")[1].split("\n")).stream()
				.map(str -> str.split(" - ", 2)).map(strarr -> new Question(Integer.parseInt(strarr[0]), strarr[1])).toList();
		driver.findElement(new ById("numcards")).sendKeys(Keys.ESCAPE);
		
		//find card # *****************************************************************************************FIND CARD #
        List<Integer> allQNums = IntStream.rangeClosed(1, 652).boxed().collect(Collectors.toList());
        Collections.shuffle(allQNums);
        seenNums = new HashSet<Integer>();

		for (int i = 0; i < 50; i++)
			findCardNumber((new Random()).nextInt(652) + 1);
		System.out.println("found 50");
		
		for (int qnum: allQNums)
			findCardNumber(qnum);
		System.out.println("found 652");

		for (int i = 0; i < 50; i++)
			findCardNumber((new Random()).nextInt(652) + 1);
		System.out.println("found 50");

		driver.close();
    	System.out.println("done");
	}
	
	private static void findCardNumber(int qnum) {
		driver.findElement(new ById("enteredcardnum")).sendKeys((new Random()).nextInt(2) == 0 ? (Keys.chord(Keys.CONTROL, "a") + Keys.DELETE) : 
			("" + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE));
		
		driver.findElement(new ById("enteredcardnum")).sendKeys(qnum + "");
		driver.findElement(new ById("enteredcardnum")).sendKeys((new Random()).nextInt(2) == 0 ? Keys.ENTER : Keys.TAB);
		assertEquals("question", deck.get(qnum-1), new Question(driver.findElement(new ById("question"))));

		seenNums.add(qnum);
		assertEquals("total cards", seenNums.size()+"", driver.findElement(new ById("numviewed")).getText());
		assertEquals("total cards", "652", driver.findElement(new ById("numcards")).getText());
	}
}
