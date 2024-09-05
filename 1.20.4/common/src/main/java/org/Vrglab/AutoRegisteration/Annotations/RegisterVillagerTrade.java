package org.Vrglab.AutoRegisteration.Annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface RegisterVillagerTrade {
    String Name();
}
