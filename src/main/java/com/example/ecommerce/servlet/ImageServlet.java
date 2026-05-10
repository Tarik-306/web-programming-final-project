package com.example.ecommerce.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/product-image")
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = req.getParameter("file");
        if (file == null || file.trim().isEmpty()) {
            file = "urun-yok.png";
        }

        // Veritabanında assets/img/telefon.png gibi kayıtlı olsa bile sadece dosya adını alır.
        file = file.replace("\\", "/");
        int slash = file.lastIndexOf('/');
        if (slash >= 0) {
            file = file.substring(slash + 1);
        }

        // Güvenlik: klasör dışına çıkmayı engelle.
        if (!file.matches("[A-Za-z0-9_.-]+") || !(file.endsWith(".png") || file.endsWith(".jpg") || file.endsWith(".jpeg") || file.endsWith(".webp"))) {
            file = "urun-yok.png";
        }

        String resourcePath = "/assets/img/" + file;
        InputStream in = getServletContext().getResourceAsStream(resourcePath);
        if (in == null && !"urun-yok.png".equals(file)) {
            in = getServletContext().getResourceAsStream("/assets/img/urun-yok.png");
            file = "urun-yok.png";
        }
        if (in == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (file.endsWith(".jpg") || file.endsWith(".jpeg")) {
            resp.setContentType("image/jpeg");
        } else if (file.endsWith(".webp")) {
            resp.setContentType("image/webp");
        } else {
            resp.setContentType("image/png");
        }
        resp.setHeader("Cache-Control", "public, max-age=86400");

        try (InputStream input = in; OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = input.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
    }
}
