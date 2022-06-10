package mx.gob.senasica.sguvf;

import mx.gob.senasica.sguvf.entidades.DispMovil;
import mx.gob.senasica.sguvf.entidades.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class SguvfApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SguvfApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String queryNotAuthorized = "SELECT * FROM DispMovil  WHERE Autorizado = 0";
        List<DispMovil> notAuthorizedDevices = jdbcTemplate.query(queryNotAuthorized,
                BeanPropertyRowMapper.newInstance(DispMovil.class));

        if (!notAuthorizedDevices.isEmpty()) {
            List<Long> usersList = new ArrayList<>();

            for (DispMovil devices : notAuthorizedDevices) {
                if (devices.getIdUsuario() != null) {
                    if (devices.getIdUsuario() == 2059) {
                        System.out.println("Dispositivo no incluido en la consulta de datos");
                    } else {
                        usersList.add(devices.getIdUsuario());
                    }
                }
            }

            if (!usersList.isEmpty()) {
                for (Long id : usersList) {
                    searchAndDeleteDuplicates(id);
                }
            }
        } else {
            //Todo upodate and clean this STARS
            clearDuplicates();
            //Todo upodate and clean this ENDS
        }
    }

    private void searchAndDeleteDuplicates(Long idUsuario) {
        try {
            String sql = "SELECT * FROM DispMovil  where IdUsuario = " + idUsuario;
            List<DispMovil> devicesList = jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(DispMovil.class));

            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<Date, String> deviceValues = new HashMap<Date, String>();

            if (!devicesList.isEmpty()) {
                for (DispMovil devices : devicesList) {
                    if (devices.getUltimoAcceso() != null) {
                        if (!devices.getImei().isEmpty()) {
                            deviceValues.put(sdformat.parse(devices.getUltimoAcceso()), devices.getImei());
                        } else {
                            System.out.println("Eliminando todos los dispositivos de -> " + devices.getIdUsuario());
                            deleteDeviceById(devices.getIdUsuario());
                        }
                    }
                }

                if (!deviceValues.isEmpty()) {
                    Date maxDate = Collections.max(deviceValues.keySet());
                    String currentImei = deviceValues.get(maxDate);

                    for (Date key : deviceValues.keySet()) {
                        String oldImei = deviceValues.get(key);
                        if (!currentImei.equals(oldImei)) {
                            System.out.println("Los imei(s) a borrar son " + oldImei); //TODO Create JasperReport
                            deleteById(oldImei);
                        } else {
                            System.out.println("Dispositivos para habilitar " + currentImei); //TODO Create JasperReport
                            activateDevice(currentImei, idUsuario);
                        }
                    }
                } else {
                    System.out.println("La lista de dispositivos se encuentra vacia");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Todo update and clear code STARTS
    private void clearDuplicates() {
        String sql = "SELECT IdUsuario, COUNT(IdUsuario) FROM DispMovil dm GROUP BY IdUsuario HAVING COUNT(IdUsuario)>1";
        List<DispMovil> devicesList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(DispMovil.class));

        if (!devicesList.isEmpty()) {
            for (DispMovil devices : devicesList) {
                deleteOldDuplicatesBy(devices.getIdUsuario());
            }
        }
    }

    //Todo update and clear code ENDS

    private void deleteOldDuplicatesBy(Long paramIdUsuaio) {
        try {
            String sql = "SELECT * FROM DispMovil WHERE IdUsuario = " + paramIdUsuaio;

            List<DispMovil> devicesList = jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(DispMovil.class));
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<Date, String> deviceValues = new HashMap<Date, String>();

            if (!devicesList.isEmpty()) {
                for (DispMovil devices : devicesList) {
                    if (devices.getUltimoAcceso() != null) {
                        if (!devices.getImei().isEmpty()) {
                            deviceValues.put(sdformat.parse(devices.getUltimoAcceso()), devices.getImei());
                        } else {
                            System.out.println("Eliminando todos los dispositivos de -> " + devices.getIdUsuario());
                            deleteDeviceById(devices.getIdUsuario());
                        }
                    }
                }

                if (!deviceValues.isEmpty()) {
                    Date maxDate = Collections.max(deviceValues.keySet());
                    String currentImei = deviceValues.get(maxDate);

                    for (Date key : deviceValues.keySet()) {
                        String oldImei = deviceValues.get(key);
                        if (!currentImei.equals(oldImei)) {
                            System.out.println("Los imei(s) a borrar son " + oldImei); //TODO Create JasperReport
                            deleteById(oldImei);
                        }
                    }
                } else {
                    System.out.println("La lista de dispositivos se encuentra vacia");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void activateDevice(String paramImei, long paraIdUsuario) {
        if (paramImei != null) {
            String sqlUpdate = "UPDATE DispMovil SET Autorizado = 1 WHERE IMEI = ?";

            if (jdbcTemplate.update(sqlUpdate, paramImei) == 1) {
                System.out.println("Datos actualizados correctamente " + paramImei);
                getValuesAndSendEmail(paraIdUsuario);
            }
        }
        //TODO CREATE REPORTS
    }

    private void getValuesAndSendEmail(long paraIdUsuario) {
        String auxSubject = "Activación Dispositivo SGUVF";

        String auxEmail = null;
        String auxBody = null;

        String getUsersSql = "SELECT * FROM Usuarios  WHERE IdUsuario = " + paraIdUsuario;
        List<Usuarios> userList = jdbcTemplate.query(getUsersSql, BeanPropertyRowMapper.newInstance(Usuarios.class));

        String sql = "SELECT * FROM DispMovil  WHERE IdUsuario = " + paraIdUsuario;
        List<DispMovil> devicesList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(DispMovil.class));

        for (Usuarios users : userList) {
            if (isEmailValid(users.getUsername())) {
                auxEmail = users.getUsername();
            } else {
                System.out.println("Email invalido " + users);
            }
        }

        for (DispMovil devices : devicesList) {
            auxBody =
                    "Estimado TEF, su dispositivo Móvil :" +
                            "\n\nMarca : " + devices.getMarca() +
                            "\nModelo : " + devices.getModelo() +
                            "\n\nSe encuentra listo para operar" +
                            "\n\nRecuerde solo puede tener un dispositivo activado." +
                            "\n\nIMPORTANTE : No es necesario responder " +
                            "este email ya que es enviado automáticamente.";

        }

        if (auxEmail != null) {
            if (auxBody != null) {
                sendEmail(auxEmail, auxSubject, auxBody);
            }
        }
    }

    private void deleteDeviceById(Long paramId) {
        if (paramId != null) {
            String sqlDelete = "DELETE FROM DispMovil WHERE IdUsuario = ?";

            if (jdbcTemplate.update(sqlDelete, paramId) == 1) {
                System.out.println("Se elimino debido a un intento fallido de vulnerar la seguridad " + paramId);
            }
        }
    }

    private void deleteById(String paramImei) {
        if (paramImei != null) {
            String sqlDelete = "DELETE FROM DispMovil WHERE IMEI = ?";

            if (jdbcTemplate.update(sqlDelete, paramImei) == 1) {
                System.out.println("Datos eliminados correctamente ");
            }
        }
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void sendEmail(String paramTo, String paramSubject, String paramBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(paramTo);
        message.setSubject(paramSubject);
        message.setText(paramBody);
        getEmailSender().send(message);
    }

    @Bean
    public JavaMailSender getEmailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");//correo.senasica.gob.mx
        //mailSender.setHost("correo.senasica.gob.mx");
        mailSender.setPort(587);//same

        mailSender.setUsername("imash1709@gmail.com");//miguel.samaniego.c
        //mailSender.setUsername("miguel.samaniego.c@senasica.gob.mx");
        mailSender.setPassword("Nic02022.*");//
        //mailSender.setPassword("miguelsamaniego44");//

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.debug", "true");

        return mailSender;
    }

}
