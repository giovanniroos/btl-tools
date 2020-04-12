package za.co.giovanniroos.btl.btltools.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import za.co.giovanniroos.btl.btltools.Column;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ExcelTools {

    public static void main(String[] args) {

        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet1 = wb.createSheet("Transactions");
            //
            try {
                //HEADERS
                Row rowHeaders = sheet1.createRow(0);
                rowHeaders.createCell(Column.ID.getWriteIndex()).setCellValue(Column.ID.getName()); //1
                rowHeaders.createCell(Column.DATE.getWriteIndex()).setCellValue(Column.DATE.getName());//2
                rowHeaders.createCell(Column.ACCOUNT.getWriteIndex()).setCellValue(Column.ACCOUNT.getName());//3
                rowHeaders.createCell(Column.TX_TYPE.getWriteIndex()).setCellValue(Column.TX_TYPE.getName());//4
                rowHeaders.createCell(Column.AMOUNT.getWriteIndex()).setCellValue(Column.AMOUNT.getName());//5
                rowHeaders.createCell(Column.AMOUNT_UNIT.getWriteIndex()).setCellValue(Column.AMOUNT_UNIT.getName());//6
                rowHeaders.createCell(Column.FEE.getWriteIndex()).setCellValue(Column.FEE.getName());//7
                rowHeaders.createCell(Column.FEE_UNIT.getWriteIndex()).setCellValue(Column.FEE_UNIT.getName());//8
                rowHeaders.createCell(Column.STATUS.getWriteIndex()).setCellValue(Column.STATUS.getName());//9
                rowHeaders.createCell(Column.BALANCE_BTL.getWriteIndex()).setCellValue(Column.BALANCE_BTL.getName());//10
                rowHeaders.createCell(Column.BALANCE_BTC.getWriteIndex()).setCellValue(Column.BALANCE_BTC.getName());//11
                rowHeaders.createCell(Column.TX_INFO.getWriteIndex()).setCellValue(Column.TX_INFO.getName());//12
                rowHeaders.createCell(Column.PLAN_NAME.getWriteIndex()).setCellValue(Column.PLAN_NAME.getName());//13
                rowHeaders.createCell(Column.PARTNER_LEVEL.getWriteIndex()).setCellValue(Column.PARTNER_LEVEL.getName());//14
                rowHeaders.createCell(Column.PARTNER_ID.getWriteIndex()).setCellValue(Column.PARTNER_ID.getName());//15
                rowHeaders.createCell(Column.BONUS_PERS.getWriteIndex()).setCellValue(Column.BONUS_PERS.getName());//16
                rowHeaders.createCell(Column.WEAK_LEG_VOLUME.getWriteIndex()).setCellValue(Column.WEAK_LEG_VOLUME.getName());//17
                rowHeaders.createCell(Column.PLAN_ID.getWriteIndex()).setCellValue(Column.PLAN_ID.getName());//18
                rowHeaders.createCell(Column.ADDRESS.getWriteIndex()).setCellValue(Column.ADDRESS.getName());//19

                //the file to be opened for reading
                FileInputStream fis = new FileInputStream("C:\\dev\\source\\private\\btl-tools\\src\\main\\resources\\transactions.txt");
                Scanner sc = new Scanner(fis);    //file to be scanned
                //returns true if there is another line to read
                int rowIndex = 0;
                int colIndex = 1;
                Row row = null;
                String type = "";
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.isEmpty()) {
                        continue;
                    }
                    //ID
                    if (line.startsWith("1000817")) {
                        rowIndex++;
                        row = sheet1.createRow(rowIndex);
                        row.createCell(Column.ID.getWriteIndex()).setCellValue(line);
                        colIndex++;
                        continue;
                    }
                    //DATE
                    if (colIndex == Column.DATE.getReadIndex()) { //1
                        //11/04/2020 15:39:41
                        Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(line);
//                        SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                        row.createCell(Column.DATE.getWriteIndex()).setCellValue(date);
                        colIndex++;
//                        colIndex=0;
                        continue;
                    }
                    //ACCOUNT
                    if (colIndex == Column.ACCOUNT.getReadIndex()) { //2
                        row.createCell(Column.ACCOUNT.getWriteIndex()).setCellValue(line);
                        colIndex++;
//                        colIndex=0;
                        continue;
                    }
                    //TYPE
                    if (colIndex == Column.TX_TYPE.getReadIndex()) { //3
                        type = line;
                        row.createCell(Column.TX_TYPE.getWriteIndex()).setCellValue(line);
                        colIndex++;
//                        colIndex=0;
                        continue;
                    }
                    //AMOUNT
                    if (colIndex == Column.AMOUNT.getReadIndex()) {  //4
                        //29.052000 BTL
                        String[] tokens = line.split(" ");
                        row.createCell(Column.AMOUNT.getWriteIndex()).setCellValue(Double.parseDouble(tokens[0]));
                        row.createCell(Column.AMOUNT_UNIT.getWriteIndex()).setCellValue(tokens[1]);
                        colIndex++;
                        colIndex++;
//                        colIndex=0;
                        continue;
                    }
                    //%
                    if (colIndex == Column.BONUS_PERS.getReadIndex()) { //6
                        //(3%)
                        if (line.contains("%")) {
                            String[] chunk = line.split("%");
                            String pers = chunk[0].substring(1);
                            row.createCell(Column.BONUS_PERS.getWriteIndex()).setCellValue(Double.parseDouble(pers));
                            colIndex++;
                            continue;
                        } else if (type.equals("Receipt") || type.equals("Deposit") || type.equals("Transfer") ||
                                type.equals("Exchange") || type.equals("Withdrawal") || type.equals("Funding")) {
                            colIndex++;
                        }
                    }
                    //FEE
                    if (colIndex == Column.FEE.getReadIndex()) {
                        //0 BTL
                        String[] chunk = line.split(" ");
                        row.createCell(Column.FEE.getWriteIndex()).setCellValue(Double.parseDouble(chunk[0]));
                        row.createCell(Column.FEE_UNIT.getWriteIndex()).setCellValue(chunk[1]);
                        colIndex++;
//                        colIndex=0;
                        continue;
                    }
                    //STATUS
                    if (colIndex == Column.STATUS.getReadIndex()) {
                        //0 BTL
                        row.createCell(Column.STATUS.getWriteIndex()).setCellValue(line);
                        colIndex++;
//                        colIndex=0;
                        continue;
                    }
                    //BALANCE_BTL
                    if (colIndex == Column.BALANCE_BTL.getReadIndex()) {
                        //17112.226619 BTL
                        String[] chunk = line.split(" ");
                        if (chunk[1].equals("BTL")) {
                            row.createCell(Column.BALANCE_BTL.getWriteIndex()).setCellValue(Double.parseDouble(chunk[0]));
                        } else if (chunk[1].equals("BTC")) {
                            row.createCell(Column.BALANCE_BTC.getWriteIndex()).setCellValue(Double.parseDouble(chunk[0]));
                        }
                        colIndex++;
                        colIndex++;
//                        colIndex=0;
                        continue;
                    }
//                    //BALANCE_BTL
//                    if (colIndex == Column.BALANCE_BTC.getReadIndex()) {
//                        //17112.226619 BTL
//                        String[] chunk = line.split(" ");
//                        row.createCell(Column.BALANCE_BTL.getWriteIndex()).setCellValue(Double.parseDouble(chunk[0]));
//                        colIndex++;
////                        colIndex=0;
//                        continue;
//                    }
//                    //NOTES
                    if (colIndex == Column.TX_INFO.getReadIndex()) {
                        if (line.startsWith("Direct Bonus")) {
                            // Direct Bonus from the Limited plan
                            // purchase by a Level 2 partner:
                            // B0013277
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Direct Bonus");
                            String[] chunks = line.split(" ");
                            row.createCell(Column.PLAN_NAME.getWriteIndex()).setCellValue(chunks[4] + " Plan");
                            line = sc.nextLine();
                            chunks = line.split(" ");
                            row.createCell(Column.PARTNER_LEVEL.getWriteIndex()).setCellValue(Integer.parseInt(chunks[4]));
                            row.createCell(Column.PARTNER_ID.getWriteIndex()).setCellValue(sc.nextLine());
                        } else if (line.contains("Binary Bonus")) {
                            //8% Binary Bonus of the weak leg
                            //volume: $ 698.000
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Binary Bonus");
                            String[] chunks = line.split("%");
                            row.createCell(Column.BONUS_PERS.getWriteIndex()).setCellValue(Double.parseDouble(chunks[0]));
                            line = sc.nextLine();
                            chunks = line.split(" ");
                            row.createCell(Column.WEAK_LEG_VOLUME.getWriteIndex()).setCellValue(Double.parseDouble(chunks[2]));
                        } else if (line.contains("Passive Bonus")) {
                            //3% Passive Bonus of your Level 2
                            //partners’ profit
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Passive Bonus");
                            String[] chunks = line.split(" ");
                            row.createCell(Column.PARTNER_LEVEL.getWriteIndex()).setCellValue(Integer.parseInt(chunks[6]));
                            String[] pers = chunks[0].split("%");
                            row.createCell(Column.BONUS_PERS.getWriteIndex()).setCellValue(Double.parseDouble(pers[0]));

                        } else if (line.startsWith("Profit")) {
                            //Profit from the Medium plan
                            //#9449830563
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Profit");
                            String[] chunks = line.split(" ");
                            row.createCell(Column.PLAN_NAME.getWriteIndex()).setCellValue(chunks[3] + " Plan");
                            line = sc.nextLine();
                            row.createCell(Column.PLAN_ID.getWriteIndex()).setCellValue(line);
                        } else if (line.startsWith("Receiving funds")) {
                            //Receiving funds from user:
                            //B0000999 (Janis Lacis)
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Receiving funds");
                            String[] chunks = sc.nextLine().split(" ");
                            row.createCell(Column.PARTNER_ID.getWriteIndex()).setCellValue(chunks[0]);
                        } else if (line.startsWith("Purchasing")) {
                            //Purchasing the Medium plan
                            //#9449830563
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Purchasing");
                            String[] chunks = line.split(" ");
                            row.createCell(Column.PLAN_NAME.getWriteIndex()).setCellValue(chunks[2] + " Plan");
                            line = sc.nextLine();
                            row.createCell(Column.PLAN_ID.getWriteIndex()).setCellValue(line);
                        } else if (line.startsWith("Transferring")) {
                            //Transferring funds to user:
                            //B0010821
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Transferring");
                            line = sc.nextLine();
                            row.createCell(Column.PARTNER_ID.getWriteIndex()).setCellValue(line);
                        } else if (line.startsWith("Exchange")) {
                            if (line.contains("BTC")) {
                                //Exchange of funds; 0.05233199 BTC
                                //payment received from BTL Token
                                line = sc.nextLine();
                                row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue(line);
//                                String[] chunks = line.split(" ");
//                                row.createCell(colIndex + 8).setCellValue(chunks[3]);
                            } else if (line.contains("BTL")) {
                                //Exchange of funds; 1 410.750000 BTL
                                //payment sent to Bitcoin BTC
                                line = sc.nextLine();
                                row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue(line);
//                                String[] chunks = line.split(" ");
//                                row.createCell(colIndex + 8).setCellValue(chunks[4]);
                            }
                        } else if (line.startsWith("Excess profit")) {
                            //Excess profit from the Limited plan
                            //#500684000
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Excess profit");
                            String[] chunks = line.split(" ");
                            row.createCell(Column.PLAN_NAME.getWriteIndex()).setCellValue(chunks[4] + " Plan");
                            line = sc.nextLine();
                            row.createCell(Column.PLAN_ID.getWriteIndex()).setCellValue(line);
                        } else if (line.contains("Withdrawal")) {
                            //Bitcoin BTC Withdrawal:
                            //1mTiPGwM4ZwUtg8PLhbEJhEzj8pmF3kXp
                            //OR
                            //Bitcoin BTC Withdrawal:
                            //Bitcoin
//                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Withdrawal");
//                            String[] chunks = line.split(" ");
//                            row.createCell(colIndex + 8).setCellValue(chunks[1]);
                            if (!line.equals("Bitcoin")) {
                                line = sc.nextLine();
                                row.createCell(Column.ADDRESS.getWriteIndex()).setCellValue(line);
                            }
                        } else if (line.startsWith("Personal Account Funding")) {
                            //Personal Account Funding
                            row.createCell(Column.TX_INFO.getWriteIndex()).setCellValue("Personal Account Funding");
                        }
                        colIndex = 1;
                        continue;
                    }
                }
                sc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            //
            String path = "C:\\dev\\source\\private\\btl-tools\\src\\main\\resources\\workbook.xlsx";
            try (OutputStream fileOut = new FileOutputStream(path)) {
                wb.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
