import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user on 15.02.2016.
 https://wiki.ringcentral.com/pages/viewpage.action?pageId=202584136
 https://wiki.ringcentral.com/pages/viewpage.action?pageId=202584142
 https://wiki.ringcentral.com/display/FT/BT+metering
 */
public class Main {
    public static File specialTypeFromSS;
    public static File specialTypeFromWi;
    public static SpecialDestination[] specialDestinationTypeFromSS;
    public static ArrayList<Number> numberArrayListFromSS = new ArrayList<Number>();
    public static Number[] numbersFromWi;
    public static ArrayList<String> unexpArea;
    public static ArrayList<String> missingArea;

    public static void main(String[] args) throws FileNotFoundException {
        specialTypeFromSS = new File("D:\\10\\8.4.txt");
        specialTypeFromWi = new File("D:\\10\\8.4.xlsx");
        String[] arraySS = getFromSS(specialTypeFromSS);
        String[] arrayWi = getFromWiki(specialTypeFromWi);
        specialDestinationTypeFromSS = new SpecialDestination[arraySS.length];
        for(int i = 0; i < specialDestinationTypeFromSS.length; i++) {
            specialDestinationTypeFromSS[i] = new SpecialDestination(splitData(arraySS[i]));
        }
        for (SpecialDestination sd: specialDestinationTypeFromSS) {
            for (String s: sd.number) {
                numberArrayListFromSS.add(new Number(sd,s));
            }
        }
        numbersFromWi = new Number[arrayWi.length];
        for (int i = 0; i < numbersFromWi.length; i++) {
            numbersFromWi[i] = new Number(splitDataFromWi(arrayWi[i]));
        }


        System.out.println(numberArrayListFromSS.size());
        System.out.println(numbersFromWi.length);
        unexpArea = getUnexpArea();
        missingArea = getMissingArea();

        for (Number ns: numberArrayListFromSS) {
            for (Number nw: numbersFromWi) {
                if(ns.number.equals(nw.number)){
                    ns.equals(nw);
                }
            }
        }


        System.out.println("MissingArea"+missingArea);
        System.out.println("UnexpecArea"+unexpArea);
        System.out.println("-----------------------------------");

    }
    public static ArrayList getUnexpArea() {
        String s = "";
        String w = "";
        ArrayList list = new ArrayList();
        for (int i = 0; i < numberArrayListFromSS.size(); i++) {
            for (int j = 0; j < numbersFromWi.length; j++) {
                s = (numberArrayListFromSS.get(i).number);
                w = (numbersFromWi[j].number);
                if(s.equals(w)) {
                    break;
                }
            }
            if (!w.equals(s)){
                list.add(numberArrayListFromSS.get(i).number);
            }
        }
        return list;

    }
    public static ArrayList getMissingArea() {
        String s = "";
        String w = "";
        ArrayList list = new ArrayList();
        for (int i = 0; i < numbersFromWi.length; i++) {
            for (int j = 0; j < numberArrayListFromSS.size(); j++) {
                s = (numberArrayListFromSS.get(j).number);
                w = (numbersFromWi[i].number);
                if(s.equals(w)) {
                    break;
                }
            }
            if (!s.equals(w)){
                list.add(numbersFromWi[i].number);
            }
        }
        return list;
    }

    public static String[] getFromSS(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                String s;
                int i = 0;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    if (i%2!=0) {
                        sb.append("\n");
                    }
                    i++;
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return  sb.toString().split("\n");
    }
    public static String[] splitData (String str) {
        String[] split = new String[5];
        String[] tempSplit = str.split("\t");
        for (int i = 0; i < tempSplit.length; i++) {
            if (i != 0) {
                split[i] = tempSplit[i].replaceAll("N/A","0.0").replaceAll("p","").replaceAll("\\*","");
            } else {
                split[i] = tempSplit[i];
            }
        }
        return split;
    }
    public static String[] getFromWiki(File file) throws FileNotFoundException {
        FileInputStream fileXL = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(fileXL);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();
            String namber = "0";
            String ppc = "0";
            String ppm0 = "0";
            String ppm1 = "0";
            String ppm2 = "0";
            String ppm = "0";
            String ac = "0";
            while (it.hasNext()) {
                Row row = it.next();

                Cell pref = row.getCell(2);
                pref.setCellType(1);
                String prefix = pref.getStringCellValue();
                Character c = prefix.charAt(1);

                //pref.getStringCellValue().contains("09")
                if (!(c.equals('9')||c.equals('1'))){
                    Cell cellNumber = row.getCell(2);
                    cellNumber.setCellType(1);
                    Cell cellAC = row.getCell(10);
                    cellAC.setCellType(1);
                    Cell cellPPC = row.getCell(11);
                    cellPPC.setCellType(1);
                    Cell cellPPM0 = row.getCell(4);
                    cellPPM0.setCellType(1);
                    Cell cellPPM1 = row.getCell(9);
                    cellPPM1.setCellType(1);
                    Cell cellPPM2 = row.getCell(5);
                    cellPPM2.setCellType(1);
                    namber = cellNumber.getStringCellValue();
                    if (cellPPC.getStringCellValue().length()==0) {
                        ppc = "0.0";
                    } else {
                        ppc = cellPPC.getStringCellValue().replaceAll(",",".");
                    }
                    if (cellPPM0.getStringCellValue().length()==0) {
                        ppm0 = "0.0";
                    } else {
                        ppm0 = cellPPM0.getStringCellValue().replaceAll(",",".");
                    }
                    if (cellPPM1.getStringCellValue().length()==0) {
                        ppm1 = "0.0";
                    } else {
                        ppm1 = cellPPM1.getStringCellValue().replaceAll(",",".");
                    }
                    if (cellPPM2.getStringCellValue().length()==0) {
                        ppm2 = "0.0";
                    } else {
                        ppm2 = cellPPM2.getStringCellValue().replaceAll(",",".");
                    }
                    //  ppm0 = cellPPM0.getStringCellValue().replaceAll(",",".");
                    //  ppm1 = cellPPM1.getStringCellValue().replaceAll(",",".");
                    ac = cellAC.getStringCellValue().replaceAll(",",".");
                    double ppcD = Double.parseDouble(ppc);
                    ppc = String.valueOf(new BigDecimal(ppcD).setScale(3, RoundingMode.HALF_UP).floatValue());
                    double ppmD = Double.parseDouble(ppm0)+Double.parseDouble(ppm1); //+Double.parseDouble(ppm2);
                    ppm = String.valueOf(new BigDecimal(ppmD).setScale(3, RoundingMode.HALF_UP).floatValue());
                    double acD = Double.parseDouble(ac);
                    ac = String.valueOf(new BigDecimal(acD).setScale(3, RoundingMode.HALF_UP).floatValue());
                    sb.append(namber).append(";").append(ac).append(";").append(ppc).append(";").append(ppm).append("\n");
                }

            }

        } catch (IOException e) {
          e.printStackTrace();
        }
        return sb.toString().split("\n");
    }
    public static String[] splitDataFromWi (String str) {
        return str.split(";");
    }
}
