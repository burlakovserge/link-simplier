package ru.burlakov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.burlakov.dto.ResponseDto;
import ru.burlakov.entity.Link;
import ru.burlakov.exception.ElementException;
import ru.burlakov.exception.Message;
import ru.burlakov.repository.LinkRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LinkService {
    private final Hashids hashids;
    private final LinkRepository linkRepository;
    private final CustomCache cache;
    private final CacheManager cacheManager;

    @Cacheable(cacheNames = "original_links")
    public Link getFromDb(String source) {
        Optional<Link> sourceOpt = linkRepository.findByOriginal(source);

        if (sourceOpt.isEmpty()) {
            Link savedLink = linkRepository.save(new Link(null, source, LocalDate.now()));
            String shortUrl = hashids.encode(savedLink.getId());
            savedLink.setShortLink(shortUrl);
            linkRepository.save(savedLink);
            return savedLink;
        }

        return sourceOpt.get();
    }

    @CachePut(cacheNames = "short_links")
    public String getShortLink(String source) {
        cache.putData(source);
        log.info(String.valueOf(cache));

        Optional<Cache.ValueWrapper> short_links = Optional.ofNullable(cacheManager.getCache("short_links").get(source));
        if (short_links.isEmpty()) {
            return linkRepository.findByShortLink(source).map(Link::getOriginal)
                    .orElseThrow(() -> new ElementException(Message.SHORT_LINK_NOT_FOUND));
        }

        return (String) short_links.get().get();
    }

    public ResponseDto getStat(String url) {
        Long shortUrlCount = linkRepository.getShortUrlCount(url);
        Optional<Link> linkOpt = linkRepository.findByShortLink(url);
        if (linkOpt.isPresent()) {
            Link link = linkOpt.get();
            return new ResponseDto(link.getShortLink(), link.getOriginal(), link.getCount(), shortUrlCount);
        } else throw new ElementException(Message.SHORT_LINK_NOT_FOUND);
    }

    public Page<Link> getPageableLink(Integer page, Integer count) {
        Pageable sortedByPriceDesc = PageRequest.of(page, count, Sort.by("count").descending());
        return linkRepository.findAll(sortedByPriceDesc);
    }

}
