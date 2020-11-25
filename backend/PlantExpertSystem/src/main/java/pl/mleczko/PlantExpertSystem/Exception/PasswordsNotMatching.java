package pl.mleczko.PlantExpertSystem.Exception;


public class PasswordsNotMatching extends RuntimeException {

    public PasswordsNotMatching() {
        super("Podane hasło jest nieprawidłowe");
    }
}
