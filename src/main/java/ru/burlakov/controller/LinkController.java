package ru.burlakov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.burlakov.dto.RequestDto;
import ru.burlakov.dto.ResponseDto;
import ru.burlakov.entity.Link;
import ru.burlakov.service.LinkService;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody RequestDto request) {
        Link fromDb = linkService.getFromDb(request.getOriginal());
        return ResponseEntity.ok("link: /l/" + fromDb.getShortLink());
    }

    @GetMapping("/l/{source}")
    public void redirect(@PathVariable String source) {
        linkService.getShortLink(source);
    }

    @GetMapping("/stats/{shortUrl}")
    public ResponseEntity<ResponseDto> getStats(@PathVariable String shortUrl) {
        return ResponseEntity.ok(linkService.getStat(shortUrl));
    }

    @GetMapping("/stat")
    public ResponseEntity<Page<Link>> getStat(@RequestParam Integer page, @RequestParam Integer count) {
        return ResponseEntity.ok(linkService.getPageableLink(page, count));
    }

}
