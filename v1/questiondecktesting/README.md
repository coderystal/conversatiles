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
### QuestionDeckFlipAndReset
* click card or _draw_ * 652
  * increases **viewed** by 1, **total** unchanged
  * added to _history_
  * flips through entire deck (all qnums 1-652)
* reset * 10
  * after _reset_ (first time after flipped through entire deck)
  * click card or _draw_ * m
    * check first 2 validations from above click card or _draw_
### QuestionDeckUndo
* undo * 10
  * after click card or _draw_ * mf
    * maintaining total historyset and count and trailing previous card
    * increases **viewed** by 1
  * click _undo_ * mu
    * _history_ unchanged
    * first undo: shown card is previous card
    * all undos
      * duplicate of some card in historyset (adding to does not change size of historyset)
      * **viewed** unchanged, **total** unchanged
### UNTESTED
* enabled/disabled states - reset, undo, draw
* specific order of undo after first - low value
* find card #
* use deck
