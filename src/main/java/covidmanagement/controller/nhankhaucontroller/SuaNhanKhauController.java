package covidmanagement.controller.nhankhaucontroller;

import covidmanagement.Main;
import covidmanagement.Utility;
import covidmanagement.controller.hokhaucontroller.ThemHoKhauController;
import covidmanagement.model.HoKhauModel;
import covidmanagement.model.NhanKhauModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SuaNhanKhauController implements Initializable  {
        @FXML
        private Button btnCapNhat;
        @FXML
        private Button btnDong;

        @FXML
        private TextField txtHoVaTen, txtMaNhanKhau, txtTonGiao, txtNguyenQuan, txtNgheNghiep, txtQuocTich,
                txtSDT, txtQuanHeVoiChuHo, txtCMND_CCCD, txtMaHoKhau, txtGhiChu;
        @FXML
        private DatePicker pickerNgaySinh;

        @FXML
        private RadioButton lachuhoco, lachuhokhong, gioitinhnam, gioitinhnu;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                pickerNgaySinh.setConverter(Utility.LOCAL_DATE_CONVERTER);
        }

        public void setField(int maNhanKhau, String hoVaTen, String gioiTinh, LocalDate ngaySinh, String cmnd_CCCD_,
                             String quocTich, String tonGiao, String sDT, String nguyenQuan, String ngheNghiep,
                             int maHoKhau, Boolean laChuHo, String quanHeVoiChuHo, String ghiChu){

                txtMaNhanKhau.setText(String.valueOf(maNhanKhau));
                txtMaNhanKhau.setDisable(true);
                txtHoVaTen.setText(hoVaTen);

                if (gioiTinh.equalsIgnoreCase("Nam")) gioitinhnam.setSelected(true);
                if (gioiTinh.equalsIgnoreCase("Nữ"))gioitinhnu.setSelected(true);

                pickerNgaySinh.setValue(ngaySinh);
                txtCMND_CCCD.setText(cmnd_CCCD_);
                txtQuocTich.setText(quocTich);
                txtTonGiao.setText(tonGiao);
                txtSDT.setText(sDT);
                txtNguyenQuan.setText(nguyenQuan);
                txtNgheNghiep.setText(ngheNghiep);
                txtMaHoKhau.setText(String.valueOf(maHoKhau));

                if (laChuHo) lachuhoco.setSelected(true);
                else lachuhokhong.setSelected(true);

                txtQuanHeVoiChuHo.setText(quanHeVoiChuHo);
                txtGhiChu.setText(ghiChu);
        }

        @FXML
        void suaActionevent(ActionEvent event) throws SQLException {
                if (txtHoVaTen.getText().isBlank()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Trường Họ Và Tên không được để trống!");
                        alert.show();
                        return;
                }else if (pickerNgaySinh.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Trường Ngày Sinh không được để trống!");
                        alert.show();
                        return;
                }else if (pickerNgaySinh.getValue().isAfter(LocalDate.now())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Vui lòng nhập lại ngày sinh!");
                        alert.show();
                        return;
                }else if (!gioitinhnam.isSelected() && !gioitinhnu.isSelected()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Trường Giới Tính không được để trống!");
                        alert.show();
                        return;
                } else if (!txtCMND_CCCD.getText().isBlank() && !txtCMND_CCCD.getText().matches("[0-9]+")) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Trường CMND_CCCD chỉ được chứa chữ số!");
                        alert.show();
                        return;
                }else if (txtQuocTich.getText().isBlank()){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Trường Quốc Tịch không được để trống!");
                        alert.show();
                        return;
                }else if (!txtSDT.getText().isBlank()) {
                        if (!txtSDT.getText().matches("[0-9]+")) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Trường Số Điện Thoại chỉ được chứa chữ số!");
                                alert.show();
                                return;
                        }
                }else if (txtMaHoKhau.getText().isBlank()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Trường Mã Hộ Khẩu không được để trống!");
                        alert.show();
                        return;
                }else if (!txtMaHoKhau.getText().matches("[0-9]+")) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Trường Mã Hộ Khẩu chỉ được chứa chữ số!");
                        alert.show();
                        return;
                }
                if (lachuhokhong.isSelected() && txtQuanHeVoiChuHo.getText().isBlank()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Vui lòng nhập trường Quan Hệ Với Chủ Hộ!");
                        alert.show();
                        return;
                }
                if (lachuhokhong.isSelected() && txtQuanHeVoiChuHo.getText().equalsIgnoreCase("Chủ hộ")) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Vui lòng điền lại trường quan hệ với chủ hộ!!");
                        alert.show();
                        return;
                }
                if (lachuhoco.isSelected() && !txtQuanHeVoiChuHo.getText().isBlank()){
                        if (!txtQuanHeVoiChuHo.getText().equalsIgnoreCase("Chủ hộ"))
                        {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Vui lòng điền lại trường quan hệ với chủ hộ!!");
                                alert.show();
                                return;
                        }
                }
                int maNhanKhau = Integer.parseInt(txtMaNhanKhau.getText());
                String hoVaTen = txtHoVaTen.getText();

                String gioiTinh;
                if (gioitinhnam.isSelected()) {
                        gioiTinh = "Nam";
                } else {
                        gioiTinh = "Nữ";
                }

                LocalDate ngaySinh = pickerNgaySinh.getValue();
                String cmnd_CCCD_ = txtCMND_CCCD.getText();
                String quocTich = txtQuocTich.getText();
                String tonGiao = txtTonGiao.getText();
                String sDT = txtSDT.getText();
                String nguyenQuan = txtNguyenQuan.getText();
                String ngheNghiep = txtNgheNghiep.getText();
                String ghiChu = txtGhiChu.getText();

//                try{
//                        Integer.parseInt(txtMaHoKhau.getText());
//                }catch(NumberFormatException e){
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setHeaderText("Lỗi: " + e.getMessage() + "." + "Mã nhân khẩu không được để trống!");
//                        alert.show();
//                }
                int maHoKhau = Integer.parseInt(txtMaHoKhau.getText());

                String quanHeVoiChuHo;
                // TODO
                Boolean laChuHo;
                if (lachuhoco.isSelected()) {
                        laChuHo = true;
                        quanHeVoiChuHo = "Chủ hộ";

                } else {
                        laChuHo = false;
                        quanHeVoiChuHo = txtQuanHeVoiChuHo.getText();
                }
                if (!HoKhauModel.isExistedHoKhau(maHoKhau)){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Mã hộ khẩu chưa tồn tại trong dữ liệu. Bạn có muốn tạo mới?");
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.OK){
                                try {
                                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hokhau/themhokhau-view.fxml"));
                                        Scene scene = new Scene(fxmlLoader.load());
                                        Stage stage = new Stage();
                                        ThemHoKhauController controller = fxmlLoader.getController();
                                        controller.setMaHoKhau(maHoKhau);
                                        stage.setTitle("Thêm hộ khẩu mới");
                                        stage.setScene(scene);
                                        stage.showAndWait();
                                } catch (IOException e){
                                        e.printStackTrace();
                                        return;
                                }
                        } else {
                                return;
                        }
                }
                NhanKhauModel.updateNhanKhau(maNhanKhau, hoVaTen, gioiTinh, ngaySinh, cmnd_CCCD_, quocTich, tonGiao,
                                        sDT, nguyenQuan, ngheNghiep, maHoKhau, laChuHo, quanHeVoiChuHo, ghiChu);
        }

        @FXML
        void dongActionevent(ActionEvent event) {
            Stage stage = (Stage) btnDong.getScene().getWindow();
            stage.close();
        }
}

