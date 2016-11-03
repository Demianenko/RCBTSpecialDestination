/**
 * Created by user on 15.02.2016.
 */
public class SpecialDestination {
    String name;
    String accessCharge;
    String pricePerConnection;
    String pricePerMinute;
    String[] number;

    public SpecialDestination(String[] str) {
        name = str[0];
        pricePerMinute = str[1];
        pricePerConnection = str[2];
        accessCharge = str[3];
        number = str[4].replaceAll(" ","").split(",");

    }

}
