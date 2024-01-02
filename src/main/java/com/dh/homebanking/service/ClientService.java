package com.dh.homebanking.service;

import java.util.List;

import com.dh.homebanking.model.Client;
import com.dh.homebanking.repository.IDao;

public class ClientService {

    private IDao<Client> clientDao;

    public ClientService(IDao<Client> clientDao) {
        this.clientDao = clientDao;
    }

    public Client registrarClient(Client client) {
        return clientDao.guardar(client);

    }

    public void eliminar(Integer id) {
        clientDao.eliminar(id);
    }

    public Client buscar(Integer id) {
        return clientDao.buscar(id);
    }

    public List<Client> buscarTodos() {
        return clientDao.buscarTodos();
    }

    public Client actualizar(Client client) {
        return clientDao.actualizar(client);
    }


}
