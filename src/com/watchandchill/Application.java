package com.watchandchill;

import java.nio.file.Paths;

import com.alexanderthelen.applicationkit.database.Connection;
import com.alexanderthelen.applicationkit.gui.MasterDetailViewController;
import com.alexanderthelen.applicationkit.gui.WindowController;
import com.watchandchill.gui.AuthenticationViewController;
import com.watchandchill.gui.LoginViewController;
import com.watchandchill.gui.MasterViewController;
import com.watchandchill.gui.RegistrationViewController;

public class Application extends com.alexanderthelen.applicationkit.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start() throws Exception {
        setConnection(new Connection(Paths.get("netflix.db")));

        WindowController mainWindowController = WindowController.createWithName("window");
        mainWindowController.setTitle("Netflix");

        AuthenticationViewController authenticationViewController = AuthenticationViewController
                .createWithName("authentication");
        authenticationViewController.setTitle("Authentifizierung");

        LoginViewController loginViewController = LoginViewController.createWithName("login");
        loginViewController.setTitle("Anmeldung");
        authenticationViewController.setLoginViewController(loginViewController);

        RegistrationViewController registrationViewController = RegistrationViewController
                .createWithName("registration");
        registrationViewController.setTitle("Registrierung");
        authenticationViewController.setRegistrationViewController(registrationViewController);

        MasterDetailViewController mainViewController = MasterDetailViewController.createWithName("main");
        mainViewController.setTitle("Netflix");
        mainViewController.setMasterViewController(MasterViewController.createWithName("master"));
        authenticationViewController.setMainViewController(mainViewController);

        mainWindowController.setViewController(authenticationViewController);
        setWindowController(mainWindowController);
        show();
    }
}