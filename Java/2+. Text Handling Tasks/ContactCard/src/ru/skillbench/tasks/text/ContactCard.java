package ru.skillbench.tasks.text;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * ЦЕЛИ ЗАДАЧИ:<ul>
 * <li>Научиться разбирать (parse) потоки InputStreams с помощью java.util.Scanner.</li>
 * <li>Узнать о работе с датами в java.time (рекомендуется) и java.util.Calendar.</li>
 * <li>Попрактиковаться в выбрасывании исключений пакета java.util</li>
 * </ul>
 * <p/>
 * ЗАДАНИЕ<br/>
 * Создавать экземпляры своего класса визитной карточки (класса, реализующего данный интерфейс) 
 *  методами {@link #getInstance(Scanner)} и {@link #getInstance(String)}.<br/>
 * Большинство других методов должны просто возвращать считанные через {@link Scanner} значения, 
 *   однако хранить ли их в отдельных полях класса или иным способом - на усмотрение разработчика.<br/>
 * Расчет возраста по дате рождения может проводиться в методе {@link #getAge()} 
 *  - необязательно это делать сразу в методе getInstance().<br/>
 * У экземпляра класса {@link ContactCard}, созданного конструктором (а не методом getInstance()), 
 *   не будут вызываться никакие методы, кроме getInstance()<br/>
 * <p/>
 * ПРИМЕЧАНИЕ:<br/>
 * Предложенный в задании формат электронной визитки ({@link #getInstance(Scanner)}) - это упрощенная версия 
 * <a href="https://en.wikipedia.org/wiki/VCard">стандартного формата vCard</a>.<br/>
 * ДОПОЛНИТЕЛЬНОЕ ТРЕБОВАНИЕ: попробуйте написать парсинг так, чтобы метод getInstance() завершался без ошибки
 *   с любыми данными, удовлетворяющими формату vCard 3.0 или 4.0, за исключением требований к номеру телефона
 *   (в vCard могут быть не только указанные в задании поля, причем они могут идти в любом порядке).<br/>
 * 
 * @author Alexey Evdokimov
*/
public interface ContactCard{
	/**
	 * Основной метод парсинга: создает экземпляр карточки из источника данных (Scanner), 
	 * содержащего следующие текстовые данные о человеке (5 полей):<br/>
	 * 1) FN - Полное имя - обязательное поле<br/>
	 * 2) ORG - Организация - обязательное поле<br/>
	 * 3) GENDER - Пол - один символ: F или M. Это поле в данных может отсутствовать.<br/>
	 * 4) BDAY - Дата рождения - в следующем формате: "DD-MM-YYYY", где DD - 2 цифры, обозначающие день, 
	 *		MM - 2 цифры, обозначающие месяц, YYYY - 4 цифры, обозначающие год. 
	 *    Это поле в данных может отсутствовать.<br/>
	 * 5) TEL - Номер телефона - ровно 10 цифр, не включающие код страны. Полей TEL может быть 0 или несколько,
	 *    и разные поля TEL различаются значением обязательного атрибута TYPE, например: 
	 *    TEL;TYPE=HOME,VOICE:4991112233<br/>
	 * <p/>
	 * Каждое из этих полей в источнике данных расположено на отдельной строке; 
	 *  строки в стандарте vCard отделяются символом CRLF (\r\n).<br/>
	 * Имя поля отделяется от его значения двоеточием, например: GENDER:F<br/>
	 * Если нужно, можно предположить, что порядок полей будет именно такой, как выше.<br/> 
	 * Но первой строкой всегда идет BEGIN:VCARD, последней - END:VCARD.<br/>
	 * Пример содержимого Scanner:<br/>
	 * <code>
	 * BEGIN:VCARD
	 * FN:Forrest Gump
	 * ORG:Bubba Gump Shrimp Co.
	 * BDAY:06-06-1944
	 * TEL;TYPE=WORK,VOICE:4951234567
	 * TEL;TYPE=CELL,VOICE:9150123456
	 * END:VCARD
	 * </code>
	 * <p/>
	 * ПРИМЕЧАНИЕ: Такой метод в реальных приложениях был бы static, однако 
	 *  система проверки учебных задач проверяет только не-статические методы.
	 * 
	 * @param scanner Источник данных
	 * @return {@link ContactCard}, созданный из этих данных
	 * @throws InputMismatchException Возникает, если структура или значения данных не соответствуют формату, 
	 *   описанному выше; например, если после названия поля нет двоеточия или дата указана в ином формате 
	 *   или номер телефона содержит неверное число цифр. 
	 * @throws NoSuchElementException Возникает, если данные не содержат обязательных полей 
	 *   (FN, ORG, BEGIN:VCARD, END:VCARD)
	 */
	public ContactCard getInstance(Scanner scanner);
	/**
	 * Метод создает {@link Scanner} и вызывает {@link #getInstance(Scanner)}
	 * @param data Данные для разбора, имеющие формат, описанный в {@link #getInstance(Scanner)}
	 * @return {@link ContactCard}, созданный из этих данных
	 */
	public ContactCard getInstance(String data);
	 
	/**
	 * @return Полное имя - значение vCard-поля FN: например, "Forrest Gump"
	 */
	public String getFullName();
	
	/**
	 * @return Организация - значение vCard-поля ORG: например, "Bubba Gump Shrimp Co."
	 */	
	public String getOrganization();
	
	/**
	 * Если поле GENDER отсутствует в данных или равно "M", этот метод возвращает false
	 * @return true если этот человек женского пола (GENDER:F)
	 */	
	public boolean isWoman();
	
	/**
	 * ПРИМЕЧАНИЕ: в современных приложениях рекомендуется для работы с датой применять java.time.LocalDate,
	 *  однако такие классы как java.util.Calendar или java.util.Date необходимо знать.
	 * @return День рождения человека в виде {@link Calendar}
	 * @throws NoSuchElementException Если поле BDAY отсутствует в данных
	 */	
	public Calendar getBirthday();
	
	/**
	 * ПРИМЕЧАНИЕ: В реализации этого метода рекомендуется использовать {@link DateTimeFormatter}
	 * @return Возраст человека на данный момент в виде {@link Period}
	 * @throws NoSuchElementException Если поле BDAY отсутствует в данных
	 */	
	public Period getAge();
	
	/**
	 * @return Возраст человека в годах: например, 74
	 * @throws NoSuchElementException Если поле BDAY отсутствует в данных
	 */	
	public int getAgeYears();
	
	/**
	 * Возвращает номер телефона в зависимости от типа.
	 * @param type Тип телефона, который содержится в данных между строкой "TEL;TYPE=" и двоеточием   
	 * @return Номер телефона - значение vCard-поля TEL, приведенное к следующему виду: "(123) 456-7890"
	 * @throws NoSuchElementException если в данных нет телефона указанного типа
	 */	
	public String getPhone(String type);
	
}
