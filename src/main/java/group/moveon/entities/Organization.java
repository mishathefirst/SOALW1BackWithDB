package group.moveon.entities;

public class Organization {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address officialAddress; //Поле не может быть null
}
