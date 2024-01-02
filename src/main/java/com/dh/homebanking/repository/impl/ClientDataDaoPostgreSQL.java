package com.dh.homebanking.repository.impl;

import com.dh.homebanking.model.Account;
import com.dh.homebanking.model.ClientData;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class ClientDataDaoPostgreSQL {
    final static Logger log = Logger.getLogger(ClientDataDaoPostgreSQL.class);
    private final static String DB_JDBC_DRIVER = "org.postgresql.Driver";
    private final static String DB_URL = "your database URL";
    private final static String DB_USER = "your database user";
    private final static String DB_PASSWORD = "your database password";


    public void guardar(int clientID, ClientData clientData) {
        log.debug("guardando un nuevo client data");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia especificando que el ID lo auto incrementa la base de datos y que nos devuelva esa Key es decir ID
            preparedStatement = connection.prepareStatement("INSERT INTO client_data(client_id, first_name, last_name, born_date, dni, phone_number, nationality) VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, clientID);
            preparedStatement.setString(2, clientData.getFirstName());
            preparedStatement.setString(3, clientData.getLastName());
            java.util.Date utilDate = clientData.getBornDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setString(5, clientData.getDni());
            preparedStatement.setString(6, clientData.getPhoneNumber());
            preparedStatement.setString(7, clientData.getNationality());

            //3 Ejecutar una sentencia SQL y obtener los ID que se autogeneraron en la base de datos
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if(keys.next())
                clientData.setClientDataId(keys.getInt(1));

            preparedStatement.close();



        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public ClientData buscar(Integer id) {
        log.debug("Buscando al client con id = " + id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ClientData clientData = null;
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM client_data where client_id = ?");
            preparedStatement.setInt(1,id);

            //3 Ejecutar una sentencia SQL
            ResultSet result = preparedStatement.executeQuery();

            //4 Obtener resultados
            while (result.next()) {
                Integer client_data_id = result.getInt("client_data_id");
                Integer client_id = result.getInt("client_id");
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                Date born_date = result.getDate("born_date");
                String dni = result.getString("dni");
                String phone_number = result.getString("phone_number");
                String nationality = result.getString("nationality");


                clientData = new ClientData(client_data_id, client_id,first_name,last_name,born_date,dni, phone_number, nationality);
            }

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            log.error(throwables);
        }

        return clientData;
    }


    public void eliminar(Integer id) {

    }


    public List<ClientData> buscarTodos() {
        return null;
    }


    public ClientData actualizar(ClientData clientData) {
        return null;
    }
}
