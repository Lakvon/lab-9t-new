package ua.opnu;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Task {
    public static void main(String[] args) {
        Task task = new Task();
        List<String> words = new ArrayList<>(List.of("four", "score", "and", "seven", "years", "ago"));
        task.removeShorterStrings(words);
        System.out.println("Довші слова з кожної пари: " + words);

        Queue<Integer> queue = new ArrayDeque<>(List.of(3, 8, 17, 9, 17, 8, 3));
        System.out.println("Паліндром: " + task.isPalindrome(queue));
        System.out.println("Черга після перевірки: " + queue);

        System.out.println("Довжина найдовшого слова: " + task.maxLength(Set.of("one", "two", "three")));
        System.out.println("Найрідше значення: " + task.rarest(Map.of("Олена", 20, "Іван", 22, "Юлія", 20)));
    }

    public void removeShorterStrings(List<String> list) {
        for (int i = 0; i + 1 < list.size(); i++) {
            String first = list.get(i);
            String second = list.get(i + 1);
            list.remove(first.length() <= second.length() ? i : i + 1);
        }
    }

    public void stutter(List<String> list) {
        ListIterator<String> words = list.listIterator();
        while (words.hasNext()) {
            String word = words.next();
            words.add(word);
        }
    }

    public void switchPairs(List<String> list) {
        for (int i = 0; i + 1 < list.size(); i += 2) {
            Collections.swap(list, i, i + 1);
        }
    }

    public void removeDuplicates(List<String> list) {
        Iterator<String> words = list.iterator();
        if (!words.hasNext()) {
            return;
        }
        String previous = words.next();
        while (words.hasNext()) {
            String current = words.next();
            if (Objects.equals(previous, current)) {
                words.remove();
            } else {
                previous = current;
            }
        }
    }

    public void markLength4(List<String> list) {
        ListIterator<String> words = list.listIterator(list.size());
        while (words.hasPrevious()) {
            if (words.previous().length() == 4) {
                words.add("****");
                words.previous(); // Переступаємо вставлену позначку.
            }
        }
    }

    public boolean isPalindrome(Queue<Integer> queue) {
        ArrayDeque<Integer> reversed = new ArrayDeque<>();
        for (Integer number : queue) {
            reversed.push(number);
        }
        for (Integer number : queue) {
            if (!number.equals(reversed.pop())) {
                return false;
            }
        }
        return true;
    }

    public void reorder(Queue<Integer> queue) {
        ArrayDeque<Integer> negatives = new ArrayDeque<>();
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int number = queue.remove();
            if (number < 0) {
                negatives.push(number);
            } else {
                queue.add(number);
            }
        }
        int nonNegativeCount = queue.size();
        while (!negatives.isEmpty()) {
            queue.add(negatives.pop());
        }
        // Переносимо невід’ємні числа за впорядковані від’ємні.
        for (int i = 0; i < nonNegativeCount; i++) {
            queue.add(queue.remove());
        }
    }

    public void rearrange(Queue<Integer> queue) {
        ArrayDeque<Integer> odds = new ArrayDeque<>();
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int number = queue.remove();
            if (number % 2 == 0) {
                queue.add(number);
            } else {
                odds.push(number);
            }
        }
        // Читаємо стек від дна, щоб зберегти початковий порядок непарних.
        odds.descendingIterator().forEachRemaining(queue::add);
    }

    public int maxLength(Set<String> set) {
        int longest = 0;
        for (String word : set) {
            longest = Math.max(longest, word.length());
        }
        return longest;
    }

    public void removeEvenLength(Set<String> set) {
        set.removeIf(word -> word.length() % 2 == 0);
    }

    public int numInCommon(List<Integer> list1, List<Integer> list2) {
        Set<Integer> remaining = new HashSet<>(list1);
        int common = 0;
        for (Integer number : list2) {
            if (remaining.remove(number)) {
                common++;
            }
        }
        return common;
    }

    public boolean isUnique(Map<String, String> map) {
        return new HashSet<>(map.values()).size() == map.size();
    }

    public <V> Map<String, V> intersect(Map<String, V> map1, Map<String, V> map2) {
        Map<String, V> common = new HashMap<>();
        for (Map.Entry<String, V> entry : map1.entrySet()) {
            if (map2.entrySet().contains(entry)) {
                common.put(entry.getKey(), entry.getValue());
            }
        }
        return common;
    }

    public Map<String, Integer> reverse(Map<Integer, String> map) {
        Map<String, Integer> reversed = new HashMap<>();
        map.forEach((key, value) -> reversed.put(value, key));
        return reversed;
    }

    public int rarest(Map<String, Integer> map) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Порожня мапа не має найрідшого значення");
        }
        Map<Integer, Integer> counts = new HashMap<>();
        for (int number : map.values()) {
            counts.merge(number, 1, Integer::sum);
        }
        int rarestValue = Integer.MAX_VALUE;
        int lowestCount = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int number = entry.getKey();
            int count = entry.getValue();
            if (count < lowestCount || (count == lowestCount && number < rarestValue)) {
                lowestCount = count;
                rarestValue = number;
            }
        }
        return rarestValue;
    }

    public int maxOccurrences(List<Integer> list) {
        Map<Integer, Integer> counts = new HashMap<>();
        int maximum = 0;
        for (int number : list) {
            int count = counts.merge(number, 1, Integer::sum);
            maximum = Math.max(maximum, count);
        }
        return maximum;
    }
}
