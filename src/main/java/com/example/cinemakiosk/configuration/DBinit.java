package com.example.cinemakiosk.configuration;

import com.example.cinemakiosk.model.Actor;
import com.example.cinemakiosk.model.Movie;
import com.example.cinemakiosk.model.MovieGenre;
import com.example.cinemakiosk.repository.MovieGenreRepository;
import com.example.cinemakiosk.repository.MovieRepository;
import com.example.cinemakiosk.utils.DateUtils;
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

    @PostConstruct
    public void init() throws IOException {

//        movieRepository.deleteAll();
//        movieGenreRepository.deleteAll();

        List<MovieGenre> genres = movieGenreRepository.findAll();
        if (genres.isEmpty()) {
            MovieGenre g1 = new MovieGenre();
            g1.setName("Avventura");
            genres.add(g1);

            MovieGenre g2 = new MovieGenre();
            g2.setName("Azione");
            genres.add(g2);

            MovieGenre g3 = new MovieGenre();
            g3.setName("Animazione");
            genres.add(g3);

            MovieGenre g4 = new MovieGenre();
            g4.setName("Musical");
            genres.add(g4);

            MovieGenre g5 = new MovieGenre();
            g5.setName("Fantascienza");
            genres.add(g5);

            MovieGenre g6 = new MovieGenre();
            g6.setName("Fantasy");
            genres.add(g6);

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
                    createActor("Oscar", "Isaac", "classpath:static/images/sp4.jpg")
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

            movieRepository.saveAll(movies);
        }
    }

    private MovieGenre getGenre(String genre) {
        List<MovieGenre> genres = movieGenreRepository.getByName(genre);
        if (genres.isEmpty()) {
            log.error("Genre Not Found: {}", genre);
        }
        return genres.get(0);
    }

    private Actor createActor(String name, String surname, String path) throws IOException {
        Actor a = new Actor();
        a.setName(name);
        a.setSurname(surname);
        File file = ResourceUtils.getFile(path);
        a.setImg(Files.readAllBytes(file.toPath()));
        return a;
    }
}
