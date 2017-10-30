package com.pango.comunicaciones;

import com.pango.comunicaciones.model.PasajeroModel;
import com.pango.comunicaciones.model.PersonaPostReservaModel;
import com.pango.comunicaciones.model.TicketModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NILRAD on 25/09/2017.
 */

public class Utils {

    public static String baseUrl = "https://app.antapaccay.com.pe/proportal/scom_service/api";
    public static String loginDominio = "anyaccess";
    public static String token = "eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNyc2Etc2hhMjU2IiwidHlwIjoiSldUIn0.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYW50YXBhY2NheSIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMDAwMDA3OTU4MCIsImV4cCI6MTUwNjYzNjUzMCwiaXNzIjoiaHR0cDovL2lzc3Vlci5jb20iLCJhdWQiOiJodHRwOi8vbXlzaXRlLmNvbSJ9.gse25ySe4fptGQasIEioL3THSrj1BzQAcGC4EqOsEGzjXeQwf8DG2kHGMmLdNHOID1TF06WlSkJU4lxCK297bxkNJLqdo86rPKOcvCHQMMFtHWYGTbdLgVdPS4CWfK-KIl1WTQNWavna-pSqn_WM-aBGnifqsARbdMMjOLvO2XHY9eKBhn8u0uleaeWobW5oKBTFiUpgO7JaVGbZL23yVOpCPJBil_XzzyaCt1ixodO7yuqOlClJw2IyW0RAO_lJu2cXUVYT8D0fM5KI-qnx6ClxY4j91YmGhcM5vR1yovKCbzND0-1PRrHzxSTi3M-LcqIIRChlsWHdtDik7-DbPA";

    public static Boolean esAdmin = false;
    public static String nombres = "Quille Sucasaca, Alexander";
    public static String codPersona = "0000079580";

    static String getUrlForReservaTicketsLogin(String username, String password) {
        return baseUrl + "/membership/authenticate?username=" + username + "&password=" + password + "&domain=" + Utils.loginDominio;
    }

    static String getUrlForCheckIfAdmin() {
        return baseUrl + "/persona/Get_Usuario";
    }
    static String getUrlForReservaTicketTerminales() {
        return baseUrl + "/maestro/get_terminal";
    }

    public static String getUrlForBuscarTickets(String origenEscogido, String destinoEscogido, String fechaEscogida, int pagina,int cantidadTickets) {
        String url= baseUrl + "/ticket/get_tickets/" + origenEscogido + "/" + destinoEscogido + "/" + fechaEscogida + "/"+pagina+"/" + cantidadTickets;
        return url.replace(" ", "%20");
    }

    public static String getUrlForReservaTicketDetalle(String codigoTicket) {
        return baseUrl + "/ticket/get_ticket/" + codigoTicket;
    }


    public static String getTicketProperty(TicketModel ticket, String s) {
        DateFormat formatoInicial = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy");
        DateFormat formatoHora = new SimpleDateFormat("h:mm a");
        Date temp= null;
        try {
            temp= formatoInicial.parse(ticket.FECHA+"T"+ticket.HORA);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (s){
            case "Nro Programa":
                return ticket.IDPROG;
            case "Nombre Bus":
                return ticket.SERVICIO;
            case "Origen":
                return ticket.ORIGEN;
            case "Destino":
                return ticket.DESTINO;
            case "Fecha":
                return formatoRender.format(temp);
            case "Hora":
                return formatoHora.format(temp).replace(". ","").replace(".","");
            case "Disponibles":
                return ticket.DISPONIBLES;
            case "Ocupados":
                return "" + ticket.RESERVAS;
            case "Total Asientos":
                return ticket.ASIENTOS;
            case "Reservas Hecha":
                return ticket.SEPARADO?"SI":"NO";
            case "Tipo Bus":
                return "" + ticket.TIPOBUS;
            case "Patente":
                return ticket.PATENTE;
            case "Marca":
                return ticket.MARCA;
            case "Modelo":
                return ticket.MODELO;
            case "Tipo Vehiculo":
                return ticket.TIPOVEH;

            default:
                return "";
        }
    }

    public static PersonaPostReservaModel getPersonaPostReservaModel(String codTicket) {
        PersonaPostReservaModel persona = new PersonaPostReservaModel();
        persona.DNI = Utils.codPersona;
        persona.IDPROG = codTicket;
        //persona.dn = "";
       // persona.Email = "";
        return persona;
    }

    public static String getUrlForAgregarReserva() {
        return baseUrl + "/persona/Reservar";
    }

    public static String getUrlForEliminarReserva() {
        return baseUrl + "/persona/DeleteReserva";
    }


    public static String getUrlForReservaTicketListaPasajeros(String codigoTicket) {
        return baseUrl + "/persona/get_pasajeros/" + codigoTicket;
    }

    public static String getUrlForReservaTicketBuscarPasajeros(String dni, String nombre, String empresa,int pagina, int cantidad) {
        String url= baseUrl + "/persona/Get_Personas?DNI=" + dni + "&Nombres=" + nombre + "&Empresa=" + empresa + "&Pagenumber="+pagina+"&Elemperpage=" + cantidad;
        return url.replace(" ", "%20");
    }

    public static PersonaPostReservaModel fromPasajeroToPersona(PasajeroModel pasajero, String codigoTicket){
        PersonaPostReservaModel persona = new PersonaPostReservaModel();
        //persona.CodPersona = pasajero.CodPersona;
        persona.IDPROG= codigoTicket;
        persona.NOMBRES = pasajero.NOMBRES;
        persona.DNI = pasajero.DNI;
        return persona;
    }

    public static String getFechaHoy() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-YYYY");
        return sd.format(date);
    }

    public static  String ChangeUrl(String url){

        String urlOk=url.replaceAll("\\s","%20").replaceAll("ó","%f3").replaceAll("á","%e1").replaceAll("é","%e9")
                .replaceAll("í","%ed").replaceAll("ú","%fa").replaceAll("ñ","%f1").replaceAll("Ñ","%d1")
                .replaceAll("Á","%c1").replaceAll("É","%c9").replaceAll("Í","%cd").replaceAll("Ó","%d3")
                .replaceAll("Ú","%da");

        return urlOk;
    }

}
