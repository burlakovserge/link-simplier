package ru.burlakov.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.burlakov.entity.Link;
import ru.burlakov.repository.LinkRepository;
import ru.burlakov.service.CustomCache;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
@Profile("cron")
public class Scheduler {

    private final LinkRepository linkRepository;
    private final CustomCache cache;

    @Scheduled(fixedRate = 50000)
    public void updateLinksCount() {
        Set<String> keys = cache.getCache().keySet();
        List<Link> links = linkRepository.findAllByShortIn(keys);
        Set<Link> collect = links.stream()
                .peek(e -> e.setCount(e.getCount() + cache.getData(e.getShortLink())))
                .collect(Collectors.toSet());
        linkRepository.saveAll(collect);
        cache.clear();
    }

}
