package ua.lg.karazhanov.configuration.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import rx.Observable;
import ua.lg.karazhanov.configuration.rest.RestController;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

@Service
@Slf4j
public class ControllerPostBeanProcessor implements BeanPostProcessor, Ordered {
//    private String apiPath;
//    @Getter
//    private List<ControllerModel> controllerModels = new ArrayList<>();
//    @Getter
//    private List<ControllerModel> proxyMethods = new ArrayList<>();
//    @Getter
//    private long startedTime;
//
//    public ControllerPostBeanProcessor(@Value("${const.api.path.prefix}") String apiPath) {
//        this.apiPath = apiPath;
//    }
//    public void start() {
//        startedTime = System.currentTimeMillis();
//    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        boolean assignableFrom = RestController.class.isAssignableFrom(aClass);
        if (assignableFrom) {
            log.info(String.valueOf(aClass));
            RestController controller = (RestController) bean;
            Class<? extends RestController> controllerClass = controller.getClass();

//            String path;
//            if (Utils.getAnnotation(controller, Api.class)) {
//                path = apiPath + controller.path();
//            } else {
//                path = controller.path();
//            }
//            ControllerModel p = new ControllerModel(
//                    fromAnotationGet(controllerMethods, controller.isGetAuth()),
//                    fromAnotationPost(controllerMethods, controller.isPostAuth()),
//                    fromAnotationPut(controllerMethods, controller.isPutAuth()),
//                    fromAnotationDelete(controllerMethods, controller.isDeleteAuth()),
//                    path,
//                    isDeprecated(controllerClass),
//                    aClass.getCanonicalName());
//            controller.setControllerModel(p);
//            if (isProxy(controllerClass)) {
//                proxyMethods.add(p);
//            } else {
//                controllerModels.add(p);
//            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
