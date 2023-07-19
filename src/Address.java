import java.util.Date;

/*
  Класс сущности OBJECT файла AS_ADDR_OBJ.XML
  Класс содержит поля, отражающие атрибуты
  тега OBJECT файла AS_ADDR_OBJ.XML

  Версия: 1.0
  Автор: Черномуров Семён
  Последнее изменение: 19.07.2023
*/
public class Address {


    private final long objectId; // Идентификатор адреса
    private final String name; // Название адреса
    private final String typeName; // Тип адреса
    private final Date startDate; // Начало действия адреса
    private final Date endDate; // Окончание действия адреса
    private final boolean isActual; // Актуальность адреса
    private final boolean isActive; // Активность адреса

    //Конструктор
    public Address(Builder builder) {
        this.objectId = builder.objectId;
        this.name = builder.name;
        this.typeName = builder.typeName;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.isActual = builder.isActual;
        this.isActive = builder.isActive;
    }

    //* Методы получения полей класса
    public Date getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }

    public long getObjectId() {
        return objectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isActual() {
        return isActual;
    }
    //* Методы получения полей класса

    // Шаблон "Строитель" для класса
    public static class Builder {
        private long objectId;
        private String name;
        private String typeName;
        private Date startDate;
        private Date endDate;
        private boolean isActual;
        private boolean isActive;

        public Address build() {
            return new Address(this);
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder isActual(boolean isActual) {
            this.isActual = isActual;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder objectId(long objectId) {
            this.objectId = objectId;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder typeName(String typeName) {
            this.typeName = typeName;
            return this;
        }
    }
}
