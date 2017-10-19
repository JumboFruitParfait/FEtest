package fedex.fedexlocationservice;

import android.graphics.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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

    public void beaconID(){
        try {
            URL url = new URL("https://edit.meridianapps.com /api/locations/5427610480279552/beacons");
            URLConnection urlc = url.openConnection();
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            //get result
            BufferedReader br = new BufferedReader(new InputStreamReader(urlc
                    .getInputStream()));
            String l;
            while ((l = br.readLine()) != null) {
                System.out.println(l);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
