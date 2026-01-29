package questiondecktesting;

import org.openqa.selenium.WebElement;

public class Question implements Comparable<Question>{
	int num;
	String text;
	public Question(WebElement card) {
		String[] qDtls = card.getText().split("\n");
		num = Integer.parseInt(qDtls[0].substring(1));
		text = qDtls[1];
	}
	public Question(int num, String text) {
		this.num = num;
		this.text = text;
	}
	@Override
	public int compareTo(Question o) {
		return this.num - o.num; 
	}
	@Override
	public String toString() {
		return num + " " + text;
	}
	@Override
	public boolean equals(Object other) {
		return this.num == ((Question)other).num && this.text.equals(((Question)other).text);
	}
}
