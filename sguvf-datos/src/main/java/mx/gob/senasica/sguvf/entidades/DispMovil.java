package mx.gob.senasica.sguvf.entidades;

import javax.persistence.*;

@Entity
@Table(name = "DispMovil")
public class DispMovil {
    private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "IdUsuario")
   private Long IdUsuario;

    @Column(name = "IMEI")
    private String imei;

    @Column(name = "SistOperativo")
    private String SistOperativo;

    @Column(name = "VersionAplicMovil")
    private String VersionAplicMovil;

    @Column(name = "UltimoAcceso")
    private String UltimoAcceso;

    @Column(name = "Marca")
    private String Marca;

    @Column(name = "Modelo")
    private String Modelo;

    @Column(name = "Memoria")
    private String Memoria;

    @Column(name = "Autorizado")
    private String Autorizado;

    public Long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSistOperativo() {
        return SistOperativo;
    }

    public void setSistOperativo(String sistOperativo) {
        SistOperativo = sistOperativo;
    }

    public String getVersionAplicMovil() {
        return VersionAplicMovil;
    }

    public void setVersionAplicMovil(String versionAplicMovil) {
        VersionAplicMovil = versionAplicMovil;
    }

    public String getUltimoAcceso() {
        return UltimoAcceso;
    }

    public void setUltimoAcceso(String ultimoAcceso) {
        UltimoAcceso = ultimoAcceso;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getMemoria() {
        return Memoria;
    }

    public void setMemoria(String memoria) {
        Memoria = memoria;
    }

    public String getAutorizado() {
        return Autorizado;
    }

    public void setAutorizado(String autorizado) {
        Autorizado = autorizado;
    }
}
