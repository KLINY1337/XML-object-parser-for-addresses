import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
  Класс дерева адресов
  Класс содержит необходимые
  методы для работы с деревьями,
  а также вложенный класс узла
  дерева

  Версия: 1.0
  Автор: Черномуров Семён
  Последнее изменение: 19.07.2023
*/
public class Tree {
    private final List<Tree.Node> treeNodes; // Список узлов дерева

    //Конструктор
    public Tree() {
        this.treeNodes = new LinkedList<>();
    }

    //Класс узла дерева
    public static class Node {
        private Long data; // Данные узла
        private Node parent; // Родительский узел
        private final List<Node> children = new LinkedList<>(); // Список дочерних узлов

        //* Методы получения полей класса-узла
        public Long getData() {
            return data;
        }

        public List<Node> getChildren() {
            return children;
        }
        //* Методы получения полей класса-узла
    }

    //Метод получения корневого узла дерева
    Node getRoot() {
        return treeNodes.get(0);
    }

    //Метод заполнения дерева узлами, полученными из объектов иерархии
    public void fill(List<HierarchyItem> suitableHierarchyItems) {

        //Создаем список узлов дерева с не всеми заполненными родителями
        for (HierarchyItem item : suitableHierarchyItems) {
            Tree.Node node = treeNodes.stream().filter(n -> item.getObjectId() == n.data).findAny().orElse(new Tree.Node());
            node.data = item.getObjectId();
            node.parent = treeNodes.stream().filter(n -> n.data == item.getParentObjectId()).findFirst().orElse(null);
            treeNodes.add(node);
        }

        //Дозаполняем всех родителей у узлов
        for (HierarchyItem item : suitableHierarchyItems) {
            Tree.Node node = treeNodes.stream().filter(n -> item.getObjectId() == n.data).findAny().orElse(new Tree.Node());
            node.parent = treeNodes.stream().filter(n -> n.data == item.getParentObjectId()).findFirst().orElse(null);
        }

        //Заполняем списки дочерних узлов
        for (int i = 0; i < treeNodes.size(); i++) {
            Tree.Node currentNode = treeNodes.get(i);
            for (int j = 0; j < treeNodes.size(); j++) {
                if (treeNodes.get(j).parent == currentNode) {
                    currentNode.children.add(treeNodes.get(j));
                }
            }
        }
    }
}