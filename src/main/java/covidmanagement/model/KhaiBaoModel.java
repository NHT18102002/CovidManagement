package covidmanagement.model;

import covidmanagement.Main;
import covidmanagement.Utility;
import covidmanagement.controller.khaibaocontroller.SuaKhaiBaoController;
import covidmanagement.controller.khaibaocontroller.XemKhaiBaoController;
import covidmanagement.database.QueryDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KhaiBaoModel {
    public String getTen() {
        return ten;
    }

    private String ten;
    private int maKhaiBao;

    private int maNhanKhau;
    //private static KhaiBaoModel.gioiTinh gioiTinh;
    private String diemKhaiBao;
    private LocalDate ngayKhaiBao;
    private boolean BHYT;
    private String lichTrinh;
    private boolean trieuChung;
    private boolean tiepXucNguoiBenh;
    public boolean tiepXucNguoiTuVungDich;
    public boolean tiepXucNguoiCoBieuHien;
    public String benhNen;
    public Button changeButton;
    public Button deleteButton;
    public Button viewButton;

    public int getMaKhaiBao() {
        return maKhaiBao;
    }
    public int getMaNhanKhau() {return maNhanKhau;}
    public String getDiemKhaiBao() {
        return diemKhaiBao;
    }

    public LocalDate getNgayKhaiBao() {
        return ngayKhaiBao;
    }


    public boolean isBHYT() {
        return BHYT;
    }

    public String getLichTrinh() {
        return lichTrinh;
    }

    public boolean isTrieuChung() {
        return trieuChung;
    }

    public boolean isTiepXucNguoiBenh() {
        return tiepXucNguoiBenh;
    }

    public boolean isTiepXucNguoiTuVungDich() {
        return tiepXucNguoiTuVungDich;
    }

    public boolean isTiepXucNguoiCoBieuHien() {
        return tiepXucNguoiCoBieuHien;
    }

    public String getBenhNen() {
        return benhNen;
    }

    public Button getChangeButton() {
        return changeButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
    public Button getViewButton() {
        return viewButton;
    }

    public KhaiBaoModel(int maKhaiBao, int maNhanKhau, String diemkhaibao, LocalDate ngayKhaiBao,
                        boolean bhyt, String lichTrinh, boolean trieuchung, boolean tiepXucNguoiBenh,
                        boolean tiepXucNguoiTuVungDich, boolean tiepXucNguoiCoBieuHien, String benhNen) {
        this.maKhaiBao = maKhaiBao;
        this.maNhanKhau = maNhanKhau;
        this.diemKhaiBao = diemkhaibao;
        this.ngayKhaiBao = ngayKhaiBao;
        BHYT = bhyt;
        this.lichTrinh = lichTrinh;
        this.trieuChung = trieuchung;
        this.tiepXucNguoiBenh = tiepXucNguoiBenh;
        this.tiepXucNguoiTuVungDich = tiepXucNguoiTuVungDich;
        this.tiepXucNguoiCoBieuHien = tiepXucNguoiCoBieuHien;
        this.benhNen = benhNen;
        this.changeButton = new Button("Sửa");
        this.deleteButton = new Button("Xóa");
        this.viewButton = new Button("Xem");
        changeButton.setOnAction(this::handleChangeClick);
        deleteButton.setOnAction(this::handleDeleteClick);
        viewButton.setOnAction(this::handleViewClick);
        setName(maNhanKhau);
    }

    private void handleDeleteClick(ActionEvent actionEvent) {
        Utility.displayConfirmDeleteDialog("Bạn muốn xóa khai báo này không?", maKhaiBao, "Khaibao");
        LichSuModel.add("Xóa khai báo số " + maKhaiBao);
    }

    private void handleChangeClick(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("khaibao/suakhaibao-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Chỉnh sửa thông tin khai báo y tế");
            stage.show();
            SuaKhaiBaoController mainController = fxmlLoader.getController();
            mainController.setField(maKhaiBao ,diemKhaiBao, ngayKhaiBao, maNhanKhau, lichTrinh,
                    BHYT, trieuChung, tiepXucNguoiBenh, tiepXucNguoiTuVungDich,
                    tiepXucNguoiCoBieuHien, benhNen);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    private void handleViewClick(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("khaibao/xemkhaibao-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
//            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Chi tiết thông tin khai báo y tế");
            stage.show();
            XemKhaiBaoController mainController = fxmlLoader.getController();
            mainController.setField(diemKhaiBao, ngayKhaiBao, maNhanKhau, lichTrinh,
                    BHYT, trieuChung, tiepXucNguoiBenh, tiepXucNguoiTuVungDich,
                    tiepXucNguoiCoBieuHien, benhNen);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static List<KhaiBaoModel> getKhaiBaoList(){
        List<KhaiBaoModel> queryList = new ArrayList<>();
        try {
            QueryDB queryDB = new QueryDB();
            String sql = "select * from khaibao;";

            ResultSet rs = queryDB.executeQuery(sql);
            while (rs.next()) {
                int _maKB = rs.getInt("makhaibao");
                int _maNK = rs.getInt("manhankhau");
                String _diemKB = rs.getString("diemkhaibao");
                LocalDate _ngayKB = rs.getDate("ngaykhaibao").toLocalDate();
                boolean _BHYT = rs.getBoolean("BHYT");
                String _lichtrinh = rs.getString("lichtrinh");
                boolean _trieuchung = rs.getBoolean("trieuchung");
                boolean _tiepxucnguoibenh = rs.getBoolean("tiepxucnguoibenh");
                boolean _tiepxucnguoituvungdich = rs.getBoolean("tiepxucnguoituvungdich");
                boolean _tiepxucnguoicobieuhien = rs.getBoolean("tiepxucnguoicobieuhien");
                String _benhnen = rs.getString("benhnen");
                queryList.add(new KhaiBaoModel(_maKB, _maNK, _diemKB, _ngayKB, _BHYT, _lichtrinh, _trieuchung,
                        _tiepxucnguoibenh, _tiepxucnguoituvungdich, _tiepxucnguoicobieuhien, _benhnen));
            }
            rs.close();
            queryDB.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return queryList;
    }
    public static void add(int maNhanKhau, String diemkhaibao, LocalDate ngayKhaiBao,
                           boolean bhyt, String lichTrinh, boolean trieuchung, boolean tiepXucNguoiBenh,
                           boolean tiepXucNguoiTuVungDich, boolean tiepXucNguoiCoBieuHien, String benhNen) throws SQLException{
        QueryDB queryDB = new QueryDB();
        PreparedStatement statement = queryDB.getConnection().prepareStatement(
                "INSERT INTO KhaiBao(maNhanKhau, diemKhaiBao, ngayKhaiBao, BHYT, lichTrinh,  trieuChung, tiepXucNguoiBenh, tiepXucNguoiTuVungDich, tiepXucNguoiCoBieuHien, benhNen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
        );
        statement.setInt(1, maNhanKhau);
        statement.setString(2, diemkhaibao);
        statement.setDate(3, Date.valueOf(ngayKhaiBao));
        statement.setBoolean(4, bhyt);
        statement.setString(5, lichTrinh);
        statement.setBoolean(6, trieuchung);
        statement.setBoolean(7, tiepXucNguoiBenh);
        statement.setBoolean(8, tiepXucNguoiTuVungDich);
        statement.setBoolean(9, tiepXucNguoiCoBieuHien);
        statement.setString(10, benhNen);
        statement.executeUpdate();
        statement.close();
        queryDB.close();

        LichSuModel.add("Thêm một khai báo");
    }
    public static void update(int maKhaiBao, String diemkhaibao, LocalDate ngayKhaiBao,
                           boolean bhyt, String lichTrinh, boolean trieuchung, boolean tiepXucNguoiBenh,
                           boolean tiepXucNguoiTuVungDich, boolean tiepXucNguoiCoBieuHien, String benhNen) throws SQLException{
        QueryDB queryDB = new QueryDB();
        PreparedStatement statement = queryDB.getConnection().prepareStatement(
                "UPDATE KhaiBao SET (diemKhaiBao, ngayKhaiBao, BHYT, lichTrinh,  trieuChung, tiepXucNguoiBenh, tiepXucNguoiTuVungDich, tiepXucNguoiCoBieuHien, benhNen) = (?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE makhaibao = ?;"
        );
        statement.setString(1, diemkhaibao);
        statement.setDate(2, Date.valueOf(ngayKhaiBao));
        statement.setBoolean(3, bhyt);
        statement.setString(4, lichTrinh);
        statement.setBoolean(5, trieuchung);
        statement.setBoolean(6, tiepXucNguoiBenh);
        statement.setBoolean(7, tiepXucNguoiTuVungDich);
        statement.setBoolean(8, tiepXucNguoiCoBieuHien);
        statement.setString(9, benhNen);
        statement.setInt(10, maKhaiBao);
        statement.executeUpdate();
        statement.close();
        queryDB.close();

        LichSuModel.add("Sửa đổi thông tin khai báo số " + maKhaiBao);
    }

    public static void deleteKhaiBao(int maNhanKhau) throws SQLException {
        PreparedStatement preparedStatement = null;
        QueryDB queryDB = null;
        try {
            queryDB = new QueryDB();
            preparedStatement = queryDB.getConnection().prepareStatement(
                    "DELETE FROM khaibao WHERE MaNhankhau = ?;");
            preparedStatement.setInt(1, maNhanKhau);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            queryDB.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Lỗi khi Xóa dữ liệu: " + e.getMessage() + "." + "Vui lòng thực hiện lại!!");
            alert.show();
        }
    }

    private void setName(int maNK) {
        try {
            NhanKhauModel nhanKhau = NhanKhauModel.getInstanceById(maNK);
            this.ten = nhanKhau.getHoTen();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
