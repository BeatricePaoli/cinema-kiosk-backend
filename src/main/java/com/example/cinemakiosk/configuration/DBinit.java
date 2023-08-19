package com.example.cinemakiosk.configuration;

import com.example.cinemakiosk.model.*;
import com.example.cinemakiosk.repository.*;
import com.example.cinemakiosk.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class DBinit {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieGenreRepository movieGenreRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private BookingRepository bookingRepository;

//    @PostConstruct
    public void init() throws IOException {

        bookingRepository.deleteAll();
        showRepository.deleteAll();
        movieRepository.deleteAll();
        actorRepository.deleteAll();
        movieGenreRepository.deleteAll();
        theaterRepository.deleteAll();

        List<MovieGenre> genres = movieGenreRepository.findAll();
        if (genres.isEmpty()) {

            genres = Arrays.asList(createGenre("Avventura"), createGenre("Azione"), createGenre("Animazione"),
                    createGenre("Musical"), createGenre("Fantascienza"), createGenre("Fantasy"),
                    createGenre("Drammatico"), createGenre("Commedia"), createGenre("Horror"));

            movieGenreRepository.saveAll(genres);
        }

        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            Movie m = new Movie();
            m.setName("Spider-man: Across the Spiderverse");
            m.setDescription("""
                    Dopo essersi riunito con Gwen Stacy l'amichevole Spider-Man di quartiere di Brooklyn Miles Morales viene catapultato nel Multiverso, dove incontra una squadra di Spider-Eroi incaricata di proteggerne l'esistenza. Ma quando gli eroi si scontrano su come affrontare una nuova minaccia, Miles si ritrova contro gli altri “Ragni” e dovrà ridefinire cosa significa essere un eroe per poter salvare le persone che ama di più.
                    Ormai studente al college, Miles Morales si è anche riunito con Gwen Stacy. Da Brooklyn, l’amichevole Spider-Man di quartiere viene trasportato nel Multiverso, lì dove incontra una squadra di Spider-Eroi, i quali hanno il compito di proteggere l’esistenza stessa dalle minacce cui può andare incontro per mano di nemici che intendono distruggerla.
                    Quando si troveranno a dover difendere il Multiverso da un pericolo imminente, gli Spider-Eroi saranno in disaccordo sulle scelte da intraprendere, arrivando addirittura a scontrarsi. In questa incandescente situazione, Miles si ritroverà da solo contro gli altri “Ragni”, e dovrà comprendere prima di tutto cosa significhi essere un eroe che ha tra le proprie mani il destino delle altre persone.""");
            m.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2023, 6, 1)));
            m.setDurationMins(140);
            m.setScore(4.5f);
            m.setActors(new HashSet<>(Arrays.asList(
                    createActor("Shameik", "Moore", "classpath:static/images/sp1.jpg"),
                    createActor("Hailee", "Steinfeld", "classpath:static/images/sp2.jpg"),
                    createActor("Issa", "Rae", "classpath:static/images/sp3.jpg"),
                    createActor("Oscar", "Isaac", "classpath:static/images/sp4.jpg"),
                    createActor("Brian Tyree", "Henry", "classpath:static/images/sp5.jpg"),
                    createActor("Jake", "Johnson", "classpath:static/images/sp6.jpg"),
                    createActor("Luna Lauren", "Velez", "classpath:static/images/sp7.jpg"),
                    createActor("Rachel", "Dratch", "classpath:static/images/sp8.jpg")
            )));
            m.setGenres(new HashSet<>(Arrays.asList(getGenre("Avventura"), getGenre("Azione"), getGenre("Animazione"))));
            File file = ResourceUtils.getFile("classpath:static/images/spider.jpg");
            m.setImg(Files.readAllBytes(file.toPath()));
            movies.add(m);

            Movie m2 = new Movie();
            m2.setName("Transformers - il Risveglio");
            m2.setDescription("Sul mondo dei Maximals, robot con la forma di animali terrestri, giunge il divora pianeti Unicron, in cerca della chiave di transcurvatura. I Maximals però fuggono portandola con sé, impedendogli così di liberarsi. Il transformers Scourge, al servizio di Unicron, è però sulle loro tracce... Nella New York del 1994, nel mentre, l'ex soldato Noah Diaz e la brillante archeologa Elena Wallace navigano difficoltà personali e soprattutto professionali, che li portano, in modo diverso, a scoprire l'esistenza dei Transformers. Per questi ultimi la chiave di Transcurvatura sarebbe il solo modo di ritornare sul pianeta nativo di Cybertron, ma la minaccia di Unicron è troppo grande per correre dei rischi.");
            m2.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2024, 6, 7)));
            m2.setDurationMins(117);
            m2.setScore(4f);
            m2.setActors(new HashSet<>(Arrays.asList(
                    createActor("Anthony", "Ramos", "classpath:static/images/tr1.jpg"),
                    createActor("Dominique", "Fishback", "classpath:static/images/tr2.jpg")
            )));
            m2.setGenres(new HashSet<>(Arrays.asList(getGenre("Avventura"), getGenre("Azione"), getGenre("Fantascienza"))));
            File file2 = ResourceUtils.getFile("classpath:static/images/transformers.jpg");
            m2.setImg(Files.readAllBytes(file2.toPath()));
            movies.add(m2);

            Movie m3 = new Movie();
            m3.setName("La sirenetta");
            m3.setDescription("Ariel è una sirena, una delle sette figlie del Re Tritone, ma ha una passione per il mondo di superficie che il padre non gradisce. Rischia la vita tra gli squali per esplorare i relitti in fondo al mare e raccogliere oggetti della terraferma, per lei molto misteriosi. Quando durante una tempesta salva un bel marinaio, che è anche un principe, ne resta perdutamente innamorata. I divieti del padre servono solo a spingerla in direzione di Ursula, la strega del mare, che le offre di renderla umana in cambio della sua voce. Ariel accetta e avrà solo tre giorni di tempo per baciare il principe, un'impresa ulteriormente complicata dal sortilegio della strega, che gioca sporco e le ruba anche la memoria...");
            m3.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2024, 5, 24)));
            m3.setDurationMins(135);
            m3.setScore(3f);
            m3.setActors(new HashSet<>(Arrays.asList(
                    createActor("Halle", "Bailey", "classpath:static/images/si1.jpg"),
                    createActor("Jonah", "Hauer-King", "classpath:static/images/si2.jpg"),
                    createActor("Daveed", "Diggs", "classpath:static/images/si3.jpg"),
                    createActor("Jacob", "Tremblay", "classpath:static/images/si4.jpg")
            )));
            m3.setGenres(new HashSet<>(Arrays.asList(getGenre("Avventura"), getGenre("Musical"), getGenre("Fantasy"))));
            File file3 = ResourceUtils.getFile("classpath:static/images/sirenetta.jpg");
            m3.setImg(Files.readAllBytes(file3.toPath()));
            movies.add(m3);

            Movie m4 = new Movie();
            m4.setName("The Flash");
            m4.setDescription("Si avvicina il giorno del processo per il padre di Barry Allen, da anni in carcere per l'omicidio di sua moglie. Il ragazzo, che in realtà è anche il supereroe The Flash, ha cercato in ogni modo di dimostrare la sua innocenza, ma anche con l'aiuto delle Wayne Enterprises e dei suoi superpoteri non ha trovato come scagionarlo. Per disperazione arriva a correre più veloce di quanto abbia mai fatto, fino a raggiungere una sorta di ruota del tempo. Ma scopre presto che viaggiare nel passato non cambia solo il presente e il futuro: in questo mondo infatti il suo amico Batman è molto diverso e non sembra esserci alcuna traccia di Superman. In compenso il Barry di questa realtà vive felice con i genitori, ma per proteggere la sua vita il Barry originale dovrà stravolgerla e, nel farlo, finirà per perdere i propri poteri");
            m4.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2024, 5, 15)));
            m4.setDurationMins(144);
            m4.setScore(4f);
            m4.setActors(new HashSet<>(Arrays.asList(
                    createActor("Ezra", "Miller", "classpath:static/images/fl1.jpg"),
                    createActor("Michael", "Keaton", "classpath:static/images/fl2.jpg"),
                    createActor("Ben", "Affleck", "classpath:static/images/fl3.jpg"),
                    createActor("Michael", "Shannon", "classpath:static/images/fl4.jpg")
            )));
            m4.setGenres(new HashSet<>(Arrays.asList(getGenre("Avventura"), getGenre("Azione"), getGenre("Fantasy"))));
            File file4 = ResourceUtils.getFile("classpath:static/images/flash.jpg");
            m4.setImg(Files.readAllBytes(file4.toPath()));
            movies.add(m4);

            Movie m5 = new Movie();
            m5.setName("Denti da Squalo");
            m5.setDescription("Denti da squalo, il film diretto da Davide Gentile, si svolge in un’estate diversa dalle altre per il tredicenne Walter (Tiziano Menichelli).\n" +
                    "È la prima estate che passerà senza suo padre Antonio (Claudio Santamaria) che è morto da poco in un incidente sul lavoro.\n" +
                    "Siamo sul litorale romano e la scuola è appena finita, sono rimasti da soli lui e sua madre Rita (Virginia Raffaele) che difficilmente accetta questo terribile lutto. Rita si chiude sempre più in se stessa e i suoi silenzi la stanno allontanano da suo figlio.\n" +
                    "Walter passa le sue giornate gironzolando senza meta e un giorno la sua attenzione viene catturata da un luogo misterioso, una villa con una grande piscina. L’acqua della piscina però è torbida e contrariamente a quello che pensa inizialmente, la villa non è abbandonata.\n" +
                    "Ad occuparla c’è il criminale di zona conosciuto come Il Corsaro (Edoardo Pesce), e con lui a custodire la villa c’è anche Carlo (Stefano Rosci), un teppistello.\n" +
                    "Questo sarà l’inizio di un’avventura unica e poetica che Walter ricorderà per tutta la vita.");
            m5.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2024, 6, 8)));
            m5.setDurationMins(104);
            m5.setScore(3f);
            m5.setActors(new HashSet<>(Arrays.asList(
                    createActor("Claudio", "Santamaria", "classpath:static/images/sq1.jpg"),
                    createActor("Edoardo", "Pesce", "classpath:static/images/sq2.jpg"),
                    createActor("Virginia", "Raffaele", "classpath:static/images/sq3.jpg")
            )));
            m5.setGenres(new HashSet<>(Arrays.asList(getGenre("Drammatico"))));
            File file5 = ResourceUtils.getFile("classpath:static/images/squalo.jpg");
            m5.setImg(Files.readAllBytes(file5.toPath()));
            movies.add(m5);

            Movie m6 = new Movie();
            m6.setName("Elemental");
            m6.setDescription("Ember Lumen, adolescente di temperamento, è cresciuta a Elemental City, città multietnica dove gli abitanti sono fatti di fuoco e di acqua, di terra e di aria. La città, a maggioranza acquatici, è insofferente ai migranti, soprattutto quelli ardenti come Ember, che non perdono occasione per umiliare e 'raffreddare'. Completamente dedicata al suo vecchio padre a un passo dalla pensione, ha deciso di dargli il cambio nell'attività di famiglia, un negozio di prodotti 'etnici' frequentato da clienti esigenti che non mancano di indispettirla. Durante una crisi di collera, l'ennesima, che fa tremare muri e condotti, un'onda anomala recapita al suo indirizzo Wade Ripple, water gay timido e sentimentale, pieno di buoni propositi e lacrime che versa copiosamente alla prima emozione. Incompatibili per le leggi di Elemental, i due elementi antagonisti stabiliranno una connessione che volgerà presto in amore.");
            m6.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2023, 6, 21)));
            m6.setDurationMins(93);
            m6.setScore(4f);
            m6.setActors(new HashSet<>(Arrays.asList(
                    createActor("Mamoudou", "Athie", "classpath:static/images/el1.jpg"),
                    createActor("Wendi", "McLendon-Covey", "classpath:static/images/el2.jpg"),
                    createActor("Catherine", "O'Hara", "classpath:static/images/el3.jpg"),
                    createActor("Leah", "Lewis", "classpath:static/images/el4.jpg")
            )));
            m6.setGenres(new HashSet<>(Arrays.asList(getGenre("Avventura"), getGenre("Animazione"), getGenre("Commedia"))));
            File file6 = ResourceUtils.getFile("classpath:static/images/elemental.jpg");
            m6.setImg(Files.readAllBytes(file6.toPath()));
            movies.add(m6);

            Movie m7 = new Movie();
            m7.setName("Fidanzata In Affitto");
            m7.setDescription("Siamo a Montauk, l'estate non è ancora iniziata e Maddie sta già piena di casini - le hanno sequestrato la macchina per delle multe non pagate, ma senza la macchina non può lavorare come autista Uber e arrotondare lo stipendio che prende come cameriera, e senza quei soldi in più non riesce a stare dietro alle tasse di proprietà sulla casa che ha ereditato dalla madre dopo la sua morte... Un annuncio su Craiglist sembra però fare al suo (disperato) caso: Laird e Allison, una coppia di ricchi newyorchesi con casa sulle spiagge di Montauk, cerca un \"aiuto\" per il figlio Percy, a loro modo di vedere troppo inesperto e impreparato ad affrontare la vita al college a cui accederà a settembre. Se Maddie riuscirà a farlo uscire dal suo guscio, svezzandolo a livello emotivo e sessuale, avrà in cambio una Buick Regal. E così tutti i suoi problemi si risolveranno. O forse no.");
            m7.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2024, 6, 21)));
            m7.setDurationMins(103);
            m7.setScore(3.5f);
            m7.setActors(new HashSet<>(Arrays.asList(
                    createActor("Jennifer", "Lawrence", "classpath:static/images/fi1.jpg"),
                    createActor("Matthew", "Broderick", "classpath:static/images/fi2.jpg"),
                    createActor("Laura", "Benanti", "classpath:static/images/fi3.jpg"),
                    createActor("Natalie", "Morales", "classpath:static/images/fi4.jpg")
            )));
            m7.setGenres(new HashSet<>(Arrays.asList(getGenre("Commedia"))));
            File file7 = ResourceUtils.getFile("classpath:static/images/fidanzata.jpg");
            m7.setImg(Files.readAllBytes(file7.toPath()));
            movies.add(m7);

            Movie m8 = new Movie();
            m8.setName("Indiana Jones e il Quadrante del Destino");
            m8.setDescription("Indiana Jones ha appeso il cappello e da qualche anno insegna archeologia all'università di New York. In attesa di un divorzio, che pesa come il lutto del figlio, il professor Jones si trascina al lavoro e dentro una vita ordinaria 'scossa' soltanto dagli schiamazzi dei vicini. Alla vigilia della conquista della Luna, riceve la visita di Helena Shaw, figlia di un vecchio amico 'ucciso' dalla sua ossessione: la macchina di Anticitera, congegno meccanico concepito da Archimede per trovare buchi temporali. La metà del quadrante riposa da anni negli archivi di Indiana Jones, dopo averlo sottratto ai nazisti sconfitti nel 1944. Tornata dal suo passato, Helena vorrebbe recuperare il curioso reperto per venderlo a un'asta in Marocco. A pedinarla, bramando lo stesso bene, è Jürgen Voller, ex nazista che ha partecipato al progetto Apollo 11 sotto falso nome. (Ab)battuto anni prima da Indiana Jones su un treno in corsa, vuole rintracciare le due parti del quadrante e viaggiare nel tempo cambiando il corso della Storia. Una sparatoria in piena 'parata lunare' avvia la ricerca del prezioso oggetto. Tra Marocco e Sicilia, nazisti e antichi romani, l'avventura è servita.");
            m8.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2023, 6, 28)));
            m8.setDurationMins(143);
            m8.setScore(4f);
            m8.setActors(new HashSet<>(Arrays.asList(
                    createActor("Harrison", "Ford", "classpath:static/images/in1.jpg"),
                    createActor("Phoebe", "Waller-Bridge", "classpath:static/images/in2.jpg"),
                    createActor("Madds", "Mikkelsen", "classpath:static/images/in3.jpg"),
                    createActor("Thomas", "Kretschmann", "classpath:static/images/in4.jpg")
            )));
            m8.setGenres(new HashSet<>(Arrays.asList(getGenre("Azione"), getGenre("Avventura"))));
            File file8 = ResourceUtils.getFile("classpath:static/images/indiana.jpg");
            m8.setImg(Files.readAllBytes(file8.toPath()));
            movies.add(m8);

            Movie m9 = new Movie();
            m9.setName("Insidious: La Porta Rossa");
            m9.setDescription("La famiglia Lambert non vive un momento brillante. Papà Josh è un po' confuso, si è separato dalla moglie e vive un rapporto conflittuale in particolare con il figlio Dalton, che è cresciuto e inizia il suo percorso al college studiando arte. Né lui, né Josh sanno di avere cancellato con l'ipnosi i ricordi del loro terribile viaggio nell'Altrove popolato di anime dannate e fameliche. L'insegnante di Josh lo stimola, come il resto della classe, a ripescare nella memoria e nel subconscio ricordi e impressioni per arricchire la visione artistica. Quasi in trance, Dalton dipinge così una misteriosa porta rossa. Nel frattempo anche Josh, attraverso una risonanza magnetica e altri esperimenti, cerca di riguadagnare consapevolezza. Così facendo, però, ciò che di mostruoso era rimasto nell'Altrove cerca di tornare. E chiaramente non è un bene per nessuno.");
            m9.setReleaseDate(DateUtils.convertToDate(LocalDate.of(2024, 7, 5)));
            m9.setDurationMins(107);
            m9.setScore(3f);
            m9.setActors(new HashSet<>(Arrays.asList(
                    createActor("Patrick", "Wilson", "classpath:static/images/ins1.jpg"),
                    createActor("Lin", "Shaye", "classpath:static/images/ins2.jpg"),
                    createActor("Rose", "Byrne", "classpath:static/images/ins3.jpg"),
                    createActor("Ty", "Simpkins", "classpath:static/images/ins4.jpg")
            )));
            m9.setGenres(new HashSet<>(Arrays.asList(getGenre("Horror"))));
            File file9 = ResourceUtils.getFile("classpath:static/images/insidious.jpg");
            m9.setImg(Files.readAllBytes(file9.toPath()));
            movies.add(m9);

            movieRepository.saveAll(movies);
        }

        List<Theater> theaters = theaterRepository.findAll();
        if (theaters.isEmpty()) {
            Theater t = new Theater();
            t.setName("Cinema Test");

            Address a = new Address();
            a.setCity("Firenze");
            a.setCountry("Italia");
            a.setStreet("Via Pinco Pallino");
            a.setNumber("5");
            a.setZipCode("00100");
            t.setAddress(a);

            Screen s = new Screen();
            s.setName("Sala 1");
            s.setEmitterSerial("AB12");
            s.setTheater(t);
            List<Seat> seats = new ArrayList<>();
            for(char c = 'A'; c <= 'J'; ++c) {
                for (int i = 0; i < 21; i++) {
                    Seat seat = new Seat();
                    seat.setRow(String.valueOf(c));
                    seat.setCol(String.valueOf(i + 1));
                    seat.setScreen(s);
                    seats.add(seat);
                }
            }
            s.setSeats(seats);
            File file = ResourceUtils.getFile("classpath:static/json/screen_test.json");
            Map<String,Object> seatChart = new ObjectMapper().readValue(file, HashMap.class);
            s.setSeatChart(seatChart);
            t.setScreens(Collections.singletonList(s));

            TicketType t1 = new TicketType();
            t1.setName("Adulti");
            t1.setAvailableOnline(true);
            t1.setPrice(9d);
            t1.setProjectionType(ProjectionType.is2D);
            t1.setDays(Arrays.asList(Day.values()));
            t1.setTheater(t);
            TicketType t2 = new TicketType();
            t2.setName("Bambini");
            t2.setAvailableOnline(true);
            t2.setPrice(7d);
            t2.setProjectionType(ProjectionType.is2D);
            t2.setDays(Arrays.asList(Day.values()));
            t2.setTheater(t);
            t.setTicketTypes(Arrays.asList(t1, t2));

            theaterRepository.save(t);

            Screen screen = screenRepository.findAll().get(0);

            Movie movie = movieRepository.findByName("Spider-man: Across the Spiderverse").get(0);

            Show show = new Show();
            show.setDate(DateUtils.convertToDate(LocalDate.of(2023, 12, 12)));
            show.setProjectionType(ProjectionType.is2D);
            show.setLanguage("Italiano");
            show.setStartTime("16:30");
            show.setMovie(movie);
            show.setScreen(screen);

            showRepository.save(show);

            Show show2 = new Show();
            show2.setDate(DateUtils.convertToDate(LocalDate.of(2023, 12, 12)));
            show2.setProjectionType(ProjectionType.is2D);
            show2.setLanguage("Italiano");
            show2.setStartTime("19:30");
            show2.setMovie(movie);
            show2.setScreen(screen);

            showRepository.save(show2);

            Movie movie2 = movieRepository.findByName("Elemental").get(0);

            Show show3 = new Show();
            show3.setDate(DateUtils.convertToDate(LocalDate.of(2023, 12, 13)));
            show3.setProjectionType(ProjectionType.is2D);
            show3.setLanguage("Italiano");
            show3.setStartTime("18:30");
            show3.setMovie(movie2);
            show3.setScreen(screen);

            showRepository.save(show3);

            Show show4 = new Show();
            show4.setDate(DateUtils.convertToDate(LocalDate.of(2023, 12, 12)));
            show4.setProjectionType(ProjectionType.is2D);
            show4.setLanguage("Italiano");
            show4.setStartTime("21:30");
            show4.setMovie(movie2);
            show4.setScreen(screen);

            showRepository.save(show4);

            Movie movie3 = movieRepository.findByName("Indiana Jones e il Quadrante del Destino").get(0);

            Show show5 = new Show();
            show5.setDate(DateUtils.convertToDate(LocalDate.of(2023, 12, 13)));
            show5.setProjectionType(ProjectionType.is2D);
            show5.setLanguage("Italiano");
            show5.setStartTime("17:30");
            show5.setMovie(movie3);
            show5.setScreen(screen);

            showRepository.save(show5);
        }
    }

    private MovieGenre getGenre(String genre) {
        List<MovieGenre> genres = movieGenreRepository.getByName(genre);
        if (genres.isEmpty()) {
            log.error("Genre Not Found: {}", genre);
        }
        return genres.get(0);
    }

    private MovieGenre createGenre(String name) {
        MovieGenre m = new MovieGenre();
        m.setName(name);
        return m;
    }

    private Actor createActor(String name, String surname, String path) throws IOException {
        Actor a = new Actor();
        a.setName(name);
        a.setSurname(surname);
        File file = ResourceUtils.getFile(path);
        a.setImg(Files.readAllBytes(file.toPath()));
        a = actorRepository.save(a);
        return a;
    }
}
