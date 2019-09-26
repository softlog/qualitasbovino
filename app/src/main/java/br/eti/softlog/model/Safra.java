package br.eti.softlog.model;

import android.os.Environment;
import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.File;

@Entity(nameInDb = "safra")
public class Safra {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "safra")
    private int safra;

    @Generated(hash = 443673183)
    public Safra(Long id, int safra) {
        this.id = id;
        this.safra = safra;
    }

    @Generated(hash = 43886560)
    public Safra() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSafra() {
        return this.safra;
    }

    public void setSafra(int safra) {
        this.safra = safra;
    }


    public void criarDiretorioSafra() {
        String dirMain = "/mnt/sdcard/qualitas_bovino";
        File folder = new File(dirMain);
        if (!folder.exists()) {
            folder.mkdir();
        } else {
            Log.d("Diretorio já existe", dirMain);
        }

        String dirSafra = dirMain + "/" + String.valueOf(this.getSafra());
        File folderSafra = new File(dirSafra);
        if (!folderSafra.exists()) {
            folderSafra.mkdir();
        } else {
            Log.d("Diretorio já existe", dirSafra);
        }

    }


    @Override
    public String toString() {
        return "SAFRA " + String.valueOf(this.getSafra());
    }
}
