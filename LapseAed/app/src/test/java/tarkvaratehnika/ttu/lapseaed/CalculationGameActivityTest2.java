package tarkvaratehnika.ttu.lapseaed;

import org.junit.Assert;
import org.junit.Test;

import tarkvaratehnika.ttu.lapseaed.calculationGame.CalculationGameActivityEasy;


public class CalculationGameActivityTest2 {
    @Test
    public void testCalc() {
        int score = CalculationGameActivityEasy.scoreTotal;
        Assert.assertEquals(0,score);

    }

}
