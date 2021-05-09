package lightfilesystem;

/**
 * LightFileSystem-ben 4 féle hozzáférési lehetőség van:
 * READ - Fájlt csak olvashatja a felhasználó
 * WRITE - Fájlt csak írhatja a felhasználó, de látni nem láthatja
 * NONE - A felhasználó nem csinálhat semmit se a fájlal (ez a alap beállítas)
 * ALL - A fejlahsználó bármit csinálhat a fájlal
 */
public enum Access {
    READ,
    WRITE,
    NONE,
    ALL
}
