package fedex.fedexlocationservice;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by WeiJie on 23/09/2017.
 */

public class Generator {

    public String generateID() {
        String ID = "";
        Random randomGenerator = new Random();
        for (int idx = 1; idx <= 20; ++idx){
            int randomInt = randomGenerator.nextInt(10);
            ID = ID + randomInt;
        }
        return ID;
    }

    public int bgColor(String ID) {
        char lastNum =  ID.charAt(ID.length() - 1);
        if (lastNum == '0') {return Color.BLACK;}
        else if (lastNum == '1') {return Color.WHITE;}
        else if (lastNum == '2') {return Color.RED;}
        else if (lastNum == '3') {return Color.BLUE;}
        else if (lastNum == '4') {return Color.YELLOW;}
        else if (lastNum == '5') {return Color.CYAN;}
        else if (lastNum == '6') {return Color.GREEN;}
        else if (lastNum == '7') {return Color.LTGRAY;}
        else if (lastNum == '8') {return Color.GRAY;}
        else {return Color.MAGENTA;}
    }

    public int textColor(int bgColor) {
        if (bgColor == Color.BLACK) {return Color.WHITE;}
        else if (bgColor == Color.WHITE) {return Color.BLACK;}
        else if (bgColor == Color.RED) {return Color.WHITE;}
        else if (bgColor == Color.BLUE) {return Color.WHITE;}
        else if (bgColor == Color.YELLOW) {return Color.BLACK;}
        else if (bgColor == Color.CYAN) {return Color.BLACK;}
        else if (bgColor == Color.GREEN) {return Color.BLACK;}
        else if (bgColor == Color.LTGRAY) {return Color.BLACK;}
        else if (bgColor == Color.GRAY) {return Color.WHITE;}
        else {return Color.WHITE;}
    }
}
