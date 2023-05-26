package dev.abhisek.userservice.services.external;

import dev.abhisek.userservice.entity.Snippet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SNIPPET-SERVICE")
public interface SnippetService {
    @GetMapping("/snippet/{snippetId}")
    Snippet getSnippetBySnippetId(@PathVariable String snippetId);
}
