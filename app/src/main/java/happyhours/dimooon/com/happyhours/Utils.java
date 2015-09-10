package happyhours.dimooon.com.happyhours;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getDate(long timeStampStr) {

        try {
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date netDate = (new Date(timeStampStr));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

}
