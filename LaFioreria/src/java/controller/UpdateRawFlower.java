package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.FlowerTypeDAO;
import model.FlowerType;
import java.util.List;
import util.Validate;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateRawFlower", urlPatterns = {"/update_flower"})
public class UpdateRawFlower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FlowerTypeDAO ftDAO = new FlowerTypeDAO();
        int flowerId = Integer.parseInt(request.getParameter("flower_id"));
        FlowerType flowerType = ftDAO.getFlowerTypeById(flowerId);
        if (flowerType == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy loại hoa với ID " + flowerId);
            return;
        }
        request.setAttribute("item", flowerType);
        request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Lấy tham số từ form
            String flowerIdStr = request.getParameter("flower_id");
            String flowerName = request.getParameter("flower_name");
            String image = request.getParameter("image");

            // Validate các field
            String flowerNameError = Validate.validateLength(flowerName, "Tên loại hoa", 1, 45);
            String imageError = Validate.validateImageUrl(image);

            // Nếu có lỗi, giữ dữ liệu và hiển thị popup
            if (flowerNameError != null || imageError != null) {
                request.setAttribute("flowerId", flowerIdStr);
                request.setAttribute("flowerName", flowerName);
                request.setAttribute("image", image);
                request.setAttribute("flowerNameError", flowerNameError);
                request.setAttribute("imageError", imageError);
                request.setAttribute("showErrorPopup", true);

                FlowerTypeDAO ftDAO = new FlowerTypeDAO();
                request.setAttribute("item", ftDAO.getFlowerTypeById(Integer.parseInt(flowerIdStr)));
                request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
                return;
            }

            // Xóa các lỗi và dữ liệu trong session nếu validate thành công
            session.removeAttribute("flowerNameError");
            session.removeAttribute("imageUrlError");
            session.removeAttribute("flowerId");
            session.removeAttribute("flowerName");
            session.removeAttribute("image");

            // Chuyển đổi dữ liệu
            int flowerId = Integer.parseInt(flowerIdStr);

            // Lấy thông tin hiện tại của FlowerType để giữ giá trị active
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            FlowerType currentFlowerType = ftDAO.getFlowerTypeById(flowerId);
            if (currentFlowerType == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy loại hoa với ID " + flowerId);
                return;
            }

            // Cập nhật loại hoa, giữ nguyên active
            ftDAO.updateFlowerType(flowerId, flowerName, image, true);

            // Thông báo thành công và chuyển hướng
            session.setAttribute("message", "Cập nhật loại hoa thành công!");
            response.sendRedirect("DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi cập nhật loại hoa: " + e.getMessage());
            request.setAttribute("showErrorPopup", true);
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            request.setAttribute("item", ftDAO.getFlowerTypeById(Integer.parseInt(request.getParameter("flower_id"))));
            request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating Raw Flower information";
    }
}