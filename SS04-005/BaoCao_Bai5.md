# Báo Cáo Phân Tích Thực Hành

## 1. Sơ đồ kiến trúc 3 tầng (Data Flow)
**Client** (Browser/Postman) 
   ↓ HTTP Request (GET, POST, DELETE)
**Controller** (`OrderController`) : Nhận Request ở Endpoint, sử dụng `@ExceptionHandler` để bắt lỗi nhập liệu sai lệch, gọi khối logic của `Service`.
   ↓ Constructor Injection (`@Autowired`)
**Service** (`OrderService`)       : Chứa các biểu thức, thuật toán xử lý logic nghiệp vụ xử lý đơn hàng. Hoạt động nhịp nhàng, làm trung gian kết nối giữa Controller và Repository.
   ↓ Constructor Injection (`@Autowired`)
**Repository** (`OrderRepository`): Trực tiếp tương tác với các nguồn dữ liệu bên trong CSDL bằng cách truy vấn.
   ↓ 
**Database** (hoặc Mock Data)      : Dữ liệu ảo/Thực tế cung cấp về Backend cho Client truy vấn thông tin, huỷ đơn.

## 2. Bẫy dữ liệu ép kiểu (Type Mismatch)
Khi nhân viên vô tình gõ nhầm ID đơn hàng thành nhầm phím chữ (Ví dụ: `/orders/chucai`), Spring MVC sẽ cố gắng ép thuộc tính kiểu chữ là `"chucai"` sang tham số số `Long id` của route `@GetMapping("/{id}")`. Thao tác này sẽ phát sinh về đối tượng Lỗi (Exception) dẫn tới sụp đổ hệ thống (Application Crash). Lỗi gặp ở ngữ cảnh này là: `MethodArgumentTypeMismatchException`. 

*Cách phòng trừ & khắc phục:* 
Chúng ta sử dụng Annotation `@ExceptionHandler(MethodArgumentTypeMismatchException.class)` trực tiếp bên trong cấp độ Controller (hoặc Global bằng `@ControllerAdvice`) để bắt lỗi (catch) kịp thời. Thông qua đó, ngăn ngừa sự sụp đổ của Application Server, và trả về cho Browser thông báo lỗi hợp thị giác như: "ID đơn hàng phải là một số".

## 3. Tính An Toàn (Idempotent) giữa Hành động POST (Tạo Đơn) và DELETE (Hủy Đơn)
- **Phương thức POST (Tạo đơn hàng):** Trong chuẩn hệ sinh thái của Rest, POST không mang tính lũy đẳng (Idempotent). Mỗi Request của POST hoàn toàn độc lập với nhau, mang ý nghĩa sinh ra một bản nguyên mới (New Data Record) trên Hệ thống Backend. Do đó, nếu vì do mạng giật lag mà thiết bị phản hồi kém và nhân viên nhà hàng vô tình bấm tới 3-4 lần liên tục thì ứng dụng sẽ thực hiện 3 hành động tạo đơn mới hoàn toàn tách rời nhau. Điều này sinh ra các đơn hàng lặp trùng lặp (rớt/rác) ngoài ý muốn của Doanh nghiệp.
- **Phương thức DELETE (Hủy đơn hàng):** Ngược lại với POST, trong chuẩn Rest, DELETE là phương thức có tính Idempotent (nhờ vào tính chất idempotent của REST). Việc phát hành request Hủy/Xoá 1 lần hay 3-4 lần ngẫu nhiên lên mặt của Server thì trạng thái Hệ thống không bị thay đổi dư thừa.
    - Lần đầu gửi xoá: hệ thống sẽ thực hiện Hủy đơn. 
    - Các lần thao tác nhầm sau (trong thời gian ngắn): sẽ không phát sinh dữ liệu lỗi (data corrupt/rác), Server chỉ trả về báo hiệu là tài nguyên ID này không tồn tại hoặc đã bị hủy thành công trước đó (Error 404). 
    - Nhờ đó việc bị lag và vô tình nhấn Hủy 3-4 lần vẫn an toàn 100% đối với hệ thống, không tác động tiêu cực đến CSDL của nhà hàng.
