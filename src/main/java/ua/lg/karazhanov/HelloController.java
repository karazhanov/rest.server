package ua.lg.karazhanov;

import org.springframework.stereotype.Component;
import rx.Observable;
import ua.lg.karazhanov.configuration.RestController;
import ua.lg.karazhanov.configuration.annotations.GET;

@Component
public class HelloController extends RestController {

    @GET
    public Observable<?> hello() {
        return null;
    }
}
