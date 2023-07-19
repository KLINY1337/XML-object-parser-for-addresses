import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
  Основной класс приложения
  Класс содержит основную логику
  программы и методы исполнения
  задач

  Версия: 1.0
  Автор: Черномуров Семён
  Последнее изменение: 19.07.2023
*/
public class Main {

    //Метод исполнения программы
    public static void main(String[] args) {

        //Запускаем задачу 1
        System.out.println("Task 1:");
        executeTask1();

        //Запускаем задачу 2
        System.out.println("\nTask 2:");
        executeTask2();

    }

    //Метод исполнения задачи 1
    private static void executeTask1() {
        Date inputDate = null;
        List<Long> inputIds = null;

        //Получаем данные с пользовательского ввода
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Write date in format yyyy-mm-dd:");
            inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());

            System.out.println("Write objects ids separated by space");
            inputIds = Arrays.stream(scanner.nextLine().split(" ")).map(Long::parseLong).toList();
        } catch (ParseException e) {
            ExceptionLogger.logException(e);
        }

        //На основании введенных данных получаем подходящие адреса
        List<Address> addressesActualByDateAndId = getSuitableAddresses(inputDate, inputIds);

        //Выводим их на экран
        printResultOfTask1(addressesActualByDateAndId);
    }

    //Метод получения адресов, подходящих по дате и идентификатору
    private static List<Address> getSuitableAddresses(Date inputDate, List<Long> inputIds) {

        //Создаем список адресов согласно списку идентификаторов, введенных пользователем, а также актуальным по дате
        List<Address> addresses = getAllAddresses();
        return addresses
                .stream()
                .filter(
                        address -> inputIds.contains(address.getObjectId()) &&
                                (inputDate.after(address.getStartDate()) && inputDate.before(address.getEndDate()))
                )
                .toList();
    }

    //Метод вывода результат выполнения задачи 1
    private static void printResultOfTask1(List<Address> suitableAddresses) {

        //Выводим адреса из списка в формате "ИДЕНТИФИКАТОР": "ТИП АДРЕСА" "ИМЯ АДРЕСА"
        for (Address address : suitableAddresses) {
            String stringToOutput = address.getObjectId() + ": " + address.getTypeName() + " " + address.getName();
            System.out.println(stringToOutput);
        }
    }

    //Метод исполнения задачи 2
    private static void executeTask2() {

        //Получаем список активных иерархий, которые не содержат объектов, не содержащихся в файле адресов
        List<HierarchyItem> suitableHierarchyItems = getActiveAndSuitableHierarchyItems();

        //Создаем и заполняем дерево адресов согласно полученной иерархии
        Tree addressTree = new Tree();
        addressTree.fill(suitableHierarchyItems);

        Tree.Node root = addressTree.getRoot();
        List<String> resultList = new ArrayList<>();

        //Заполняем список нужными строками
        fillResultList(root, getAllAddresses(), "", resultList);

        //Выводим их на экран
        printResultOfTask2(resultList);
    }

    //Метод получения активных объектов иерархии
    private static List<HierarchyItem> getActiveAndSuitableHierarchyItems() {

        //Получаем все адреса из файла адресов
        List<Address> addresses = getAllAddresses();

        //Получаем список активных и актуальных идентификаторов этих адресов
        List<Long> addressesIds = addresses
                .stream()
                .filter(address -> address.isActive() && address.isActual())
                .map(Address::getObjectId)
                .toList();

        //Получаем все объекты иерархий
        XmlStream hierarchyStream = XmlStreamProvider.getStream(XmlStreamProvider.FileTypes.HIERARCHY);
        List<HierarchyItem> hierarchyItems = Objects.requireNonNull(hierarchyStream).getAllHierarchyObjects();

        //Возвращаем активные иерархии, сортированные в порядке возрастания идентификатора родительского объекта,
        //за исключением иерархий, содержащих адреса, не содержащиеся в файле адресов
        return hierarchyItems
                .stream()
                .filter(HierarchyItem::isActive)
                .filter(item -> addressesIds.contains(item.getObjectId()))
                .sorted((HierarchyItem::compareTo))
                .toList();
    }

    //Метод вывода результат выполнения задачи 2
    private static void printResultOfTask2(List<String> resultList) {
        for (String s : resultList) {
            System.out.println(s);
        }
    }

    //Метод отбора адресов с типом адреса "проезд"
    private static void fillResultList(
            Tree.Node root,
            List<Address> addresses,
            String result,
            List<String> resultList
    ) {
        //Получаем объект адреса по идентификатору из узла дерева
        Address address = addresses.stream().filter(addr -> addr.getObjectId() == root.getData()).findAny().orElse(null);

        //Добавляем к строки записи полученный адрес
        if (address != null) {
            result += (address.getTypeName() + " " + address.getName()) + ", ";
        }
        //Если доходим до "листа" дерева, то значит, что стркоу невозможно увеличить, соответственно получили конечную
        //иерархию адресов
        if (root.getChildren().isEmpty()) {
            if (result.contains("проезд ")) {
                resultList.add(result.substring(0, result.length() - 2));
            }

        }
        //Рекурсивно вызываем этот же метод для всех дочерних узлов
        for (Tree.Node child : root.getChildren()) {
            fillResultList(child, addresses, result, resultList);
        }
    }

    //Метод получения всех адресов из XML-файла
    private static List<Address> getAllAddresses() {

        //Создаем XML-поток и получаем все адреса
        XmlStream addressesStream = XmlStreamProvider.getStream(XmlStreamProvider.FileTypes.ADDRESS);
        List<Address> addresses = Objects.requireNonNull(addressesStream).getAllAddresses();

        addressesStream.close();
        return addresses;
    }
}