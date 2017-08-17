package br.ufrj;

import java.lang.annotation.*;

@Documented
@Target( ElementType.FIELD)
@Retention( RetentionPolicy.RUNTIME )
public @interface Obrigatorio {
		boolean value() default false;

}
