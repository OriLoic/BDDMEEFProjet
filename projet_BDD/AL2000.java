import models.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AL2000 {
    private Client currentClient;

    public static void main(String[] args) {
        try {
            // Créer une instance d'AL2000 et démarrer l'application
            AL2000 al2000 = new AL2000();
            al2000.run();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'exécution du programme : " + e.getMessage());
            e.printStackTrace();
        }
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

    // Connexion d'un client en utilisant un de ses numéros de carte
    private Client login(Scanner scanner) throws SQLException {
        System.out.print("Numéro de carte : ");
        int numeroCarte = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Client client = Client.findByNumeroCarte(numeroCarte);
        if (client == null) {
            System.out.println("Client introuvable. Veuillez vous enregistrer.");
            return null;
        }

        System.out.println("Bienvenue, " + client.getNom() + " !");
        return client;
    }

    // Inscription d'un nouveau client avec plusieurs numéros de carte
    private Client register(Scanner scanner) throws SQLException {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        // Demander les numéros de carte et les ajouter à une liste
        ArrayList<Integer> numerosCartes = new ArrayList<>();
        System.out.print("Numéro de carte : ");
        int numeroCarte = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne
        numerosCartes.add(numeroCarte);

        // Ajouter le client avec ses numéros de cartes
        Client client = new Client(nom, numerosCartes);
        client.add();

        System.out.println("Inscription réussie ! Bienvenue, " + client.getNom() + " !");
        return client;
    }

    // Afficher les films disponibles
    public void displayAvailableFilms() throws SQLException {
        List<Film> films = Film.search(""); // Vide pour récupérer tous les films
        System.out.println("\n--- Films Disponibles ---");
        for (Film film : films) {
            System.out.println(film.getDetails());
        }
    }

    // Louer un film avec carte de crédit
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

        Film film = Film.findById(filmId);
        if (film != null && film.getSupportDisponible().contains(support)) {
            new LocationCarteCredit(support, 1).add();
            System.out.println("Film loué avec succès !");
        } else {
            System.out.println("Échec de la location. Vérifiez le film ou le support choisi.");
        }
    }

    // Louer un film avec carte d'abonnement
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

        CarteAbonnement carte = CarteAbonnement.findByNumeroCarte(carteId);  // Recherche par numéro de carte
        Film film = Film.findById(filmId);

        if (carte != null && film != null && carte.getSolde() >= 4.0 && film.getSupportDisponible().contains(support)) {
            carte.setSolde(carte.getSolde() - 4.0f);
            carte.setNbFilmsLoues(carte.getNbFilmsLoues() + 1);
            carte.update();
            new LocationAbonne(support, 1).add();
            System.out.println("Film loué avec succès !");
        } else {
            System.out.println("Échec de la location. Vérifiez le solde ou le film choisi.");
        }
    }

    // Voir l'historique de location du client
    public void viewRentalHistory() throws SQLException {
        // Prendre un des numéros de carte pour afficher l'historique
        int carteId = currentClient.getNumerosCartes().get(0);  // Prendre le premier numéro de carte (ou gérer autrement)
        CarteAbonnement carte = CarteAbonnement.findByNumeroCarte(carteId);
        
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
