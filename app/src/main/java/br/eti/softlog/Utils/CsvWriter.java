package br.eti.softlog.Utils;


import android.os.Environment;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jason on 2017/12/8.
 */

public class CsvWriter {


    private static String CSV_FILE_PATH;


    private static final String CSV_FILE__NAME_EXTENSION = ".csv";


    private static final String CSV_FILE_CHARSET = "UTF-8";


    private static final byte[] CSV_FILE_BOM = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    private static final String CSV_SEPARATOR = ";";


    public static void toCsvFile(List<String> title, List<List<String>> contents, String fileName, String path) {
        try
        {
            CSV_FILE_PATH = path + "/";
            File file = createFile(fileName);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(CSV_FILE_BOM);

            OutputStreamWriter osw = new OutputStreamWriter(fos, CSV_FILE_CHARSET);
            BufferedWriter bw = new BufferedWriter(osw);

            writeOneLine(bw, title);
            for(List<String> oneLine : contents)
                writeOneLine(bw, oneLine);

            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) { }
        catch (FileNotFoundException e) { }
        catch (IOException e) {
            //Log.d("Erro",e.getMessage());
        }
    }


    private static File createFile(String fileName) throws IOException {
        String nomeBD = Prefs.getString("dataBaseCurrent","");

        //String fileName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date());
        File file = new File(CSV_FILE_PATH + fileName + CSV_FILE__NAME_EXTENSION);
        if (file.exists())
            file.delete();
        file.createNewFile();
        return file;
    }


    private static void writeOneLine(BufferedWriter writer, List<String> oneLineColumns) throws IOException {
        if (null != writer && null != oneLineColumns)
        {
            StringBuffer oneLine = new StringBuffer();
            for (String column : oneLineColumns)
            {
                oneLine.append(CSV_SEPARATOR);
                oneLine.append("\"");
                oneLine.append(null != column ? column.replaceAll("\"", "\"\"") : "");
                oneLine.append("\"");
            }


            writer.write(oneLine.toString().replaceFirst(";", ""));
            writer.newLine();
        }
    }

}