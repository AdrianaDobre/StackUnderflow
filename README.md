# StackUnderflow

## Descrierea proiectului 

Platforma StackUnderflow permite utilizatorilor să își creeze un cont și să se autentifice pentru a putea să adauge și să răspundă la întrebări. Acești pot sugera modificări/îmbunătățiri la comentariile deja postate. În plus, utilizatorul care a postat întrebarea poate să aleagă cel mai bun răspuns. Adăugarea de răspunsuri, întrebări și alegerea răspunsului ca fiind cel mai potrivit va aduce puncte utilizatorului, puncte care duc la obținerea de badge-uri. Întrebările postate de utilizator pot fi împărțite pe topic-uri. De asemenea, un utilizator poate da like/dislike la comentariile adăugate de alți utilizatori. Pentru comentarii se poate vedea un istoric ce conține toate editările și sugestiile acestora. 

## Tehnologii

Partea de backend a proiectului a fost implementată folosind limbajul de programare Java, partea de frontend a fost realizată în Angular, iar pentru baza de date am folosit MySql. 

## Diagrama bazei de date

![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/e84e9aec-511e-4d56-84d2-b4f15abe7548)


## Diagrame UML

O centralizare a diagramelor din cadrul echipei, cât și contribuția fiecărui membru se pot găsi în tabelul de mai jos:

DE ADAUGAT POZA

### 1. Diagrama generală (USE CASE)

Pentru a pune în evidență funcționalitățile și cerințele aplicației, am conturat cazurile de utilizare prin intermediul diagramei de use case de mai jos. În diagramă, sunt definite interacțiunile dintre utilizatorii cu rol de vizitator/user și platforma implementată. 

![use_case](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/998dcc20-dbe6-4bde-8a83-1b57ed20664c)

### 2. Diagrama de clasă
 ![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/097bbc70-14cb-48e5-a2b7-bdd1a506be9b)

### 3. Diagrama de pachete
![package_diagram](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/f3b9651b-edf0-4a4d-aec2-38678413e1bb)

### 4. Diagrama de deployment
![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/0ce07456-178a-4e4c-ba5a-fb9458dd8ab5)

### 5. Diagrama Use Case (Contract API)

O variantă detaliată a diagramei Use Case se poate vedea aici (DE ADAUGAT LINK)

### 6. Diagrama de activitate pentru gestionarea adăugării de sugestii
![Activity Diagram For Handling Suggestions](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/31819dbb-52c6-49a9-9992-67c3eb11972e)

### 7. Diagramă de secvență
![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/83ec5abb-10c2-4d18-8226-6a99f9bdfc07)

## Design Patterns

* #### Repository Pattern

Am folosit repositories pentru accesarea contextului bazei de date, deoarece oferă o mai bună menținere și decuplare a infrastructurii. Ele separă stratul logic de date de restul straturilor și ajută la scrierea unui proiect curat și lizibil. Am utilizat Spring Data care permite generarea repositories pentru entitățile din baza de date. Acestea pot genera cele mai comune operațiuni de creare, citire, actualizare și ștergere (CRUD) și interogări personalizate. Un exemplu se poate observa în imaginea de mai jos.

![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/df6a6b2a-aa23-42e9-8037-0b7925c6a41d)

* #### Data Transfer Object Pattern
  
DTO este un design pattern bine-cunoscut și adesea folosit. Un DTO este o clasă Java simplă care ajută la rafinarea unui obiect pentru a fi transferat în cel mai eficient și sigur mod din backend către client (în cazul nostru frontend). Totodată, ajută la minimizarea dependințelor. 

![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/9149012f-031b-4f0b-8e65-9ca152160c0f)

* #### Client Server Pattern
  
Modelul client-server este o arhitectură de calcul distribuită care împarte aplicațiile software în două roluri distincte: client și server. Acest model arhitectural permite separarea preocupărilor între interfața cu utilizatorul (client) și procesarea sau stocarea datelor (server). Modelul client-server este o structură care distribuie sarcini între furnizorii de resurse sau servicii numite servere și elementele care solicită resurse sau servicii, numite clienți.





