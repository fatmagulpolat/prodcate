package com.prodcate.prodcate.mediator;

public interface RequestHandler <TRequest extends Request<TResponse>, TResponse>  {
    TResponse handle(final TRequest request);
}