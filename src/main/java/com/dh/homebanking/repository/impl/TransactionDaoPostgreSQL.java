package com.dh.homebanking.repository.impl;

import com.dh.homebanking.model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoPostgreSQL {

    //root@srv451320:~# docker run --name mi_postgres -e POSTGRES_USER=database -e POSTGRES_PASSWORD=database -d -p 5432:5432 postgres
    //c9c56377750c9fd8dab8262d0fcf7b211e67f71fc8f45b10d3c5f0c643369ddc
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

    public void guardar(Integer destinyAccountMoneyReceived, Integer originAccountMoneyLost, int originAccount, int destinyAccount) {
        log.debug("Generando una nueva transaccion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia especificando que el ID lo auto incrementa la base de datos y que nos devuelva esa Key es decir ID
            preparedStatement = connection.prepareStatement("INSERT INTO transaction(amount) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, destinyAccountMoneyReceived);
            preparedStatement.executeUpdate();

            log.debug("Insertamos en transaction");

            //3 Ejecutar una sentencia SQL y obtener los ID que se autogeneraron en la base de datos
            ResultSet keys = preparedStatement.getGeneratedKeys();
            int transactionID = 0;
            if(keys.next()) {
                transactionID = keys.getInt(1);
                //transaction.setTransactionId(transactionID);
            }

            // Sentencia que actualiza la cuenta de origen
            preparedStatement = connection.prepareStatement("update account set balance = balance - ? where account_id = ?");
            preparedStatement.setInt(1, originAccountMoneyLost);
            preparedStatement.setInt(2, originAccount);
            preparedStatement.executeUpdate();

            // Sentencia que actualiza el balance de la cuenta de destino
            preparedStatement = connection.prepareStatement("update account set balance = balance + ? where account_id = ?");
            preparedStatement.setInt(1, destinyAccountMoneyReceived);
            preparedStatement.setInt(2, destinyAccount);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO account_transaction(transaction_id, origin_account, destiny_account) VALUES(?, ?, ?)");
            preparedStatement.setInt(1, transactionID);
            preparedStatement.setInt(2, originAccount);
            preparedStatement.setInt(3, destinyAccount);
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        //return client;
    }


    public List<TransactionData> buscarTodos(int clientId) {
        log.debug("Buscando las cuentas del client con id = " + clientId);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<TransactionData> transactionsData = new ArrayList<>();;
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            //transactionsData = new ArrayList<>();
            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT *  FROM transaction  INNER JOIN account_transaction ON transaction.transaction_id = account_transaction.transaction_id  WHERE origin_account IN (SELECT account_id FROM account WHERE client_id = ?) OR destiny_account IN (SELECT account_id FROM account WHERE client_id = ?);");
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, clientId);

            //3 Ejecutar una sentencia SQL
            ResultSet result = preparedStatement.executeQuery();
// transaction_id | amount  |       operation_time       | transaction_id | origin_account | destiny_account
            //4 Obtener resultados
            while (result.next()) {
                Integer transaction_id = result.getInt("transaction_id");
                Integer amount = result.getInt("amount");
                Date client_status_date = result.getDate("operation_time");
                Integer origin_account = result.getInt("origin_account");
                Integer destiny_account = result.getInt("destiny_account");
                TransactionData account = new TransactionData(transaction_id, amount, client_status_date, origin_account, destiny_account);
                transactionsData.add(account);
            }

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            log.error(throwables);
        }

        return transactionsData;
    }


}
