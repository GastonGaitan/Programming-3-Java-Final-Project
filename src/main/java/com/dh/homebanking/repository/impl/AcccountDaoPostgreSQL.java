package com.dh.homebanking.repository.impl;

import com.dh.homebanking.model.Account;
import com.dh.homebanking.model.Client;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcccountDaoPostgreSQL {

    final static Logger log = Logger.getLogger(AcccountDaoPostgreSQL.class);
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
//    private final static String DB_JDBC_DRIVER = "org.postgresql.Driver";
//    private final static String DB_URL = "jdbc:postgresql://191.101.70.195:5432/";
//    private final static String DB_USER = "database"; // Por alguna razon para conectarme al que levante en mi vps tuve que hacerlo
//    // con el user que use en mi comanda de creacion
//    private final static String DB_PASSWORD = "database";

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

    public void guardar(int clientId, Account account) {
        log.debug("guardando un nuevo account");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia especificando que el ID lo auto incrementa la base de datos y que nos devuelva esa Key es decir ID
            preparedStatement = connection.prepareStatement("INSERT INTO account(client_id, balance, currency_type) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, account.getBalance());
            preparedStatement.setString(3, account.getcurrencyType());

            //3 Ejecutar una sentencia SQL y obtener los ID que se autogeneraron en la base de datos
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if(keys.next())
                account.setaccountID(keys.getInt(1));

            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Account> buscarTodos(int clientId) {
        log.debug("Buscando las cuentas del client con id = " + clientId);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Client client = null;
        List<Account> accounts = null;
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            accounts = new ArrayList<>();
            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM account where client_id = ?");
            preparedStatement.setInt(1, clientId);

            //3 Ejecutar una sentencia SQL
            ResultSet result = preparedStatement.executeQuery();

            //4 Obtener resultados
            while (result.next()) {
                Integer account_id = result.getInt("account_id");
                Integer client_id = result.getInt("client_id");
                Integer balance = result.getInt("balance");
                String currency_type = result.getString("currency_type");

                Account account = new Account(account_id, client_id, balance, currency_type);
                accounts.add(account);
            }

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            log.error(throwables);
        }

        return accounts;
    }

    public Account buscarPorAccountId(int accountId) {
        log.debug("Buscando la cuenta con id = " + accountId);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Client client = null;
        Account account = null;
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM account where account_id = ?");
            preparedStatement.setInt(1, accountId);

            //3 Ejecutar una sentencia SQL
            ResultSet result = preparedStatement.executeQuery();

            //4 Obtener resultados
            while (result.next()) {
                Integer account_id = result.getInt("account_id");
                Integer client_id = result.getInt("client_id");
                Integer balance = result.getInt("balance");
                String currency_type = result.getString("currency_type");
                account = new Account(account_id, client_id, balance, currency_type);
            }

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            log.error(throwables);
        }

        return account;
    }

}
