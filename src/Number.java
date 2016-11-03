/**
 * Created by user on 16.02.2016.
 */
public class Number {
    String name;
    String accessCharge;
    String pricePerConnection;
    String pricePerMinute;
    String number;

    public Number (String[] str) {
        number = str[0].replaceAll(" ","");
        accessCharge = str[1];
        pricePerConnection = str[2];
        pricePerMinute = str[3];
    }
    public Number (SpecialDestination sd, String s) {
        name = sd.name;
        accessCharge = sd.accessCharge;
        pricePerConnection = sd.pricePerConnection;
        pricePerMinute = sd.pricePerMinute;
        number = s;
    }
    public boolean equals (Object obj) {
        if (obj == null) {
            System.out.println("obg = null");
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        Number other = (Number) obj;
        if ((!accessCharge.equals(other.accessCharge))||(!pricePerMinute.equals(other.pricePerMinute))|| (!pricePerConnection.equals(other.pricePerConnection))) {
            System.out.println("accessCharge " + accessCharge + " != " + other.accessCharge);
            System.out.println("pricePerConnection "+ pricePerConnection + " != " + other.pricePerConnection);
            System.out.println("pricePerMinute "+ pricePerMinute + " != " + other.pricePerMinute);
            System.out.println("name " + name+" "+other.name);
            System.out.println("number " + number + " " + other.number);
            System.out.println("------------------------------------------------------------");
            return false;
        }
        return true;
    }
}
