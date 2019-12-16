package br.eti.softlog.qualitasbovino;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.blankj.utilcode.util.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.DaoMaster;
import br.eti.softlog.model.DaoSession;
import br.eti.softlog.model.DatabaseUpgradeHelper;
import br.eti.softlog.model.MTFDados;

/**
 * Created by Paulo Sergio Alves on 2018/01/31.
 */

public class AppMain extends Application {

    private static AppMain singleton;
    DatabaseUpgradeHelper helper;
    //DaoMaster.DevOpenHelper helper;

    private DaoSession mDaoSession;
    private DaoSession mDaoSessionCentral;

    private SQLiteDatabase db;

    String nameDb;

    public AppMain getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        Utils.init(this);
        singleton = this;

    }

    public SQLiteDatabase getDb(){
        return db;
    }

    public void setBD(String nome_bd) {

        //helper = new DaoMaster.DevOpenHelper(this, nome_bd + ".db", null);
        helper = new DatabaseUpgradeHelper(getApplicationContext(),nome_bd + ".db");
        SQLiteDatabase db = helper.getWritableDatabase();
        this.db = db;
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        nameDb = nome_bd;

        //if (helper.getVersion() == 22 && helper.isUpgrade())
            //updateDadosVersion22();

    }

    public void updateDadosVersion22() {

        List<MTFDados> dados = mDaoSession.getMTFDadosDao().loadAll();
        for (int i=0;i<dados.size();i++){
            String filename = dados.get(i).getIdf();
            String[] partes = filename.split(" ");
            Log.d("Atualizando Idf",dados.get(i).getIdf());
////            if (partes.length == 2){
////                dados.get(i).setPrefixIdf(partes[0]);
////                dados.get(i).setCodigoIdf(Long.valueOf(partes[1]));
////            } else {
////                dados.get(i).setCodigoIdf(Long.valueOf(partes[0]));
////            }
            mDaoSession.update(dados.get(i));
        }
    }



    public boolean backupBD(Context context, String nome_bd) {

        String dirMain = "/mnt/sdcard/qualitas_bovino";

        String dirBackup = dirMain + "/backup";
        File folderBackup = new File(dirBackup);
        if (!folderBackup.exists()) {
            folderBackup.mkdir();
        }


        Date date = new Date();
        String cDate = Util.getDateTimeFormatYMD(date);

        //Código de Backup
        try {
            // Caminho de Origem do Seu Banco de Dados
            InputStream in = new FileInputStream(
                    new File(String.valueOf(context.getDatabasePath(nome_bd + ".db"))));


            // Caminho de Destino do Backup do Seu Banco de Dados
            OutputStream out = new FileOutputStream(new File(
                    dirBackup + "/" + nome_bd + "_" + cDate + ".db"));

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean restoreBD(Context context, String nome_bd) {
        String dirMain = "/mnt/sdcard/qualitas_bovino/backup/";

        //Código para Restauração do Backup
        try {
            // Caminho do Backup Banco de Dados
            InputStream in = new FileInputStream(
                    new File(dirMain + nome_bd + ".db"));

            // Caminho de Destino do Backup do Seu Banco de Dados
            OutputStream out = new FileOutputStream(new File(
                    "/data/data/br.eti.softlog.qualitasbovino/databases/" + nome_bd + ".db"));


            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public String getPathDocs() {
        PackageManager m = getPackageManager();
        String s = getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return p.applicationInfo.dataDir;
    }

}