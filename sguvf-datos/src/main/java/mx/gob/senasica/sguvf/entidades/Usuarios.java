package mx.gob.senasica.sguvf.entidades;

import javax.persistence.*;

@Entity
@Table(name = "Usuarios")
public class Usuarios {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUsuario")
    private Long IdUsuario;

    @Column(name = "IdRol")
    private String IdRol;

    @Column(name = "NivelAcceso")
    private String NivelAcceso;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "Nombre")
    private String Nombre;

    @Column(name = "UnidadVerificacion")
    private String UnidadVerificacion;

    @Column(name = "Direccion")
    private String Direccion;

    @Column(name = "Ciudad")
    private String Ciudad;

    @Column(name = "Estado")
    private String Estado;

    @Column(name = "Municipio")
    private String Municipio;

    @Column(name = "CodigoPostal")
    private String CodigoPostal;

    @Column(name = "Telefono")
    private String Telefono;

    @Column(name = "Fax")
    private String Fax;

    @Column(name = "Celular")
    private String Celular;

    @Column(name = "Pregunta")
    private String Pregunta;

    @Column(name = "Respuesta")
    private String Respuesta;

    @Column(name = "Activo")
    private String Activo;

    @Column(name = "LockedFlag")
    private String LockedFlag;

    @Column(name = "FechaLocked")
    private String FechaLocked;

    @Column(name = "FechaCambioPassword")
    private String FechaCambioPassword;

    @Column(name = "ContFallosLogin")
    private String ContFallosLogin;

    @Column(name = "FechaFallosLogin")
    private String FechaFallosLogin;

    @Column(name = "FechaUltimoLogin")
    private String FechaUltimoLogin;

    @Column(name = "PrimeraVez")
    private String PrimeraVez;

    @Column(name = "RFC")
    private String RFC;

    @Column(name = "CURP")
    private String CURP;

    public Long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getIdRol() {
        return IdRol;
    }

    public void setIdRol(String idRol) {
        IdRol = idRol;
    }

    public String getNivelAcceso() {
        return NivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {
        NivelAcceso = nivelAcceso;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUnidadVerificacion() {
        return UnidadVerificacion;
    }

    public void setUnidadVerificacion(String unidadVerificacion) {
        UnidadVerificacion = unidadVerificacion;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        CodigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        Pregunta = pregunta;
    }

    public String getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(String respuesta) {
        Respuesta = respuesta;
    }

    public String getActivo() {
        return Activo;
    }

    public void setActivo(String activo) {
        Activo = activo;
    }

    public String getLockedFlag() {
        return LockedFlag;
    }

    public void setLockedFlag(String lockedFlag) {
        LockedFlag = lockedFlag;
    }

    public String getFechaLocked() {
        return FechaLocked;
    }

    public void setFechaLocked(String fechaLocked) {
        FechaLocked = fechaLocked;
    }

    public String getFechaCambioPassword() {
        return FechaCambioPassword;
    }

    public void setFechaCambioPassword(String fechaCambioPassword) {
        FechaCambioPassword = fechaCambioPassword;
    }

    public String getContFallosLogin() {
        return ContFallosLogin;
    }

    public void setContFallosLogin(String contFallosLogin) {
        ContFallosLogin = contFallosLogin;
    }

    public String getFechaFallosLogin() {
        return FechaFallosLogin;
    }

    public void setFechaFallosLogin(String fechaFallosLogin) {
        FechaFallosLogin = fechaFallosLogin;
    }

    public String getFechaUltimoLogin() {
        return FechaUltimoLogin;
    }

    public void setFechaUltimoLogin(String fechaUltimoLogin) {
        FechaUltimoLogin = fechaUltimoLogin;
    }

    public String getPrimeraVez() {
        return PrimeraVez;
    }

    public void setPrimeraVez(String primeraVez) {
        PrimeraVez = primeraVez;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }
}