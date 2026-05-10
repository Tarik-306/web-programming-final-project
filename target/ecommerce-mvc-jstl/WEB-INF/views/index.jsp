<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="partials/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Ürünler</h2>

<div class="mb-3">
    <a class="btn btn-outline-primary btn-sm" href="home">Tümü</a>
    <c:forEach var="c" items="${categories}">
        <a class="btn btn-outline-primary btn-sm" href="home?categoryId=${c.id}">${c.name}</a>
    </c:forEach>
</div>

<div class="row g-3">
    <c:forEach var="p" items="${products}">
        <div class="col-md-4">
            <div class="card h-100">
                <c:choose>
                    <c:when test="${fn:startsWith(p.imageUrl, 'http')}">
                        <img src="${p.imageUrl}" class="card-img-top product-img" onerror="this.src='${pageContext.request.contextPath}/product-image?file=urun-yok.png'">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/product-image?file=${p.imageUrl}" class="card-img-top product-img" onerror="this.src='${pageContext.request.contextPath}/product-image?file=urun-yok.png'">
                    </c:otherwise>
                </c:choose>

                <div class="card-body">
                    <h5>${p.name}</h5>
                    <p>${p.description}</p>
                    <p><b><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₺"/></b></p>
                    <c:if test="${p.stock gt 0}"><span class="badge bg-success">Stok: ${p.stock}</span></c:if>
                    <c:if test="${p.stock le 0}"><span class="badge bg-danger">Stokta Yok</span></c:if>
                </div>

                <div class="card-footer d-flex gap-2">
                    <a class="btn btn-info btn-sm" href="product?id=${p.id}">Detay</a>
                    <form method="post" action="cart">
                        <input type="hidden" name="productId" value="${p.id}">
                        <input type="hidden" name="quantity" value="1">
                        <button class="btn btn-success btn-sm" ${p.stock le 0 ? 'disabled' : ''}>Sepete Ekle</button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="partials/footer.jsp" %>
