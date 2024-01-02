package com.dh.homebanking.interfaceElements;
import com.dh.homebanking.defaults.Defaults;
import com.dh.homebanking.model.*;
import com.dh.homebanking.repository.impl.ClientDaoPostgreSQL;
import com.dh.homebanking.repository.impl.ClientDataDaoPostgreSQL;
import com.dh.homebanking.repository.impl.AcccountDaoPostgreSQL;
import com.dh.homebanking.repository.impl.TransactionDaoPostgreSQL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class componentCreator {

    // Frame creation
    public  static  JFrame createFrame(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Homebanking Application");
        frame.setLayout(null); // Si sacp estp aparece el home banking icono
        frame.setSize(1200, 650);
        frame.setResizable(false);
        ImageIcon image = new ImageIcon("src\\main\\java\\com\\dh\\homebanking\\interfaceElements\\logo.png");
        frame.setIconImage(image.getImage());
        System.out.println("Directorio de trabajo actual: " + System.getProperty("user.dir"));
        frame.getContentPane().setBackground(new Color(48, 51,73));
        frame.setVisible(true);
        return frame;
    }

    // Home bank Icon
    public static JLabel createHomeLabel() {
        JLabel label = new JLabel();
        label.setText("Homebanking application by Gastón Gaitan");
        label.setForeground(Color.white);
        label.setIcon(Defaults.getResizeImage("src\\main\\java\\com\\dh\\homebanking\\interfaceElements\\logo.png", 100, 100));
        label.setBounds(450, 120, 250, 250);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    // Home menu, we use this button to access to the admin button
    public  static JButton createAccessAsAdministratorButton(){
        JButton button = new JButton("Access as administrator");
        button.setBackground(Color.gray);
        button.setForeground(Color.white);
        button.setBounds(450,330,250,40);
        button.setFocusable(false);
        //button.addActionListener(e -> System.out.println("Access as Administrator"));
        return  button;
    }

    // Home menu, we use this button to access as client
    public  static JButton createAccessAsClientButton(){
        JButton accessAsClient = new JButton("Access as client");
        accessAsClient.setBackground(Color.gray);
        accessAsClient.setBounds(450,380,250,40);
        accessAsClient.setForeground(Color.white);
        accessAsClient.setFocusable(false);
        return  accessAsClient;
    }

    // Home section where you select if you want to access as+
    public static void getHomePanel(JFrame frame){
        JLabel homeLabel = componentCreator.createHomeLabel();

        // Access as Administrator
        JButton accessAsAdministrator = componentCreator.createAccessAsAdministratorButton();

        // Access as client
        JButton accessAsClient = componentCreator.createAccessAsClientButton();

        frame.add(homeLabel);
        frame.add(accessAsAdministrator);
        frame.add(accessAsClient);
        frame.revalidate();
        frame.repaint();

        accessAsAdministrator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                componentCreator.getAdministratorAccessPanel(frame);
            }
        });

        accessAsClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                componentCreator.getClientAccessPanel(frame);
            }
        });
    }

    // This back button is used to travel from the administrator access panel and the home.
    public  static JButton createBackButton(JFrame frame, String place){
        JButton backButton = new JButton("← Back");
        backButton.setBounds(1000, 50, 100, 25);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                if (place.equals("home")){
                    getHomePanel(frame);
                } else if (place.equals("administratorPanel")){
                    getAdministratorPanel(frame);
                }
            }
        });
        return backButton;
    }

    // This back button is used when we access as client
    public  static JButton createBackButtonForClient(JFrame frame, Integer clientID){
        JButton backButton = new JButton("← Back");
        backButton.setBounds(1000, 50, 100, 25);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                componentCreator.getClientPanel(frame, clientID);
            }
        });
        return backButton;
    }

    // Same as home label but when we access as client
    public static JLabel createMainLabel(String panelDescription) {
        JLabel label = new JLabel();
        label.setText(panelDescription);
        label.setForeground(Color.white);
        label.setIcon(Defaults.getResizeImage("src\\main\\java\\com\\dh\\homebanking\\interfaceElements\\logo.png", 100, 100));
        label.setBounds(35, -50, 250, 250);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    // Access as administrator panel
    public static void getAdministratorAccessPanel(JFrame frame){
        JButton backButton = createBackButton(frame, "home");
        frame.add(backButton);

        JLabel adminUsernameTag = new JLabel("Admin Username ");
        adminUsernameTag.setPreferredSize(new Dimension(200, 50));
        adminUsernameTag.setBounds(340,300,200,25);
        adminUsernameTag.setForeground(Color.white);
        frame.add(adminUsernameTag);

        JTextField adminUser = new JTextField();
        adminUser.setPreferredSize(new Dimension(250, 40));
        adminUser.setBounds(450, 300, 250, 25);
        frame.add(adminUser);

        JLabel wrongCredentials = new JLabel("Password ");
        wrongCredentials.setPreferredSize(new Dimension(200, 50));
        wrongCredentials.setBounds(380,350,200,25);
        wrongCredentials.setForeground(Color.white);
        frame.add(wrongCredentials);

        JTextField adminPassword = new JPasswordField(0);
        adminPassword.setPreferredSize(new Dimension(250, 40));
        adminPassword.setBounds(450, 350, 250, 25);
        frame.add(adminPassword);

        JButton adminLogin = new JButton("Login ");
        adminLogin.setBounds(520, 400, 100, 25);
        frame.add(adminLogin);

        JLabel accessData = new JLabel("Pss... The username is 'admin' and the password is '123'.");
        accessData.setPreferredSize(new Dimension(200, 50));
        accessData.setBounds(420,450,500,25);
        accessData.setForeground(Color.white);
        frame.add(accessData);

        adminLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userEntered = adminUser.getText();
                String passwordEntered = adminPassword.getText();
                if ((Objects.equals(userEntered, "admin") && (Objects.equals(passwordEntered, "123")))){
                    frame.getContentPane().removeAll();
                    frame.revalidate();
                    frame.repaint();
                    getAdministratorPanel(frame);
                } else {
                    JLabel wrongCredentials = new JLabel("Wrong credentials. Try again.");
                    wrongCredentials.setPreferredSize(new Dimension(200, 50));
                    wrongCredentials.setBounds(490,250,500,25);
                    wrongCredentials.setForeground(Color.red);
                    frame.add(wrongCredentials);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }

    // Called once we access as administrator successfully
    public static int getDollarExchangeRate(){
        double valorPromedioBlue = 0;
        try {
            // Dollar value api
            URL url = new URL("https://api.bluelytics.com.ar/v2/latest");

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read answer from an API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Convert the answer into a JSON file
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

            // Obtain the dollar value
            valorPromedioBlue = jsonObject.getAsJsonObject("blue").get("value_avg").getAsDouble();

            // Close
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int) valorPromedioBlue;
    }

    public static void getAdministratorPanel(JFrame frame) {
        JButton backButton = createBackButton(frame, "home");
        frame.add(backButton);

        JLabel administratorLabel = createMainLabel("Welcome to the administrator panel.");
        frame.add(administratorLabel);


        // Create a way to have all clients here
        ClientDaoPostgreSQL clientDaoPostgreSQL = new ClientDaoPostgreSQL();
        List<Client> clientList = clientDaoPostgreSQL.buscarTodos();


        JLabel dollarExchangeRate = new JLabel("Today's dollar exchange rate is 1 USD = " + getDollarExchangeRate() + " Pesos");
        configureLabel(dollarExchangeRate, 400, 100, 350, 25);
        frame.add(dollarExchangeRate);

        JButton createNewClient = createNewClient(25, 200, frame);
        frame.add(createNewClient);

        JButton createAnAccountForAClient = createNewAccountForClient(225, 200, frame);
        frame.add(createAnAccountForAClient);

        int y = 200;
        int x = 25;

        y += 30;
        JLabel clientIdIndex = new JLabel("Client ID");
        configureLabel(clientIdIndex, x, y, 50, 25);
        frame.add(clientIdIndex);
        x += 50;

        JLabel clientStatusIndex = new JLabel("Client Status");
        configureLabel(clientStatusIndex, x, y, 150, 25);
        frame.add(clientStatusIndex);
        x += 150;

        JLabel clientStatusDateIndex = new JLabel("Status Date");
        configureLabel(clientStatusDateIndex, x, y, 150, 25);
        frame.add(clientStatusDateIndex);
        x += 150;

        JLabel clientTypeIndex = new JLabel("Client Type");
        configureLabel(clientTypeIndex, x, y, 150, 25);
        frame.add(clientTypeIndex);
        x += 150;

        JLabel usernameIndex = new JLabel("Username");
        configureLabel(usernameIndex, x, y, 150, 25);
        frame.add(usernameIndex);
        x += 150;

        JLabel emailIndex = new JLabel("Email");
        configureLabel(emailIndex, x, y, 150, 25);
        frame.add(emailIndex);
        x += 100;

        JLabel passwordIndex = new JLabel("Password");
        configureLabel(passwordIndex, x, y, 200, 25);
        frame.add(passwordIndex);
        x += 150;

        frame.revalidate();
        frame.repaint();

        for (Client client : clientList) {
            x = 25;
            y += 30;
            System.out.println(client.toString());

            JLabel clientId = new JLabel(String.valueOf(client.getClientId()));
            configureLabel(clientId, x, y, 50, 25);
            frame.add(clientId);
            x += 50;

            JLabel clientStatus = new JLabel(client.getStatus());
            configureLabel(clientStatus, x, y, 150, 25);
            frame.add(clientStatus);
            x += 150;

            JLabel clientStatusDate = new JLabel(String.valueOf(client.getClientStatusDate()));
            configureLabel(clientStatusDate, x, y, 150, 25);
            frame.add(clientStatusDate);
            x += 150;

            JLabel clientType = new JLabel(String.valueOf(client.getClientType()));
            configureLabel(clientType, x, y, 150, 25);
            frame.add(clientType);
            x += 150;

            JLabel username = new JLabel(client.getUsername());
            configureLabel(username, x, y, 150, 25);
            frame.add(username);
            x += 150;

            JLabel email = new JLabel(client.getEmail());
            configureLabel(email, x, y, 150, 25);
            frame.add(email);
            x += 100;

            JLabel password = new JLabel(client.getPassword());
            configureLabel(password, x, y, 200, 25);
            frame.add(password);
            x += 200;

            JButton moreInfo = moreInfoButton(x, y, client.getClientId(), frame);
            frame.add(moreInfo);

            x += 100;
            JButton delete = deleteClientButton(x, y, client.getClientId(), client.getUsername(), frame);
            frame.add(delete);

            frame.revalidate();
            frame.repaint();
        }
    }

    // Create new account menu
    public static void getAddNewAccountPanel(JFrame frame){
        JButton backButton = createBackButton(frame,"administratorPanel");
        frame.add(backButton);

        JTextField clientID = new JTextField();
        clientID.setPreferredSize(new Dimension(250, 40));
        clientID.setBounds(500, 250, 250, 25);
        frame.add(clientID);

        JLabel clientIDTag = new JLabel("Client ID: ");
        clientIDTag.setPreferredSize(new Dimension(200, 20));
        clientIDTag.setBounds(430,250,500,25);
        clientIDTag.setForeground(Color.white);
        frame.add(clientIDTag);

        JTextField initialSalaryTextField = new JTextField();
        initialSalaryTextField.setPreferredSize(new Dimension(250, 40));
        initialSalaryTextField.setBounds(500, 300, 250, 25);
        frame.add(initialSalaryTextField);

        JLabel initialSalaryTextFieldTag = new JLabel("Starting salary: ");
        initialSalaryTextFieldTag.setPreferredSize(new Dimension(200, 20));
        initialSalaryTextFieldTag.setBounds(400,300,500,25);
        initialSalaryTextFieldTag.setForeground(Color.white);
        frame.add(initialSalaryTextFieldTag);

        // Moneda de la cuenta (Pesos o USD)
        JLabel accountCurrencyLabel = new JLabel("Account Currency:");
        accountCurrencyLabel.setBounds(380, 350, 250, 25);
        accountCurrencyLabel.setForeground(Color.WHITE);
        frame.add(accountCurrencyLabel);

        ButtonGroup accountCurrencyGroup = new ButtonGroup();

        JRadioButton pesosRadioButton = new JRadioButton("Pesos");
        pesosRadioButton.setBounds(530, 350, 70, 25);
        frame.add(pesosRadioButton);
        accountCurrencyGroup.add(pesosRadioButton);

        JRadioButton usdRadioButton = new JRadioButton("USD");
        usdRadioButton.setBounds(630, 350, 70, 25);
        frame.add(usdRadioButton);
        accountCurrencyGroup.add(usdRadioButton);

        JButton createNewAccount = new JButton("Create account");
        createNewAccount.setBounds(470+70, 400, 150, 25);
        frame.add(createNewAccount);

        JLabel instructionToSendMoney = new JLabel("Specify the client id and the currency of the new account to create.");
        instructionToSendMoney.setPreferredSize(new Dimension(200, 100));
        instructionToSendMoney.setBounds(430,450,1000,25);
        instructionToSendMoney.setForeground(Color.white);
        frame.add(instructionToSendMoney);

        createNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Creating a new account for client " + clientID.getText());

                JRadioButton selectedCurrencyButton = null;

                Enumeration<AbstractButton> allCurrencyButtons = accountCurrencyGroup.getElements();
                while (allCurrencyButtons.hasMoreElements()) {
                    JRadioButton tempCurrencyButton = (JRadioButton) allCurrencyButtons.nextElement();
                    if (tempCurrencyButton.isSelected()) {
                        selectedCurrencyButton = tempCurrencyButton;
                        break;
                    }
                }

                assert selectedCurrencyButton != null;
                Account newAccount = new Account(0, Integer.parseInt(initialSalaryTextField.getText()), selectedCurrencyButton.getText());
                AcccountDaoPostgreSQL acccountDaoPostgreSQL = new AcccountDaoPostgreSQL();

                acccountDaoPostgreSQL.guardar(Integer.parseInt(clientID.getText()), newAccount);


                frame.getContentPane().removeAll();
                JLabel ClientSuccessfullyCreated = new JLabel("New account has been created successfully");
                ClientSuccessfullyCreated.setForeground(Color.WHITE);
                configureLabel(ClientSuccessfullyCreated, 400, 300, 400, 60);
                frame.add(ClientSuccessfullyCreated);
                frame.add(createBackButton(frame, "administratorPanel"));
                frame.revalidate();
                frame.repaint();
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    // Button that insert the account to the database
    public static JButton createNewAccountForClient(int x, int y, JFrame frame) {
        JButton button = new JButton("Create new Account");
        button.setBounds(x, y, 200, 25);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Mostrar el menu para crear una cuenta para un cliente");
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                getAddNewAccountPanel(frame);
            }
        });
        return button;
    }

    // Delete client button
    public static JButton deleteClientButton(int x, int y, int clientId, String clientName, JFrame frame) {
        JButton button = new JButton("Delete");
        button.setBounds(x, y, 100, 25);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Vamos a borrar el cliente " + clientId);
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                delteClientPanel(frame, clientId, clientName);
            }
        });
        return button;
    }

    public  static  void delteClientPanel(JFrame frame, int clientId, String clientName){
        System.out.println("Vamos a eliminar el cliente " + clientId + " cuyo nombre es " + clientName);
        JButton backButton = createBackButton(frame, "administratorPanel");

        JLabel ClientSuccessfullyCreated = new JLabel("Are you sure you want to eliminate the client " + clientName + "?");
        ClientSuccessfullyCreated.setForeground(Color.WHITE);
        configureLabel(ClientSuccessfullyCreated, 400, 300, 400, 60);
        frame.add(ClientSuccessfullyCreated);

        JButton deleteclient = deleteClient(500, 400, clientName, clientId, frame);
        frame.add(deleteclient);

        frame.add(backButton);
        frame.revalidate();
        frame.repaint();
    }

    public static JButton deleteClient(int x, int y, String clientName, int clientId, JFrame frame) {
        JButton button = new JButton("Delete client " + clientName);
        button.setBounds(x, y, 200, 25);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                ClientDaoPostgreSQL cliendaoPostgreSQL = new ClientDaoPostgreSQL();
                cliendaoPostgreSQL.eliminar(clientId);
                getAdministratorPanel(frame);
            }
        });
        return button;
    }

    // More info button on the top of the administrator panel
    public static JButton moreInfoButton(int x, int y, int clientId, JFrame frame) {
        JButton button = new JButton("More Info");
        button.setBounds(x, y, 100, 25);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Vamos a buscar la info personal del cliente " + clientId);
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                getMoreClientInfoPanel(frame, clientId);
            }
        });

        return button;
    }

    // Used to create some labels in the code with certain details.
    private static void configureLabel(JLabel label, int x, int y, int width, int height) {
        label.setPreferredSize(new Dimension(width, height));
        label.setBounds(x, y, width, height);
        label.setForeground(Color.BLACK);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto horizontalmente
    }

    // Where you put your client name and password
    public static void getClientAccessPanel(JFrame frame) {
        JButton backButton = createBackButton(frame, "home");
        frame.add(backButton);

        JTextField clientUsername = new JTextField();
        clientUsername.setPreferredSize(new Dimension(250, 40));
        clientUsername.setBounds(400+100, 300, 250, 25);
        frame.add(clientUsername);

        JLabel clientUsernameTag = new JLabel("Username");
        clientUsernameTag.setPreferredSize(new Dimension(200, 20));
        clientUsernameTag.setBounds(330+100,300,500,25);
        clientUsernameTag.setForeground(Color.white);
        frame.add(clientUsernameTag);

        JTextField clientPassword = new JPasswordField(0);
        clientPassword.setPreferredSize(new Dimension(250, 40));
        clientPassword.setBounds(400+100, 350, 250, 25);
        frame.add(clientPassword);

        JLabel clientPasswordTag = new JLabel("Password");
        clientPasswordTag.setPreferredSize(new Dimension(200, 20));
        clientPasswordTag.setBounds(330+100,350,500,25);
        clientPasswordTag.setForeground(Color.white);
        frame.add(clientPasswordTag);

        JButton clientLogin = new JButton("Login");
        clientLogin.setBounds(470+100, 400, 100, 25);
        frame.add(clientLogin);

        JLabel accessData = new JLabel("Access as Administrator and create a client if you don't have one.");
        accessData.setPreferredSize(new Dimension(200, 100));
        accessData.setBounds(350+100,450,500,25);
        accessData.setForeground(Color.white);
        frame.add(clientLogin);

        clientLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userEntered = clientUsername.getText();
                String passwordEntered = clientPassword.getText();
                // Aqui el condicional deberia hacer un request a la base de datos, trabajar luego con las peticiones a la base

                ClientDaoPostgreSQL clientDaoPostgreSQL = new ClientDaoPostgreSQL();

                Client client = clientDaoPostgreSQL.buscarPorUsername(userEntered);

                if (client != null && (Objects.equals(userEntered, client.getUsername()) && (Objects.equals(passwordEntered, client.getPassword())))){
                    frame.getContentPane().removeAll();
                    frame.revalidate();
                    frame.repaint();
                    // Crear el get ClientPanel
                    componentCreator.getClientPanel(frame, client.getClientId());
                } else {
                    JLabel wrongCredentials = new JLabel("Wrong username or passsword. Try again.");
                    wrongCredentials.setPreferredSize(new Dimension(200, 50));
                    wrongCredentials.setBounds(500,450,500,25);
                    wrongCredentials.setForeground(Color.red);
                    frame.add(wrongCredentials);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }

    // Accessed once you accessed to your client account successfully
    public  static  void getClientPanel(JFrame frame, int clientId){
        JButton backButton = createBackButton(frame, "home");
        frame.add(backButton);

        JLabel clientLabel = createMainLabel("Welcome to the client panel.");
        frame.add(clientLabel);
        ClientDataDaoPostgreSQL clientDataDaoPostgreSQL = new ClientDataDaoPostgreSQL();
        ClientDaoPostgreSQL clientDaoPostgreSQL = new ClientDaoPostgreSQL();

        Client client = clientDaoPostgreSQL.buscar(clientId);
        ClientData clientData = clientDataDaoPostgreSQL.buscar(clientId);

        // Poniendo la data en pantalla
        JLabel clientIdTitle = new JLabel("Welcome to the main menu " + clientData.getFirstName() + " " + clientData.getLastName());
        configureLabel(clientIdTitle, 350, 100, 400, 25);
        frame.add(clientIdTitle);

        int y = 200;
        int x = 50;

        // Show personal data label
        JLabel personalInfo = new JLabel("Personal info:");
        configureLabel(personalInfo, 150, 170, 150, 25);
        frame.add(personalInfo);

        // Show name
        JLabel name = new JLabel("Full name: " + clientData.getFirstName() + " " + clientData.getLastName());
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setBounds(25, 200, 150, 25);
        name.setForeground(Color.WHITE);
        frame.add(name);

        // Show DNI
        JLabel dni = new JLabel("DNI: " + clientData.getDni());
        dni.setHorizontalAlignment(SwingConstants.CENTER);
        dni.setBounds(5, 220, 150, 25);
        dni.setForeground(Color.WHITE);
        frame.add(dni);

        // Show status details
        JLabel status = new JLabel("Client status: " + client.getStatus() + " since " + client.getClientStatusDate());
        status.setHorizontalAlignment(SwingConstants.LEFT);
        status.setBounds(250, 200, 300, 25);
        status.setForeground(Color.WHITE);
        frame.add(status);

        JLabel clientType = new JLabel("Client type: " + client.getClientType());
        clientType.setHorizontalAlignment(SwingConstants.LEFT);
        clientType.setBounds(250, 220, 300, 25);
        clientType.setForeground(Color.WHITE);
        frame.add(clientType);

        JLabel username = new JLabel("Username: " + client.getUsername());
        username.setHorizontalAlignment(SwingConstants.LEFT);
        username.setBounds(250, 240, 300, 25);
        username.setForeground(Color.WHITE);
        frame.add(username);

        JLabel password = new JLabel("Password: " + client.getPassword());
        password.setHorizontalAlignment(SwingConstants.LEFT);
        password.setBounds(250, 260, 300, 25);
        password.setForeground(Color.WHITE);
        frame.add(password);

        JLabel clientID = new JLabel("Client ID: " + client.getClientId());
        clientID.setHorizontalAlignment(SwingConstants.LEFT);
        clientID.setBounds(250, 280, 300, 25);
        clientID.setForeground(Color.WHITE);
        frame.add(clientID);

        JLabel bornDate = new JLabel("Born date: " + clientData.getBornDate());
        bornDate.setHorizontalAlignment(SwingConstants.LEFT);
        bornDate.setBounds(48, 240, 300, 25);
        bornDate.setForeground(Color.WHITE);
        frame.add(bornDate);

        JLabel phoneNumber = new JLabel("Phone number: " + clientData.getPhoneNumber());
        phoneNumber.setHorizontalAlignment(SwingConstants.LEFT);
        phoneNumber.setBounds(48, 280, 300, 25);
        phoneNumber.setForeground(Color.WHITE);
        frame.add(phoneNumber);

        JLabel Nationality = new JLabel("Origin country: " + clientData.getNationality());
        Nationality.setHorizontalAlignment(SwingConstants.LEFT);
        Nationality.setBounds(48, 260, 300, 25);
        Nationality.setForeground(Color.WHITE);
        frame.add(Nationality);

        // Titulo de las cuentas
        JLabel accountsInfo = new JLabel("Accounts:");
        configureLabel(accountsInfo, 150, 320, 150, 25);
        frame.add(accountsInfo);

        // Mostrando las cuentas:
        AcccountDaoPostgreSQL accountDaoPostgreSQL = new AcccountDaoPostgreSQL();
        List<Account> accountList = accountDaoPostgreSQL.buscarTodos(clientId);

        y = 340;
        x = 75;

        y += 30;
        JLabel clientIdIndex = new JLabel("Account ID");
        configureLabel(clientIdIndex, x, y, 70, 25);
        frame.add(clientIdIndex);
        x += 50;

        JLabel clientStatusIndex = new JLabel("Balance");
        configureLabel(clientStatusIndex, x, y, 150, 25);
        frame.add(clientStatusIndex);
        x += 100;

        JLabel clientStatusDateIndex = new JLabel("Currency");
        configureLabel(clientStatusDateIndex, x, y, 150, 25);
        frame.add(clientStatusDateIndex);

        JLabel transactions = new JLabel("Transactions:");
        configureLabel(transactions, 770, 170, 150, 25);
        frame.add(transactions);

        frame.revalidate();
        frame.repaint();

        List<Integer> clientAccounts = new ArrayList<>();

        for (Account account : accountList) {

            clientAccounts.add(account.getaccountID());

            x = 75;
            y += 30;
            System.out.println(client.toString());

            JLabel accountID = new JLabel(String.valueOf(account.getaccountID()));
            configureLabel(accountID, x, y, 50, 25);
            frame.add(accountID);
            x += 50;

            JLabel accountBalance = new JLabel(String.valueOf(account.getBalance()));
            configureLabel(accountBalance, x, y, 150, 25);
            frame.add(accountBalance);
            x += 100;

            JLabel accountCurrency = new JLabel(String.valueOf(account.getcurrencyType()));
            configureLabel(accountCurrency, x, y, 150, 25);
            frame.add(accountCurrency);
            x += 150;

            JButton newTransactionButton = new JButton("+ Send money");
            newTransactionButton.setBounds(380, y, 150, 25);
            frame.add(newTransactionButton);

            newTransactionButton.addActionListener(e -> {
                System.out.println("Agregar nueva trasaccion");
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                getSendTransactionPanel(frame, clientId, account.getaccountID());
            });

            frame.revalidate();
            frame.repaint();
        }

        y = 200;

        TransactionDaoPostgreSQL transactionDaoPostgreSQL = new TransactionDaoPostgreSQL();
        List<TransactionData> transactionDataList = transactionDaoPostgreSQL.buscarTodos(clientId);

        for (TransactionData transactionData : transactionDataList) {

            x = 650;
            y += 30;

            System.out.println(client.toString());

            JLabel amount = null;

            String transactionCurrency = accountDaoPostgreSQL.buscarPorAccountId(transactionData.getDestinyAccount()).getcurrencyType();

            // This checks if the client has sent or received money
            if (clientAccounts.contains(transactionData.getOriginAccount())) {
                amount = new JLabel(" - " + String.valueOf(transactionData.getAmount()) + " " + transactionCurrency);
                configureLabel(amount, x, y, 90, 25);
                amount.setHorizontalAlignment(SwingConstants.LEFT);
                amount.setForeground(Color.RED);
                JLabel originAccount = new JLabel(" sent to account id " + String.valueOf(transactionData.getDestinyAccount().intValue()) + " from account id " + transactionData.getOriginAccount() + " at " + transactionData.getOperationTime());
                x += 85;
                configureLabel(originAccount, x, y, 350, 25);
                originAccount.setHorizontalAlignment(SwingConstants.LEFT);
                frame.add(originAccount);
            } else {
                amount = new JLabel(" + " + String.valueOf(transactionData.getAmount()) + " " + transactionCurrency);
                configureLabel(amount, x, y, 90, 25);
                amount.setHorizontalAlignment(SwingConstants.LEFT);
                amount.setForeground(Color.GREEN);
                JLabel originAccount = new JLabel(" received from account id " + String.valueOf(transactionData.getOriginAccount().intValue()) + " to account id " + transactionData.getDestinyAccount() + " at " + transactionData.getOperationTime());
                x += 70;
                configureLabel(originAccount, x, y, 350, 25);
                originAccount.setHorizontalAlignment(SwingConstants.LEFT);
                frame.add(originAccount);
            }
            frame.add(amount);
            frame.revalidate();
            frame.repaint();
        }


        frame.revalidate();
        frame.repaint();
    }

    // After accessing as Client to access the send transaction panel
    public static void getSendTransactionPanel(JFrame frame, Integer clientID, Integer originAccountID){
        JButton backButton = createBackButtonForClient(frame, clientID);
        frame.add(backButton);

        JLabel conversionNote = new JLabel("Our bank works with USD and Pesos accounts. Money will be converted into the destiny account currency.");
        configureLabel(conversionNote, 300, 220, 650, 25);
        frame.add(conversionNote);

        JLabel dollarExchangeRate = new JLabel("Today's dollar exchange rate is 1 USD = " + getDollarExchangeRate() + " Pesos");
        configureLabel(dollarExchangeRate, 450, 250, 350, 25);
        frame.add(dollarExchangeRate);

        JTextField destinationAccountId = new JTextField();
        destinationAccountId.setPreferredSize(new Dimension(250, 40));
        destinationAccountId.setBounds(400+100, 300, 250, 25);
        frame.add(destinationAccountId);

        JLabel destinationAccountIdTag = new JLabel("Destination account id");
        destinationAccountIdTag.setPreferredSize(new Dimension(200, 20));
        destinationAccountIdTag.setBounds(330,300,500,25);
        destinationAccountIdTag.setForeground(Color.white);
        frame.add(destinationAccountIdTag);

        JTextField amountToSend = new JTextField();
        amountToSend.setPreferredSize(new Dimension(250, 40));
        amountToSend.setBounds(400+100, 350, 250, 25);
        frame.add(amountToSend);

        JLabel amountToSendTag = new JLabel("Amount to send");
        amountToSendTag.setPreferredSize(new Dimension(200, 20));
        amountToSendTag.setBounds(330,350,500,25);
        amountToSendTag.setForeground(Color.white);
        frame.add(amountToSendTag);

        JButton sendMoneyButton = new JButton("Send money");
        sendMoneyButton.setBounds(470+70, 400, 150, 25);
        frame.add(sendMoneyButton);

        JLabel instructionToSendMoney = new JLabel("Insert the destination account and the amount that you want to send.");
        instructionToSendMoney.setPreferredSize(new Dimension(200, 100));
        instructionToSendMoney.setBounds(420,450,1000,25);
        instructionToSendMoney.setForeground(Color.white);
        frame.add(instructionToSendMoney);

        sendMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer destinationAccount = Integer.parseInt(destinationAccountId.getText());
                Integer originAccountMoneyLost = Integer.parseInt(amountToSend.getText());

                AcccountDaoPostgreSQL accountDaoPostgreSQL = new AcccountDaoPostgreSQL();

                Account originAccount = accountDaoPostgreSQL.buscarPorAccountId(originAccountID);
                Account destinyAccount = accountDaoPostgreSQL.buscarPorAccountId(destinationAccount);

                Integer destinyAccountMoneyReceived = null;

                // If both accounts have the same currency
                if (originAccount.getcurrencyType().equals(destinyAccount.getcurrencyType())){
                    System.out.println("Se envia entre cuentas de igual moneda " + originAccount.getcurrencyType());
                    destinyAccountMoneyReceived = originAccountMoneyLost;
                // Else, if the origin account is in pesos
                } else if (originAccount.getcurrencyType().equals("Pesos")) {
                    System.out.println("Se envia desde una cuenta en pesos a otra en usd.");
                    destinyAccountMoneyReceived = originAccountMoneyLost / getDollarExchangeRate();
                // If origin account is in USD
                } else if  (originAccount.getcurrencyType().equals("USD")) {
                    System.out.println("Se envia desde una cuenta en dolares a otra en pesos.");
                    destinyAccountMoneyReceived = originAccountMoneyLost * getDollarExchangeRate();
                }

                String symbol = destinyAccount.getcurrencyType();

                if (destinyAccount != null){
                    frame.getContentPane().removeAll();
                    frame.revalidate();
                    frame.repaint();
                    // Crear el get ClientPanel
                    System.out.println("Account ID exists!");
                    //Transaction transaction = new Transaction(originAccountID);
                    TransactionDaoPostgreSQL transactionDaoPostgreSQL = new TransactionDaoPostgreSQL();
                    transactionDaoPostgreSQL.guardar(destinyAccountMoneyReceived, originAccountMoneyLost, originAccountID, destinationAccount);

                    JButton backButton = componentCreator.createBackButtonForClient(frame, clientID);
                    frame.add(backButton);

                    JLabel ClientSuccessfullyCreated = new JLabel( destinyAccountMoneyReceived + " " + symbol + " has been sent to the account whose ID is " + destinationAccount);
                    ClientSuccessfullyCreated.setForeground(Color.WHITE);
                    configureLabel(ClientSuccessfullyCreated, 400, 300, 400, 60);
                    frame.add(ClientSuccessfullyCreated);
                } else {
                    JLabel wrongCredentials = new JLabel("The account does not exist. Try again.");
                    wrongCredentials.setPreferredSize(new Dimension(200, 50));
                    wrongCredentials.setBounds(500,500,500,25);
                    wrongCredentials.setForeground(Color.red);
                    frame.add(wrongCredentials);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }


    // Access to the getAddNewClientPanel
    public static JButton createNewClient(int x, int y, JFrame frame) {
        JButton button = new JButton("Create new Client");
        button.setBounds(x, y, 200, 25);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Mostrar el menu para crear el cliente");
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                getAddNewClientPanel(frame);
            }
        });
        return button;
    }

    // Access to the panel where you create a new client
    public  static  void getAddNewClientPanel(JFrame frame) {
        JButton backButton = createBackButton(frame, "administratorPanel");
        frame.add(backButton);

        JLabel administratorLabel = createMainLabel("Client panel.");
        frame.add(administratorLabel);

        // Email
        JLabel emailTag = new JLabel("Email:");
        emailTag.setBounds(50, 200, 250, 25);
        emailTag.setForeground(Color.WHITE);
        frame.add(emailTag);

        JTextField emailTextField = new JTextField();
        emailTextField.setBounds(50, 230, 250, 25);
        frame.add(emailTextField);

// Username
        JLabel usernameTag = new JLabel("Username:");
        usernameTag.setBounds(420, 200, 250, 25);
        usernameTag.setForeground(Color.WHITE);
        frame.add(usernameTag);

        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(420, 230, 250, 25);
        frame.add(usernameTextField);

// Password
        JLabel passwordTag = new JLabel("Password:");
        passwordTag.setBounds(800, 200, 250, 25);
        passwordTag.setForeground(Color.WHITE);
        frame.add(passwordTag);

        JTextField passwordTextField = new JTextField();
        passwordTextField.setBounds(800, 230, 250, 25);
        frame.add(passwordTextField);

        // Name
        JLabel newClientFirstNameTag = new JLabel("First name:");
        newClientFirstNameTag.setBounds(50, 170+100, 250, 25);
        newClientFirstNameTag.setForeground(Color.WHITE);
        frame.add(newClientFirstNameTag);

        JTextField newClientFirstNameTextField = new JTextField();
        newClientFirstNameTextField.setBounds(50, 200+100, 250,25);
        frame.add(newClientFirstNameTextField);

        // Last name
        JLabel newClientLastNameTag = new JLabel("Last name:");
        newClientLastNameTag.setBounds(420, 170+100, 250, 25);
        newClientLastNameTag.setForeground(Color.WHITE);
        frame.add(newClientLastNameTag);

        JTextField newClientLastNameTextField = new JTextField();
        newClientLastNameTextField.setBounds(420, 200+100, 250,25);
        frame.add(newClientLastNameTextField);

        // Born Date
        JLabel bornDateTag = new JLabel("Born date (follow format dd-MM-yyyy):");
        bornDateTag.setBounds(800, 170+100, 250, 25);
        bornDateTag.setForeground(Color.WHITE);
        frame.add(bornDateTag);

        JTextField bornDateTextField = new JTextField();
        bornDateTextField.setBounds(800, 200+100, 250,25);
        frame.add(bornDateTextField);

        JLabel dniTag = new JLabel("DNI:");
        dniTag.setBounds(50, 250+100, 250, 25);
        dniTag.setForeground(Color.WHITE);
        frame.add(dniTag);

        JTextField dniTextField = new JTextField();
        dniTextField.setBounds(50, 280+100, 250, 25);
        frame.add(dniTextField);

        // Phone Number
        JLabel phoneNumberTag = new JLabel("Phone Number:");
        phoneNumberTag.setBounds(420, 250+100, 250, 25);
        phoneNumberTag.setForeground(Color.WHITE);
        frame.add(phoneNumberTag);

        JTextField phoneNumberTextField = new JTextField();
        phoneNumberTextField.setBounds(420, 280+100, 250, 25);
        frame.add(phoneNumberTextField);

        // Origin Country
        JLabel originCountryTag = new JLabel("Origin Country:");
        originCountryTag.setBounds(800, 250+100, 250, 25);
        originCountryTag.setForeground(Color.WHITE);
        frame.add(originCountryTag);

        JTextField originCountryTextField = new JTextField();
        originCountryTextField.setBounds(800, 280+100, 250, 25);
        frame.add(originCountryTextField);

        // Tipo de cliente (Personal o Corporate)
        JLabel clientTypeLabel = new JLabel("Client Type:");
        clientTypeLabel.setBounds(50, 330+100, 250, 25);
        clientTypeLabel.setForeground(Color.WHITE);
        frame.add(clientTypeLabel);

        ButtonGroup clientTypeGroup = new ButtonGroup();

        JRadioButton personalCheckBox = new JRadioButton("Personal");
        personalCheckBox.setBounds(50, 360+100, 100, 25);
        frame.add(personalCheckBox);
        clientTypeGroup.add(personalCheckBox);

        JRadioButton corporateCheckBox = new JRadioButton("Corporate");
        corporateCheckBox.setBounds(160, 360+100, 100, 25);
        frame.add(corporateCheckBox);
        clientTypeGroup.add(corporateCheckBox);

        // Moneda de la cuenta (Pesos o USD)
        JLabel accountCurrencyLabel = new JLabel("Account Currency:");
        accountCurrencyLabel.setBounds(420, 330+100, 250, 25);
        accountCurrencyLabel.setForeground(Color.WHITE);
        frame.add(accountCurrencyLabel);

        ButtonGroup accountCurrencyGroup = new ButtonGroup();

        JRadioButton pesosRadioButton = new JRadioButton("Pesos");
        pesosRadioButton.setBounds(420, 360+100, 70, 25);
        frame.add(pesosRadioButton);
        accountCurrencyGroup.add(pesosRadioButton);

        JRadioButton usdRadioButton = new JRadioButton("USD");
        usdRadioButton.setBounds(500, 360+100, 70, 25);
        frame.add(usdRadioButton);
        accountCurrencyGroup.add(usdRadioButton);

        // Campo para el salario inicial
        JLabel initialSalaryLabel = new JLabel("Initial Salary:");
        initialSalaryLabel.setBounds(800, 330+100, 250, 25);
        initialSalaryLabel.setForeground(Color.WHITE);
        frame.add(initialSalaryLabel);

        JTextField initialSalaryTextField = new JTextField();
        initialSalaryTextField.setBounds(800, 360+100, 250, 25);
        frame.add(initialSalaryTextField);

        // Botón para crear el cliente
        JButton createClientButton = new JButton("Create Client");
        createClientButton.setBounds(500, 450+100, 150, 25);
        frame.add(createClientButton);

        createClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();

                ClientDaoPostgreSQL clientDaoPostgreSQL = new ClientDaoPostgreSQL();

                JRadioButton clientTypeButton = null;
                Enumeration<AbstractButton> allButtons = clientTypeGroup.getElements();
                while (allButtons.hasMoreElements()) {
                    JRadioButton temp = (JRadioButton) allButtons.nextElement();
                    if (temp.isSelected()) {
                        clientTypeButton = temp;
                        break;
                    }
                }

                assert clientTypeButton != null;
                Client client = new Client(clientTypeButton.getText(), usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText());

                //Generando fecha de nacimiento
                String fechaNacimientoString = bornDateTextField.getText();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date fechaNacimiento = null;
                try {
                    fechaNacimiento = formatter.parse(fechaNacimientoString);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

                ClientData clientData = new ClientData(0, newClientFirstNameTextField.getText(), newClientLastNameTextField.getText(), fechaNacimiento, dniTextField.getText(), phoneNumberTextField.getText(), originCountryTextField.getText());

                // Getting currency selected
                JRadioButton selectedCurrencyButton = null;

                Enumeration<AbstractButton> allCurrencyButtons = accountCurrencyGroup.getElements();
                while (allCurrencyButtons.hasMoreElements()) {
                    JRadioButton tempCurrencyButton = (JRadioButton) allCurrencyButtons.nextElement();
                    if (tempCurrencyButton.isSelected()) {
                        selectedCurrencyButton = tempCurrencyButton;
                        break;
                    }
                }

                assert selectedCurrencyButton != null;
                Account account = new Account(0, Integer.parseInt(initialSalaryTextField.getText()), selectedCurrencyButton.getText());

                clientDaoPostgreSQL.guardar(client, clientData, account);

                JLabel ClientSuccessfullyCreated = new JLabel("New client has been created successfully");
                ClientSuccessfullyCreated.setForeground(Color.WHITE);
                configureLabel(ClientSuccessfullyCreated, 400, 300, 400, 60);
                frame.add(ClientSuccessfullyCreated);

                JButton backButton = createBackButton(frame, "administratorPanel");
                frame.add(backButton);
                frame.revalidate();
                frame.repaint();

                System.out.println("Boton para crear nuevo cliente activado");
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    // Access to more info about the client from the administrator section
    public  static  void getMoreClientInfoPanel(JFrame frame, int clientId){
        JButton backButton = createBackButton(frame, "administratorPanel");
        frame.add(backButton);

        JLabel administratorLabel = createMainLabel("Welcome tom the client info pannel.");
        frame.add(administratorLabel);
        ClientDataDaoPostgreSQL clientDataDaoPostgreSQL = new ClientDataDaoPostgreSQL();
        ClientDaoPostgreSQL clientDaoPostgreSQL = new ClientDaoPostgreSQL();

        Client client = clientDaoPostgreSQL.buscar(clientId);
        ClientData clientData = clientDataDaoPostgreSQL.buscar(clientId);

        // Poniendo la data en pantalla
        JLabel clientIdTitle = new JLabel("More about client No " + clientData.getClientId());
        configureLabel(clientIdTitle, 450, 100, 200, 25);
        frame.add(clientIdTitle);

        int y = 200;
        int x = 50;

        // Show personal data label
        JLabel personalInfo = new JLabel("Personal info:");
        configureLabel(personalInfo, 150, 170, 150, 25);
        frame.add(personalInfo);

        // Show name
        JLabel name = new JLabel("Full name: " + clientData.getFirstName() + " " + clientData.getLastName());
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setBounds(25, 200, 150, 25);
        name.setForeground(Color.WHITE);
        frame.add(name);

        // Show DNI
        JLabel dni = new JLabel("DNI: " + clientData.getDni());
        dni.setHorizontalAlignment(SwingConstants.CENTER);
        dni.setBounds(5, 220, 150, 25);
        dni.setForeground(Color.WHITE);
        frame.add(dni);

        // Show status details
        JLabel status = new JLabel("Client status: " + client.getStatus() + " since " + client.getClientStatusDate());
        status.setHorizontalAlignment(SwingConstants.LEFT);
        status.setBounds(250, 200, 300, 25);
        status.setForeground(Color.WHITE);
        frame.add(status);

        JLabel clientType = new JLabel("Client type: " + client.getClientType());
        clientType.setHorizontalAlignment(SwingConstants.LEFT);
        clientType.setBounds(250, 220, 300, 25);
        clientType.setForeground(Color.WHITE);
        frame.add(clientType);

        JLabel username = new JLabel("Username: " + client.getUsername());
        username.setHorizontalAlignment(SwingConstants.LEFT);
        username.setBounds(250, 240, 300, 25);
        username.setForeground(Color.WHITE);
        frame.add(username);

        JLabel password = new JLabel("Password: " + client.getPassword());
        password.setHorizontalAlignment(SwingConstants.LEFT);
        password.setBounds(250, 260, 300, 25);
        password.setForeground(Color.WHITE);
        frame.add(password);

        JLabel clientID = new JLabel("Client ID: " + client.getClientId());
        clientID.setHorizontalAlignment(SwingConstants.LEFT);
        clientID.setBounds(250, 280, 300, 25);
        clientID.setForeground(Color.WHITE);
        frame.add(clientID);

        JLabel bornDate = new JLabel("Born date: " + clientData.getBornDate());
        bornDate.setHorizontalAlignment(SwingConstants.LEFT);
        bornDate.setBounds(48, 240, 300, 25);
        bornDate.setForeground(Color.WHITE);
        frame.add(bornDate);

        JLabel phoneNumber = new JLabel("Phone number: " + clientData.getPhoneNumber());
        phoneNumber.setHorizontalAlignment(SwingConstants.LEFT);
        phoneNumber.setBounds(48, 280, 300, 25);
        phoneNumber.setForeground(Color.WHITE);
        frame.add(phoneNumber);

        JLabel Nationality = new JLabel("Origin country: " + clientData.getNationality());
        Nationality.setHorizontalAlignment(SwingConstants.LEFT);
        Nationality.setBounds(48, 260, 300, 25);
        Nationality.setForeground(Color.WHITE);
        frame.add(Nationality);

        // Titulo de las cuentas

        JLabel accountsInfo = new JLabel("Accounts:");
        configureLabel(accountsInfo, 150, 320, 150, 25);
        frame.add(accountsInfo);

        // Mostrando las cuentas:

        AcccountDaoPostgreSQL accountDaoPostgreSQL = new AcccountDaoPostgreSQL();
        List<Account> accountList = accountDaoPostgreSQL.buscarTodos(clientId);

        y = 340;
        x = 75;

        y += 30;
        JLabel clientIdIndex = new JLabel("Account ID");
        configureLabel(clientIdIndex, x, y, 70, 25);
        frame.add(clientIdIndex);
        x += 50;

        JLabel clientStatusIndex = new JLabel("Balance");
        configureLabel(clientStatusIndex, x, y, 150, 25);
        frame.add(clientStatusIndex);
        x += 100;

        JLabel clientStatusDateIndex = new JLabel("Currency");
        configureLabel(clientStatusDateIndex, x, y, 150, 25);
        frame.add(clientStatusDateIndex);

        JLabel transactions = new JLabel("Transaction history:");
        configureLabel(transactions, 750, 170, 150, 25);
        frame.add(transactions);

        frame.revalidate();
        frame.repaint();

        List<Integer> clientAccounts = new ArrayList<>();

        for (Account account : accountList) {

            clientAccounts.add(account.getaccountID());

            x = 75;
            y += 30;
            System.out.println(client.toString());

            JLabel accountID = new JLabel(String.valueOf(account.getaccountID()));
            configureLabel(accountID, x, y, 50, 25);
            frame.add(accountID);
            x += 50;

            JLabel accountBalance = new JLabel(String.valueOf(account.getBalance()));
            configureLabel(accountBalance, x, y, 150, 25);
            frame.add(accountBalance);
            x += 100;

            JLabel accountCurrency = new JLabel(String.valueOf(account.getcurrencyType()));
            configureLabel(accountCurrency, x, y, 150, 25);
            frame.add(accountCurrency);
            x += 150;
            frame.revalidate();
            frame.repaint();
        }

        y = 200;

        TransactionDaoPostgreSQL transactionDaoPostgreSQL = new TransactionDaoPostgreSQL();
        List<TransactionData> transactionDataList = transactionDaoPostgreSQL.buscarTodos(clientId);

        for (TransactionData transactionData : transactionDataList) {

            x = 650;
            y += 30;

            System.out.println(client.toString());

            JLabel amount = null;

            String transactionCurrency = accountDaoPostgreSQL.buscarPorAccountId(transactionData.getDestinyAccount()).getcurrencyType();

            // This checks if the client has sent or received money
            if (clientAccounts.contains(transactionData.getOriginAccount())) {
                amount = new JLabel(" - " + String.valueOf(transactionData.getAmount()) + " " + transactionCurrency);
                configureLabel(amount, x, y, 90, 25);
                amount.setHorizontalAlignment(SwingConstants.LEFT);
                amount.setForeground(Color.RED);
                JLabel originAccount = new JLabel(" sent to account id " + String.valueOf(transactionData.getDestinyAccount().intValue()) + " from account id " + transactionData.getOriginAccount() + " at " + transactionData.getOperationTime());
                x += 85;
                configureLabel(originAccount, x, y, 350, 25);
                originAccount.setHorizontalAlignment(SwingConstants.LEFT);
                frame.add(originAccount);
            } else {
                amount = new JLabel(" + " + String.valueOf(transactionData.getAmount()) + " " + transactionCurrency);
                configureLabel(amount, x, y, 90, 25);
                amount.setHorizontalAlignment(SwingConstants.LEFT);
                amount.setForeground(Color.GREEN);
                JLabel originAccount = new JLabel(" received from account id " + String.valueOf(transactionData.getOriginAccount().intValue()) + " to account id " + transactionData.getDestinyAccount() + " at " + transactionData.getOperationTime());
                x += 86;
                configureLabel(originAccount, x, y, 350, 25);
                originAccount.setHorizontalAlignment(SwingConstants.LEFT);
                frame.add(originAccount);
            }
            frame.add(amount);
            frame.revalidate();
            frame.repaint();
        }

        frame.revalidate();
        frame.repaint();
    }
}


