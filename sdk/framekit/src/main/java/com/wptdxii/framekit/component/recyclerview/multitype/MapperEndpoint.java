package com.wptdxii.framekit.component.recyclerview.multitype;

import com.wptdxii.framekit.component.recyclerview.multitype.ClassProvider;

public interface MapperEndpoint<T> {

    void mapper(ClassProvider<T> classProvider);
}
