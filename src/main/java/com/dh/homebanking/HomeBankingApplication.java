package com.dh.homebanking;

import javax.swing.*;


//import com.dh.homebanking.frame.MyFrame;
import com.dh.homebanking.interfaceElements.componentCreator;

public class HomeBankingApplication {

    public static void main(String[] args) {
        // //SpringApplication.run(HomeBankingApplication.class, args);

        //        // // Creamos el objeto DAO
        //        // ClientDaoPostgreSQL clientDaoPostgreSQL = new ClientDaoPostgreSQL();
        
        // // // Crear un objeto client
        // Client client = new Client("A");

        // // Guardar el client en la base de datos
        // clientDaoPostgreSQL.guardar(client);

        // // Borramos cliente
        // clientDaoPostgreSQL.eliminar(4);

        // // Buscando Clientes
        // Client client2 = clientDaoPostgreSQL.buscar(3);
        // System.out.println(client2.toString());

        // // Buscar todos los clientes
        // List<Client> listaClientes = clientDaoPostgreSQL.buscarTodos();
        // for (Client cliente : listaClientes) {
        //     System.out.println(cliente.toString());
        // }

        // // Actualizar Cliente
        // // Primero busco el cliente que quiero actualizar
        // Client client3 = clientDaoPostgreSQL.buscar(5);
        // System.out.println(client3.toString());
        // client3.setClient_status("SUS");
        // clientDaoPostgreSQL.actualizar(client3);
        // System.out.println(client3.toString());

        // Creating Buttons
        // Main img


        // Initializing Swing app
        JFrame frame = componentCreator.createFrame();
        componentCreator.getHomePanel(frame);


    }
}





















