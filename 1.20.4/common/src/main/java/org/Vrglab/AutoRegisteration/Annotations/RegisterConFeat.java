package org.Vrglab.AutoRegisteration.Annotations;

import org.Vrglab.Modloader.enumTypes.ConFeatType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface RegisterConFeat {
    String Name();
    ConFeatType Type();
}
