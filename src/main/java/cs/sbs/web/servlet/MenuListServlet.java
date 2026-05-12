package cs.sbs.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MenuListServlet extends HttpServlet {

    private static List<MenuItem> menu = new ArrayList<>();

    static {
        menu.add(new MenuItem(1, "Fried Rice", 8.0));
        menu.add(new MenuItem(2, "Fried Noodles", 9.0));
        menu.add(new MenuItem(3, "Burger", 10.0));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String searchName = req.getParameter("name");

        out.println("Menu List:");

        if (searchName == null || searchName.trim().isEmpty()) {
            // 返回所有菜单
            for (MenuItem item : menu) {
                out.println(item.getId() + ". " + item.getName() + " - $" + item.getPrice());
            }
        } else {
            // 按名称搜索（不区分大小写，包含匹配）
            boolean found = false;
            for (MenuItem item : menu) {
                if (item.getName().toLowerCase().contains(searchName.toLowerCase())) {
                    out.println(item.getId() + ". " + item.getName() + " - $" + item.getPrice());
                    found = true;
                }
            }
            if (!found) {
                out.println("No menu item found with name containing: " + searchName);
            }
        }
    }
}