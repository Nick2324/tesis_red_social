package sportsallaround.snadeportivo.usuarios.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LuisFelipe on 21/06/2015.
 */
public class Usuario implements Parcelable{
    private String primerNombre;
    private String segundoNombre;
    private String apellidos;
    private String usuario;
    private String contrasena;
    private String eMail;
    private String numeroContacto;
    private String fechaNacimiento;
    private String fechaRegistro;
    private String sexo;
    private boolean estado;
    private String tipoUsuario;

    public Usuario(JSONObject user) throws JSONException {
        primerNombre = user.getString("primerNombre");
        segundoNombre = user.getString("segundoNombre");
        apellidos = user.getString("apellidos");
        usuario = user.getString("usuario");
        contrasena = user.getString("contrasena");
        eMail = user.getString("eMail");
        numeroContacto = user.getString("numeroContacto");
        fechaNacimiento = user.getString("fechaNacimiento");
        fechaRegistro = user.getString("fechaRegistro");
        sexo = user.getString("sexo");
        estado = user.getBoolean("estado");
    }

    public Usuario() {
        // TODO Auto-generated constructor stub
    }

    public String getPrimerNombre() {
        return primerNombre;
    }
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }
    public String getSegundoNombre() {
        return segundoNombre;
    }
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String geteMail() {
        return eMail;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    public String getNumeroContacto() {
        return numeroContacto;
    }
    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(primerNombre);
        dest.writeString(segundoNombre );
        dest.writeString(apellidos);
        dest.writeString(usuario);
        dest.writeString(contrasena);
        dest.writeString(eMail);
        dest.writeString(numeroContacto);
        dest.writeString(fechaNacimiento);
        dest.writeString(fechaRegistro);
        dest.writeString(sexo);
        dest.writeByte((byte)(estado ? 1:0));
    }

    public Usuario(Parcel in) {
        primerNombre = in.readString();
        segundoNombre = in.readString();
        apellidos = in.readString();
        usuario = in.readString();
        contrasena = in.readString();
        eMail = in.readString();
        numeroContacto = in.readString();
        fechaNacimiento = in.readString();
        fechaRegistro = in.readString();
        sexo = in.readString();
        estado = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>(){
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder retorno = new StringBuilder("");
        retorno.append("{\"primerNombre\": \"" + primerNombre + "\",");
        retorno.append("\"segundoNombre\": \"" + segundoNombre + "\",");
        retorno.append("\"apellidos\": \"" + apellidos + "\",");
        retorno.append("\"usuario\": \"" + usuario + "\",");
        retorno.append("\"contrasena\": \"" + contrasena + "\",");
        retorno.append("\"eMail\": \"" + eMail + "\",");
        retorno.append("\"numeroContacto\": \"" + numeroContacto + "\",");
        retorno.append("\"fechaNacimiento\": \"" + fechaNacimiento + "\",");
        retorno.append("\"fechaRegistro\": \"" + fechaRegistro + "\",");
        retorno.append("\"sexo\": \"" + sexo + "\",");
        retorno.append("\"estado\": \"" + estado + "\"}");
        return retorno.toString();
    }
}
