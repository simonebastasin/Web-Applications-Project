<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.lang.*" %>
<%
    LocalDateTime now = LocalDateTime.now();

    int year = now.getYear();

%>

<footer class="bg-dark bg-gradient skyline text-white">
    <div class="container footer-container">
        <p class="p-3 m-0">
            Electromechanics Shop &copy; <%= year%>
        </p>
        <p class="p-3 m-0">
            Made with <i class="fa-solid fa-heart"></i> by S. Bastasin, S. Bortolin, G. Ceccon, D. Colussi, G. Czaczkes, M. Lando, G. Nicoletti, A. Pastore, M. Pozzer
        </p>
    </div>
</footer>
<script>const rootPath = '${pageContext.request.contextPath}'</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
<script src="<c:url value="/js/script.js"/>"></script>