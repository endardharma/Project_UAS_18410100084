package id.undika.project_uas.model;

public class ModelMk {
    private String namaMk, jamMk, hariMk, ruanganMk, id;

    public ModelMk(String namaMk, String jamMk, String hariMk, String ruanganMk){
        this.namaMk = namaMk;
        this.jamMk = jamMk;
        this.hariMk = hariMk;
        this.ruanganMk = ruanganMk;
    }

    public String getNamaMk(){
        return namaMk;
    }
    public void setNamaMk(String namaMk){
        this.namaMk = namaMk;
    }

    public String getJamMk(){
        return jamMk;
    }
    public void setJamMk(String jamMk){
        this.jamMk = jamMk;
    }

    public String getHariMk(){
        return hariMk;
    }
    public void setHariMk(String hariMk){
        this.hariMk = hariMk;
    }

    public String getRuanganMk(){
        return ruanganMk;
    }
    public void setRuanganMk(String ruanganMk){
        this.ruanganMk = ruanganMk;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
}
