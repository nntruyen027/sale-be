package entier.person.sale.constant;

public enum QuyenCons {

    // =====NGƯƠI DÙNG  =====
    USER_READ("user:read"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    // ====== QUYEN ========
    QUYEN_READ("quyen:read"),
    QUYEN_CREATE("quyen:create"),
    QUYEN_UPDATE("quyen:update"),
    QUYEN_DELETE("quyen:delete"),

    // ==== VAI TRÒ ======
    ROLE_READ("role:read"),
    ROLE_CREATE("role:create"),
    ROLE_UPDATE("role:update"),
    ROLE_DELETE("role:delete"),

    // ====== TỈNH ========
    TINH_READ("tinh:read"),
    TINH_CREATE("tinh:create"),
    TINH_UPDATE("tinh:update"),
    TINH_DELETE("tinh:delete"),

    // ====== XA ==========
    XA_READ("xa:read"),
    XA_CREATE("xa:create"),
    XA_UPDATE("xa:update"),
    XA_DELETE("xa:delete"),


    // ====== Tham số =======
    PARAM_READ("param:read"),
    PARAM_CREATE("param:create"),
    PARAM_UPDATE("param:update"),
    PARAM_DELETE("param:delete"),


    // ===== PRODUCT =====
    PRODUCT_READ("product:read"),
    PRODUCT_CREATE("product:create"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),


    // ===== ORDER =====
    ORDER_READ("order:read"),
    ORDER_CREATE("order:create"),
    ORDER_UPDATE("order:update"),
    ORDER_DELETE("order:delete"),


    // ---- PRODUCT_TYPE ======
    PTYPE_READ("ptype:read"),
    PTYPE_CREATE("ptype:create"),
    PTYPE_UPDATE("ptype:update"),
    PTYPE_DELETE("ptype:delete");


    private final String permission;

    QuyenCons(String permission) {
        this.permission = permission;
    }

    public String value() {
        return permission;
    }
}
