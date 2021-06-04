package Model;

public class ModelTaco {
    String _id, NamaTaco, Status, Tanggal;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNamaTaco() {
        return NamaTaco;
    }

    public void setNamaTaco(String namaTaco) {
        NamaTaco = namaTaco;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }
}
