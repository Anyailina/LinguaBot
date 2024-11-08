package org.annill.linguabot.feignClient;

import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "wordSuggestion")
public interface WordSuggestionFeignClient {
    @PostMapping
    List<WordSuggestionDto> getAnswer(@RequestBody WordSuggestionDto wordSuggestionDto);
}
