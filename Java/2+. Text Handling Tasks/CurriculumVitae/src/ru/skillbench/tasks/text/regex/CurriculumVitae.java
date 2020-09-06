package ru.skillbench.tasks.text.regex;

import java.util.List;
import java.util.NoSuchElementException;


/**
 * ЦЕЛИ ЗАДАЧИ<ul>
 * <li>Изучить регулярные выражениям (включая группы) и классы пакета java.util.regex.</li>
 * <li>Попрактиковаться в объектно-ориентированном программировании и использовании String/StringBuilder.</li>
 * <li>Привыкать выбрасывать исключения во всех случаях, когда это требуется, но с минимальным количеством операторов throw.</li>
 * </ul>
 * <p/>
 * ЗАДАНИЕ<br/>
 * Реализовать класс, хранящий текст одного резюме (curriculum vitae) с возможностью изменения некоторых частей резюме.<br/>
 * Getter-методы класса возвращают фамилию/имя/отчество и номера телефонов из резюме; 
 * update-методы меняют фамилию и номер телефона непосредственно в тексте резюме.<br/>
 * Каждый метод (кроме {@link #setText(String)}) должен выбрасывать соответствующие исключения,
 *    если резюме не содержит корректной информации, требуемой данным методом.
 * <p/>
 * ДОПОЛНИТЕЛЬНО<br/>
 * Класс может также скрывать и снова показывать (hide/unhide) некоторые персональные данные в резюме.<br/>
 * Чтобы реализовать это, следует выбрать подходящий класс из Collections Framework.
 * <p/>
 * NOTES<ul>
 * <li>Если вы хотите проверить класс в вашем собственном тесте / методе main(), вы можете скопировать текст
 *  любого реального резюме на английском языке, вставить его в простой текстовый редактор типа Блокнота под Windows, 
 *  сохранить его в файл и загружать этот файл в свой тест.</li>
 * </ul>
 *  
 * @author Alexey Evdokimov
 */
public interface CurriculumVitae {
	/**
	 * Это регулярное выражения для использования в методе {@link #getPhones()}. Оно описывает несколько форматов 
	 * номеров телефонов - такие как американский, российский и украинский форматы (для больших городов); 
	 * оно НЕ соответствует британскому и французскому форматам.<br/>
	 * Выражение не включает код страны. Необязательными являются код региона (3 цифры в начале)
	 *   и доп. номер в конце (extension: одна или несколько цифр после строки "ext" или "ext.").<br/>
	 * Число цифр в номере, удовлетворяющем выражению, не меньше 7.<br/>
	 * Примеры номеров, удовлетворяющих выражению: "(916)125-4171", "495 926-93-47 ext.1846", "800 250 0890"
	 */
	public static final String PHONE_PATTERN = 
			"(\\(?([1-9][0-9]{2})\\)?[-. ]*)?([1-9][0-9]{2})[-. ]*(\\d{2})[-. ]*(\\d{2})(\\s*ext\\.?\\s*([0-9]+))?";
	
