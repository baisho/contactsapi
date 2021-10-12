Kapcsolattartó REST API

Az alábbi API kapcsolattartók kezelésére létrehozott REST szolgáltatást nyújt. Az adatok PostgreSQL adatbázisban 
vannak elmentve és kezelve. Az API az adott szerkezetű és összetételű adatokkal operál:

    id – a kapcsolattartó azonosítója, számérték
    lastName - a kapcsolattartó vezetékneve, szövegérték
    firstName - a kapcsolattartó keresztneve, szövegérték
    email - a kapcsolattartó email címe, szövegérték valós email cím formátum vizsgálattal
    phone - a kapcsolattartó telefonszáma, szövegérték E-164 formátum vizsgálattal (pl. +123456789)
    note – megjegyzés, szövegérték
    status – a kapcsolattartó státusza, enumerációs érték „ACTIVE” és „DELETED” értékekkel
    dateCreation – a kapcsolattartó adatbázisba kerülésének ideje, dátum és idő érték
    dateLastModify - a kapcsolattartó rekordját érintő utolsó módosítás ideje, dátum és idő érték
    company – a kapcsolattartó cégének adatai, mely két értéket tartalmaz:
	id - kapcsolattartó cégének azonosítója, számérték
	name - kapcsolattartó cégének neve, szövegérték


A dátum és idő érték az alábbi formátum szerint iródik:
éééé-hh-nn óó:pp:mp.emp
pl: 2021-10-12 10:01:13.789

Az API különböző végpontokkal rendelkezik, melyek az alábbi eseteket fedik le:
•	Kapcsolattartók listázása
•	Kapcsolattartók részletezése
•	Kapcsolattartók létrehozása
•	Kapcsolattartók módosítása 
•	Kapcsolattartók törlése




Listázás végpont

A végpont az alábbi URL-lel érhető el:
/contacts/list?pageNo=0&pageSize=10

A végpont GET lekérést vár, és az adatbázisban található összes aktív státuszú kapcsolattartót küldi vissza kilistázva 
JSON formátumban a vezetéknév szerint ábécérendbe rakva. A lista adatait, túl sok kapcsolattartó esetén, laponként is 
le tudjuk kérni. A list URL részlet után URL paraméterként a megjeleníteni kívánt oldalszámot (pageNo) és az egy oldalon 
megjelenített kapcsolattartók számát (pageSize) tudjuk meghatározni. Ha a paramétereket üresen hagyjuk, vagy lehagyjuk az 
URL-ről, akkor az alapértelmezett értékek szerint (pageNo=0, pageSize=10) történik a lekérdezés.
Minden listaelem a következő adatokat adja vissza: id, lastName, firstName, email, phone, status, company(id, name)




Részletezése végpont

A végpont az alábbi URL-lel érhető el:
/contacts/details?id=8

A végpont egy adott kapcsolattartó összes adatát adja vissza részletezve, a végpont GET lekérést vár, paraméterben 
meghatározva pedig a kapcsolattartó azonosítóját (id) kell megadni.
A részletezett elem a következő adatokat adja vissza : id, lastName, firstName, email, phone, note, status, 
dateCreation, dateLastModify, company(id, name)




Létrehozás végpont

A végpont az alábbi URL-lel érhető el:
/contacts/save

Ez a végpont az új kapcsolattartók adatainak adatbázisba történő mentését szolgálja. A lekérés automatikusan 
hozzárendel egy új azonosítót a kapcsolattartóhoz, ACTIVE-ra állítja az elmentett kapcsolattartó státuszát, valamint 
automatikusan beállítja a létrehozás és az utolsó módosítás idejét a lekérés pillanatára. A végpont POST lekérést vár 
el, melynek a törzsének JSON formátúmúnak kell lennie, és az alábbi adatokat kell tartalmaznia: lastName, firstName, 
email, company(id, name)

Opcionálisan tartalmazhatja még az alábbiakat: phone, note




Módosítás végpont

A végpont az alábbi URL-lel érhető el:
/contacts/update
Ez a végpont a már meglévő kapcsolattartók adatainak módosítására szolgál. A lekérés automatikusan beállítja az utolsó 
módosítás idejét a lekérés pillanatára, a státuszt érintetlenül hagyja ACTIVE állapotban. A végpont PUT lekérést vár el, 
melynek a törzsének JSON formátúmúnak kell lennie, és az alábbi adatokat kell tartalmaznia: id, lastName, firstName, 
email, dateCreation, company(id, name)

Opcionálisan tartalmazhatja még az alábbiakat: phone, note




Törlés végpont

A végpont az alábbi URL-lel érhető el:
/contacts/delete

Ez a végpont a már meglévő kapcsolattartók státuszának módosítására szolgál. A lekérés automatikusan beállítja az utolsó 
módosítás idejét a lekérés pillanatára, a státuszt pedig ACTIVE állapotról átállítja DELETED állapotra. A végpont PUT 
lekérést vár el, melynek a törzsének JSON formátúmúnak kell lennie, és az alábbi adatokat kell tartalmaznia: id, lastName, 
firstName, email, dateCreation, company(id, name)
Opcionálisan tartalmazhatja még az alábbiakat: phone, note





# Használati útmutató

A projekt tartalmaz egy `docker-compose.yml` fájlt, ami adatbázist biztosít a fejlesztéshez.

## Konténerek indítása

Az indításhoz előfeltétel, hogy Docker (pl. Docker for Desktop) telepítve legyen a számítógépen.

Ezt követően parancssorból a konténerek egyszerűen elindíthatóak az alábbi parancs kiadásával:  
`docker-compose up`

Leállítani megszakítás küldésével (`CTRL+C`) lehetséges.

### Daemon mód

Ebben az esetben a konténerek a háttérben fognak futni.

Indítás: `docker-compose up -d`  
Leállítás: `docker-compose down`

Logok olvasása:  
`docker-compose logs -f database`  
`docker-compose logs -f mailhog`

## Adatbázis

Adatbázisként PostgreSQL került beállításra. A konténer nem került beállításra perzisztens volume, így a konténer
törlésekor elveszhet a benne tárolt adat.

Inicializáláskor az adatbázisba betöltésre kerül egy `company` tábla néhány példa adattal, ami a cég kapcsolat
kialakításához használható a feladatban.

### Csatlakozáshoz szükséges adatok

jDBC connection string: `jdbc:postgresql://localhost:15432/contactsapi`

Felhasználónév: `contactsapi`  
Jelszó: `contactsapi`  
Adatbázis: `contactsapi`
