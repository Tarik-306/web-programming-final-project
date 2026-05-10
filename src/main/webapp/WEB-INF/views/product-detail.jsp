<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="partials/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row">
    <div class="col-md-5">
        <c:choose>
            <c:when test="${fn:startsWith(product.imageUrl, 'http')}">
                <img class="img-fluid rounded" src="${product.imageUrl}" onerror="this.src='${pageContext.request.contextPath}/product-image?file=urun-yok.png'">
            </c:when>
            <c:otherwise>
                <img class="img-fluid rounded" src="${pageContext.request.contextPath}/product-image?file=${product.imageUrl}" onerror="this.src='${pageContext.request.contextPath}/product-image?file=urun-yok.png'">
            </c:otherwise>
        </c:choose>
    </div>

    <div class="col-md-7">
        <h2>${product.name}</h2>
        <p>${product.description}</p>
        <p>Kategori: ${product.categoryName}</p>
        <p>Fiyat: <b><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₺"/></b></p>
        <p>Stok: ${product.stock}</p>

        <c:choose>
            <c:when test="${product.stock gt 0}">
                <form method="post" action="cart">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input class="form-control w-25 mb-2" type="number" name="quantity" min="1" max="${product.stock}" value="1">
                    <button class="btn btn-success">Sepete Ekle</button>
                </form>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger">Stokta Yok</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="partials/footer.jsp" %>
