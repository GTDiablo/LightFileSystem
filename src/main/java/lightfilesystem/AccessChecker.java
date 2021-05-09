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
}
