package tools;

public interface Acceptable {

    String DEV_ID_VALID = "^DEV\\d{3}$";
    /* ^: Bắt đầu chuỗi
    DEV: Phải bắt đầu đúng bằng 3 chữ cái "DEV".

\\d{3}: Theo sau là đúng 3 chữ số (0-9). (Ví dụ: DEV001, DEV999).

$: Kết thúc chuỗi
     */

    int MIN_SALARY = 1000;
    // Theo sau là đúng 3 chữ số (từ 0-9)
    // dấu gạch chéo ngược phải viết hai lần (\\) 
    // để đại diện cho một dấu gạch chéo đơn trong Regex

    String DATE_VALID = "\\d{2}/\\d{2}/\\d{4}";
    String YEAR_VALID = "\\d{4}";

}
