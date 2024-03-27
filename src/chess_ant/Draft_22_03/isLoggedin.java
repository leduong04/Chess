package chess_ant.Draft_22_03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class isLoggedin {
    public static int isLoggedIn()
    {
        // Xác định đường dẫn đến tệp lưu trữ thông tin đăng nhập
        String filePath = "src\\chess_ant\\Draft_22_03\\login_info.txt";

        try {
            File file = new File(filePath);

            // Kiểm tra xem tệp tồn tại và có thể đọc được không
            if (file.exists() && file.canRead()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                // Đọc dòng đầu tiên từ tệp, đại diện cho userid
                if ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                    // Chuyển đổi dòng đọc được thành số nguyên và trả về
                    return Integer.parseInt(line.trim());
                }

                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException ex) {
            // Xử lý nếu không thể chuyển đổi thành số nguyên
            ex.printStackTrace();
        }

        // Trả về 0 nếu có bất kỳ lỗi nào xảy ra hoặc không tìm thấy thông tin đăng nhập
        return 0;
    }
}