	/**
	 * Задает текст резюме.<br/>
	 * О реализации: текст НЕ должен анализировать в этом методе.
	 * @param text Текст резюме
	 */
	void setText(String text);
	/**
	 * Рекомендуется вызывать этот метод во всех остальных методах вашего класса.
	 * @return Текущий текст резюме (который мог измениться не только методом setText, но и методами update*).
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	String getText();

	/**
	 * Возвращает список телефонов в том же порядке, в котором они расположены в самом резюме.<br/>
	 * О реализации: используйте {@link #PHONE_PATTERN} для поиска телефонов;
	 * используйте группы этого регулярного выражения, чтобы извлечь код региона и extension из найденных номеров;
	 * если код региона или extension не присутствует в номере, объект {@link Phone} должен хранить отрицательное значение. 
	 * @see Phone
	 * @return Список, который не может быть <code>null</code>, но может быть пустым (если ни одного телефона не найдено).
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	List<Phone> getPhones();
	
	/**
	 * Возвращает полное имя, т.е. ПЕРВУЮ часть текста резюме, которая удовлетворяет такие критериям: 
	 * <ol>
	 * <li>полное имя содержит 2 или 3 слова, разделенных пробелом (' ');</li>
	 * <li>каждое слово содержит не меньше двух символов;</li>
	 * <li>первый символ слова - это заглавная латинская буква (буква английского алфавита в upper case);</li>
	 * <li>последний символ слова - это либо точка ('.'), либо строчная(lower case) латинская буква;</li>
	 * <li>не первые и не последние символы слова - это только строчные (lower case) латинские буквы.</li>
	 * </ol>
	 * @return Полное имя (в точности равно значению в тексте резюме)
	 * @throws NoSuchElementException Если резюме не содержит полного имени, которое удовлетворяет критериям.
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	String getFullName();
	/**
	 * Возвращает имя (первое слово из полного имени {@link #getFullName()}).
	 * @throws NoSuchElementException Если резюме не содержит полного имени.
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	String getFirstName();
	/**
	 * Возвращает отчество (второе слово из полного имени {@link #getFullName()})
	 *  или <code>null</null>, если полное имя состоит только из двух слов.
	 * @throws NoSuchElementException Если резюме не содержит полного имени.
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	String getMiddleName();
	/**
	 * Возвращает фамилию (последнее слово из полного имени {@link #getFullName()}).
	 * @throws NoSuchElementException Если резюме не содержит полного имени.
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	String getLastName();
	
	/**
	 * Заменяет фамилию на <code>newLastName</code> в тексте резюме.
	 * @see #getLastName()
	 * @param newLastName Не может быть null
	 * @throws NoSuchElementException Если резюме не содержит полного имени.
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	void updateLastName(String newLastName);

	/**
	 * Заменяет <code>oldPhone.getNumber()</code> на <code>newPhone.getNumber()</code> в тексте резюме.<br/>
	 * О реализации: использование regex здесь ведет к большему объему кода, чем вызов не связанных с
	 *  регулярными выражениями методов {@link String} (или метода {@link String} и метода {@link StringBuilder}).
	 * @param oldPhone Не может быть null
	 * @param newPhone Не может быть null
	 * @throws IllegalArgumentException Если резюме не содержит текста, равного <code>oldPhone.getNumber()</code>.
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	void updatePhone(Phone oldPhone, Phone newPhone);
	
	/**
	 * Ищет строку <code>piece</code> в тексте резюме и скрывает ее, то есть заменяет каждый символ из
	 *  <code>piece</code> на символ 'X', за исключениеми следующих разделительных символов: ' ', '.' и '@'.
	 * Число символов 'X' равно числу замененных символов.<br/>
	 * Например: "John A. Smith" заменяется на "XXXX X. XXXXX", "john@hp.com" - на "XXXX@XX.XXX".<br/>
	 * Эта замена может быть отменена путем вызова {@link #unhideAll()}.
	 * @param piece Не может быть null
	 * @throws IllegalArgumentException Если резюме не содержит текста, равного <code>piece</code>.
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	void hide(String piece);
	/**
	 * Ищет строку <code>phone</code> в тексте резюме и скрывает ее, то есть, заменяет все ЦИФРЫ из 
	 *  <code>phone</code> на символ 'X'.<br/>
	 * Например: "(123)456 7890" заменяется на "(XXX)XXX XXXX".<br/>
	 * Эта замена может быть отменена путем вызова {@link #unhideAll()}.
	 * @param phone Не может быть null
	 * @throws IllegalArgumentException Если резюме не содержит текста, равного <code>phone</code>.
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	void hidePhone(String phone);
	/**
	 * Отменяет все изменения, сделанные методами {@link #hide(String)} и {@link #hidePhone(String)},
	 *  т.е. заменяет куски текста с символами 'X' в текущем тексте резюме (скрытые куски, вставленные ранее)
	 *  на соответствующие куски из исходного текста резюме.<br/>
	 * Примечание: в резюме не может быть двух (или более) одинаковых скрытых кусков (одинаковых куско с 'X').<br/>
	 * О реализации: исходные и скрытые куски следует хранить в некой коллекции.
	 *  Кроме того, эта коллекция должна очищаться при вызове {@link #setText(String)}.
	 * @return Число кусков, замененных в тексте резюме при выполнении метода
	 * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
	 */
	int unhideAll();
	
	/**
	 * Этот класс хранит информацию о номере телефона.<br/>
	 * В дополнении к полному номеру (String) он может хранить два необязательных поля, ивлеченных из полного 
	 *  номера и преобразованных в тип int: код региона и extension (доп. номер).
	 * @see CurriculumVitae#PHONE_PATTERN
	 */
	public static class Phone{
		private String number;
		private int areaCode;
		private int extension;
		public Phone(String number) {
			this.number = number;
			this.areaCode = -1;
			this.extension = -1;
		}
		public Phone(String number, int areaCode, int extension) {
			this.number = number;
			this.areaCode = areaCode;
			this.extension = extension;
		}
		/**
		 * @return Полный номер в виде String
		 */
		public final String getNumber() {
			return number;
		}
		/**
		 * @return код региона (или отрицательное число, если код региона не задан)
		 */
		public final int getAreaCode() {
			return areaCode;
		}
		/**
		 * @return extension (или отрицательное число, если extension не задан)
		 */
		public final int getExtension() {
			return extension;
		}
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Phone)) 
				return false;
			Phone p = (Phone) obj;
			if(getNumber() == null)//null is considered to be equal to null
				return p.getNumber() == null;
			if(!getNumber().equals(p.getNumber()))
				return false;
			if(!equalsOptional(getAreaCode(), p.getAreaCode()))
				return false;
			if(!equalsOptional(getExtension(), p.getExtension()))
				return false;
			return true;
		}
		private boolean equalsOptional(int v1, int v2){
			if(v1 < 0) return v2 < 0;
			else return v1 == v2;
		}
		private void addMarker(StringBuilder sb, int value, boolean fromStart){
			String s = "";
			if(value < 0) {
				s = fromStart ? "{NO CODE}" : "{NO EXT}";
			} else {
				String sValue = Integer.toString(value); 
				int j = fromStart ? sb.indexOf(sValue) : sb.lastIndexOf(sValue);
				if(j < 0 || (fromStart && j > 1) || (!fromStart && j < sb.length()-sValue.length()) ){
					s = "{WRONG:"+sValue+"}";
				}
			}
			if(fromStart) sb.insert(0, s);
			else sb.append(s);
		}
		/**
		 * Строковое представление телефона. 
		 * Добавляет "{NO CODE}" перед {@link #number}, если значение {@link #areaCode} отрицательно, и
		 *  добавляет "{NO EXT}" после {@link #number}, если значение {@link #extension} отрицательно.
		 * Если areaCode / extension не соответствует самому номеру, строка "WRONG" добавляется
		 *  перед / после {@link #number}.
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(number);
			addMarker(sb, areaCode, true);
			addMarker(sb, extension, false);
			return sb.toString();
		}
	}

}
