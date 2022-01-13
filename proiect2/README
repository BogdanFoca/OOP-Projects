# Santa Claus is coming to ACS students
Datele din fisier se citesc de obiectul JSONReader care citeste datele in format JSON si creeaza instante ale claselor corespunzatoare cu parametrii citit.

Toate informatiile sunt stocate in Database, obiect singleton.

Inainte de inceperea unei noi simulari, Databaseul este curatat.

Simularea este calculata de obiectul SimulationManager de tip Singleton.

Acesta initializeaza un obiect de tip JSONOutput care stocheaza in el outputul pentru fiecare an din simulare.

In anul 0:

    Se calculeaza bugetul pentru fiecare copil.
    Se creeaza o lista de outputChildren ce va fi adaugata in JSONOutput si se adauga la ea toti copiii din Database, converiti in OutputChild.
    Se impart cadourile pentru copii.
    Se elimina cei deveniti adulti.
    Se adauga copiii din outputChildren in JSONOutput.
In fiecare an de dupa:

    Se calculeaza bugetul pentru fiecare copil.
    Se updateaza Databaseul cu informatiile din JSONReader de la anul curent
        Fiecare copil devine mai mare cu 1 an si daca devine adult este scos din Database.
        Se adauga copiilor nice scoreul nou si preferintele de cadou.
        Se adauga copiii noi care nu sunt adulti.
    Se creeaza o lista de outputChildren ce va fi adaugata in JSONOutput si se adauga la ea toti copiii din Database, converiti in OutputChild.
    Se impart cadourile pentru copii.
    Se elimina cei deveniti adulti.
    Se adauga copiii din outputChildren in JSONOutput.
Se scriu informatiile din JSONOutput intr-un fisier.