import java.util.Date;

/*
  Класс сущности ITEM файла AS_ADM_HIERARCHY.XML
  Класс содержит поля, отражающие атрибуты
  тега ITEM файла AS_ADM_HIERARCHY.XML

  Версия: 1.0
  Автор: Черномуров Семён
  Последнее изменение: 19.07.2023
*/
public class HierarchyItem implements Comparable<HierarchyItem>{

    private final long objectId; // Идентификатор адреса
    private final long parentObjectId; // Идентификатор родительского адреса
    private final Date startDate; // Начало действия связи
    private final Date endDate; // Окончание действия связи
    private final boolean isActive; // Признак актуальности связи

    //Конструктор
    public HierarchyItem(Builder builder) {
        this.objectId = builder.objectId;
        this.parentObjectId = builder.parentObjectId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.isActive = builder.isActive;
    }

    //Метод сравнения объектов класса по идентификатору
    //родительского адреса
    @Override
    public int compareTo(HierarchyItem hierarchyItem) {
        return Long.compare(this.parentObjectId, hierarchyItem.parentObjectId);
    }

    //* Методы получения полей класса
    public long getObjectId() {
        return objectId;
    }

    public long getParentObjectId() {
        return parentObjectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return isActive;
    }
    //* Методы получения полей класса

    // Шаблон "Строитель" для класса
    public static class Builder {

        private long objectId;
        private long parentObjectId;
        private Date startDate;
        private Date endDate;
        private boolean isActive;

        public HierarchyItem build() {
            return new HierarchyItem(this);
        }

        public Builder objectId(long objectId) {
            this.objectId = objectId;
            return this;
        }

        public Builder parentObjectId(long parentObjectId) {
            this.parentObjectId = parentObjectId;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }
    }
}
