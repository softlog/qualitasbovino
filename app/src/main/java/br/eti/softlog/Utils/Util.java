package br.eti.softlog.Utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrador on 2018/03/03.
 */

public final class Util {

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public static String getDateFormat(Date data) {
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
        String dataExtenso = formatador.format(data);
        return dataExtenso;
    }

    public String getDateFormatDMY(String data, String pattern) {
        Date dateData;
        String dataFormatada;

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            dateData = formato.parse(data);
            formato.applyPattern(pattern);
            dataFormatada = formato.format(dateData);
        } catch (ParseException e) {
            dataFormatada = data;
        }

        return dataFormatada;

    }
    public static String getDateFormatDMY(String data) {


        Date dateData;
        String dataFormatada;

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            dateData = formato.parse(data);
            formato.applyPattern("dd/MM/yyyy");
            dataFormatada = formato.format(dateData);
        } catch (ParseException e) {
            dataFormatada = data;
        }

        return dataFormatada;
    }

    public static  String getDateFormatYMD(Date data) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formato.applyPattern("yyyy-MM-dd");

        String dataFormatada = formato.format(data);
        return dataFormatada;
    }


    public static  String getDateFormatArquivo(Date data) {

        SimpleDateFormat formato = new SimpleDateFormat();
        formato.applyPattern("yyyyMMdd");

        String dataFormatada = formato.format(data);
        return dataFormatada;
    }


    public static  String getDateTimeFormatYMD(Date data) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formato.applyPattern("yyyy-MM-dd HH:mm:ss");

        String dataFormatada = formato.format(data);
        return dataFormatada;
    }

    /** Returns the consumer friendly device name */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static String preencherComZerosAEsquerda(String texto, int tamanho)
    {
        if (texto.length() >= tamanho) {
            return texto;
        }
        StringBuffer sb = new StringBuffer(texto);
        while (sb.length() < tamanho) {
            sb.insert(0, '0');
        }

        return sb.toString();
    }


    public static String removerNaoDigitos(String entrada)
    {
        if (entrada == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < entrada.length(); i++)
        {
            char l = entrada.charAt(i);
            if ((l >= '0') && (l <= '9')) {
                sb.append(l);
            }
        }
        return sb.toString();
    }

    public static String ltrim(String entrada, char caracter)
    {
        if (entrada == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < entrada.length(); i++)
        {
            char l = entrada.charAt(i);

            if ((l >= '0') && (l <= '9')) {
                sb.append(l);
            }
        }
        return sb.toString();
    }

    public static int getThemeAccentColor(Context context) {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        } else {
            //Get colorAccent defined for AppCompat
            colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    public static int getThemePrimaryColor(Context context) {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorPrimary;
        } else {
            //Get colorAccent defined for AppCompat
            colorAttr = context.getResources().getIdentifier("colorPrimary", "attr", context.getPackageName());
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    public static int getThemePrimaryDarkColor(Context context) {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorPrimaryDark;
        } else {
            //Get colorAccent defined for AppCompat
            colorAttr = context.getResources().getIdentifier("colorPrimaryDark", "attr", context.getPackageName());
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    public String trataIdf(String idf){
        String idfFlag;
        String idfAlfa;
        String idfNum;
        int tamanho;

        idfFlag = "";
        idfAlfa = "";
        idfNum = "";

        String idfTratado;

        idfTratado = "";

        if (idf == null)
            return "";

        if (idf.isEmpty())
            return "";

        for (int i=0;i<idf.length();i++){
            char chr = idf.charAt(i);

            int temp;
            temp = 0;
            switch (chr){
                case 42:
                    temp = 1;
                case 45:
                    temp = 1;
                case 64:
                    temp = 1;
                default:
                    temp = 0;
            }
            if (temp==1)
                idfFlag = idfFlag + idf.substring(i,i+1);

            if ( (chr>=65 && chr<=90) || (chr>=97 && chr <=122) )
                temp = 1;
            else
                temp = 0;

            if (temp==1)
                idfAlfa = idfAlfa + idf.substring(i,i+1);

            if (chr>=48 && chr<=57)
                temp = 1;
            else
                temp = 0;

            if (temp==1)
                idfNum = idfNum + idf.substring(i,i+1);

        }

        if (idfFlag.length()>2)
            idfFlag = idfFlag.substring(idfFlag.length()-2,idfFlag.length());

        if (idfAlfa.length()>6)
            idfAlfa = idfAlfa.substring(idfAlfa.length()-6,6);

        if (idfNum.length()>8)
            idfNum = idfNum.substring(0,8);

        idfTratado = idfAlfa + " " +  Integer.valueOf(idfNum).toString();
        //idfNum.replaceFirst("^0+(?!$)", "");
        //idfTratado = String.format("%1$" + 6 + "s", idfAlfa)
          //          + String.format("%1$" + 8 + "s", idfNum).replace(" ", "0");

        //idfTratado = idfAlfa
        //        + String.format("%1$" + 8 + "s", idfNum).replace(" ", "0");

        return idfTratado.trim();

    }

    public String trataIdf_2(String idf){
        String idfFlag;
        String idfAlfa;
        String idfNum;
        int tamanho;

        idfFlag = "";
        idfAlfa = "";
        idfNum = "";

        String idfTratado;

        idfTratado = "";

        if (idf == null)
            return "";

        if (idf.isEmpty())
            return "";

        for (int i=0;i<idf.length();i++){
            char chr = idf.charAt(i);

            int temp;
            temp = 0;
            switch (chr){
                case 42:
                    temp = 1;
                case 45:
                    temp = 1;
                case 64:
                    temp = 1;
                default:
                    temp = 0;
            }
            if (temp==1)
                idfFlag = idfFlag + idf.substring(i,i+1);

            if ( (chr>=65 && chr<=90) || (chr>=97 && chr <=122) )
                temp = 1;
            else
                temp = 0;

            if (temp==1)
                idfAlfa = idfAlfa + idf.substring(i,i+1);

            if (chr>=48 && chr<=57)
                temp = 1;
            else
                temp = 0;

            if (temp==1)
                idfNum = idfNum + idf.substring(i,i+1);

        }

        if (idfFlag.length()>2)
            idfFlag = idfFlag.substring(idfFlag.length()-2,idfFlag.length());

        if (idfAlfa.length()>6)
            idfAlfa = idfAlfa.substring(idfAlfa.length()-6,6);

        if (idfNum.length()>8)
            idfNum = idfNum.substring(0,8);

        idfTratado = idfAlfa + " " +  Integer.valueOf(idfNum).toString();
        //idfNum.replaceFirst("^0+(?!$)", "");
        //idfTratado = String.format("%1$" + 6 + "s", idfAlfa)
        //          + String.format("%1$" + 8 + "s", idfNum).replace(" ", "0");

        //idfTratado = idfAlfa
        //        + String.format("%1$" + 8 + "s", idfNum).replace(" ", "0");

        return idfTratado.trim();

    }


    public String getPrefixoIdf(String idf){
        String idfFlag;
        String idfAlfa;
        String idfNum;
        int tamanho;

        idfFlag = "";
        idfAlfa = "";
        idfNum = "";

        String idfTratado;

        idfTratado = "";

        if (idf == null)
            return "";

        if (idf.isEmpty())
            return "";

        for (int i=0;i<idf.length();i++){
            char chr = idf.charAt(i);

            int temp;
            temp = 0;
            switch (chr){
                case 42:
                    temp = 1;
                case 45:
                    temp = 1;
                case 64:
                    temp = 1;
                default:
                    temp = 0;
            }
            if (temp==1)
                idfFlag = idfFlag + idf.substring(i,i+1);

            if ( (chr>=65 && chr<=90) || (chr>=97 && chr <=122) )
                temp = 1;
            else
                temp = 0;

            if (temp==1)
                idfAlfa = idfAlfa + idf.substring(i,i+1);

            if (chr>=48 && chr<=57)
                temp = 1;
            else
                temp = 0;

            if (temp==1)
                idfNum = idfNum + idf.substring(i,i+1);

        }
        if (idfAlfa.length()>6)
            idfAlfa = idfAlfa.substring(idfAlfa.length()-6,6);

        return idfAlfa;

    }

    public Long getCodigoIdf(String idf){
        String idfFlag;
        String idfAlfa;
        String idfNum;
        int tamanho;

        idfFlag = "";
        idfAlfa = "";
        idfNum = "";

        String idfTratado;

        idfTratado = "";

        if (idf == null)
            return Long.valueOf(0);

        if (idf.isEmpty())
            return Long.valueOf(0);

        for (int i=0;i<idf.length();i++){
            char chr = idf.charAt(i);

            int temp;
            temp = 0;
            switch (chr){
                case 42:
                    temp = 1;
                case 45:
                    temp = 1;
                case 64:
                    temp = 1;
                default:
                    temp = 0;
            }
            if (temp==1)
                idfFlag = idfFlag + idf.substring(i,i+1);

            if ( (chr>=65 && chr<=90) || (chr>=97 && chr <=122) )
                temp = 1;
            else
                temp = 0;

            if (temp==1)
                idfAlfa = idfAlfa + idf.substring(i,i+1);

            if (chr>=48 && chr<=57)
                temp = 1;
            else
                temp = 0;

            if (temp==1)
                idfNum = idfNum + idf.substring(i,i+1);

        }

        if (idfNum.length()>8)
            idfNum = idfNum.substring(0,8);

        return Long.valueOf(idfNum);

    }
}

