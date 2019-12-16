package br.eti.softlog.model;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Paulo Sérgio Alves on 2018/04/10.
 */

public class DatabaseUpgradeHelper extends DaoMaster.OpenHelper {

    private int version;
    boolean upgrade;

    public DatabaseUpgradeHelper(Context context, String name) {
        super(context, name);
    }

    public int getVersion(){
        return version;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        List<Migration> migrations = getMigrations();


        // Only run migrations past the old version
        for (Migration migration : migrations) {
            if (oldVersion < migration.getVersion()) {
                upgrade = true;
                version = newVersion;
                migration.runMigration(db);
            }
        }
    }

    private List<Migration> getMigrations() {
        List<Migration> migrations = new ArrayList<>();
        migrations.add(new MigrationV18());
        migrations.add(new MigrationV19());
        migrations.add(new MigrationV20());
        migrations.add(new MigrationV22());
        migrations.add(new MigrationV23());
        migrations.add(new MigrationV24());
        migrations.add(new MigrationV26());
        migrations.add(new MigrationV27());
        migrations.add(new MigrationV28());

        // Sorting just to be safe, in case other people add migrations in the wrong order.
//        Comparator<Migration> migrationComparator = new Comparator<Migration>() {
//            @Override
//            public int compare(Migration m1, Migration m2) {
//                return m1.getVersion().compareTo(m2.getVersion());
//            }
//        };
//        Collections.sort(migrations, migrationComparator);

        return migrations;
    }

    private static class MigrationV18 implements Migration {

        @Override
        public Integer getVersion() {
            return 18;
        }

        @Override
        public void runMigration(Database db) {


            db.execSQL("ALTER TABLE " + MedicaoDao.TABLENAME + " ADD COLUMN obrigatorio INTEGER;");
            //Adding new table
            //Log.d("Versao BD","18");


            //UsuarioDao.createTable(db, false);
        }
    }


    private static class MigrationV19 implements Migration {

        @Override
        public Integer getVersion() {
            return 19;
        }

        @Override
        public void runMigration(Database db) {


            //db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN idf2 STRING;");
            //Adding new table
            //Log.d("Versao BD","19");
            //UsuarioDao.createTable(db, false);
        }
    }


    private static class MigrationV20 implements Migration {

        @Override
        public Integer getVersion() {
            return 20;
        }

        @Override
        public void runMigration(Database db) {

            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN idf2 TEXT;");
            //db.execSQL("ALTER TABLE " + MedicaoDao.TABLENAME + " ADD COLUMN obrigatorio INTEGER;");
            //Adding new table
            //Log.d("Versao BD","20");
            //UsuarioDao.createTable(db, false);
        }
    }

    private static class MigrationV22 implements Migration {

        @Override
        public Integer getVersion() {
            return 22;
        }

        @Override
        public void runMigration(Database db) {

            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN dep_nasc DOUBLE;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN codigo_idf INTEGER;");


            MotivoDescarteDao.createTable(db,true);

            //db.execSQL("ALTER TABLE " + MedicaoDao.TABLENAME + " ADD COLUMN obrigatorio INTEGER;");

        }
    }

    private static class MigrationV23 implements Migration {

        @Override
        public Integer getVersion() {
            return 23;
        }

        @Override
        public void runMigration(Database db) {

            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN dep_nasc REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN perc_nasc STRING;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN dep_desm REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN perc_desm REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN dep_gpd REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN perc_gpd REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN p_gpd REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN dep_sob REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN perc_sob REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN p_sob REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN dep_ce REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN perc_ce REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN rc_ce REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN ce REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN dep_musc REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN perc_musc REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN musc REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN ind_qlt REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN perc_qlt REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN rank_qlt REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN marcacao INTEGER;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN classificacao INTEGER;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN mocho INTEGER;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN p_marcacao REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN ce_marcacao REAL;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN mot_desc_id INTEGER;");
            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN ceip STRING;");
            //db.execSQL("ALTER TABLE " + MedicaoDao.TABLENAME + " ADD COLUMN obrigatorio INTEGER;");

        }
    }

    private static class MigrationV24 implements Migration {

        @Override
        public Integer getVersion() {
            return 24;
        }

        @Override
        public void runMigration(Database db) {

            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN classificacao_fp TEXT;");

            //db.execSQL("ALTER TABLE " + MedicaoDao.TABLENAME + " ADD COLUMN obrigatorio INTEGER;");

        }
    }

    private static class MigrationV26 implements Migration {

        @Override
        public Integer getVersion() {
            return 26;
        }

        @Override
        public void runMigration(Database db) {

            MotivoDescarteAnimaisDao.createTable(db,true);

            //db.execSQL("ALTER TABLE " + MedicaoDao.TABLENAME + " ADD COLUMN obrigatorio INTEGER;");

        }
    }

    private static class MigrationV27 implements Migration {

        @Override
        public Integer getVersion() {
            return 27;
        }

        @Override
        public void runMigration(Database db) {

            MotivoDescarteAnimaisDao.dropTable(db,true);
            MotivoDescarteAnimaisDao.createTable(db,true);

            //db.execSQL("ALTER TABLE " + MedicaoDao.TABLENAME + " ADD COLUMN obrigatorio INTEGER;");

        }
    }

    private static class MigrationV28 implements Migration {

        @Override
        public Integer getVersion() {
            return 28;
        }

        @Override
        public void runMigration(Database db) {

            db.execSQL("ALTER TABLE " + MTFDadosDao.TABLENAME + " ADD COLUMN lote STRING;");

        }
    }

    private interface Migration {
        Integer getVersion();
        void runMigration(Database db);
    }
}