package lightfilesystem;

/**
 * Segéd osztály a File osztály mellé.
 * File osztálnak csak egy faladata van: tartalmazza a fájl adatait.
 * A hozzáférési és engedélyek kezelésére készült ez az osztály.
 */
public class AccessChecker {
    /**
     * Megkapjuk, hogy a felhasználónak milyen hozzáférése a felhasználónak.
     *
     * @param file Az a fájl amitől a hozzáférést kérjük le.
     * @param user Felhasználó akit ellenőrzünk.
     * @return Hozzáférés típusa.
     */
    public static Access getUserAccess(File file, User user){
        if(file.getAuthor().equals(user)){
            return Access.ALL;
        }

        if(file.getGroups().contains(user.getGroup())){
            return file.getGroupsAccess();
        }
        return file.getOtherAccess();
    }

    /**
     * Ellenőrizzük le, hogy a megadd jelszó megegyezik a fájl jelszavával.
     *
     * @param file Az a fájl amitől a hozzáférést kérjük le.
     * @param password Felhasználótól kapott jelszó.
     * @return Igaz, ha a jelszavak megegyeznek.
     */
    public static Boolean isPasswordValid(File file, String password){
        return password.equals(file.getPassword());
    }
}
