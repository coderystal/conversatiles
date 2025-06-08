# question deck test suite
legend:
* _UI text_
* **UI number**
* testing code / structures
## Java dependencies
* Selenium `org.openqa.selenium`
* JUnit `org.junit`
* WebDriverManager `import io.github.bonigarcia.wdm.WebDriverManager`
## Features tested
### FlipAndReset
* click card or _draw_ * 652
  * increases **viewed** by 1, **total** unchanged
  * added to _history_
* flipped through deck validations
  * all qnums 1-652 represented
  * seen questions match _deck_
* test * 10
  * _reset_ (first time after flipped through entire deck)
  * click card or _draw_ * m
    * check first 2 validations from above click card or _draw_
### Undo
* test * 10
  * click card or _draw_ * mf
    * maintaining total historyset and count and trailing previous card
    * increases **viewed** by 1
  * click _undo_ * mu
    * _history_ unchanged
    * first undo: shown card is previous card
    * all undos
      * duplicate of some card in historyset (adding to does not change size of historyset)
      * **viewed** unchanged, **total** unchanged
### DisabledButtons
* test * 10
  * reset state
    * **viewed** and **total** are enabled
    * _reset_ and back are disabled
    * _draw_ is enabled
  * click card or _draw_ * mf (first round, mf = all cards)
    * **viewed**, **total**, and _reset_ are enabled
    * _back_ is enabled if at least 2 cards have been flipped
    * _draw_ is enabled if not all cards have been flipped
  * click reset
### Backable
* test * 10
  * click card or _draw_ * mf (first round, mf = all cards)
    * +1 backable for each, starting from 1 for 2nd card
    * _back_ button is enabled when backable > 0
  * click _back_ * (up to mb, or when backable is 0, whichever is first)
    * -1 backable for each
    * _back_ button is enabled when backable > 0
  * click _back_ * backable
  * _back_ button is disabled
### FindCardNumber
* store _deck_
* test (*50 + *652 + *50 - includes revisiting cards, before and after the whole deck has been seen)
  * clear input (ctrl+a delete or baskpace *3)
  * type qnum
  * send enter or tab
  * increases **viewed** if applicable, **total** unchanged
### UNTESTED
* specific order of undo after first - low value
* use deck
