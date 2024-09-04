package org.Vrglab.Modloader.Types;

import org.Vrglab.AutoRegisteration.Objects.AutoRegisteryObject;

import java.lang.annotation.Annotation;

public interface IAutoLoadResolver<T extends AutoRegisteryObject, B extends Annotation> {

    Object accept(T registery, B annotation);
}
