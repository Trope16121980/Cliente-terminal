/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author david
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import modelo.Users;
import modelo.Jornada;
import modelo.Empleados;
import modelo.Empresa;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Gustavo_Senorans
 */
public class Cliente {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        boolean salir = false;
        try {
            //IMPLEMENTA
            Socket socket = new Socket("192.168.56.1", 8888);
            Scanner lectorPalabra = new Scanner(System.in);
            BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));//flujo lectura del server
            BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//flujo envio al server
            ObjectInputStream perEnt;

            String codigo = "0";
            //proceso de login
            String mensajeServer = lector.readLine();   //leemos el mensaje de bienvenidoa del server        
            System.out.println(mensajeServer);//en el mensaje nos pide el login y pass
            ///escribimos el login y pass///
            //lo escribimos primero el login separmos con : y luego el pass (luego, en los clientes gráficos los enviaremos igual)
            System.out.println("Por favor introduce usuario y contraseña con este formato 'usuario:constraseña'");
            String palabra = lectorPalabra.nextLine();
            //ahora escribimos en servidor , enviandole el login
            escriptor.write(palabra);
            escriptor.newLine();
            escriptor.flush();
            if (palabra.equalsIgnoreCase("exit")) {
                salir = true;
                lector.close();
                escriptor.close();
                socket.close();
            } else {
                //leemos la respuesta, nos enviará un codigo 
                mensajeServer = lector.readLine();
                if (mensajeServer.equalsIgnoreCase("-1")) {
                    System.out.println("____________________________________________________________________");
                    System.out.println("El login es erroneo");//vemos el código
                    salir = true;
                    lector.close();
                    escriptor.close();
                    socket.close();

                } else if (mensajeServer.equalsIgnoreCase("-2")) {
                    System.out.println("____________________________________________________________________");
                    System.out.println("El usuario ya esta conectado");//vemos el código
                    salir = true;
                    lector.close();
                    escriptor.close();
                    socket.close();
                } else {
                    codigo = mensajeServer;
                    System.out.println(mensajeServer);//vemos el código
                    System.out.println("El codigo es: " + codigo);//vemos el código    

                    while (!salir) {
                        System.out.println("____________________________________________________________________");
                        System.out.println("\nDebes solicitar al server una tabla: \n"
                                + "codigo,crud,tabla,columna,palabra,orden\n"
                                + "codigo,crud,tabla,columna,palabra,columna,palabra,orden");
//                        if (socket.isConnected()) {
//                            System.out.println("El socket está conectado.");
//                        } else {
//                            System.out.println("El socket no está conectado.");
//                        }
                        palabra = lectorPalabra.nextLine();

                        //ahora escribimos en servidor , enviandole la palabra a buscar 
                        if (palabra.equalsIgnoreCase("exit")) { //primero comprobamos si es exit
                            escriptor.write(palabra);
                            escriptor.newLine();
                            escriptor.flush();
                            salir = true;
                            lector.close();
                            escriptor.close();
                            socket.close();

                        } else {// y ahora comprobamos que la frase este correcta si no enviamos una establecida (menos el codigo que sera error, es por si fallan las otras palabras)

                            String[] frase = new String[6];
                            frase = palabra.split(",");

                            String[] NomApellido = new String[8];
                            NomApellido = palabra.split(",");

                            String[] insertEmpresas = new String[10];
                            insertEmpresas = palabra.split(",");

                            String[] insertUsuarios = new String[12];
                            insertUsuarios = palabra.split(",");

                            String[] insertEmpleadoMailTelf = new String[16];
                            insertEmpleadoMailTelf = palabra.split(",");

                            String[] insertEmpleadoMT = new String[18];
                            insertEmpleadoMT = palabra.split(",");

                            String[] insertEmpleado = new String[20];
                            insertEmpleado = palabra.split(",");

                            if (frase[5].equals("0") || frase[5].equals("1")) {
                                String codigoUserRecibido = frase[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String crud = frase[1];
                                String nombreTabla = frase[2]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String columna = frase[3]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String palabraAbuscar = frase[4];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String orden = frase[5];// si es el caso el orden, si no hay ponemos 0

                                System.out.println("____________________________________________________________________");
                                System.out.println("codigoUserRecibido: " + codigoUserRecibido);
                                System.out.println("crud: " + crud);
                                System.out.println("nombreTabla: " + nombreTabla);
                                System.out.println("columna: " + columna);
                                System.out.println("palabraAbuscar: " + palabraAbuscar);
                                System.out.println("orden: " + orden);
                                System.out.println("____________________________________________________________________");

                                palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + columna + "," + palabraAbuscar + "," + orden;

                                if (codigoUserRecibido.equals("")) {
                                    codigoUserRecibido = "0";
                                }

                                if (crud.equals("0")) {
                                    if (nombreTabla.equals("0") && columna.equals("dni")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> listaPersonasdni = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaPersonasdni = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaPersonasdni.size(); i++) {
                                            if (columna.equals("dni") && palabraAbuscar.equals(listaPersonasdni.get(i).getDni())) {
                                                System.out.println("Dni: " + listaPersonasdni.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaPersonasdni.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaPersonasdni.get(i).getApellido() + "\n"
                                                        + "Nombre empresa: " + listaPersonasdni.get(i).getNomempresa() + "\n"
                                                        + "Departamento: " + listaPersonasdni.get(i).getDepartament() + "\n"
                                                        + "Codigo tarjeta: " + listaPersonasdni.get(i).getCodicard() + "\n"
                                                        + "Mail: " + listaPersonasdni.get(i).getMail() + "\n"
                                                        + "Teléfono: " + listaPersonasdni.get(i).getTelephon() + "\n");
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("0") && columna.equals("nomempresa")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> listaTotalEmpleadosNomEmpresa = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaTotalEmpleadosNomEmpresa = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaTotalEmpleadosNomEmpresa.size(); i++) {
                                            if (columna.equals("nomempresa") && palabraAbuscar.equals(listaTotalEmpleadosNomEmpresa.get(i).getNomempresa())) {
                                                System.out.println("Dni: " + listaTotalEmpleadosNomEmpresa.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaTotalEmpleadosNomEmpresa.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaTotalEmpleadosNomEmpresa.get(i).getApellido() + "\n"
                                                        + "Nombre empresa: " + listaTotalEmpleadosNomEmpresa.get(i).getNomempresa() + "\n"
                                                        + "Departamento: " + listaTotalEmpleadosNomEmpresa.get(i).getDepartament() + "\n"
                                                        + "Codigo tarjeta: " + listaTotalEmpleadosNomEmpresa.get(i).getCodicard() + "\n"
                                                        + "Mail: " + listaTotalEmpleadosNomEmpresa.get(i).getMail() + "\n"
                                                        + "Teléfono: " + listaTotalEmpleadosNomEmpresa.get(i).getTelephon() + "\n");
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("0") && columna.equals("departament")) {

                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> listaTotalEmpleadosDepart = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaTotalEmpleadosDepart = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaTotalEmpleadosDepart.size(); i++) {
                                            if (columna.equals("departament") && palabraAbuscar.equals(listaTotalEmpleadosDepart.get(i).getDepartament())) {
                                                System.out.println("Dni: " + listaTotalEmpleadosDepart.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaTotalEmpleadosDepart.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaTotalEmpleadosDepart.get(i).getApellido() + "\n"
                                                        + "Nombre empresa: " + listaTotalEmpleadosDepart.get(i).getNomempresa() + "\n"
                                                        + "Departamento: " + listaTotalEmpleadosDepart.get(i).getDepartament() + "\n"
                                                        + "Codigo tarjeta: " + listaTotalEmpleadosDepart.get(i).getCodicard() + "\n"
                                                        + "Mail: " + listaTotalEmpleadosDepart.get(i).getMail() + "\n"
                                                        + "Teléfono: " + listaTotalEmpleadosDepart.get(i).getTelephon() + "\n");
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("0") && columna.equals("codicard")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> listaTotalEmpleadosCodiCard = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaTotalEmpleadosCodiCard = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaTotalEmpleadosCodiCard.size(); i++) {
                                            String codicard = String.valueOf(listaTotalEmpleadosCodiCard.get(i).getCodicard());
                                            if (columna.equals("codicard") && palabraAbuscar.equals(codicard)) {
                                                System.out.println("Dni: " + listaTotalEmpleadosCodiCard.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaTotalEmpleadosCodiCard.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaTotalEmpleadosCodiCard.get(i).getApellido() + "\n"
                                                        + "Nombre empresa: " + listaTotalEmpleadosCodiCard.get(i).getNomempresa() + "\n"
                                                        + "Departamento: " + listaTotalEmpleadosCodiCard.get(i).getDepartament() + "\n"
                                                        + "Codigo tarjeta: " + listaTotalEmpleadosCodiCard.get(i).getCodicard() + "\n"
                                                        + "Mail: " + listaTotalEmpleadosCodiCard.get(i).getMail() + "\n"
                                                        + "Teléfono: " + listaTotalEmpleadosCodiCard.get(i).getTelephon() + "\n");
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("0") && columna.equals("mail")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> listaTotalEmpleadosMail = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaTotalEmpleadosMail = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaTotalEmpleadosMail.size(); i++) {
                                            if (columna.equals("mail") && palabraAbuscar.equals(listaTotalEmpleadosMail.get(i).getMail())) {
                                                System.out.println("Dni: " + listaTotalEmpleadosMail.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaTotalEmpleadosMail.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaTotalEmpleadosMail.get(i).getApellido() + "\n"
                                                        + "Nombre empresa: " + listaTotalEmpleadosMail.get(i).getNomempresa() + "\n"
                                                        + "Departamento: " + listaTotalEmpleadosMail.get(i).getDepartament() + "\n"
                                                        + "Codigo tarjeta: " + listaTotalEmpleadosMail.get(i).getCodicard() + "\n"
                                                        + "Mail: " + listaTotalEmpleadosMail.get(i).getMail() + "\n"
                                                        + "Teléfono: " + listaTotalEmpleadosMail.get(i).getTelephon() + "\n");
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("0") && columna.equals("telephon")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> listaTotalEmpleadosTelf = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaTotalEmpleadosTelf = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaTotalEmpleadosTelf.size(); i++) {
                                            String telephon = String.valueOf(listaTotalEmpleadosTelf.get(i).getTelephon());
                                            if (columna.equals("telephon") && palabraAbuscar.equals(telephon)) {
                                                System.out.println("Dni: " + listaTotalEmpleadosTelf.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaTotalEmpleadosTelf.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaTotalEmpleadosTelf.get(i).getApellido() + "\n"
                                                        + "Nombre empresa: " + listaTotalEmpleadosTelf.get(i).getNomempresa() + "\n"
                                                        + "Departamento: " + listaTotalEmpleadosTelf.get(i).getDepartament() + "\n"
                                                        + "Codigo tarjeta: " + listaTotalEmpleadosTelf.get(i).getCodicard() + "\n"
                                                        + "Mail: " + listaTotalEmpleadosTelf.get(i).getMail() + "\n"
                                                        + "Teléfono: " + listaTotalEmpleadosTelf.get(i).getTelephon() + "\n");
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();

                                    } else if (nombreTabla.equals("1") && columna.equals("dni")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Users> listaToUsersDni = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaToUsersDni = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaToUsersDni.size(); i++) {
                                            if (columna.equals("dni") && palabraAbuscar.equals(listaToUsersDni.get(i).getDni())) {
                                                System.out.println("Login: " + listaToUsersDni.get(i).getLogin() + "\n"
                                                        + "Password: " + listaToUsersDni.get(i).getPass() + "\n"
                                                        + "Tipo de user: " + listaToUsersDni.get(i).getNumtipe() + "\n"
                                                        + "DNI: " + listaToUsersDni.get(i).getDni());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("1") && columna.equals("login")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Users> listaTotalUsersLogin = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaTotalUsersLogin = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaTotalUsersLogin.size(); i++) {
                                            if (columna.equals("login") && palabraAbuscar.equals(listaTotalUsersLogin.get(i).getLogin())) {
                                                System.out.println("Login: " + listaTotalUsersLogin.get(i).getLogin() + "\n"
                                                        + "Password: " + listaTotalUsersLogin.get(i).getPass() + "\n"
                                                        + "Tipo de user: " + listaTotalUsersLogin.get(i).getNumtipe() + "\n"
                                                        + "DNI: " + listaTotalUsersLogin.get(i).getDni());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("1") && columna.equals("numtipe")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Users> listaTotalUsersTipe = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaTotalUsersTipe = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaTotalUsersTipe.size(); i++) {
                                            String numtipe = String.valueOf(listaTotalUsersTipe.get(i).getNumtipe());
                                            if (columna.equals("numtipe") && palabraAbuscar.equals(numtipe)) {
                                                System.out.println("Login: " + listaTotalUsersTipe.get(i).getLogin() + "\n"
                                                        + "Password: " + listaTotalUsersTipe.get(i).getPass() + "\n"
                                                        + "Tipo de user: " + listaTotalUsersTipe.get(i).getNumtipe() + "\n"
                                                        + "DNI: " + listaTotalUsersTipe.get(i).getDni());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();

                                    } else if (nombreTabla.equals("2") && columna.equals("nom")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empresa> listaEmpresasNom = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaEmpresasNom = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaEmpresasNom.size(); i++) {
                                            if (columna.equals("nom") && palabraAbuscar.equals(listaEmpresasNom.get(i).getNom())) {
                                                System.out.println("____________________________________________________________________");
                                                System.out.println("Nombre empresa: " + listaEmpresasNom.get(i).getNom() + "\n"
                                                        + "Dirección: " + listaEmpresasNom.get(i).getAddress() + "\n"
                                                        + "Teléfono: " + listaEmpresasNom.get(i).getTelephon());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("2") && columna.equals("address")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empresa> listaEmpresasAddress = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaEmpresasAddress = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaEmpresasAddress.size(); i++) {
                                            if (columna.equals("address") && palabraAbuscar.equals(listaEmpresasAddress.get(i).getAddress())) {
                                                System.out.println("____________________________________________________________________");
                                                System.out.println("Nombre empresa: " + listaEmpresasAddress.get(i).getNom() + "\n"
                                                        + "Dirección: " + listaEmpresasAddress.get(i).getAddress() + "\n"
                                                        + "Teléfono: " + listaEmpresasAddress.get(i).getTelephon());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("2") && columna.equals("telephon")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empresa> listaEmpresasTelepho = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaEmpresasTelepho = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaEmpresasTelepho.size(); i++) {
                                            String telephon = String.valueOf(listaEmpresasTelepho.get(i).getTelephon());
                                            if (columna.equals("telephon") && palabraAbuscar.equals(telephon)) {
                                                System.out.println("____________________________________________________________________");
                                                System.out.println("Nombre empresa: " + listaEmpresasTelepho.get(i).getNom() + "\n"
                                                        + "Dirección: " + listaEmpresasTelepho.get(i).getAddress() + "\n"
                                                        + "Teléfono: " + listaEmpresasTelepho.get(i).getTelephon());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();

                                    } else if (nombreTabla.equals("3") && columna.equals("dni")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Jornada> listaToJornadaDni = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaToJornadaDni = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaToJornadaDni.size(); i++) {
                                            if (columna.equals("dni") && palabraAbuscar.equals(listaToJornadaDni.get(i).getDni())) {
                                                System.out.println("Dni: " + listaToJornadaDni.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaToJornadaDni.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaToJornadaDni.get(i).getApellido() + "\n"
                                                        + "Código tarjeta: " + listaToJornadaDni.get(i).getCodicard() + "\n"
                                                        + "Hora entrada: " + listaToJornadaDni.get(i).getHoraentrada() + "\n"
                                                        + "Hora salida: " + listaToJornadaDni.get(i).getHorasalida() + "\n"
                                                        + "Total: " + listaToJornadaDni.get(i).getTotal() + "\n"
                                                        + "Fecha: " + listaToJornadaDni.get(i).getFecha());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("3") && columna.equals("codicard")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Jornada> listaJornadaCodiCard = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaJornadaCodiCard = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaJornadaCodiCard.size(); i++) {
                                            String codicard = String.valueOf(listaJornadaCodiCard.get(i).getCodicard());
                                            if (columna.equals("codicard") && palabraAbuscar.equals(codicard)) {
                                                System.out.println("Dni: " + listaJornadaCodiCard.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaJornadaCodiCard.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaJornadaCodiCard.get(i).getApellido() + "\n"
                                                        + "Código tarjeta: " + listaJornadaCodiCard.get(i).getCodicard() + "\n"
                                                        + "Hora entrada: " + listaJornadaCodiCard.get(i).getHoraentrada() + "\n"
                                                        + "Hora salida: " + listaJornadaCodiCard.get(i).getHorasalida() + "\n"
                                                        + "Total: " + listaJornadaCodiCard.get(i).getTotal() + "\n"
                                                        + "Fecha: " + listaJornadaCodiCard.get(i).getFecha());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("3") && columna.equals("fecha")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Jornada> listaTotalJornadaFecha = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaTotalJornadaFecha = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaTotalJornadaFecha.size(); i++) {
                                            if (columna.equals("fecha") && palabraAbuscar.equals(listaTotalJornadaFecha.get(i).getFecha())) {
                                                System.out.println("Dni: " + listaTotalJornadaFecha.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaTotalJornadaFecha.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaTotalJornadaFecha.get(i).getApellido() + "\n"
                                                        + "Código tarjeta: " + listaTotalJornadaFecha.get(i).getCodicard() + "\n"
                                                        + "Hora entrada: " + listaTotalJornadaFecha.get(i).getHoraentrada() + "\n"
                                                        + "Hora salida: " + listaTotalJornadaFecha.get(i).getHorasalida() + "\n"
                                                        + "Total: " + listaTotalJornadaFecha.get(i).getTotal()
                                                        + "Fecha: " + listaTotalJornadaFecha.get(i).getFecha());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (!nombreTabla.equals(null) && columna.equals("0")) {
                                        switch (nombreTabla) {
                                            case "0" -> {
                                                //ahora si enviamos al server los datos que queremos, sin errores
                                                escriptor.write(palabra);
                                                escriptor.newLine();
                                                escriptor.flush();
                                                System.out.println("Le enviamos esto al server: " + palabra);

                                                List<Empleados> listaPersonas = new ArrayList<>();

                                                perEnt = new ObjectInputStream(socket.getInputStream());
                                                listaPersonas = (ArrayList) perEnt.readObject();
                                                System.out.println("____________________________________________________________________");
                                                //recibo objeto
                                                for (int i = 0; i < listaPersonas.size(); i++) {
                                                    System.out.println("Dni: " + listaPersonas.get(i).getDni() + "\n"
                                                            + "Nombre: " + listaPersonas.get(i).getNom() + "\n"
                                                            + "Apellido: " + listaPersonas.get(i).getApellido() + "\n"
                                                            + "Nombre empresa: " + listaPersonas.get(i).getNomempresa() + "\n"
                                                            + "Departamento: " + listaPersonas.get(i).getDepartament() + "\n"
                                                            + "Codigo tarjeta: " + listaPersonas.get(i).getCodicard() + "\n"
                                                            + "Mail: " + listaPersonas.get(i).getMail() + "\n"
                                                            + "Teléfono: " + listaPersonas.get(i).getTelephon() + "\n");
                                                    System.out.println("____________________________________________________________________");
                                                }
                                                perEnt.getObjectInputFilter();
                                            }
                                            case "1" -> {

                                                //ahora si enviamos al server los datos que queremos, sin errores
                                                escriptor.write(palabra);
                                                escriptor.newLine();
                                                escriptor.flush();

                                                System.out.println("Le enviamos esto al server: " + palabra);
                                                List<Users> listaUsers = new ArrayList<>();

                                                perEnt = new ObjectInputStream(socket.getInputStream());
                                                listaUsers = (ArrayList) perEnt.readObject();

                                                System.out.println("____________________________________________________________________");
                                                //recibo objeto

                                                for (int i = 0; i < listaUsers.size(); i++) {
                                                    System.out.println("Login: " + listaUsers.get(i).getLogin() + "\n"
                                                            + "Password: " + listaUsers.get(i).getPass() + "\n"
                                                            + "Tipo de user: " + listaUsers.get(i).getNumtipe() + "\n"
                                                            + "DNI: " + listaUsers.get(i).getDni());
                                                    System.out.println("____________________________________________________________________");
                                                }
                                                perEnt.getObjectInputFilter();
                                            }

                                            case "2" -> {

                                                //ahora si enviamos al server los datos que queremos, sin errores
                                                escriptor.write(palabra);
                                                escriptor.newLine();
                                                escriptor.flush();
                                                System.out.println("Le enviamos esto al server: " + palabra);
                                                List<Empresa> listaEmpresa = new ArrayList<>();
                                                perEnt = new ObjectInputStream(socket.getInputStream());
                                                listaEmpresa = (ArrayList) perEnt.readObject();
                                                System.out.println("____________________________________________________________________");
                                                //recibo objeto
                                                for (int i = 0; i < listaEmpresa.size(); i++) {
                                                    System.out.println("Nombre empresa: " + listaEmpresa.get(i).getNom() + "\n"
                                                            + "Dirección: " + listaEmpresa.get(i).getAddress() + "\n"
                                                            + "Teléfono: " + listaEmpresa.get(i).getTelephon());
                                                    System.out.println("____________________________________________________________________");
                                                }
                                                perEnt.getObjectInputFilter();
                                            }
                                            case "3" -> {

                                                //ahora si enviamos al server los datos que queremos, sin errores
                                                escriptor.write(palabra);
                                                escriptor.newLine();
                                                escriptor.flush();
                                                System.out.println("Le enviamos esto al server: " + palabra);
                                                List<Jornada> listaJorandas = new ArrayList<>();
                                                perEnt = new ObjectInputStream(socket.getInputStream());
                                                listaJorandas = (ArrayList) perEnt.readObject();

                                                System.out.println("____________________________________________________________________");
                                                //recibo objeto
                                                for (int i = 0; i < listaJorandas.size(); i++) {
                                                    System.out.println("Dni: " + listaJorandas.get(i).getDni() + "\n"
                                                            + "Nombre: " + listaJorandas.get(i).getNom() + "\n"
                                                            + "Apellido: " + listaJorandas.get(i).getApellido() + "\n"
                                                            + "Código tarjeta: " + listaJorandas.get(i).getCodicard() + "\n"
                                                            + "Hora entrada: " + listaJorandas.get(i).getHoraentrada() + "\n"
                                                            + "Hora salida: " + listaJorandas.get(i).getHorasalida() + "\n"
                                                            + "Total: " + listaJorandas.get(i).getTotal() + "\n"
                                                            + "Fecha: " + listaJorandas.get(i).getFecha());
                                                    System.out.println("____________________________________________________________________");
                                                }
                                                perEnt.getObjectInputFilter();
                                            }
                                        }
                                    }
                                }
                            } else if (NomApellido[7].equals("0") || NomApellido[7].equals("1")) {
                                String codigoUserRecibido = NomApellido[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String crud = NomApellido[1];
                                String nombreTabla = NomApellido[2]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String nom = NomApellido[3]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoNom = NomApellido[4];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String apellido = NomApellido[5]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoApellido = NomApellido[6];
                                String orden = NomApellido[7];// si es el caso el orden, si no hay ponemos 0

                                System.out.println("____________________________________________________________________");
                                System.out.println("codigoUserRecibido: " + codigoUserRecibido);
                                System.out.println("crud: " + crud);
                                System.out.println("nombreTabla: " + nombreTabla);
                                System.out.println("nom: " + nom);
                                System.out.println("datoNom: " + datoNom);
                                System.out.println("apellido: " + apellido);
                                System.out.println("datoApellido: " + datoApellido);
                                System.out.println("orden: " + orden);
                                System.out.println("____________________________________________________________________");

                                palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + nom + "," + datoNom + "," + apellido + "," + datoApellido + "," + orden;

                                if (codigoUserRecibido.equals("")) {
                                    codigoUserRecibido = "0";
                                }

                                if (crud.equals("0")) {
                                    if (nombreTabla.equals("0") && nom.equals("nom") && apellido.equals("apellido")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> listaEmpleadosNomApellido = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaEmpleadosNomApellido = (ArrayList) perEnt.readObject();

                                        for (int i = 0; i < listaEmpleadosNomApellido.size(); i++) {
                                            if (nom.equals("nom")
                                                    && datoNom.equals(listaEmpleadosNomApellido.get(i).getNom())
                                                    && apellido.equals("apellido")
                                                    && datoApellido.equals(listaEmpleadosNomApellido.get(i).getApellido())) {
                                                System.out.println("Dni: " + listaEmpleadosNomApellido.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaEmpleadosNomApellido.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaEmpleadosNomApellido.get(i).getApellido() + "\n"
                                                        + "Nombre empresa: " + listaEmpleadosNomApellido.get(i).getNomempresa() + "\n"
                                                        + "Departamento: " + listaEmpleadosNomApellido.get(i).getDepartament() + "\n"
                                                        + "Codigo tarjeta: " + listaEmpleadosNomApellido.get(i).getCodicard() + "\n"
                                                        + "Mail: " + listaEmpleadosNomApellido.get(i).getMail() + "\n"
                                                        + "Teléfono: " + listaEmpleadosNomApellido.get(i).getTelephon() + "\n");
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    } else if (nombreTabla.equals("3") && nom.equals("nom") && apellido.equals("apellido")) {
                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Jornada> listaJornadaNomApellido = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        listaJornadaNomApellido = (ArrayList) perEnt.readObject();
                                        for (int i = 0; i < listaJornadaNomApellido.size(); i++) {
                                            if (nom.equals("nom")
                                                    && datoNom.equals(listaJornadaNomApellido.get(i).getNom())
                                                    && apellido.equals("apellido")
                                                    && datoApellido.equals(listaJornadaNomApellido.get(i).getApellido())) {
                                                System.out.println("Dni: " + listaJornadaNomApellido.get(i).getDni() + "\n"
                                                        + "Nombre: " + listaJornadaNomApellido.get(i).getNom() + "\n"
                                                        + "Apellido: " + listaJornadaNomApellido.get(i).getApellido() + "\n"
                                                        + "Código tarjeta: " + listaJornadaNomApellido.get(i).getCodicard() + "\n"
                                                        + "Hora entrada: " + listaJornadaNomApellido.get(i).getHoraentrada() + "\n"
                                                        + "Hora salida: " + listaJornadaNomApellido.get(i).getHorasalida() + "\n"
                                                        + "Total: " + listaJornadaNomApellido.get(i).getTotal() + "\n"
                                                        + "Fecha: " + listaJornadaNomApellido.get(i).getFecha());
                                                System.out.println("____________________________________________________________________");
                                            }
                                        }
                                        perEnt.getObjectInputFilter();
                                    }
                                }
                            } else if (insertEmpresas[9].equals("0") || insertEmpresas[9].equals("1")) {
                                String codigoUserRecibido = insertEmpresas[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String crud = insertEmpresas[1];
                                String nombreTabla = insertEmpresas[2]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String nom = insertEmpresas[3]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoNom = insertEmpresas[4];
                                String address = insertEmpresas[5];
                                String datoAddress = insertEmpresas[6];
                                String telephon = insertEmpresas[7]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoTelephon = insertEmpresas[8];
                                String orden = insertEmpresas[9];// si es el caso el orden, si no hay ponemos 0

                                System.out.println("____________________________________________________________________");
                                System.out.println("codigoUserRecibido: " + codigoUserRecibido);
                                System.out.println("crud: " + crud);
                                System.out.println("nombreTabla: " + nombreTabla);
                                System.out.println("nom: " + nom);
                                System.out.println("datoNom: " + datoNom);
                                System.out.println("address: " + address);
                                System.out.println("datoApellido: " + datoAddress);
                                System.out.println("telephon: " + telephon);
                                System.out.println("datoTelephon: " + datoTelephon);
                                System.out.println("orden: " + orden);
                                System.out.println("____________________________________________________________________");

                                palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + nom + "," + datoNom + "," + address
                                        + "," + datoAddress + "," + telephon + "," + datoTelephon + "," + orden;

                                if (codigoUserRecibido.equals("")) {
                                    codigoUserRecibido = "0";
                                }

                                if (crud.equals("1")) {
                                    if (nombreTabla.equals("2")) {

                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empresa> insertEmpresa = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        insertEmpresa = (ArrayList) perEnt.readObject();
                                        System.out.println(("Empleado creado correctamente, sus datos son: \n"));
                                        System.out.println("Nombre: " + datoNom + "\n"
                                                + "Adrress: " + datoAddress + "\n"
                                                + "Teléfono: " + datoTelephon + "\n");
                                        System.out.println("____________________________________________________________________");
                                        perEnt.getObjectInputFilter();
                                    }
                                }
                            } else if (insertUsuarios[11].equals("0") || insertUsuarios[11].equals("1")) {
                                String codigoUserRecibido = insertUsuarios[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String crud = insertUsuarios[1];
                                String nombreTabla = insertUsuarios[2]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String login = insertUsuarios[3]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoLogin = insertUsuarios[4];
                                String pass = insertUsuarios[5];
                                String datoPass = insertUsuarios[6];
                                String numTipe = insertUsuarios[7]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoNumTipe = insertUsuarios[8];
                                String dni = insertUsuarios[9]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDni = insertUsuarios[10];
                                String orden = insertUsuarios[11];// si es el caso el orden, si no hay ponemos 0

                                System.out.println("____________________________________________________________________");
                                System.out.println("codigoUserRecibido: " + codigoUserRecibido);
                                System.out.println("crud: " + crud);
                                System.out.println("nombreTabla: " + nombreTabla);
                                System.out.println("login: " + login);
                                System.out.println("datoLogin: " + datoLogin);
                                System.out.println("pass: " + pass);
                                System.out.println("datoPass: " + datoPass);
                                System.out.println("numTipe: " + numTipe);
                                System.out.println("datoNumTipe: " + datoNumTipe);
                                System.out.println("dni: " + dni);
                                System.out.println("datoDni: " + datoDni);
                                System.out.println("orden: " + orden);
                                System.out.println("____________________________________________________________________");

                                palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + login + "," + datoLogin + "," + pass
                                        + "," + datoPass + "," + numTipe + "," + datoNumTipe + "," + dni + "," + datoDni + "," + orden;

                                if (codigoUserRecibido.equals("")) {
                                    codigoUserRecibido = "0";
                                }

                                if (crud.equals("1")) {
                                    if (nombreTabla.equals("1")) {

                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empresa> insertUser = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        insertUser = (ArrayList) perEnt.readObject();
                                        System.out.println(("Empleado creado correctamente, sus datos son: \n"));
                                        System.out.println("Login: " + datoLogin + "\n"
                                                + "Pass: " + datoPass + "\n"
                                                + "Num Tipe: " + datoNumTipe + "\n"
                                                + "Dni: " + datoDni + "\n");
                                        System.out.println("____________________________________________________________________");
                                        perEnt.getObjectInputFilter();
                                    }
                                }
                            } else if (insertEmpleadoMailTelf[15].equals("0") || insertEmpleadoMailTelf[15].equals("1")) {
                                String codigoUserRecibido = insertEmpleadoMailTelf[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String crud = insertEmpleadoMailTelf[1];
                                String nombreTabla = insertEmpleadoMailTelf[2]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String dni = insertEmpleadoMailTelf[3]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDni = insertEmpleadoMailTelf[4];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String nom = insertEmpleadoMailTelf[5]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoNom = insertEmpleadoMailTelf[6];
                                String apellido = insertEmpleadoMailTelf[7];// si es el caso el orden, si no hay ponemos 0
                                String datoApellido = insertEmpleadoMailTelf[8]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String nomempresa = insertEmpleadoMailTelf[9];
                                String datoNomempresa = insertEmpleadoMailTelf[10]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String departament = insertEmpleadoMailTelf[11]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDepartament = insertEmpleadoMailTelf[12];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String codicard = insertEmpleadoMailTelf[13]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoCodicard = insertEmpleadoMailTelf[14];
                                String orden = insertEmpleadoMailTelf[15];// si es el caso el orden, si no hay ponemos 0

                                System.out.println("____________________________________________________________________");
                                System.out.println("codigoUserRecibido: " + codigoUserRecibido);
                                System.out.println("crud: " + crud);
                                System.out.println("nombreTabla: " + nombreTabla);
                                System.out.println("dni: " + dni);
                                System.out.println("datoDni: " + datoDni);
                                System.out.println("nom: " + nom);
                                System.out.println("datoNom: " + datoNom);
                                System.out.println("apellido: " + apellido);
                                System.out.println("datoApellido: " + datoApellido);
                                System.out.println("nomempresa: " + nomempresa);
                                System.out.println("datoNomempresa: " + datoNomempresa);
                                System.out.println("departament: " + departament);
                                System.out.println("datoDepartament: " + datoDepartament);
                                System.out.println("codicard: " + codicard);
                                System.out.println("datoCodicar: " + datoCodicard);
                                System.out.println("orden: " + orden);
                                System.out.println("____________________________________________________________________");

                                palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + dni + "," + datoDni + "," + nom + "," + datoNom + "," + apellido
                                        + "," + datoApellido + "," + nomempresa + "," + datoNomempresa + "," + departament + "," + datoDepartament + "," + codicard + "," + datoCodicard
                                        + "," + orden;

                                if (codigoUserRecibido.equals("")) {
                                    codigoUserRecibido = "0";
                                }

                                if (crud.equals("1")) {
                                    if (nombreTabla.equals("0")) {

                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> insertEmpleadosMailTelf = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        insertEmpleadosMailTelf = (ArrayList) perEnt.readObject();
                                        System.out.println(("Empleado creado correctamente, sus datos son: \n"));
                                        System.out.println("Dni: " + datoDni + "\n"
                                                + "Nombre: " + datoNom + "\n"
                                                + "Apellido: " + datoApellido + "\n"
                                                + "Nombre empresa: " + datoNomempresa + "\n"
                                                + "Departamento: " + datoDepartament + "\n"
                                                + "Codigo tarjeta: " + datoCodicard + "\n");
                                        System.out.println("____________________________________________________________________");
                                        perEnt.getObjectInputFilter();
                                    }
                                }
                            } else if (insertEmpleadoMT[17].equals("0") && insertEmpleadoMT[15].equals("mail")
                                    || insertEmpleadoMT[17].equals("1") && insertEmpleadoMT[15].equals("mail")) {

                                String codigoUserRecibido = insertEmpleadoMT[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String crud = insertEmpleadoMT[1];
                                String nombreTabla = insertEmpleadoMT[2]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String dni = insertEmpleadoMT[3]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDni = insertEmpleadoMT[4];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String nom = insertEmpleadoMT[5]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoNom = insertEmpleadoMT[6];
                                String apellido = insertEmpleadoMT[7];// si es el caso el orden, si no hay ponemos 0
                                String datoApellido = insertEmpleadoMT[8]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String nomempresa = insertEmpleadoMT[9];
                                String datoNomempresa = insertEmpleadoMT[10]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String departament = insertEmpleadoMT[11]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDepartament = insertEmpleadoMT[12];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String codicard = insertEmpleadoMT[13]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoCodicard = insertEmpleadoMT[14];
                                String mail = insertEmpleadoMT[15];// si es el caso el orden, si no hay ponemos 0
                                String datoMail = insertEmpleadoMT[16];
                                String orden = insertEmpleadoMT[17];// si es el caso el orden, si no hay ponemos 0

                                System.out.println("____________________________________________________________________");
                                System.out.println("codigoUserRecibido: " + codigoUserRecibido);
                                System.out.println("crud: " + crud);
                                System.out.println("nombreTabla: " + nombreTabla);
                                System.out.println("dni: " + dni);
                                System.out.println("datoDni: " + datoDni);
                                System.out.println("nom: " + nom);
                                System.out.println("datoNom: " + datoNom);
                                System.out.println("apellido: " + apellido);
                                System.out.println("datoApellido: " + datoApellido);
                                System.out.println("nomempresa: " + nomempresa);
                                System.out.println("datoNomempresa: " + datoNomempresa);
                                System.out.println("departament: " + departament);
                                System.out.println("datoDepartament: " + datoDepartament);
                                System.out.println("codicard: " + codicard);
                                System.out.println("datoCodicar: " + datoCodicard);
                                System.out.println("mail: " + mail);
                                System.out.println("datoMail: " + datoMail);
                                System.out.println("orden: " + orden);
                                System.out.println("____________________________________________________________________");

                                palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + dni + "," + datoDni + "," + nom + "," + datoNom + "," + apellido
                                        + "," + datoApellido + "," + nomempresa + "," + datoNomempresa + "," + departament + "," + datoDepartament + "," + codicard + "," + datoCodicard
                                        + "," + mail + "," + datoMail + "," + orden;

                                if (codigoUserRecibido.equals("")) {
                                    codigoUserRecibido = "0";
                                }

                                if (crud.equals("1")) {
                                    if (nombreTabla.equals("0")) {

                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> insertEmpleadosMail = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        insertEmpleadosMail = (ArrayList) perEnt.readObject();
                                        System.out.println(("Empleado creado correctamente, sus datos son: \n"));
                                        System.out.println("Dni: " + datoDni + "\n"
                                                + "Nombre: " + datoNom + "\n"
                                                + "Apellido: " + datoApellido + "\n"
                                                + "Nombre empresa: " + datoNomempresa + "\n"
                                                + "Departamento: " + datoDepartament + "\n"
                                                + "Codigo tarjeta: " + datoCodicard + "\n"
                                                + "Mail: " + datoMail);
                                        System.out.println("____________________________________________________________________");
                                        perEnt.getObjectInputFilter();
                                    }
                                }
                            } else if (insertEmpleadoMT[17].equals("0") && insertEmpleadoMT[15].equals("telephon")
                                    || insertEmpleadoMT[17].equals("1") && insertEmpleadoMT[15].equals("telephon")) {

                                String codigoUserRecibido = insertEmpleadoMT[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String crud = insertEmpleadoMT[1];
                                String nombreTabla = insertEmpleadoMT[2]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String dni = insertEmpleadoMT[3]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDni = insertEmpleadoMT[4];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String nom = insertEmpleadoMT[5]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoNom = insertEmpleadoMT[6];
                                String apellido = insertEmpleadoMT[7];// si es el caso el orden, si no hay ponemos 0
                                String datoApellido = insertEmpleadoMT[8]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String nomempresa = insertEmpleadoMT[9];
                                String datoNomempresa = insertEmpleadoMT[10]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String departament = insertEmpleadoMT[11]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDepartament = insertEmpleadoMT[12];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String codicard = insertEmpleadoMT[13]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoCodicard = insertEmpleadoMT[14];
                                String telephon = insertEmpleadoMT[15];// si es el caso el orden, si no hay ponemos 0
                                String datoTelephon = insertEmpleadoMT[16];
                                String orden = insertEmpleadoMT[17];// si es el caso el orden, si no hay ponemos 0

                                System.out.println("____________________________________________________________________");
                                System.out.println("codigoUserRecibido: " + codigoUserRecibido);
                                System.out.println("crud: " + crud);
                                System.out.println("nombreTabla: " + nombreTabla);
                                System.out.println("dni: " + dni);
                                System.out.println("datoDni: " + datoDni);
                                System.out.println("nom: " + nom);
                                System.out.println("datoNom: " + datoNom);
                                System.out.println("apellido: " + apellido);
                                System.out.println("datoApellido: " + datoApellido);
                                System.out.println("nomempresa: " + nomempresa);
                                System.out.println("datoNomempresa: " + datoNomempresa);
                                System.out.println("departament: " + departament);
                                System.out.println("datoDepartament: " + datoDepartament);
                                System.out.println("codicard: " + codicard);
                                System.out.println("datoCodicar: " + datoCodicard);
                                System.out.println("telephon: " + telephon);
                                System.out.println("datoTelephon: " + datoTelephon);
                                System.out.println("orden: " + orden);
                                System.out.println("____________________________________________________________________");

                                palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + dni + "," + datoDni + "," + nom + "," + datoNom + "," + apellido
                                        + "," + datoApellido + "," + nomempresa + "," + datoNomempresa + "," + departament + "," + datoDepartament + "," + codicard + "," + datoCodicard
                                        + "," + telephon + "," + datoTelephon + "," + orden;

                                if (codigoUserRecibido.equals("")) {
                                    codigoUserRecibido = "0";
                                }

                                if (crud.equals("1")) {
                                    if (nombreTabla.equals("0")) {

                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> insertEmpleadosTelf = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        insertEmpleadosTelf = (ArrayList) perEnt.readObject();
                                        System.out.println(("Empleado creado correctamente, sus datos son: \n"));
                                        System.out.println("Dni: " + datoDni + "\n"
                                                + "Nombre: " + datoNom + "\n"
                                                + "Apellido: " + datoApellido + "\n"
                                                + "Nombre empresa: " + datoNomempresa + "\n"
                                                + "Departamento: " + datoDepartament + "\n"
                                                + "Codigo tarjeta: " + datoCodicard + "\n"
                                                + "Telephon: " + datoTelephon);
                                        System.out.println("____________________________________________________________________");
                                        perEnt.getObjectInputFilter();
                                    }
                                }
                            } else if (insertEmpleado[19].equals("0") || insertEmpleado[19].equals("1")) {
                                String codigoUserRecibido = insertEmpleado[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String crud = insertEmpleado[1];
                                String nombreTabla = insertEmpleado[2]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String dni = insertEmpleado[3]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDni = insertEmpleado[4];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String nom = insertEmpleado[5]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoNom = insertEmpleado[6];
                                String apellido = insertEmpleado[7];// si es el caso el orden, si no hay ponemos 0
                                String datoApellido = insertEmpleado[8]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                                String nomempresa = insertEmpleado[9];
                                String datoNomempresa = insertEmpleado[10]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                                String departament = insertEmpleado[11]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoDepartament = insertEmpleado[12];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String codicard = insertEmpleado[13]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoCodicard = insertEmpleado[14];
                                String mail = insertEmpleado[15];// si es el caso el orden, si no hay ponemos 0
                                String datoMail = insertEmpleado[16];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                                String telephon = insertEmpleado[17]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                                String datoTelephon = insertEmpleado[18];
                                String orden = insertEmpleado[19];// si es el caso el orden, si no hay ponemos 0

                                System.out.println("____________________________________________________________________");
                                System.out.println("codigoUserRecibido: " + codigoUserRecibido);
                                System.out.println("crud: " + crud);
                                System.out.println("nombreTabla: " + nombreTabla);
                                System.out.println("dni: " + dni);
                                System.out.println("datoDni: " + datoDni);
                                System.out.println("nom: " + nom);
                                System.out.println("datoNom: " + datoNom);
                                System.out.println("apellido: " + apellido);
                                System.out.println("datoApellido: " + datoApellido);
                                System.out.println("nomempresa: " + nomempresa);
                                System.out.println("datoNomempresa: " + datoNomempresa);
                                System.out.println("departament: " + departament);
                                System.out.println("datoDepartament: " + datoDepartament);
                                System.out.println("codicard: " + codicard);
                                System.out.println("datoCodicar: " + datoCodicard);
                                System.out.println("mail: " + mail);
                                System.out.println("datoMail: " + datoMail);
                                System.out.println("telephon: " + telephon);
                                System.out.println("datoTelephon: " + datoTelephon);
                                System.out.println("orden: " + orden);
                                System.out.println("____________________________________________________________________");

                                palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + dni + "," + datoDni + "," + nom + "," + datoNom + "," + apellido
                                        + "," + datoApellido + "," + nomempresa + "," + datoNomempresa + "," + departament + "," + datoDepartament + "," + codicard + "," + datoCodicard
                                        + "," + mail + "," + datoMail + "," + telephon + "," + datoTelephon + "," + orden;

                                if (codigoUserRecibido.equals("")) {
                                    codigoUserRecibido = "0";
                                }

                                if (crud.equals("1")) {
                                    if (nombreTabla.equals("0")) {

                                        escriptor.write(palabra);
                                        escriptor.newLine();
                                        escriptor.flush();
                                        System.out.println("Le enviamos esto al server: " + palabra);

                                        List<Empleados> insertEmpleados = new ArrayList<>();

                                        perEnt = new ObjectInputStream(socket.getInputStream());
                                        insertEmpleados = (ArrayList) perEnt.readObject();
                                        System.out.println(("Empleado creado correctamente, sus datos son: \n"));
                                        System.out.println("Dni: " + datoDni + "\n"
                                                + "Nombre: " + datoNom + "\n"
                                                + "Apellido: " + datoApellido + "\n"
                                                + "Nombre empresa: " + datoNomempresa + "\n"
                                                + "Departamento: " + datoDepartament + "\n"
                                                + "Codigo tarjeta: " + datoCodicard + "\n"
                                                + "Mail: " + datoMail + "\n"
                                                + "Teléfono: " + datoTelephon + "\n");
                                        System.out.println("____________________________________________________________________");
                                        perEnt.getObjectInputFilter();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            socket.close();
        } catch (UnknownHostException ex) {
            System.out.println("____________________________________________________________________");
            //Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex + "\n Problema con la clase desconocida");
        } catch (IOException ex) {
            System.out.println("____________________________________________________________________");
            //Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex + "\n Problema con entrada y salida sockets");
        }
    }

    public static int contarCaracteres(String cadena, char caracter) {//para contar los :
        int posicion, contador = 0;
        //se busca la primera vez que aparece
        posicion = cadena.indexOf(caracter);
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada                                 
            posicion = cadena.indexOf(caracter, posicion + 1);
        }
        return contador;
    }

}
