package tarkvaratehnika.ttu.lapseaed;

import org.junit.Assert;
import org.junit.Test;

import tarkvaratehnika.ttu.lapseaed.calculationGame.CalculationGameActivity;


public class CalculationGameActivityTest {

    @Test
    public void testIfAnswerIsCorrect() {
        int score = CalculationGameActivity.scoreTotal;
        if(CalculationGameActivity.isRight){
            Assert.assertTrue(score > 0);
        } else {
           Assert.assertTrue(score == 0);
        }

    }
}
