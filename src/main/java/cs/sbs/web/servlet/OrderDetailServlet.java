package cs.sbs.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        int id = -1;

        // 方法1: 尝试从查询参数获取 (?id=123)
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                out.println("Error: Invalid order ID");
                return;
            }
        }

        // 方法2: 如果查询参数没有，尝试从路径参数获取 (/order/123)
        if (id == -1) {
            String pathInfo = req.getPathInfo();
            if (pathInfo != null && !pathInfo.equals("/")) {
                String idStr = pathInfo.substring(1);
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    out.println("Error: Invalid order ID");
                    return;
                }
            }
        }

        // 如果两种方式都没获取到ID
        if (id == -1) {
            out.println("Error: Order ID is required");
            return;
        }

        // 从 OrderCreateServlet 获取订单列表
        List<Order> orders = OrderCreateServlet.getOrders();

        // 查找订单
        Order order = null;
        for (Order o : orders) {
            if (o.getId() == id) {
                order = o;
                break;
            }
        }

        if (order == null) {
            out.println("Order not found");
            return;
        }

        // 返回订单详情
        out.println("Order Detail");
        out.println();
        out.println("Order ID: " + order.getId());
        out.println("Customer: " + order.getCustomer());
        out.println("Food: " + order.getFood());
        out.println("Quantity: " + order.getQuantity());
    }
}