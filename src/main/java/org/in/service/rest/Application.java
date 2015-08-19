package org.in.service.rest;

import java.util.Collections;
import java.util.Set;

public abstract class Application {
   private static final Set<Object> emptySet = Collections.emptySet();

   public abstract Set<Class<?>> getClasses();


   public Set<Object> getSingletons() {
      return emptySet;
   }

}
