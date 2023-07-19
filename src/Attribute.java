import javax.xml.stream.XMLStreamReader;

/*
  Класс атрибута XML-тега
  Класс содержит поля, отражающие
  название атрибута и его значение

  Версия: 1.0
  Автор: Черномуров Семён
  Последнее изменение: 19.07.2023
*/
public class Attribute {

    private final String name; // Имя атрибута
    private final String value; // Значение атрибута

    //Конструктор
    public Attribute(XMLStreamReader xmlStreamReader, int attributeNumber) {
        this.name = xmlStreamReader.getAttributeLocalName(attributeNumber);
        this.value = xmlStreamReader.getAttributeValue(attributeNumber);
    }

    //* Методы получения полей класса
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
    //* Методы получения полей класса
}
