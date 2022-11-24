package ru.burlakov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.burlakov.entity.Link;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LinkRepository extends PagingAndSortingRepository<Link, Long> {
    Optional<Link> findByShortLink(String link);

    Optional<Link> findByOriginal(String link);

    @Query(value = "Select l from Link l where l.shortLink in :set")
    List<Link> findAllByShortIn(@Param("set") Set<String> set);

    @Query(value = "select number from (select row_number() over (order by count desc) as number, l.* from link l) " +
            "as output where output.short =?1", nativeQuery = true)
    Long getShortUrlCount(String s);

}
