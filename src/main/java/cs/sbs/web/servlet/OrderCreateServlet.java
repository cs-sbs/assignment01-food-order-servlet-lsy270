package cs.sbs.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OrderCreateServlet extends HttpServlet {

    private static List<Order> orders = new ArrayList<>();
    private static int nextId = 1000;  //

    // 提供一个公开的静态方法让其他类访问订单列表
    public static List<Order> getOrders() {
        return orders;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int id) {
        nextId = id;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String customer = req.getParameter("customer");
        String food = req.getParameter("food");
        String quantityStr = req.getParameter("quantity");

        // 验证参数
        if (customer == null || customer.trim().isEmpty()) {
            out.println("Error: customer name is required");
            return;
        }
        if (food == null || food.trim().isEmpty()) {
            out.println("Error: food name is required");
            return;
        }
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            out.println("Error: quantity is required");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                out.println("Error: quantity must be a positive number");
                return;
            }
        } catch (NumberFormatException e) {
            out.println("Error: quantity must be a valid number");
            return;
        }

        // 创建订单
        Order order = new Order();
        order.setId(nextId++);
        order.setCustomer(customer.trim());
        order.setFood(food.trim());
        order.setQuantity(quantity);
        orders.add(order);

        // 返回订单ID
        out.println("Order Created: " + order.getId());
    }
}