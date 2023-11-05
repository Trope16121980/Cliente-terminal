package cliente;

/**
 *
 * @author Gustavo Se�or�ns Varela
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

	private static Scanner lectorPalabra;

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		boolean salir = false;
		try {
			// IMPLEMENTA
			Socket socket = new Socket("192.168.56.1", 8888);
			lectorPalabra = new Scanner(System.in);
			BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			ObjectInputStream perEnt;

			String codigo = "0";
			String mensajeServer = lector.readLine();
			System.out.println(mensajeServer);
			System.out.println("Por favor introduce usuario y contrase�a con este formato 'usuario:constrase�a'");
			String palabra = lectorPalabra.nextLine();
			escriptor.write(palabra);
			escriptor.newLine();
			escriptor.flush();
			if (palabra.equalsIgnoreCase("exit")) {
				salir = true;
				lector.close();
				escriptor.close();
				socket.close();
			} else {
				mensajeServer = lector.readLine();
				if (mensajeServer.equalsIgnoreCase("-1")) {
					System.out.println("____________________________________________________________________");
					System.out.println("El login es erroneo");
					salir = true;
					lector.close();
					escriptor.close();
					socket.close();

				} else if (mensajeServer.equalsIgnoreCase("-2")) {
					System.out.println("____________________________________________________________________");
					System.out.println("El usuario ya esta conectado");
					salir = true;
					lector.close();
					escriptor.close();
					socket.close();
				} else {
					codigo = mensajeServer;
					System.out.println(mensajeServer);
					System.out.println("El codigo es: " + codigo);

					while (!salir) {
						System.out.println("____________________________________________________________________");
						System.out.println("\nDebes solicitar al server una tabla: \n"
								+ "codigo,crud,tabla,columna,palabra,orden\n"
								+ "codigo,crud,tabla,columna,palabra,columna,palabra,orden");
						palabra = lectorPalabra.nextLine();

						if (palabra.equalsIgnoreCase("exit")) {
							escriptor.write(palabra);
							escriptor.newLine();
							escriptor.flush();
							salir = true;
							lector.close();
							escriptor.close();
							socket.close();

						} else {

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

							if (!codigo.equals(frase[0]) || !codigo.equals(NomApellido[0])
									|| !codigo.equals(insertEmpresas[0]) || !codigo.equals(insertUsuarios[0])
									|| !codigo.equals(insertEmpleadoMailTelf[0]) || !codigo.equals(insertEmpleadoMT[0])
									|| !codigo.equals(insertEmpleado[0])) {

								System.out.println("El codigo es erroneo");

							} else if (frase[5].equals("0") || frase[5].equals("1")) {
								String codigoUserRecibido = frase[0];
								String crud = frase[1];
								String nombreTabla = frase[2];
								String columna = frase[3];
								String palabraAbuscar = frase[4];
								String orden = frase[5];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("columna: " + columna);
								System.out.println("palabraAbuscar: " + palabraAbuscar);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + columna + ","
										+ palabraAbuscar + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								if (crud.equals("0")) {
									if (nombreTabla.equals("0") && columna.equals("dni")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaPersonasdni = (List<Empleados>) receivedData;
											for (Empleados empleado : listaPersonasdni) {
												System.out.println("DNI: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("0") && columna.equals("nom")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaTotalEmpleadosNom = (List<Empleados>) receivedData;

											for (Empleados empleado : listaTotalEmpleadosNom) {
												System.out.println("Dni: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("0") && columna.equals("apellido")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaTotalEmpleadosApellido = (List<Empleados>) receivedData;

											for (Empleados empleado : listaTotalEmpleadosApellido) {
												System.out.println("Dni: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("0") && columna.equals("nomempresa")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaTotalEmpleadosNomEmpresa = (List<Empleados>) receivedData;

											for (Empleados empleado : listaTotalEmpleadosNomEmpresa) {
												System.out.println("Dni: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("0") && columna.equals("departament")) {

										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaTotalEmpleadosDepart = (List<Empleados>) receivedData;

											for (Empleados empleado : listaTotalEmpleadosDepart) {
												System.out.println("Dni: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("0") && columna.equals("codicard")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaTotalEmpleadosCodiCard = (List<Empleados>) receivedData;
											for (Empleados empleado : listaTotalEmpleadosCodiCard) {
												String codicard = String.valueOf(empleado.getCodicard());
												System.out.println("Dni: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("0") && columna.equals("mail")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaTotalEmpleadosMail = (List<Empleados>) receivedData;

											for (Empleados empleado : listaTotalEmpleadosMail) {
												System.out.println("Dni: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("0") && columna.equals("telephon")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaTotalEmpleadosTelf = (List<Empleados>) receivedData;
											for (Empleados empleado : listaTotalEmpleadosTelf) {
												String telephon = String.valueOf(empleado.getTelephon());
												System.out.println("Dni: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("1") && columna.equals("dni")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Users> listaToUsersDni = (List<Users>) receivedData;
											for (Users user : listaToUsersDni) {
												System.out.println("Login: " + user.getLogin() + "\n" + "Password: "
														+ user.getPass() + "\n" + "Tipo de user: " + user.getNumtipe()
														+ "\n" + "DNI: " + user.getDni());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
									} else if (nombreTabla.equals("1") && columna.equals("login")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Users> listaTotalUsersLogin = (List<Users>) receivedData;

											for (Users user : listaTotalUsersLogin) {
												System.out.println("Login: " + user.getLogin() + "\n" + "Password: "
														+ user.getPass() + "\n" + "Tipo de user: " + user.getNumtipe()
														+ "\n" + "DNI: " + user.getDni());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
									} else if (nombreTabla.equals("1") && columna.equals("numtipe")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Users> listaTotalUsersTipe = (List<Users>) receivedData;

											for (Users user : listaTotalUsersTipe) {
												System.out.println("Login: " + user.getLogin() + "\n" + "Password: "
														+ user.getPass() + "\n" + "Tipo de user: " + user.getNumtipe()
														+ "\n" + "DNI: " + user.getDni());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
									} else if (nombreTabla.equals("2") && columna.equals("nom")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empresa> listaEmpresasNom = (List<Empresa>) receivedData;
											for (Empresa empresa : listaEmpresasNom) {
												System.out.println(
														"____________________________________________________________________");
												System.out.println("Nombre empresa: " + empresa.getNom() + "\n"
														+ "Direccion: " + empresa.getAddress() + "\n" + "Telefono: "
														+ empresa.getTelephon());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
									} else if (nombreTabla.equals("2") && columna.equals("address")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empresa> listaEmpresasAddress = (List<Empresa>) receivedData;
											for (Empresa empresa : listaEmpresasAddress) {
												System.out.println(
														"____________________________________________________________________");
												System.out.println("Nombre empresa: " + empresa.getNom() + "\n"
														+ "Direcci�n: " + empresa.getAddress() + "\n" + "Telefono: "
														+ empresa.getTelephon());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("3") && columna.equals("dni")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {

											List<Jornada> listaToJornadaDni = (List<Jornada>) receivedData;
											for (Jornada jornada : listaToJornadaDni) {
												System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
														+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido()
														+ "\n" + "Codigo tarjeta: " + jornada.getCodicard() + "\n"
														+ "Hora entrada: " + jornada.getHoraentrada() + "\n"
														+ "Hora salida: " + jornada.getHorasalida() + "\n" + "Total: "
														+ jornada.getTotal() + "\n" + "Fecha: " + jornada.getFecha());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("3") && columna.equals("nom")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {

											List<Jornada> listaTotalJornadaNom = (List<Jornada>) receivedData;
											for (Jornada jornada : listaTotalJornadaNom) {
												System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
														+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido()
														+ "\n" + "Codigo tarjeta: " + jornada.getCodicard() + "\n"
														+ "Hora entrada: " + jornada.getHoraentrada() + "\n"
														+ "Hora salida: " + jornada.getHorasalida() + "\n" + "Total: "
														+ jornada.getTotal() + "\n" + "Fecha: " + jornada.getFecha());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("3") && columna.equals("apellido")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {

											List<Jornada> listaTotalJornadaAapellido = (List<Jornada>) receivedData;
											for (Jornada jornada : listaTotalJornadaAapellido) {
												System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
														+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido()
														+ "\n" + "Codigo tarjeta: " + jornada.getCodicard() + "\n"
														+ "Hora entrada: " + jornada.getHoraentrada() + "\n"
														+ "Hora salida: " + jornada.getHorasalida() + "\n" + "Total: "
														+ jornada.getTotal() + "\n" + "Fecha: " + jornada.getFecha());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("3") && columna.equals("codicard")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {

											List<Jornada> listaJornadaCodiCard = (List<Jornada>) receivedData;
											for (Jornada jornada : listaJornadaCodiCard) {
												String codicard = String.valueOf(jornada.getCodicard());
												System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
														+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido()
														+ "\n" + "Codigo tarjeta: " + jornada.getCodicard() + "\n"
														+ "Hora entrada: " + jornada.getHoraentrada() + "\n"
														+ "Hora salida: " + jornada.getHorasalida() + "\n" + "Total: "
														+ jornada.getTotal() + "\n" + "Fecha: " + jornada.getFecha());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("3") && columna.equals("fecha")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {

											List<Jornada> listaTotalJornadaFecha = (List<Jornada>) receivedData;
											for (Jornada jornada : listaTotalJornadaFecha) {
												System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
														+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido()
														+ "\n" + "Codigo tarjeta: " + jornada.getCodicard() + "\n"
														+ "Hora entrada: " + jornada.getHoraentrada() + "\n"
														+ "Hora salida: " + jornada.getHorasalida() + "\n" + "Total: "
														+ jornada.getTotal() + "Fecha: " + jornada.getFecha());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (!nombreTabla.equals(null) && columna.equals("0")) {
										switch (nombreTabla) {
										case "0":
											escriptor.write(palabra);
											escriptor.newLine();
											escriptor.flush();
											System.out.println("El usuario con codigo: " + codigoUserRecibido
													+ "\nenvia los datos siguiente: \n" + palabra);

											List<Empleados> listaPersonas = new ArrayList<>();

											perEnt = new ObjectInputStream(socket.getInputStream());
											listaPersonas = (ArrayList) perEnt.readObject();
											System.out.println(
													"____________________________________________________________________");

											for (int i = 0; i < listaPersonas.size(); i++) {
												System.out.println("Dni: " + listaPersonas.get(i).getDni() + "\n"
														+ "Nombre: " + listaPersonas.get(i).getNom() + "\n"
														+ "Apellido: " + listaPersonas.get(i).getApellido() + "\n"
														+ "Nombre empresa: " + listaPersonas.get(i).getNomempresa()
														+ "\n" + "Departamento: "
														+ listaPersonas.get(i).getDepartament() + "\n"
														+ "Codigo tarjeta: " + listaPersonas.get(i).getCodicard() + "\n"
														+ "Mail: " + listaPersonas.get(i).getMail() + "\n"
														+ "Telefono: " + listaPersonas.get(i).getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
											break;
										case "1":

											escriptor.write(palabra);
											escriptor.newLine();
											escriptor.flush();

											System.out.println("El usuario con codigo: " + codigoUserRecibido
													+ "\nenvia los datos siguiente: \n" + palabra);
											List<Users> listaUsers = new ArrayList<>();

											perEnt = new ObjectInputStream(socket.getInputStream());
											listaUsers = (ArrayList) perEnt.readObject();

											System.out.println(
													"____________________________________________________________________");

											for (int i = 0; i < listaUsers.size(); i++) {
												System.out.println("Login: " + listaUsers.get(i).getLogin() + "\n"
														+ "Password: " + listaUsers.get(i).getPass() + "\n"
														+ "Tipo de user: " + listaUsers.get(i).getNumtipe() + "\n"
														+ "DNI: " + listaUsers.get(i).getDni());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
											break;

										case "2":

											escriptor.write(palabra);
											escriptor.newLine();
											escriptor.flush();
											System.out.println("El usuario con codigo: " + codigoUserRecibido
													+ "\nenvia los datos siguiente: \n" + palabra);
											List<Empresa> listaEmpresa = new ArrayList<>();
											perEnt = new ObjectInputStream(socket.getInputStream());
											listaEmpresa = (ArrayList) perEnt.readObject();
											System.out.println(
													"____________________________________________________________________");

											for (int i = 0; i < listaEmpresa.size(); i++) {
												System.out.println("Nombre empresa: " + listaEmpresa.get(i).getNom()
														+ "\n" + "Direccion: " + listaEmpresa.get(i).getAddress() + "\n"
														+ "Telefono: " + listaEmpresa.get(i).getTelephon());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
											break;
										case "3":

											escriptor.write(palabra);
											escriptor.newLine();
											escriptor.flush();
											System.out.println("El usuario con codigo: " + codigoUserRecibido
													+ "\nenvia los datos siguiente: \n" + palabra);
											List<Jornada> listaJorandas = new ArrayList<>();
											perEnt = new ObjectInputStream(socket.getInputStream());
											listaJorandas = (ArrayList) perEnt.readObject();

											System.out.println(
													"____________________________________________________________________");
											for (int i = 0; i < listaJorandas.size(); i++) {
												System.out.println("Dni: " + listaJorandas.get(i).getDni() + "\n"
														+ "Nombre: " + listaJorandas.get(i).getNom() + "\n"
														+ "Apellido: " + listaJorandas.get(i).getApellido() + "\n"
														+ "Codigo tarjeta: " + listaJorandas.get(i).getCodicard() + "\n"
														+ "Hora entrada: " + listaJorandas.get(i).getHoraentrada()
														+ "\n" + "Hora salida: " + listaJorandas.get(i).getHorasalida()
														+ "\n" + "Total: " + listaJorandas.get(i).getTotal() + "\n"
														+ "Fecha: " + listaJorandas.get(i).getFecha());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
											break;
										}
									}
								} else if (crud.equals("1")) {

									if (nombreTabla.equals("3") && columna.equals("dni")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<?> dataList = (List<?>) receivedData;
											System.out.println(("Jornada creada correctamente.\n"));
											for (Object data : dataList) {
												if (data instanceof Jornada) {
													Jornada jornada = (Jornada) data;
													System.out.println("Dni: " + jornada.getDni());
													System.out.println("Nombre: " + jornada.getNom());
													System.out.println("Apellido: " + jornada.getApellido());
													System.out.println("Codigo tarjeta: " + jornada.getCodicard());
													System.out.println("Hora entrada: " + jornada.getHoraentrada());
													System.out.println("Hora salida: " + jornada.getHorasalida());
													System.out.println("Total: " + jornada.getTotal());
													System.out.println("Fecha: " + jornada.getFecha());
													System.out.println(
															"____________________________________________________________________");
												} else {
													System.out.println("Datos inesperados recibidos del servidor");

												}
												perEnt.getObjectInputFilter();
											}
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("3") && columna.equals("codicard")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<?> dataList = (List<?>) receivedData;
											System.out.println(("Jornada creada correctamente.\n"));
											for (Object data : dataList) {
												if (data instanceof Jornada) {
													Jornada jornada = (Jornada) data;
													System.out.println("Dni: " + jornada.getDni());
													System.out.println("Nombre: " + jornada.getNom());
													System.out.println("Apellido: " + jornada.getApellido());
													System.out.println("Codigo tarjeta: " + jornada.getCodicard());
													System.out.println("Hora entrada: " + jornada.getHoraentrada());
													System.out.println("Hora salida: " + jornada.getHorasalida());
													System.out.println("Total: " + jornada.getTotal());
													System.out.println("Fecha: " + jornada.getFecha());
													System.out.println(
															"____________________________________________________________________");
												} else {
													System.out.println("Datos inesperados recibidos del servidor");

												}
												perEnt.getObjectInputFilter();
											}
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									}
								}
							} else if (NomApellido[2].equals("3") && NomApellido[3].equals("dni")
									&& NomApellido[5].equals("fecha")) {
								String codigoUserRecibido = NomApellido[0];
								String crud = NomApellido[1];
								String nombreTabla = NomApellido[2];
								String dni = NomApellido[3];
								String datoDni = NomApellido[4];
								String fechas = NomApellido[5];
								String datoFecha = NomApellido[6];
								String orden = NomApellido[7];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("nom: " + dni);
								System.out.println("datoNom: " + datoDni);
								System.out.println("apellido: " + fechas);
								System.out.println("datoApellido: " + datoFecha);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + dni + ","
										+ datoDni + "," + fechas + "," + datoFecha + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								escriptor.write(palabra);
								escriptor.newLine();
								escriptor.flush();
								System.out.println("El usuario con codigo: " + codigoUserRecibido
										+ "\nenvia los datos siguiente: \n" + palabra);

								perEnt = new ObjectInputStream(socket.getInputStream());
								Object receivedData = perEnt.readObject();

								if (receivedData instanceof List) {
									List<Jornada> listaJornadaDniFecha = (List<Jornada>) receivedData;
									for (Jornada jornada : listaJornadaDniFecha) {
										System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
												+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido() + "\n"
												+ "Codigo tarjeta: " + jornada.getCodicard() + "\n" + "Hora entrada: "
												+ jornada.getHoraentrada() + "\n" + "Hora salida: "
												+ jornada.getHorasalida() + "\n" + "Total: " + jornada.getTotal() + "\n"
												+ "Fecha: " + jornada.getFecha());
										System.out.println(
												"____________________________________________________________________");
									}
									perEnt.getObjectInputFilter();
								} else if (receivedData instanceof String) {
									String errorMessage = (String) receivedData;
									System.out.println(errorMessage);
								} else {
									System.out.println("Datos inesperados recibidos del servidor");
								}

							} else if (NomApellido[2].equals("3") && NomApellido[3].equals("nom")
									&& NomApellido[5].equals("fecha")) {
								String codigoUserRecibido = NomApellido[0];
								String crud = NomApellido[1];
								String nombreTabla = NomApellido[2];
								String nom = NomApellido[3];
								String datoNom = NomApellido[4];
								String fechas = NomApellido[5];
								String datoFecha = NomApellido[6];
								String orden = NomApellido[7];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("nom: " + nom);
								System.out.println("datoNom: " + datoNom);
								System.out.println("fecha: " + fechas);
								System.out.println("datoFecha: " + datoFecha);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + nom + ","
										+ datoNom + "," + fechas + "," + datoFecha + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								escriptor.write(palabra);
								escriptor.newLine();
								escriptor.flush();
								System.out.println("El usuario con codigo: " + codigoUserRecibido
										+ "\nenvia los datos siguiente: \n" + palabra);

								perEnt = new ObjectInputStream(socket.getInputStream());
								Object receivedData = perEnt.readObject();

								if (receivedData instanceof List) {
									List<Jornada> listaJornadaNomFecha = (List<Jornada>) receivedData;
									for (Jornada jornada : listaJornadaNomFecha) {
										System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
												+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido() + "\n"
												+ "Codigo tarjeta: " + jornada.getCodicard() + "\n" + "Hora entrada: "
												+ jornada.getHoraentrada() + "\n" + "Hora salida: "
												+ jornada.getHorasalida() + "\n" + "Total: " + jornada.getTotal() + "\n"
												+ "Fecha: " + jornada.getFecha());
										System.out.println(
												"____________________________________________________________________");
									}
									perEnt.getObjectInputFilter();
								} else if (receivedData instanceof String) {
									String errorMessage = (String) receivedData;
									System.out.println(errorMessage);
								} else {
									System.out.println("Datos inesperados recibidos del servidor");
								}

							} else if (NomApellido[2].equals("3") && NomApellido[3].equals("apellido")
									&& NomApellido[5].equals("fecha")) {
								String codigoUserRecibido = NomApellido[0];
								String crud = NomApellido[1];
								String nombreTabla = NomApellido[2];
								String apellido = NomApellido[3];
								String datoApellido = NomApellido[4];
								String fechas = NomApellido[5];
								String datoFecha = NomApellido[6];
								String orden = NomApellido[7];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("apellido: " + apellido);
								System.out.println("datoapellido: " + datoApellido);
								System.out.println("fecha: " + fechas);
								System.out.println("datoFecha: " + datoFecha);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + apellido + ","
										+ datoApellido + "," + fechas + "," + datoFecha + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								escriptor.write(palabra);
								escriptor.newLine();
								escriptor.flush();
								System.out.println("El usuario con codigo: " + codigoUserRecibido
										+ "\nenvia los datos siguiente: \n" + palabra);

								perEnt = new ObjectInputStream(socket.getInputStream());
								Object receivedData = perEnt.readObject();

								if (receivedData instanceof List) {
									List<Jornada> listaJornadaNomFecha = (List<Jornada>) receivedData;
									for (Jornada jornada : listaJornadaNomFecha) {
										System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
												+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido() + "\n"
												+ "Codigo tarjeta: " + jornada.getCodicard() + "\n" + "Hora entrada: "
												+ jornada.getHoraentrada() + "\n" + "Hora salida: "
												+ jornada.getHorasalida() + "\n" + "Total: " + jornada.getTotal() + "\n"
												+ "Fecha: " + jornada.getFecha());
										System.out.println(
												"____________________________________________________________________");
									}
									perEnt.getObjectInputFilter();
								} else if (receivedData instanceof String) {
									String errorMessage = (String) receivedData;
									System.out.println(errorMessage);
								} else {
									System.out.println("Datos inesperados recibidos del servidor");
								}

							} else if (NomApellido[2].equals("3") && NomApellido[3].equals("codicard")
									&& NomApellido[5].equals("fecha")) {
								String codigoUserRecibido = NomApellido[0];
								String crud = NomApellido[1];
								String nombreTabla = NomApellido[2];
								String codicard = NomApellido[3];
								String datoCodicard = NomApellido[4];
								String fechas = NomApellido[5];
								String datoFecha = NomApellido[6];
								String orden = NomApellido[7];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("codicard: " + codicard);
								System.out.println("datocodicard: " + datoCodicard);
								System.out.println("fecha: " + fechas);
								System.out.println("datoFecha: " + datoFecha);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + codicard + ","
										+ datoCodicard + "," + fechas + "," + datoFecha + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								escriptor.write(palabra);
								escriptor.newLine();
								escriptor.flush();
								System.out.println("El usuario con codigo: " + codigoUserRecibido
										+ "\nenvia los datos siguiente: \n" + palabra);

								perEnt = new ObjectInputStream(socket.getInputStream());
								Object receivedData = perEnt.readObject();

								if (receivedData instanceof List) {
									List<Jornada> listaJornadaNomFecha = (List<Jornada>) receivedData;
									for (Jornada jornada : listaJornadaNomFecha) {
										System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
												+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido() + "\n"
												+ "Codigo tarjeta: " + jornada.getCodicard() + "\n" + "Hora entrada: "
												+ jornada.getHoraentrada() + "\n" + "Hora salida: "
												+ jornada.getHorasalida() + "\n" + "Total: " + jornada.getTotal() + "\n"
												+ "Fecha: " + jornada.getFecha());
										System.out.println(
												"____________________________________________________________________");
									}
									perEnt.getObjectInputFilter();
								} else if (receivedData instanceof String) {
									String errorMessage = (String) receivedData;
									System.out.println(errorMessage);
								} else {
									System.out.println("Datos inesperados recibidos del servidor");
								}

							} else if (NomApellido[2].equals("2") && NomApellido[3].equals("nomNuevo")
									&& NomApellido[5].equals("nom")) {
								String codigoUserRecibido = NomApellido[0];
								String crud = NomApellido[1];
								String nombreTabla = NomApellido[2];
								String nomNuevo = NomApellido[3];
								String datoNomnuevo = NomApellido[4];
								String nom = NomApellido[5];
								String datoNom = NomApellido[6];
								String orden = NomApellido[7];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("nomNuevo: " + nomNuevo);
								System.out.println("datoNomnuevo: " + datoNomnuevo);
								System.out.println("nom: " + nom);
								System.out.println("datoNom: " + datoNom);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + nomNuevo + ","
										+ datoNomnuevo + "," + nom + "," + datoNom + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								escriptor.write(palabra);
								escriptor.newLine();
								escriptor.flush();
								System.out.println("El usuario con codigo: " + codigoUserRecibido
										+ "\nenvia los datos siguiente: \n" + palabra);

								perEnt = new ObjectInputStream(socket.getInputStream());
								Object receivedData = perEnt.readObject();

								if (receivedData instanceof List) {
									List<Empresa> updateNomempresa = (List<Empresa>) receivedData;
									for (Empresa empresa : updateNomempresa) {
										System.out.println("Nombre de empresa modificado correctamente:");
										System.out.println("____________________________________________________________________");
										System.out.println("\nNombre empresa: " + datoNomnuevo + "\n" + "Direccion: "
												+ empresa.getAddress() + "\n" + "Telefonon: " + empresa.getTelephon());
										System.out.println(
												"____________________________________________________________________");
									}
									perEnt.getObjectInputFilter();
								} else if (receivedData instanceof String) {
									String errorMessage = (String) receivedData;
									System.out.println(errorMessage);
								} else {
									System.out.println("Datos inesperados recibidos del servidor");
								}

							} else if (insertEmpresas[2].equals("3") && insertEmpresas[3].equals("nom")
									&& insertEmpresas[5].equals("apellido") && insertEmpresas[7].equals("fecha")) {
								String codigoUserRecibido = insertEmpresas[0];
								String crud = insertEmpresas[1];
								String nombreTabla = insertEmpresas[2];
								String nom = insertEmpresas[3];
								String datoNom = insertEmpresas[4];
								String apellido = insertEmpresas[5];
								String datoApellido = insertEmpresas[6];
								String fechas = insertEmpresas[7];
								String datoFecha = insertEmpresas[8];
								String orden = insertEmpresas[9];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("nom: " + nom);
								System.out.println("datoNom: " + datoNom);
								System.out.println("apellido: " + apellido);
								System.out.println("datoapellido: " + datoApellido);
								System.out.println("fecha: " + fechas);
								System.out.println("datoFecha: " + datoFecha);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + nom + ","
										+ datoNom + "," + apellido + "," + datoApellido + "," + fechas + "," + datoFecha
										+ "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								escriptor.write(palabra);
								escriptor.newLine();
								escriptor.flush();
								System.out.println("El usuario con codigo: " + codigoUserRecibido
										+ "\nenvia los datos siguiente: \n" + palabra);

								perEnt = new ObjectInputStream(socket.getInputStream());
								Object receivedData = perEnt.readObject();

								if (receivedData instanceof List) {
									List<Jornada> listaJornadaNomFecha = (List<Jornada>) receivedData;
									for (Jornada jornada : listaJornadaNomFecha) {
										System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
												+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido() + "\n"
												+ "Codigo tarjeta: " + jornada.getCodicard() + "\n" + "Hora entrada: "
												+ jornada.getHoraentrada() + "\n" + "Hora salida: "
												+ jornada.getHorasalida() + "\n" + "Total: " + jornada.getTotal() + "\n"
												+ "Fecha: " + jornada.getFecha());
										System.out.println(
												"____________________________________________________________________");
									}
									perEnt.getObjectInputFilter();
								} else if (receivedData instanceof String) {
									String errorMessage = (String) receivedData;
									System.out.println(errorMessage);
								} else {
									System.out.println("Datos inesperados recibidos del servidor");
								}

							} else if (NomApellido[7].equals("0") || NomApellido[7].equals("1")) {
								String codigoUserRecibido = NomApellido[0];
								String crud = NomApellido[1];
								String nombreTabla = NomApellido[2];
								String nom = NomApellido[3];
								String datoNom = NomApellido[4];
								String apellido = NomApellido[5];
								String datoApellido = NomApellido[6];
								String orden = NomApellido[7];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("nom: " + nom);
								System.out.println("datoNom: " + datoNom);
								System.out.println("apellido: " + apellido);
								System.out.println("datoApellido: " + datoApellido);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + nom + ","
										+ datoNom + "," + apellido + "," + datoApellido + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								if (crud.equals("0")) {
									if (nombreTabla.equals("0") && nom.equals("nom") && apellido.equals("apellido")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Empleados> listaEmpleadosNomApellido = (List<Empleados>) receivedData;
											for (Empleados empleado : listaEmpleadosNomApellido) {
												System.out.println("Dni: " + empleado.getDni() + "\n" + "Nombre: "
														+ empleado.getNom() + "\n" + "Apellido: "
														+ empleado.getApellido() + "\n" + "Nombre empresa: "
														+ empleado.getNomempresa() + "\n" + "Departamento: "
														+ empleado.getDepartament() + "\n" + "Codigo tarjeta: "
														+ empleado.getCodicard() + "\n" + "Mail: " + empleado.getMail()
														+ "\n" + "Telefono: " + empleado.getTelephon() + "\n");
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}

									} else if (nombreTabla.equals("3") && nom.equals("nom")
											&& apellido.equals("apellido")) {
										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											List<Jornada> listaJornadaNomApellido = (List<Jornada>) receivedData;

											for (Jornada jornada : listaJornadaNomApellido) {
												System.out.println("Dni: " + jornada.getDni() + "\n" + "Nombre: "
														+ jornada.getNom() + "\n" + "Apellido: " + jornada.getApellido()
														+ "\n" + "Codigo tarjeta: " + jornada.getCodicard() + "\n"
														+ "Hora entrada: " + jornada.getHoraentrada() + "\n"
														+ "Hora salida: " + jornada.getHorasalida() + "\n" + "Total: "
														+ jornada.getTotal() + "\n" + "Fecha: " + jornada.getFecha());
												System.out.println(
														"____________________________________________________________________");
											}
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
									}
								}
							} else if (insertEmpresas[9].equals("0") || insertEmpresas[9].equals("1")) {
								String codigoUserRecibido = insertEmpresas[0];
								String crud = insertEmpresas[1];
								String nombreTabla = insertEmpresas[2];
								String nom = insertEmpresas[3];
								String datoNom = insertEmpresas[4];
								String address = insertEmpresas[5];
								String datoAddress = insertEmpresas[6];
								String telephon = insertEmpresas[7];
								String datoTelephon = insertEmpresas[8];
								String orden = insertEmpresas[9];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("nom: " + nom);
								System.out.println("datoNom: " + datoNom);
								System.out.println("address: " + address);
								System.out.println("datoAddress: " + datoAddress);
								System.out.println("telephon: " + telephon);
								System.out.println("datoTelephon: " + datoTelephon);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + nom + ","
										+ datoNom + "," + address + "," + datoAddress + "," + telephon + ","
										+ datoTelephon + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								if (crud.equals("1")) {
									if (nombreTabla.equals("2") && nom.equals("nom") && address.equals("address")
											&& telephon.equals("telephon")) {

										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();
										if (receivedData instanceof List) {

											System.out.println(("Empresa creada correctamente, sus datos son: \n"));
											System.out.println("Nombre: " + datoNom + "\n" + "Adrress: " + datoAddress
													+ "\n" + "Telefono: " + datoTelephon + "\n");
											System.out.println(
													"____________________________________________________________________");

											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
									}
								}

							} else if (insertUsuarios[11].equals("0") || insertUsuarios[11].equals("1")) {
								String codigoUserRecibido = insertUsuarios[0];
								String crud = insertUsuarios[1];
								String nombreTabla = insertUsuarios[2];
								String login = insertUsuarios[3];
								String datoLogin = insertUsuarios[4];
								String pass = insertUsuarios[5];
								String datoPass = insertUsuarios[6];
								String numTipe = insertUsuarios[7];
								String datoNumTipe = insertUsuarios[8];
								String dni = insertUsuarios[9];
								String datoDni = insertUsuarios[10];
								String orden = insertUsuarios[11];

								System.out.println(
										"____________________________________________________________________");
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
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + login + ","
										+ datoLogin + "," + pass + "," + datoPass + "," + numTipe + "," + datoNumTipe
										+ "," + dni + "," + datoDni + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								if (crud.equals("1")) {
									if (nombreTabla.equals("1") && login.equals("login") && pass.equals("pass")
											&& numTipe.equals("numtipe") && dni.equals("dni")) {

										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											System.out.println(("Usuario creado correctamente, sus datos son: \n"));
											System.out.println("Login: " + datoLogin + "\n" + "Pass: " + datoPass + "\n"
													+ "Num Tipe: " + datoNumTipe + "\n" + "Dni: " + datoDni + "\n");
											System.out.println(
													"____________________________________________________________________");
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
									}
								}
							} else if (insertEmpleado[19].equals("0") && insertEmpleado[9].equals("nomempresa")
									|| insertEmpleado[19].equals("1") && insertEmpleado[9].equals("nomempresa")) {
								String codigoUserRecibido = insertEmpleado[0];
								String crud = insertEmpleado[1];
								String nombreTabla = insertEmpleado[2];
								String dni = insertEmpleado[3];
								String datoDni = insertEmpleado[4];
								String nom = insertEmpleado[5];
								String datoNom = insertEmpleado[6];
								String apellido = insertEmpleado[7];
								String datoApellido = insertEmpleado[8];
								String nomempresa = insertEmpleado[9];
								String datoNomempresa = insertEmpleado[10];
								String departament = insertEmpleado[11];
								String datoDepartament = insertEmpleado[12];
								String codicard = insertEmpleado[13];
								String datoCodicard = insertEmpleado[14];
								String mail = insertEmpleado[15];
								String datoMail = insertEmpleado[16];
								String telephon = insertEmpleado[17];
								String datoTelephon = insertEmpleado[18];
								String orden = insertEmpleado[19];

								System.out.println(
										"____________________________________________________________________");
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
								System.out.println("datoCodicard: " + datoCodicard);
								System.out.println("mail: " + mail);
								System.out.println("datoMail: " + datoMail);
								System.out.println("telephon: " + telephon);
								System.out.println("datoTelephon: " + datoTelephon);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + dni + ","
										+ datoDni + "," + nom + "," + datoNom + "," + apellido + "," + datoApellido
										+ "," + nomempresa + "," + datoNomempresa + "," + departament + ","
										+ datoDepartament + "," + codicard + "," + datoCodicard + "," + mail + ","
										+ datoMail + "," + telephon + "," + datoTelephon + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								if (crud.equals("1")) {
									if (nombreTabla.equals("0") && dni.equals("dni") && nom.equals("nom")
											&& apellido.equals("apellido") && nomempresa.equals("nomempresa")
											&& departament.equals("departament") && codicard.equals("codicard")
											&& mail.equals("mail") && telephon.equals("telephon")) {

										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											System.out.println(("Empleado creado correctamente, sus datos son: \n"));
											System.out.println("Dni: " + datoDni + "\n" + "Nombre: " + datoNom + "\n"
													+ "Apellido: " + datoApellido + "\n" + "Nombre empresa: "
													+ datoNomempresa + "\n" + "Departamento: " + datoDepartament + "\n"
													+ "Codigo tarjeta: " + datoCodicard + "\n" + "Mail: " + datoMail
													+ "\n" + "Telefono: " + datoTelephon + "\n");
											System.out.println(
													"____________________________________________________________________");
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
									}
								}
							} else if (insertEmpleado[19].equals("0") && insertEmpleado[9].equals("codicard")
									|| insertEmpleado[19].equals("1") && insertEmpleado[9].equals("codicard")) {
								String codigoUserRecibido = insertEmpleado[0];
								String crud = insertEmpleado[1];
								String nombreTabla = insertEmpleado[2];
								String dni = insertEmpleado[3];
								String datoDni = insertEmpleado[4];
								String nom = insertEmpleado[5];
								String datoNom = insertEmpleado[6];
								String apellido = insertEmpleado[7];
								String datoApellido = insertEmpleado[8];
								String codicard = insertEmpleado[9];
								String datoCodicard = insertEmpleado[10];
								String horaentrada = insertEmpleado[11];
								String datoHoraentrada = insertEmpleado[12];
								String horasalida = insertEmpleado[13];
								String datoHorasalida = insertEmpleado[14];
								String total = insertEmpleado[15];
								String datoTotal = insertEmpleado[16];
								String fechas = insertEmpleado[17];
								String datoFecha = insertEmpleado[18];
								String orden = insertEmpleado[19];

								System.out.println(
										"____________________________________________________________________");
								System.out.println("codigoUserRecibido: " + codigoUserRecibido);
								System.out.println("crud: " + crud);
								System.out.println("nombreTabla: " + nombreTabla);
								System.out.println("dni: " + dni);
								System.out.println("datoDni: " + datoDni);
								System.out.println("nom: " + nom);
								System.out.println("datoNom: " + datoNom);
								System.out.println("apellido: " + apellido);
								System.out.println("datoApellido: " + datoApellido);
								System.out.println("codicard: " + codicard);
								System.out.println("datoCodicard: " + datoCodicard);
								System.out.println("horaentrad: " + horaentrada);
								System.out.println("datoHoraentrada: " + datoHoraentrada);
								System.out.println("horasalida: " + horasalida);
								System.out.println("datoHorasalida: " + datoHorasalida);
								System.out.println("total: " + total);
								System.out.println("datoTotal: " + datoTotal);
								System.out.println("fecha: " + fechas);
								System.out.println("datoFecha: " + datoFecha);
								System.out.println("orden: " + orden);
								System.out.println(
										"____________________________________________________________________");

								palabra = codigoUserRecibido + "," + crud + "," + nombreTabla + "," + dni + ","
										+ datoDni + "," + nom + "," + datoNom + "," + apellido + "," + datoApellido
										+ "," + codicard + "," + datoCodicard + "," + horaentrada + ","
										+ datoHoraentrada + "," + horasalida + "," + datoHorasalida + "," + total + ","
										+ datoTotal + "," + fechas + "," + datoFecha + "," + orden;

								if (codigoUserRecibido.equals("")) {
									codigoUserRecibido = "0";
								}

								if (crud.equals("1")) {
									if (nombreTabla.equals("3") && dni.equals("dni") && nom.equals("nom")
											&& apellido.equals("apellido") && codicard.equals("codicard")
											&& horaentrada.equals("horaentrada") && horasalida.equals("horasalida")
											&& total.equals("total") && fechas.equals("fecha")) {

										escriptor.write(palabra);
										escriptor.newLine();
										escriptor.flush();
										System.out.println("El usuario con codigo: " + codigoUserRecibido
												+ "\nenvia los datos siguiente: \n" + palabra);

										perEnt = new ObjectInputStream(socket.getInputStream());
										Object receivedData = perEnt.readObject();

										if (receivedData instanceof List) {
											System.out.println(("Empleado creado correctamente, sus datos son: \n"));
											System.out.println("Dni: " + datoDni + "\n" + "Nombre: " + datoNom + "\n"
													+ "Apellido: " + datoApellido + "\n" + "Codigo Tarjeta: "
													+ datoCodicard + "\n" + "Hora entrada: " + datoHoraentrada + "\n"
													+ "Hora salida: " + datoHorasalida + "\n" + "Total: " + datoTotal
													+ "\n" + "Fecha: " + datoFecha + "\n");
											System.out.println(
													"____________________________________________________________________");
											perEnt.getObjectInputFilter();
										} else if (receivedData instanceof String) {
											String errorMessage = (String) receivedData;
											System.out.println(errorMessage);
										} else {
											System.out.println("Datos inesperados recibidos del servidor");
										}
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
			System.out.println(ex + "\n Problema con la clase desconocida");
		} catch (IOException ex) {
			System.out.println("____________________________________________________________________");
			System.out.println(ex + "\n Problema con entrada y salida sockets");
		}
	}

	public static int contarCaracteres(String cadena, char caracter) {
		int posicion, contador = 0;
		posicion = cadena.indexOf(caracter);
		while (posicion != -1) {
			contador++;
			posicion = cadena.indexOf(caracter, posicion + 1);
		}
		return contador;
	}
}
