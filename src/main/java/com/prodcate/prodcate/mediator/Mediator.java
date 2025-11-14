package com.prodcate.prodcate.mediator;

public interface Mediator {
    <T> T send(final Request<T> request);
}
