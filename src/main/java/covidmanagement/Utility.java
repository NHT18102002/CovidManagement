package covidmanagement;

import covidmanagement.database.QueryDB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

public class Utility {
    public static final StringConverter<LocalDate> LOCAL_DATE_CONVERTER = new StringConverter<>() {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        @Override
        public String toString(LocalDate localDate) {
            if (localDate == null) return "";
            return FORMATTER.format(localDate);
        }

        @Override
        public LocalDate fromString(String s) {
            if (s.isBlank()) return null;
            return LocalDate.parse(s, FORMATTER);
        }
    };

    public static final StringConverter<Date> DATE_CONVERTER = new StringConverter<>() {
        private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        @Override
        public String toString(Date date) {
            if (date == null) return "";
            return formatter.format(date);
        }

        @Override
        public Date fromString(String s) {
            if (s.isBlank()) return null;
            try {
                return formatter.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    //chuyển từ có dấu sang không dấu không cách
    public static String removeAccent(String s){
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace(" ", "");
    }
    //hiển thị hộp thông báo lỗi
    public static void displayExceptionDialog(Exception e){
        e.printStackTrace();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    //hộp tin xác nhận xóa hàng có chỉ số id trong bảng tableName trong cơ sở dữ liệu
    public static void displayConfirmDeleteDialog(String message, int id, String tableName){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setHeaderText(message);

        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK){
            //remove row id in tableName
            String sql = "DELETE FROM " + tableName + " WHERE ma" + tableName + " = " + id + ";";
            try {
                QueryDB queryDB = new QueryDB();
                queryDB.executeUpdate(sql);
                displaySuccessDialog("Đã xóa thành công!");
            } catch (SQLException e){
                e.printStackTrace();
                Utility.displayWarningDialog("Hộ khẩu đang chứa nhân khẩu. Hãy thay đổi mã hộ khẩu của các nhân khẩu trong hộ trước");
            }
        }
    }
    // hiển thị hộp thông báo thành công
    public static void displaySuccessDialog(String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.show();
    }
    // hiển thị hộp cảnh báo
    public static void displayWarningDialog(String message){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(message);
        alert.show();
    }
}
