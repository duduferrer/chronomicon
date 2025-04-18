package bh.app.chronomicon.model.enums;

public enum Rank {
    SUBOFICIAL("SO",14),
    PRIMEIRO_SGT("1S",13),
    SEGUNDO_SGT("2S", 12),
    TERCEIRO_SGT("3S", 11),
    SEGUNDO_TENENTE("2T", 15),
    PRIMEIRO_TENENTE("1T", 16),
    CAPITAO("CP", 17),
    MAJOR("MJ", 18),
    CORONEL("CL", 19);

    private final String code;
    private final int level;

    Rank(String code, Integer level){
        this.code = code;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String getCode() {
        return code;
    }
}
