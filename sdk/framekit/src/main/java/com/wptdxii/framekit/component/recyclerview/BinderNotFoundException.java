package com.wptdxii.framekit.component.recyclerview;

public class BinderNotFoundException extends RuntimeException {

    public BinderNotFoundException(Class<?> clazz) {
        super(String.format("%s not register to type pool!", clazz.getSimpleName()));
    }
}
