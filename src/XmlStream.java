import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/*
  Класс работы с XML-файлами
  Класс содержит методы получения
  объектов из XML-тегов

  Версия: 1.0
  Автор: Черномуров Семён
  Последнее изменение: 19.07.2023
*/
public class XmlStream {

    private XMLStreamReader xmlStreamReader; // Поток чтения XML-событий
    private final InputStream inputStream; // Поток XML-файла

    //Конструктор
    public XmlStream(InputStream inputStream) {
        try {
            //Создаем XML-поток
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            this.xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        } catch (XMLStreamException e) {
            ExceptionLogger.logException(e);
        }
        this.inputStream = inputStream;
    }

    //Метод закрытия потоков
    public void close() {
        try {
            //Закрываем файловый и XML-потоки
            inputStream.close();
            xmlStreamReader.close();
        } catch (IOException | XMLStreamException e) {
            ExceptionLogger.logException(e);
        }
    }

    //Метод получения всех адресов (OBJECT)
    public List<Address> getAllAddresses() {

        //Создаем список адресов
        List<Address> addresses = new LinkedList<>();

        try {
            //Считываем данные с XML-потока
            while (xmlStreamReader.hasNext()) {

                int parseEvent = xmlStreamReader.next();

                //Когда встретили тег <OBJECT>
                if (parseEvent == XMLStreamConstants.START_ELEMENT) {

                    if (xmlStreamReader.getLocalName().equals("OBJECT")) {
                        //Получаем объект адреса из тега
                        Address address = parseXmlAddress();

                        //Добавляем объект в список адресов
                        addresses.add(address);
                    }
                }
            }
        } catch (XMLStreamException e) {
            ExceptionLogger.logException(e);
        }
        return addresses;
    }

    //Метод получения всех объектов иерархии (ITEM)
    public List<HierarchyItem> getAllHierarchyObjects() {

        //Создаем список объектов иерархии
        List<HierarchyItem> hierarchyItems = new LinkedList<>();

        try {
            //Считываем данные с XML-потока
            while (xmlStreamReader.hasNext()) {

                int parseEvent = xmlStreamReader.next();

                //Когда встретили тег <ITEM>
                if (parseEvent == XMLStreamConstants.START_ELEMENT) {

                    if (xmlStreamReader.getLocalName().equals("ITEM")) {

                        //Получаем объект иерархии из тега
                        HierarchyItem hierarchyItem = parseXmlHierarchyItem();

                        //Добавляем объект в список иерархий
                        hierarchyItems.add(hierarchyItem);
                    }
                }
            }
        } catch (XMLStreamException e) {
            ExceptionLogger.logException(e);
        }
        return hierarchyItems;
    }

    //Метод парсинга XML-тега в объект иерархии
    private HierarchyItem parseXmlHierarchyItem() {

        //Создаем объект иерархии
        HierarchyItem.Builder builder = new HierarchyItem.Builder();

        //Проходим по атрибутам тега и переносим их в созданный объект
        for (int attributeNumber = 0; attributeNumber < xmlStreamReader.getAttributeCount(); attributeNumber++) {

            Attribute attribute = new Attribute(xmlStreamReader, attributeNumber);
            try {
                switch (attribute.getName()) {
                    case "OBJECTID" -> builder.objectId(Long.parseLong(attribute.getValue()));
                    case "PARENTOBJID" -> builder.parentObjectId(Long.parseLong(attribute.getValue()));
                    case "STARTDATE" -> builder.startDate(new SimpleDateFormat("yyyy-MM-dd").parse(attribute.getValue()));
                    case "ENDDATE" -> builder.endDate(new SimpleDateFormat("yyyy-MM-dd").parse(attribute.getValue()));
                    case "ISACTIVE" -> builder.isActive(attribute.getValue().equals("1"));
                    default -> {}
                    //other attributes aren't needed
                }
            } catch (ParseException e) {
                ExceptionLogger.logException(e);
            }
        }
        return builder.build();
    }

    //Метод парсинга XML-тега в объект адреса
    private Address parseXmlAddress() {

        //Создаем объект адреса
        Address.Builder builder = new Address.Builder();

        //Проходим по атрибутам тега и переносим их в созданный объект
        for (int attributeNumber = 0; attributeNumber < xmlStreamReader.getAttributeCount(); attributeNumber++) {

            Attribute attribute = new Attribute(xmlStreamReader, attributeNumber);
            try {
                switch (attribute.getName()) {
                    case "OBJECTID" -> builder.objectId(Long.parseLong(attribute.getValue()));
                    case "NAME" -> builder.name(attribute.getValue());
                    case "TYPENAME" -> builder.typeName(attribute.getValue());
                    case "STARTDATE" -> builder.startDate(new SimpleDateFormat("yyyy-MM-dd").parse(attribute.getValue()));
                    case "ENDDATE" -> builder.endDate(new SimpleDateFormat("yyyy-MM-dd").parse(attribute.getValue()));
                    case "ISACTUAL" -> builder.isActual(attribute.getValue().equals("1"));
                    case "ISACTIVE" -> builder.isActive(attribute.getValue().equals("1"));
                    default -> {}
                    //other attributes aren't needed
                }
            } catch (ParseException e) {
                ExceptionLogger.logException(e);
            }
        }
        return builder.build();
    }
}
