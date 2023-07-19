import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*
  Класс обеспечения XML-файлами
  Класс содержит пути до
  обрабатываемых XML-файлов и
  методы получения XML-потоков

  Версия: 1.0
  Автор: Черномуров Семён
  Последнее изменение: 19.07.2023
*/
public class XmlStreamProvider {

    private static final String ADDRESSES_PATH = "files/AS_ADDR_OBJ.XML"; // Путь до файла адресов
    private static final String HIERARCHY_PATH = "files/AS_ADM_HIERARCHY.XML"; // Путь до файла иерархий
    public enum FileTypes {ADDRESS, HIERARCHY} //Тип файла (Адреса, Иерархии)

    //Метод получения XML-потока в зависимости от выбранного файла
    public static XmlStream getStream(FileTypes fileType) {
        try {
            FileInputStream inputStream = null;

            //Выбираем тип файла
            switch (fileType) {
                case ADDRESS -> inputStream = new FileInputStream(ADDRESSES_PATH);
                case HIERARCHY -> inputStream = new FileInputStream(HIERARCHY_PATH);
            }

            //Возвращаем новый XML-поток
            return new XmlStream(inputStream);
        } catch (FileNotFoundException e) {
            ExceptionLogger.logException(e);
            return null;
        }
    }
}
