package entier.person.sale.config;

import entier.person.sale.constant.QuyenCons;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("""
             hasRole('SUPER_ADMIN')
                or
            @permissionChecker.hasPermission(#root.methodAnnotation.permission)
        """)
public @interface HasPermission {
    QuyenCons permission();
}

