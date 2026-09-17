# Expense Tracker

Expense Tracker este o aplicatie Android dezvoltata in Kotlin si Jetpack Compose, care permite utilizatorului sa isi gestioneze si monitorizeze cheltuielile.

Aplicatia permite adaugarea, modificarea si stergerea cheltuielilor, precum si calcularea automata a sumelor cheltuite pe categorii. Utilizatorul poate seta un buget, iar aplicatia calculeaza suma ramasa sau valoarea depasita.

## Functionalitati

* Adaugarea unei cheltuieli
* Editarea unei cheltuieli
* Stergerea unei cheltuieli
* Categorii pentru cheltuieli:

  * Food
  * Transport
  * Entertainment
  * Other
* Calcularea automata a totalului cheltuielilor
* Calcularea cheltuielilor pe categorii
* Setarea si modificarea bugetului
* Calcularea sumei ramase din buget
* Afisarea datoriei atunci cand cheltuielile depasesc bugetul
* Salvarea persistenta a datelor

## Tehnologii

* Kotlin
* Android
* Jetpack Compose
* Room
* DataStore Preferences
* Kotlin Coroutines
* Flow
* ViewModel
* KSP

## Arhitectura

Aplicatia foloseste o separare a responsabilitatilor intre componente.

```text
UI / Composable
       |
       v
   ViewModel
      / \
     /   \
    v     v
  DAO   DataStore
   |       |
   v       v
 Room    Budget
Database
```

### UI

Interfata este realizata folosind Jetpack Compose.

Componentele UI sunt responsabile de afisarea datelor si de interactiunea cu utilizatorul. UI-ul nu acceseaza direct baza de date.

### ViewModel

`ExpenseViewModel` face legatura dintre UI si sursele de date.

Responsabilitatile sale includ:

* primirea actiunilor din UI
* apelarea operatiilor DAO
* expunerea listei de cheltuieli catre UI
* gestionarea operatiilor suspend
* transmiterea datelor necesare catre DAO

Aceasta separare permite UI-ului sa ramana concentrat pe afisare si interactiune.

### DAO

`ExpenseDao` este responsabil pentru comunicarea cu baza de date Room.

Acesta contine operatii precum:

* obtinerea tuturor cheltuielilor
* adaugarea unei cheltuieli
* modificarea unei cheltuieli
* stergerea unei cheltuieli

DAO-ul nu contine logica UI.

### Room

Room este utilizat pentru stocarea cheltuielilor.

Entitatea principala este `Expense`, care contine informatii precum:

* ID
* titlu
* descriere
* pret
* tip
* data crearii

Lista cheltuielilor este expusa prin `Flow<List<Expense>>`, permitand UI-ului sa reactioneze automat atunci cand datele din baza de date se modifica.

### DataStore

DataStore Preferences este utilizat pentru stocarea bugetului.

Bugetul este tratat ca o preferinta a aplicatiei, nu ca o entitate relationala.

Astfel, aplicatia foloseste:

```text
Room
    -> datele cheltuielilor

DataStore
    -> bugetul aplicatiei
```

DataStore expune bugetul printr-un `Flow`, iar Compose poate observa modificarile si actualiza automat interfata.

## Coroutines si suspend

Operatiile care modifica baza de date sau DataStore sunt executate folosind coroutines.

Functiile DAO precum `addExpense`, `delete` si `update` sunt `suspend`, deoarece operatiile de acces la date nu trebuie executate direct pe thread-ul UI.

In Compose, acestea sunt apelate folosind un `CoroutineScope`.

```text
UI
 |
 v
Coroutine
 |
 v
ViewModel
 |
 v
DAO / DataStore
```

## Reactive UI

Aplicatia foloseste `Flow` impreuna cu `collectAsState()` pentru a mentine interfata sincronizata cu datele.

De exemplu:

```text
Room
  |
  v
Flow<List<Expense>>
  |
  v
collectAsState()
  |
  v
Compose State
  |
  v
Recomposition
```

Atunci cand o cheltuiala este adaugata, modificata sau stearsa, lista emisa de Room se modifica, iar Compose poate actualiza automat interfata.

## Calcularea cheltuielilor

Calcularea sumelor este separata intr-o componenta `PriceCalculation`.

Aceasta primeste lista de cheltuieli si poate calcula:

* totalul cheltuielilor
* totalul pentru Food
* totalul pentru Transport
* totalul pentru Entertainment
* totalul pentru Other

Separarea acestei logici de UI permite ca partea de calcul sa fie independenta de interfata.

## Gestionarea bugetului

Bugetul este afisat pe pagina principala si poate fi modificat printr-un dialog accesibil printr-un buton de tip Settings.

Fluxul este:

```text
User
 |
 v
Settings
 |
 v
DialogBudget
 |
 v
saveBudget()
 |
 v
DataStore
 |
 v
Flow<Double>
 |
 v
HomePage
```

Atunci cand bugetul se modifica, noua valoare este emisa prin `Flow`, iar HomePage se recomputeaza automat.

## Structura proiectului

```text
com.example.expensetracker
|
├── DAO
|   └── ExpenseDao
|
├── Database
|   └── ExpenseDATABASE
|
├── DataStore
|   └── BudgetDataStore
|
├── Model
|   └── Expense
|   └── Type
|
├── ViewModel
|   ├── ExpenseViewModel
|   └── ExpenseViewModelFactory
|
├── Service
|   ├── DialogEdit
|   |   ├── AlertDialogEdit
|   |   └── DialogBudget
|   |
|   └── PriceCalculation
|
├── Interface
|   ├── HomePage
|   ├── AddPage
|   └── ViewPage
|
└── Pages
    ├── AddPage
    └── ViewListPage
```

## Principii utilizate

Proiectul urmareste cateva principii de baza:

* separarea responsabilitatilor
* UI separat de accesul la date
* folosirea ViewModel pentru logica dintre UI si date
* folosirea DAO pentru accesul la Room
* folosirea DataStore pentru preferinte simple
* folosirea Flow pentru date reactive
* folosirea Coroutines pentru operatii suspend
* mentinerea logicii de calcul separata de UI

Scopul acestei structuri este ca fiecare componenta sa aiba o responsabilitate clara si ca proiectul sa fie mai usor de inteles, modificat si extins.
