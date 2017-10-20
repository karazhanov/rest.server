package ua.lg.karazhanov.configuration.rest;

import lombok.extern.slf4j.Slf4j;
import ua.lg.karazhanov.configuration.router.exceptions.WrongRestPathException;
import ua.lg.karazhanov.configuration.router.validators.RoutePathValidator;

import javax.annotation.PostConstruct;

@Slf4j
public abstract class RestController {
    private final String basePath;

    public RestController(String basePath) {
        this.basePath = basePath;
        try {
            RoutePathValidator.validatePath(basePath, this.getClass());
        } catch (WrongRestPathException e) {
            log.error("WrongRestPathException " + e.getErrorType() + " in \"" + basePath +"\"");
        }
    }

    public String getBasePath() {
        return basePath;
    }
}
