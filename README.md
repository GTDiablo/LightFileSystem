# Light Filesystem

***Beadandó: Szoftverfejlesztés Labor***

***Készítette: Boda Zsolt***

## Rövid összegzés

Szerettem volna létrehozni egy linux-hoz hasonló fájlrendszer felügyelő, irányító és engedély kezelést szimuláló java programot. Projektben a fő üzleti logika a felhasználókon és csoportokon alapuló engedély rendszer és a program állapotát irányító állapot menedzser. Maga a LightFileSystem csomag teljesen külön is használható a GUI-tól.

## Rendszer alapszabályai
- Fájlrendszerben a nevek az azonosítók. Mindent felhasználónak, csoportnak és fájlnak egyedi neve van és csak egyedi neveket lehet adni nekik.
- A fájl tulajdonosának minden esetben van minden (ALL) joga a fájlhoz. Akkor is van joga hozzá, ha a fájlon a csoport és más felhasználó joga nincs (NONE).
- Felhasználó csak akkor láthatja a fájl tartalmát, ha a következők közül valamelyik teljesül:
    -  van olvasó joga (READ)
    - van minden joga (ALL)
    - helyesen adta meg a fájlhoz tartozó jelszavat
- Felhasználó csak akkor tudja szerkeszteni fájlt vagy a fájlhoz tartozó beállításokat, ha a következők közül valamelyik teljesül:
    - felhasználó a fájl tulajdonosa
    - fájlnak a másokra vonatkozó engedély ALL vagy WRITE
    - felhasználó benne van egy olyan csoportban, amiben a fájl is benne van és a fájl csoportokra vonatkozó engedélye ALL vagy WRITE

## Folyamat

1. Fájlrendszer és state menedzser felállítása: programban megtalálható az alapértelmezett adatokat tartalmazó config fájl (filesystem.json). A config fájl neve csakis filesystem.json lehet, de a ennek a pozíciója változhat. Ha még nem létezik a saját config fájlunk a saját felhasználó mappában, akkor a program az alapértelmezett config fájlból fogja betölteni az adatokat és ebből építi fel magának a fájlrendszert. GUI ablak bezárásakor a program már a saját mappába menti a config fájl-t és a következő alkalommal innen tölti be az adatokat (persze ha még létezik).
2. Következőnek maga a GUI ablak jön fel, ami a state menedzser adatai alapján dolgozik és számol. A state-ben nincs beállítva azonnal fájl, viszont felhasználóból igen, ami az első talált felhasználó a rendszerben.

## Rendszer követelmények
- AdoptOpenJDK -> 16.0.1.j9-adpt
- Apache Maven 3
- Linux vagy Windows 10

## Használat
Program futtatása maven-ben (jar nélkül):

    mvn clean install javafx:run

Program csomagolása jar fájlba, majd futtatása:

    mvn clean package site:site

Majd a `/target` mappában keressük meg a generált jar fájlt és adjuk ki a következő parancsot:

    java -jar LightFileSystem-1.0.jar
