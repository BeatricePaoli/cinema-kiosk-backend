package com.example.cinemakiosk.repository;

import com.example.cinemakiosk.dto.MovieFilterDto;
import com.example.cinemakiosk.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    List<Movie> findByName(String name);

    @Query("FROM Movie m LEFT JOIN FETCH m.actors LEFT JOIN FETCH m.genres WHERE m.id = :id")
    Optional<Movie> findByIdWithFetch(Long id);

    @Query("SELECT m.img FROM Movie m WHERE m.id = :id")
    byte[] findImgByMovieId(Long id);

    static Specification<Movie> search(MovieFilterDto dto, boolean searchCurrent) {
        return new Specification<>() {
            public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();

                if (searchCurrent) {
                    Root<Show> show = query.from(Show.class);
                    Join<Show, Movie> movieShow = show.join(Show_.movie);
                    Join<Show, Screen> screen = show.join(Show_.screen);
                    Join<Screen, Theater> theater = screen.join(Screen_.theater);
                    Join<Theater, Address> address = theater.join(Theater_.address);

                    predicates.add(builder.equal(root.get(Movie_.id), movieShow.get(Movie_.id)));
                    predicates.add(builder.greaterThanOrEqualTo(show.get(Show_.date), new Date()));
                    predicates.add(builder.or(
                            builder.equal(theater.get(Theater_.deleted), false),
                            builder.isNull(theater.get(Theater_.deleted)))
                    );

                    if (dto.getCity() != null) {
                        predicates.add(builder.like(builder.lower(address.get(Address_.city)), "%" + dto.getCity().toLowerCase() + "%"));
                    }
                    if (dto.getTheaterId() != null) {
                        predicates.add(builder.equal(theater.get(Theater_.id), dto.getTheaterId()));
                    }

                    query.distinct(true);
                } else {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(Movie_.releaseDate), new Date()));
                }

                if (dto.getMovie() != null) {
                    predicates.add(builder.like(builder.lower(root.get(Movie_.name)), "%" + dto.getMovie().toLowerCase() + "%"));
                }

                query.orderBy(builder.asc(root.get(Movie_.releaseDate)));

                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
