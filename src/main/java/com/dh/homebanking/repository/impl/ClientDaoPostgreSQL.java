package com.dh.homebanking.repository.impl;

import com.dh.homebanking.model.Account;
import com.dh.homebanking.model.ClientData;
import org.apache.log4j.Logger;

import com.dh.homebanking.model.Client;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class ClientDaoPostgreSQL {

    final static Logger log = Logger.getLogger(ClientDaoPostgreSQL.class);
    // Conexion a mi postgres, solo hace falta poner el puerto
    // Comanda de creacion de mi docker en mi vps: docker run --name postgres -e POSTGRES_USER=database -e POSTGRES_PASSWORD=database -p 5432:5432 -d postgres
    // Comando de acceso al contenedor: docker exec -it 05e96299df6c bash
    // Comando de acceso a la base de datos del contenedor: root@05e96299df6c:/# psql -U database
    /*
    * Tener en cuenta esto:
    root@gastongaitanvps:~# docker exec -it 05e96299df6c bash
    root@05e96299df6c:/# psql -U database
    psql (16.0 (Debian 16.0-1.pgdg120+1))
    Type "help" for help.

    database=#
    * */
    private final static String DB_JDBC_DRIVER = "org.postgresql.Driver";
    private final static String DB_URL = "jdbc:postgresql://195.35.16.20:5432/";
    private final static String DB_USER = "database"; // Por alguna razon para conectarme al que levante en mi vps tuve que hacerlo
    // con el user que use en mi comanda de creacion
    private final static String DB_PASSWORD = "database";
     /*
    Primero hay que tener en cuenta que hay que conectarse al shell del docker y crear la base de datos 

    root@gastongaitanvps:~# docker exec -it ca789e8ca35a psql -U database
    psql (16.0 (Debian 16.0-1.pgdg120+1))
    Type "help" for help.
    database=# CREATE TABLE odontologos (
        id serial PRIMARY KEY,
        nombre VARCHAR(255),
        apellido VARCHAR(255),
        matricula INTEGER
    );
    CREATE TABLE
    database=# select * from odontologos;
    id | nombre | apellido | matricula
    ----+--------+----------+-----------
    1 | Gastón | Gaitan   |    445865
    (1 row)
    */

    public void guardar(Client client, ClientData clientData, Account account) {
        log.debug("guardando un nuevo client");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia especificando que el ID lo auto incrementa la base de datos y que nos devuelva esa Key es decir ID
            preparedStatement = connection.prepareStatement("INSERT INTO client(client_type, username, email, password) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getClientType());
            preparedStatement.setString(2, client.getUsername());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPassword());

            //3 Ejecutar una sentencia SQL y obtener los ID que se autogeneraron en la base de datos
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            int clientId = 0;
            if(keys.next()) {
                clientId = keys.getInt(1);
                client.setClientId(clientId);
            }

            ClientDataDaoPostgreSQL clientDataDaoPostgreSQL = new ClientDataDaoPostgreSQL();
            clientDataDaoPostgreSQL.guardar(clientId, clientData);

            // Poner el account Dao y pasarlo con el client ID tambien
            AcccountDaoPostgreSQL accountDaoPostgreSQL = new AcccountDaoPostgreSQL();
            accountDaoPostgreSQL.guardar(clientId, account);

            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        //return client;
    }

    public void eliminar(Integer id) {
        log.debug("Borrando client con id : "+id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("DELETE FROM client_data where client_id = ?");
            preparedStatement.setInt(1,id);

            //3 Ejecutar una sentencia SQL
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM client where client_id = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            log.error(throwables);
        }


    }

    public Client buscar(Integer id) {
        log.debug("Buscando al client con id = " + id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Client client = null;
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            List<Client> clients = new ArrayList<>();
            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM client where client_id = ?");
            preparedStatement.setInt(1,id);

            //3 Ejecutar una sentencia SQL
            ResultSet result = preparedStatement.executeQuery();

            //4 Obtener resultados
            while (result.next()) {
                Integer client_id = result.getInt("client_id");
                String status = result.getString("status");
                Date client_status_date = result.getDate("client_status_date");
                String client_type = result.getString("client_type");
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("password");


                client = new Client(client_id, status, client_status_date, client_type, username, email, password);
                clients.add(client);
            }
    
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            log.error(throwables);
        }
    
        return client;
    }

    public Client buscarPorUsername(String usernameToSearch) {
        log.debug("Buscando la data del cliente con user = " + usernameToSearch);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Client client = null;
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            List<Client> clients = new ArrayList<>();

            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM client where username = ?");
            preparedStatement.setString(1,usernameToSearch);

            //3 Ejecutar una sentencia SQL
            ResultSet result = preparedStatement.executeQuery();

            //4 Obtener resultados
            while (result.next()) {
                Integer client_id = result.getInt("client_id");
                String status = result.getString("status");
                Date client_status_date = result.getDate("client_status_date");
                String client_type = result.getString("client_type");
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("password");


                client = new Client(client_id, status, client_status_date, client_type, username, email, password);
                clients.add(client);
            }

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            log.error(throwables);
        }

        return client;
    }

    public List<Client> buscarTodos() {
        log.debug("Buscando todos los clients");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Client> clients = new ArrayList<>();

        try {
            // 1. Levantar el driver y conectarse
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 2. Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM client");

            // 3. Ejecutar una sentencia SQL
            ResultSet result = preparedStatement.executeQuery();

            // 4. Obtener resultados
            while (result.next()) {
                Integer client_id = result.getInt("client_id");
                String status = result.getString("status");
                Date client_status_date = result.getDate("client_status_date");
                String client_type = result.getString("client_type");
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("password");

                // Crea un nuevo objeto client con los valores obtenidos y agrega a la lista
                Client client = new Client(client_id, status, client_status_date, client_type, username, email, password);
                clients.add(client);
            }

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            log.error(throwables);
        }

        return clients;
    }


//    public Client actualizar(Client client) {
//    Connection connection = null;
//    PreparedStatement preparedStatement = null;
//
//    try {
//        // 1. Levantar el driver y conectarse
//        Class.forName(DB_JDBC_DRIVER);
//        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//
//        // 2. Crear una sentencia
//        preparedStatement = connection.prepareStatement("UPDATE client SET client_status = ?, client_status_date = ?, client_type = ? WHERE client_id = ?");
//        preparedStatement.setString(1, client.getStatus());
//        preparedStatement.setDate(2, new java.sql.Date(client.getClientStatusDate().getTime()));
//        preparedStatement.setString(4, client.getClientType());
//        preparedStatement.setInt(5, client.getClientId());
//
//        // 3. Ejecutar una sentencia SQL
//        preparedStatement.executeUpdate();
//
//        preparedStatement.close();
//
//    } catch (SQLException | ClassNotFoundException throwables) {
//        throwables.printStackTrace();
//        log.error(throwables);
//    }
//    return client;
//}

}
