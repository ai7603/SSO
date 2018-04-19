package annotations;
@java.lang.annotation.Target(value={java.lang.annotation.ElementType.METHOD,java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
public @interface Access {
	public abstract java.lang.String[] value() default {};
}
