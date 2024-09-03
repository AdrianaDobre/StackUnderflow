# StackUnderflow

## Descrierea proiectului 

Platforma StackUnderflow permite utilizatorilor să își creeze un cont și să se autentifice pentru a putea să adauge și să răspundă la întrebări. Aceștia pot sugera modificări/îmbunătățiri la comentariile deja postate. În plus, utilizatorul care a postat întrebarea poate să aleagă cel mai bun răspuns. Adăugarea de răspunsuri, întrebări și alegerea răspunsului ca fiind cel mai potrivit va aduce puncte utilizatorului, puncte care duc la obținerea de badge-uri. Întrebările postate de utilizator pot fi împărțite pe topic-uri. De asemenea, un utilizator poate da like/dislike la comentariile adăugate de alți utilizatori. Pentru comentarii se poate vedea un istoric ce conține toate editările și sugestiile acestora. 

## Tehnologii

Partea de backend a proiectului a fost implementată folosind limbajul de programare Java, partea de frontend a fost realizată în Angular, iar pentru baza de date am folosit MySql. 

## Diagrama bazei de date

![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/e84e9aec-511e-4d56-84d2-b4f15abe7548)


## Diagrame UML

O centralizare a diagramelor din cadrul echipei, cât și contribuția fiecărui membru se pot găsi în tabelul de mai jos:

<img width="850" alt="image" src="https://github.com/AdrianaDobre/StackUnderflow/assets/79320751/f55d3fb3-b0c3-4496-acfe-0d62936ba767">


### 1. Diagrama generală (USE CASE)

Pentru a pune în evidență funcționalitățile și cerințele aplicației, am conturat cazurile de utilizare prin intermediul diagramei de use case de mai jos. În diagramă, sunt definite interacțiunile dintre utilizatorii cu rol de vizitator/user și platforma implementată. 

![use_case](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/998dcc20-dbe6-4bde-8a83-1b57ed20664c)

### 2. Diagrama de clasă
Fiecare clasă este reprezentată printr-un grafic cu 3 secțiuni verticale: numele clasei, atributele și operațiile. Atributele și operatiile ce au în față simbolul + sunt publice, iar cele ce au - sunt private. 

De exemplu, clasa cu numele Topic are două atribute private: un id de tip Long și un nume de tip String. De asemenea, are și două operații publice: saveTopic care primește ca parametru un Topic și returnează un mesaj de succes/eroare și operația getAllTopics care nu are parametrii și returnează o listă cu toate topicurile.

Relațiile dintre clase sunt de asociere și agregare. Între clasele Badge și User este o relație de asociere deoarece "Userul are Badge-uri" de cardinalitate 0..* și 0..* pentru că utilizatorul poate avea 0 sau mai multe badge-uri, iar fiecare badge poate fi asociat mai multor utilizatori sau niciunuia. În schimb între clasele Post și Comment este o relație de agregare deoarece "Postarea deține comentarii". Fiecare comentariu este dependent de postarea sa, de aceea cardinalitatea este 1, iar fiecare postare poate avea 0 sau mai multe comentarii (cardinalitate 0..*).

 ![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/097bbc70-14cb-48e5-a2b7-bdd1a506be9b)

### 3. Diagrama de pachete
Pachetele oferă o modalitate de a grupa elemente și ajută la stabilirea dependențelor dintre componentele aplicației. O relație de tip *Access* într-o astfel de diagramă indică utilizarea elementelor precum clase, interfețe dintr-un pachet sau dependența directă de elemente dintr-un alt pachet. O relație de tip *Import* într-o astfel de diagramă sugerează că elementele dintr-un pachet se bazează pe tipuri (clase, interfețe etc.) definite într-un alt pachet fără utilizare directă sau dependență de funcționalitățile lor. De exemplu, în diagrama de mai jos, întrebările depind de răspunsuri, iar această dependență ar putea fi considerată o formă de *Access*, deoarece întrebările se bazează pe elementele oferite de răspunsuri.

![package_diagram](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/f3b9651b-edf0-4a4d-aec2-38678413e1bb)

Diagrama de pachete de mai jos surprinde interacțiunea și dependența dintre pachetele de pe partea de backend. Serviciile, repositories, DTO-urile, modelele, excepțiile sunt importate în clasele specifice. De exemplu, controller-ul de întrebări importă DTO-ul întrebării și depinde de serviciul unde se află metodele care ajută la gestionarea întrebărilor. 

![package_diagram_be_new](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/8819c528-1fe3-4336-81d6-1ee8a6cce196)

### 4. Diagrama de deployment

