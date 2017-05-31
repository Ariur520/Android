package tarkvaratehnika.ttu.lapseaed;

import org.junit.Assert;
import org.junit.Test;

import tarkvaratehnika.ttu.lapseaed.calculationGame.CalculationGameActivityEasy;


public class CalculationGameActivityEasyTest {

    @Test
    public void testIfAnswerIsCorrect() {
        int score = CalculationGameActivityEasy.scoreTotal;
        if(CalculationGameActivityEasy.isRight){
            Assert.assertTrue(score > 0);
        } else {
           Assert.assertTrue(score == 0);
        }

    }
}
