package tarkvaratehnika.ttu.lapseaed;

import org.junit.Test;

import tarkvaratehnika.ttu.lapseaed.picGame.PicGameActivity;

import static junit.framework.Assert.assertNotNull;


public class PicGameActivityTest {
    @Test
    public void testIfArrayIsFilled() {

        int[] arr = PicGameActivity.pics;
        assertNotNull("Array is filled", arr);

    }
}