Diagrama de deployment surprinde componentele aplicației și legăturile dintre acestea. Componentele pot fi lansate pe același dispozitiv sau pe mai multe, dacă traficul este permis în rețea.

![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/0ce07456-178a-4e4c-ba5a-fb9458dd8ab5)

### 5. Diagrama Use Case (Contract API)

O variantă detaliată a diagramei Use Case se poate vedea [aici](https://github.com/AdrianaDobre/StackUnderflow/blob/main/diagrams/Use%20Case%20Diagram%20(API%20Contract).pdf) 

### 6. Diagrama de activitate pentru gestionarea adăugării de sugestii

Diagrama de activitate surprinde fluxul adăugării de sugestii. Utilizatorul adaugă o sugestie, iar mai apoi prin nodurile de decizie sunt reprenzentate cele două flow-uri: sugestia este acceptată de utilizatorul care a adăugat comentariul inițial sau este refuzată. Acceptarea sugestiei duce la schimbarea comentariului inițial și creșterea numărului de puncte a utilizatorului, actiunți pe care le-am indicat cu ajutorul nodului de join. 

![Activity Diagram For Handling Suggestions](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/31819dbb-52c6-49a9-9992-67c3eb11972e)

### 7. Diagrama de activitate pentru aprecierea raspunsurilor
Diagrama de mai jos reflectă fluxul realizat pentru aprecierea răspunsurilor oferite unei întrebări. Fluxul se separă în funcție de tipul votului: upvote sau downvote, urmând ca apoi să se separe fiecare în alte trei fluxuri în funcție de cazul prezent. Dacă votul este upvote, există următoarele cazuri: dacă postarea este apreciată deja de către utilizator acțiunea se termină, dacă utilizatorul a dat dislike răspunsului, acesta se șterge și se continuă fluxul ca pe varianta în care nu a fost oferit un vot anterior. Se adaugă noul vot și crește numărul de puncte ale utilizatorului, iar apoi acțiunea se termină. În cazul în care votul este downvote, există din nou trei cazuri posibile: dacă votul există deja acțiunea se termină, dacă este apreciată se șterge votul anterior și se scad punctele uttilizatorului, fluxul urmând apoi să corespundă celui îm care nu a fost înregistrat anterior un vot. Se adaugă downvot-ul și se încheie acțiunea.

![Activity Diagram For Answer Voting](https://github.com/AdrianaDobre/StackUnderflow/assets/79320751/9778740c-6655-4c7a-920d-e55bac874d3f)

### 8. Diagramă de secvență
Este prezentat un flow de baza al aplicatiei ce cuprinde urmatorii pasi: adresarea unei intrebari de catre un utilizator, postarea unui raspuns de catre alt utilizator, alegerea celui mai bun raspuns de catre owner-ul intrebari si primirea de puncte sau badge de catre utilizatorul ce a postat raspunsul castigator. Diagrama prezinta interactiunea dintre componentele principale ale aplicatiei pentru aceste actiuni.

![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/83ec5abb-10c2-4d18-8226-6a99f9bdfc07)

### 9. Diagrama de stare pentru editarea unui comentariu

Această diagramă surprinde stările și evenimentele prin care aplicația trece în momentul în care un utilizator dorește să editeze un comentariu.

![StateMachineDiagramForEditComment](https://github.com/AdrianaDobre/StackUnderflow/assets/79691379/73d65b2e-8419-417a-a387-d89252d1f083)

### 10. Diagrama de componente pentru autentificare

![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79320751/598aeb6e-2912-4b44-b3d6-a3927407d60c)

### 11. Diagrama de componente pentru proiectul de frontend
Sunt ilustrate componentele principale ale aplicatiei Angular de frontend si modul in care acestea formeaza dependintele pentru fiecare pagina accesata de utilizator. In partea de jos a diagramei sunt reprezentate componentele ce reprezinta pagini sau rute in aplicatia web, la mijloc se afla componentele mai mici ale proiectului iar in partea de sus sunt desenate clasele typescript ce reprezinta serviciile din frontend ce fac apelurile catre backend.

![frontend component diagram-2](https://github.com/AdrianaDobre/StackUnderflow/assets/79518275/73ff278d-a345-4f3f-9e11-d86684ed50a7)


Component diagram for the auth flow, depicting most Spring provided components required for validating a JWT Token, creating the AuthToken and putting the AuthToken in the context of the request.

## Design Patterns

* #### Repository Pattern

Am folosit repositories pentru accesarea contextului bazei de date, deoarece oferă o mai bună menținere și decuplare a infrastructurii. Ele separă stratul logic de date de restul straturilor și ajută la scrierea unui proiect curat și lizibil. Am utilizat Spring Data care permite generarea repositories pentru entitățile din baza de date. Acestea pot genera cele mai comune operațiuni de creare, citire, actualizare și ștergere (CRUD) și interogări personalizate. Un exemplu se poate observa în imaginea de mai jos.

![image](https://github.com/AdrianaDobre/StackUnderflow/assets/79576756/df6a6b2a-aa23-42e9-8037-0b7925c6a41d)

* #### Data Transfer Object Pattern

Data Transfer Object (DTO) este un design pattern folosit în mod frecvent în dezvoltarea de software pentru a facilita transferul de date între diferite componente ale unei aplicații, mai ales în cazul în care aceste componente se află pe servere diferite sau necesită comunicare prin rețea. Scopul principal al acestui pattern este *reducerea numărului de request-uri la server* prin gruparea mai multor date într-un singur obiect, care este apoi transmis într-o singur răspuns.
Un alt avantaj al utilizării DTO este *simplificarea procesului de mapare a datelor* între diferite layere ale aplicației. Într-un sistem complex, datele trec adesea prin multiple straturi - de la bazele de date și serviciile de business logic, până la interfața cu utilizatorul. Utilizând DTO-uri, aceste date pot fi mapate eficient între layere fără a compromite integritatea modelelor.

De asemenea, DTO-urile sunt esențiale în aplicarea principiului "separation of concerns" (separarea responsabilităților). Prin introducerea acestor obiecte intermediare, se evită expunerea directă a entităților din domeniu către exteriorul aplicației, ceea ce poate preveni modificări neintenționate sau acces neautorizat la date sensibile. DTO-urile permit filtrarea și transformarea datelor înainte ca acestea să ajungă la utilizator sau la alte sisteme externe, asigurând astfel un *nivel sporit de securitate și control asupra datelor care sunt transferate*.

În aplicația web StackUnderflow, DTO-urile sunt folosite pentru transferul și filtrarea datelor între backend-ul și frontend-ul aplicației. Vom lua ca exemplu clasa Users care corespunde cu entitatea din baza de date a aplicației (în Java în clasa Users sunt definite atât proprietățile obiectului, cât și relațiile pe care le are acesta). 

![image](https://github.com/user-attachments/assets/44aa2670-3caa-456f-a415-e6d24f9cd655)

Clasa UserDTO conține proprietăți comune clasei Users inițiale precum username, email și phoneNumber care vor fi populate printr-o mapare simplă între cele două clase. În plus conține și proprietăți noi ce sunt necesare afișării informațiilor în frontend precum badges și votes; acestea fiind populate și verificate la momentul creării request-ului. De asemenea, se poate observa cum anumite proprietăți lipsesc, password și role, pentru a nu trimite către frontend date sensibile. În final, informațiile din DTO sunt trimise către frontend, urmând ca datele să fie afișate utilizatorilor.

![image](https://github.com/user-attachments/assets/4c8aabd8-2363-4ce6-a48a-41007066a8f7)

* #### Client Server Pattern
  
Modelul Client-Server este o arhitectură de calcul distribuită care împarte aplicațiile software în două roluri distincte: client și server. Acest model arhitectural permite separarea preocupărilor între interfața cu utilizatorul (client) și procesarea sau stocarea datelor (server). Modelul Client-Server este o structură care distribuie sarcini între furnizorii de resurse sau servicii numite servere și elementele care solicită resurse sau servicii, numite clienți.

În mod obișnuit, componenta client nu partajează resursele sale, ci solicită diverse date și servicii de la server. Cele două componente sunt conectate prin intermediul unor conectori de tip request-response. Comunicarea între ele este posibilă prin utilizarea unui limbaj comun și respectarea unor reguli de bază stabilite într-un protocol de comunicație. Atunci când se transmit informații sensibile, este necesară criptarea pentru a asigura securitatea comunicării între client și server.

Principalul avantaj al utilizării modelului Client-Server constă în *centralizarea datelor cu același scop* într-un singur loc, ceea ce oferă un grad ridicat de scalabilitate, organizare și eficiență. Întreținerea sistemului este facilitată, deoarece componentele server și client pot fi modificate și actualizate separat. Totuși, acest model prezintă și unele *vulnerabilități*, cum ar fi expunerea la atacuri de tip Phishing, Man in the Middle și Denial of Service. De asemenea, în cazul în care serverul devine indisponibil, utilizatorii vor fi deconectați și nu vor putea accesa aplicația.

![client-server drawio](https://github.com/user-attachments/assets/82abf63c-193d-4337-9b9f-725d964cc987)


