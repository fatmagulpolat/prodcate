package com.prodcate.prodcate.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
public class SpringMediator implements Mediator
{
    private final AbstractApplicationContext context;

    @Autowired
    public SpringMediator(final AbstractApplicationContext context) {
        this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T send(Request<T> request) {
        if (request == null) {
            throw new NullPointerException("The given request object cannot be null");
        }

        final Class requestType = request.getClass();
        final Class<T> responseType = (Class<T>) ((ParameterizedType) requestType.getGenericInterfaces()[0]).getActualTypeArguments()[0];
        final String[] beanNames = context.getBeanNamesForType(
                ResolvableType.forClassWithGenerics(
                        RequestHandler.class,
                        requestType,
                        responseType));

        if (beanNames.length == 0) {
            throw new IllegalStateException("No handlers seemed to be registered to handle the given request. Make sure the handler is defined and marked as a spring component");
        }

        if (beanNames.length > 1) {
            throw new IllegalStateException("More than one handlers found. Only one handler per request is allowed.");
        }

        final RequestHandler<Request<T>, T> requestHandler = (RequestHandler<Request<T>, T>) context.getBean(beanNames[0]);
        return requestHandler.handle(request);
    }
}
