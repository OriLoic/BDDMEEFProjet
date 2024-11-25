import dao.*;
import models.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AL2000 {
    private final FilmDAO filmDAO;
    private final ClientDAO clientDAO;
    private final CarteAbonnementDAO carteAbonnementDAO;
    private final LocationCarteCreditDAO locationCarteCreditDAO;
    private final LocationAbonneDAO locationAbonneDAO;
    private Client currentClient;

    public AL2000(Connection connection) {
        this.filmDAO = new FilmDAO(connection);
        this.clientDAO = new ClientDAO(connection);
        this.carteAbonnementDAO = new CarteAbonnementDAO(connection);
        this.locationCarteCreditDAO = new LocationCarteCreditDAO(connection);
        this.locationAbonneDAO = new LocationAbonneDAO(connection);
    }

    public void run() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans AL2000 !");

        // Connexion ou inscription
        while (currentClient == null) {
            System.out.println("1. Se connecter");
            System.out.println("2. S'enregistrer");
            System.out.print("Choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer le saut de ligne

            if (choice == 1) {
                currentClient = login(scanner);
            } else if (choice == 2) {
                currentClient = register(scanner);
            } else {
                System.out.println("Choix invalide.");
            }
        }

        // Menu principal
        while (true) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Voir les films disponibles");
            System.out.println("2. Louer un film avec carte de crédit");
            System.out.println("3. Louer un film avec abonnement");
            System.out.println("4. Voir l'historique de location");
            System.out.println("5. Quitter");
            System.out.print("Choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer le saut de ligne

            switch (choice) {
                case 1 -> displayAvailableFilms();
                case 2 -> rentWithCredit(scanner);
                case 3 -> rentWithSubscription(scanner);
                case 4 -> viewRentalHistory();
                case 5 -> {
                    System.out.println("Merci d'avoir utilisé AL2000. Au revoir !");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private Client login(Scanner scanner) throws SQLException {
        System.out.print("Numéro de carte : ");
        int numeroCarte = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Client client = findClientByNumeroCarte(numeroCarte);
        if (client == null) {
            System.out.println("Client introuvable. Veuillez vous enregistrer.");
            return null;
        }

        System.out.println("Bienvenue, " + client.getNom() + " !");
        return client;
    }

    private Client register(Scanner scanner) throws SQLException {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Numéro de carte : ");
        int numeroCarte = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Client client = new Client(nom, numeroCarte);
        addClient(client);

        System.out.println("Inscription réussie ! Bienvenue, " + client.getNom() + " !");
        return client;
    }

    public void addClient(Client client) throws SQLException {
        clientDAO.addClient(client);
    }

    public Client findClientByNumeroCarte(int numeroCarte) throws SQLException {
        return clientDAO.findByNumeroCarte(numeroCarte);
    }

    public void displayAvailableFilms() throws SQLException {
        List<Film> films = filmDAO.search(""); // Vide pour récupérer tous les films
        System.out.println("\n--- Films Disponibles ---");
        for (Film film : films) {
            System.out.println(film.getDetails());
        }
    }

    public void rentWithCredit(Scanner scanner) throws SQLException {
        System.out.print("ID du film à louer : ");
        int filmId = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        System.out.println("Choisissez le support :");
        System.out.println("1. Blu-Ray");
        System.out.println("2. Dématérialisé");
        int supportChoice = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Support support = (supportChoice == 1) ? Support.BLU_RAY : Support.DEMATERIALISE;

        boolean success = rentFilmWithCreditCard(currentClient.getNumeroCarte(), filmId, support);
        if (success) {
            System.out.println("Film loué avec succès !");
        } else {
            System.out.println("Échec de la location. Vérifiez le film ou le support choisi.");
        }
    }

    public void rentWithSubscription(Scanner scanner) throws SQLException {
        System.out.print("ID du film à louer : ");
        int filmId = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        System.out.println("Choisissez le support :");
        System.out.println("1. Blu-Ray");
        System.out.println("2. Dématérialisé");
        int supportChoice = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Support support = (supportChoice == 1) ? Support.BLU_RAY : Support.DEMATERIALISE;

        System.out.print("ID de votre carte d'abonnement : ");
        int carteId = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        boolean success = rentFilmWithSubscription(carteId, filmId, support);
        if (success) {
            System.out.println("Film loué avec succès !");
        } else {
            System.out.println("Échec de la location. Vérifiez le solde ou le film choisi.");
        }
    }

    public boolean rentFilmWithCreditCard(int clientId, int filmId, Support support) throws SQLException {
        Film film = filmDAO.findById(filmId);
        if (film != null && film.getSupportDisponible().contains(support)) {
            LocationCarteCredit location = new LocationCarteCredit(support, 1); // Durée par défaut : 1 jour
            locationCarteCreditDAO.addLocationCarteCredit(location);
            return true;
        }
        return false;
    }

    public boolean rentFilmWithSubscription(int carteId, int filmId, Support support) throws SQLException {
        CarteAbonnement carte = carteAbonnementDAO.findById(carteId);
        Film film = filmDAO.findById(filmId);
        if (carte != null && film != null && carte.getSolde() >= 4.0 && film.getSupportDisponible().contains(support)) {
            LocationAbonne location = new LocationAbonne(support, 1); // Durée par défaut : 1 jour
            carte.setSolde(carte.getSolde() - 4.0f);
            carte.setNbFilmsLoues(carte.getNbFilmsLoues() + 1);
            carteAbonnementDAO.updateCarteAbonnement(carte);
            locationAbonneDAO.addLocationAbonne(location);
            return true;
        }
        return false;
    }

    public void viewRentalHistory() throws SQLException {
        CarteAbonnement carte = carteAbonnementDAO.findById(currentClient.getNumeroCarte());
        if (carte != null) {
            ArrayList<Film> historique = carte.getHistorique();
            System.out.println("\n--- Historique de Location ---");
            if (historique.isEmpty()) {
                System.out.println("Aucune location trouvée.");
            } else {
                for (Film film : historique) {
                    System.out.println(film.getDetails());
                }
            }
        } else {
            System.out.println("Carte d'abonnement introuvable !");
        }
    }
}


