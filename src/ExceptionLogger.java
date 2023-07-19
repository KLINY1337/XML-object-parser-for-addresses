/*
  Класс обработки исключений
  Простой класс "обработки"
  исключений, логирующий
  исключения в консоль

  Версия: 1.0
  Автор: Черномуров Семён
  Последнее изменение: 19.07.2023
*/
public class ExceptionLogger {

    //Метод логирования исключения
    public static void logException(Exception e) {
        System.err.println("Exception occurred: " + e.getMessage());
        e.printStackTrace();
    }
}
